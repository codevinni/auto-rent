package br.autorent.managedbeans;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.autorent.enums.VehicleStatus;
import br.autorent.model.Accessory;
import br.autorent.model.Agency;
import br.autorent.model.Vehicle;
import br.autorent.sessionbeans.interfaces.IAccessory;
import br.autorent.sessionbeans.interfaces.IAgency;
import br.autorent.sessionbeans.interfaces.IRentalProcess;
import br.autorent.sessionbeans.interfaces.IVehicle;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named("rentalProcessWebMB")
@SessionScoped
public class RentalProcessWebMB implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private IRentalProcess rentalProcessBean;

	@EJB
	private IVehicle vehicleBean;

	@EJB
	private IAgency agencyBean;

	@EJB
	private IAccessory accessoryBean;

	@Inject
	private AuthMB authMB;

	private Long vehicleId;
	private Vehicle vehicle;
	private Long pickupAgencyId;
	private Long returnAgencyId;
	private LocalDate pickupDate;
	private LocalDate expectedReturnDate;
	private List<Long> selectedAccessoryIds = new ArrayList<>();
	private Double estimatedTotal = 0.0;

	private List<Agency> agencies;
	private List<Accessory> availableAccessories;

	public RentalProcessWebMB() {}

	public String startFlow(Long selectedVehicleId) {
		FacesContext context = FacesContext.getCurrentInstance();

		if (!authMB.isAuthenticated()) {
			context.getExternalContext().getFlash().setKeepMessages(true);
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Você precisa estar logado para realizar uma reserva ou locação.", null));
			return "login.xhtml?faces-redirect=true";
		}

		this.vehicleId = selectedVehicleId;
		this.vehicle = vehicleBean.searchByPrimaryKey(selectedVehicleId);

		if (this.vehicle == null) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Veículo não encontrado.", null));
			return null;
		}

		if (this.vehicle.getStatus() != VehicleStatus.AVAILABLE) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Este veículo não está disponível no momento.", null));
			return null;
		}

		this.agencies = agencyBean.listAll();
		this.availableAccessories = accessoryBean.listAll();
		this.selectedAccessoryIds.clear();

		if (vehicle.getAgency() != null) {
			this.pickupAgencyId = vehicle.getAgency().getId();
			this.returnAgencyId = vehicle.getAgency().getId();
		}

		this.pickupDate = LocalDate.now();
		this.expectedReturnDate = LocalDate.now().plusDays(1);

		syncProcessBeanState();
		this.estimatedTotal = rentalProcessBean.calculateTotalValue();

		return "rental.xhtml?faces-redirect=true";
	}

	public void calculateTotal() {
		syncProcessBeanState();
		this.estimatedTotal = rentalProcessBean.calculateTotalValue();
	}

	private void syncProcessBeanState() {
		rentalProcessBean.setCustomer(authMB.getLoggedUser());
		rentalProcessBean.setVehicle(vehicle);

		if (pickupAgencyId != null) {
			Agency pickupAgency = agencyBean.searchByPrimaryKey(pickupAgencyId);
			rentalProcessBean.setPickupAgency(pickupAgency);
		}

		if (returnAgencyId != null) {
			Agency returnAgency = agencyBean.searchByPrimaryKey(returnAgencyId);
			rentalProcessBean.setReturnAgency(returnAgency);
		}

		rentalProcessBean.setPeriod(pickupDate, expectedReturnDate);

		// Sincroniza acessórios
		List<Accessory> currentAccessories = new ArrayList<>(rentalProcessBean.getSelectedAccessories());
		for (Accessory a : currentAccessories) {
			rentalProcessBean.removeAccessory(a);
		}

		if (selectedAccessoryIds != null && !selectedAccessoryIds.isEmpty() && availableAccessories != null) {
			for (Accessory acc : availableAccessories) {
				if (selectedAccessoryIds.contains(acc.getId())) {
					rentalProcessBean.addAccessory(acc);
				}
			}
		}
	}

	public String confirm(boolean asReservation) {
		FacesContext context = FacesContext.getCurrentInstance();

		if (pickupAgencyId == null || returnAgencyId == null) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Selecione as agências de retirada e devolução.", null));
			return null;
		}

		if (pickupDate == null || expectedReturnDate == null) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Defina o período de locação.", null));
			return null;
		}

		if (expectedReturnDate.isBefore(pickupDate)) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "A data de devolução não pode ser anterior à retirada.", null));
			return null;
		}

		syncProcessBeanState();

		try {
			if (asReservation) {
				rentalProcessBean.confirmReservation();
				context.getExternalContext().getFlash().setKeepMessages(true);
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Reserva confirmada com sucesso!", null));
			} else {
				rentalProcessBean.confirmRental();
				context.getExternalContext().getFlash().setKeepMessages(true);
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Locação realizada com sucesso!", null));
			}
			resetState();
			return "my-rentals.xhtml?faces-redirect=true";
		} catch (Exception e) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao confirmar: " + e.getMessage(), null));
			return null;
		}
	}

	public String cancel() {
		rentalProcessBean.cancel();
		resetState();
		return "vehicles.xhtml?faces-redirect=true";
	}

	private void resetState() {
		this.vehicleId = null;
		this.vehicle = null;
		this.pickupAgencyId = null;
		this.returnAgencyId = null;
		this.pickupDate = null;
		this.expectedReturnDate = null;
		this.selectedAccessoryIds.clear();
		this.estimatedTotal = 0.0;
	}

	public Long getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(Long vehicleId) {
		this.vehicleId = vehicleId;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public Long getPickupAgencyId() {
		return pickupAgencyId;
	}

	public void setPickupAgencyId(Long pickupAgencyId) {
		this.pickupAgencyId = pickupAgencyId;
	}

	public Long getReturnAgencyId() {
		return returnAgencyId;
	}

	public void setReturnAgencyId(Long returnAgencyId) {
		this.returnAgencyId = returnAgencyId;
	}

	public LocalDate getPickupDate() {
		return pickupDate;
	}

	public void setPickupDate(LocalDate pickupDate) {
		this.pickupDate = pickupDate;
	}

	public LocalDate getExpectedReturnDate() {
		return expectedReturnDate;
	}

	public void setExpectedReturnDate(LocalDate expectedReturnDate) {
		this.expectedReturnDate = expectedReturnDate;
	}

	public List<Long> getSelectedAccessoryIds() {
		return selectedAccessoryIds;
	}

	public void setSelectedAccessoryIds(List<Long> selectedAccessoryIds) {
		this.selectedAccessoryIds = selectedAccessoryIds;
	}

	public Double getEstimatedTotal() {
		return estimatedTotal;
	}

	public void setEstimatedTotal(Double estimatedTotal) {
		this.estimatedTotal = estimatedTotal;
	}

	public List<Agency> getAgencies() {
		return agencies;
	}

	public List<Accessory> getAvailableAccessories() {
		return availableAccessories;
	}

}
