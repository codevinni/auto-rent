package br.autorent.sessionbeans.stateful;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import br.autorent.enums.RentalStatus;
import br.autorent.enums.VehicleStatus;
import br.autorent.model.Accessory;
import br.autorent.model.Agency;
import br.autorent.model.Rental;
import br.autorent.model.User;
import br.autorent.model.Vehicle;
import br.autorent.sessionbeans.interfaces.IFleetStatus;
import br.autorent.sessionbeans.interfaces.IRental;
import br.autorent.sessionbeans.interfaces.IRentalProcess;
import br.autorent.sessionbeans.interfaces.IVehicle;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateful;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

/**
 * EJB Stateful que gerencia o fluxo completo de locação/reserva de veículos.
 * Mantém o estado da locação em construção durante a sessão do cliente.
 * Ao confirmar, persiste a locação e atualiza o status do veículo.
 */
@Stateful
public class RentalProcessBean implements IRentalProcess {

	@PersistenceContext(unitName = "autorent")
    private EntityManager em;

	@EJB
    private IVehicle vehicleBean;

    @EJB
    private IRental rentalBean;
	
    @EJB
    private IFleetStatus fleetStatus;
	
	private Rental rental;
	private List<Accessory> selectedAccessories;
	
	/** Inicializa o bean com uma locação vazia e lista de acessórios vazia. */
	public RentalProcessBean() {
		rental = new Rental();
		selectedAccessories = new ArrayList<Accessory>();
	}
	
	/** {@inheritDoc} */
	@Override
	public User getCustomer() {
		return rental.getUser();
	}

	/** {@inheritDoc} */
	@Override
	public void setCustomer(User user) {
		rental.setUser(user);
	}

	/** {@inheritDoc} */
	@Override
	public Agency getPickupAgency() {
		return rental.getPickupAgency();
	}
	
	/** {@inheritDoc} */
	@Override
	public void setPickupAgency(Agency pickupAgency) {
		rental.setPickupAgency(pickupAgency);
	}

	/** {@inheritDoc} */
	@Override
	public Agency getReturnAgency() {
		return rental.getReturnAgency();
	}
	
	/** {@inheritDoc} */
	@Override
	public void setReturnAgency(Agency returnAgency) {
		rental.setReturnAgency(returnAgency);
	}

	/** {@inheritDoc} */
	@Override
	public Vehicle getVehicle() {
		return rental.getVehicle();
	}
	
	/** {@inheritDoc} */
	@Override
	public void setVehicle(Vehicle vehicle) {
		rental.setVehicle(vehicle);
	}

	/** {@inheritDoc} */
	@Override
	public LocalDate getPickupDate() {
		return rental.getPickupDate();
	}

	/** {@inheritDoc} */
	@Override
	public LocalDate getExpectedReturnDate() {
		return rental.getExpectedReturnDate();
	}
	
	/** {@inheritDoc} */
	@Override
	public void setPeriod(LocalDate pickupDate, LocalDate expectedReturnDate) {
		rental.setPickupDate(pickupDate);
		rental.setExpectedReturnDate(expectedReturnDate);
	}

	/** {@inheritDoc} */
	@Override
	public void addAccessory(Accessory accessory) {
		selectedAccessories.add(accessory);
	}

	/** {@inheritDoc} */
	@Override
	public void removeAccessory(Accessory accessory) {
		selectedAccessories.removeIf(a -> a.getId().equals(accessory.getId()));
	}

	/** {@inheritDoc} */
	@Override
	public List<Accessory> getSelectedAccessories() {
		return selectedAccessories;
	}

	/**
	 * {@inheritDoc}
	 * Soma a diária do veículo com as diárias dos acessórios, multiplicado pelo número de dias.
	 * Retorna 0 se veículo ou datas estiverem indefinidos. Mínimo de 1 diária.
	 */
	@Override
	public Double calculateTotalValue() {

		Vehicle vehicle = rental.getVehicle();
		LocalDate pickupDate = rental.getPickupDate();
		LocalDate expectedReturnDate = rental.getExpectedReturnDate();
		
        if (vehicle == null || pickupDate == null || expectedReturnDate == null)
            return 0D;
        
        long days = ChronoUnit.DAYS.between(pickupDate, expectedReturnDate);
        
        if (days <= 0) 
            days = 1; 

        double dailyTotal = vehicle.getDailyPrice();
        
        for (Accessory a : selectedAccessories) 
            dailyTotal += a.getDailyPrice();

        return dailyTotal * days;
	}

	/**
	 * {@inheritDoc}
	 * Marca o veículo como {@code RENTED} e a locação como {@code IN_PROGRESS}.
	 * Atualiza o cache de frota e reinicia o estado do bean.
	 */
	@Override
	public Rental confirmRental() {
		
		if (rental.getVehicle() == null || rental.getUser() == null)
            throw new IllegalStateException("Cliente e veículo devem ser selecionados.");
        

        Vehicle currentVehicle = vehicleBean.searchByPrimaryKey(rental.getVehicle().getId());
        
        if (currentVehicle == null || currentVehicle.getStatus() != VehicleStatus.AVAILABLE) 
            throw new IllegalStateException("O veículo selecionado não está mais disponível.");
        
        
        currentVehicle.setStatus(VehicleStatus.RENTED);
        currentVehicle.setAccessories(selectedAccessories);
        currentVehicle = vehicleBean.update(currentVehicle);

        rental.setVehicle(currentVehicle);
        rental.setTotalPrice(calculateTotalValue());
        rental.setStatus(RentalStatus.IN_PROGRESS);
        
        rentalBean.create(rental);
        fleetStatus.refreshCache();

        Rental confirmed = this.rental;
        
        cancel();

        return confirmed;
	}

	/**
	 * {@inheritDoc}
	 * Marca o veículo como {@code RESERVED} e a locação como {@code RESERVED}.
	 * Atualiza o cache de frota e reinicia o estado do bean.
	 */
	@Override
	public Rental confirmReservation() {
		
		if (rental.getVehicle() == null || rental.getUser() == null)
            throw new IllegalStateException("Cliente e veículo devem ser selecionados.");
        
        Vehicle currentVehicle = vehicleBean.searchByPrimaryKey(rental.getVehicle().getId());
        
        if (currentVehicle == null || currentVehicle.getStatus() != VehicleStatus.AVAILABLE) 
            throw new IllegalStateException("O veículo selecionado não está mais disponível.");
        
        currentVehicle.setStatus(VehicleStatus.RESERVED);
        currentVehicle.setAccessories(selectedAccessories);
        currentVehicle = vehicleBean.update(currentVehicle);

        rental.setVehicle(currentVehicle);
        rental.setTotalPrice(calculateTotalValue());
        rental.setStatus(RentalStatus.RESERVED);
        
        rentalBean.create(rental);
        fleetStatus.refreshCache();

        Rental confirmed = this.rental;
        
        cancel();

        return confirmed;
	}

	/** {@inheritDoc} */
	@Override
	public Rental getRental() {
		return rental;
	}
	
	/** {@inheritDoc} */
	@Override
	public void cancel() {
		rental = new Rental();
		selectedAccessories = new ArrayList<Accessory>();
	}	
}