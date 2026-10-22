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
	
	public RentalProcessBean() {
		rental = new Rental();
		selectedAccessories = new ArrayList<Accessory>();
	}
	
	@Override
	public User getCustomer() {
		return rental.getUser();
	}

	@Override
	public void setCustomer(User user) {
		rental.setUser(user);
	}

	@Override
	public void setPickupAgency(Agency pickupAgency) {
		rental.setPickupAgency(pickupAgency);
	}

	@Override
	public void selectReturnAgency(Agency returnAgency) {
		rental.setReturnAgency(returnAgency);
	}

	@Override
	public void setVehicle(Vehicle vehicle) {
		rental.setVehicle(vehicle);
	}

	@Override
	public void setPeriod(LocalDate pickupDate, LocalDate expectedReturnDate) {
		rental.setPickupDate(pickupDate);
		rental.setExpectedReturnDate(expectedReturnDate);
	}

	@Override
	public void addAccessory(Accessory accessory) {
		selectedAccessories.add(accessory);
	}

	@Override
	public void removeAccessory(Accessory accessory) {
		selectedAccessories.removeIf(a -> a.getId().equals(accessory.getId()));
	}

	@Override
	public List<Accessory> getSelectedAccessories() {
		return selectedAccessories;
	}

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

	@Override
	public void cancel() {
		rental = new Rental();
		selectedAccessories = new ArrayList<Accessory>();
	}
	
}