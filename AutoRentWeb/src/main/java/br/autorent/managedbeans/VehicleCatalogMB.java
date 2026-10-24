package br.autorent.managedbeans;

import java.io.Serializable;
import java.util.List;

import br.autorent.enums.VehicleCategory;
import br.autorent.enums.VehicleStatus;
import br.autorent.model.Agency;
import br.autorent.model.Vehicle;
import br.autorent.sessionbeans.interfaces.IAgency;
import br.autorent.sessionbeans.interfaces.IVehicle;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

@Named("vehicleCatalogMB")
@RequestScoped
public class VehicleCatalogMB implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private IVehicle vehicleBean;

	@EJB
	private IAgency agencyBean;

	private Long selectedVehicleId;
	private Long selectedAgencyId;
	private VehicleCategory selectedCategory;
	private VehicleStatus selectedStatus;
	private Vehicle selectedVehicle;
	private List<Vehicle> vehicles;
	private List<Agency> agencies;

	public VehicleCatalogMB() {}

	@PostConstruct
	public void init() {
		agencies = agencyBean.listAll();
		vehicles = vehicleBean.listAll();
	}

	public void search() {
		vehicles = vehicleBean.findByFilter(selectedCategory, selectedAgencyId, selectedStatus);
	}

	public void clearFilters() {
		selectedCategory = null;
		selectedAgencyId = null;
		selectedStatus = null;
		vehicles = vehicleBean.listAll();
	}

	public void loadVehicleDetails() {
		if (selectedVehicleId != null) 
			selectedVehicle = vehicleBean.searchByPrimaryKey(selectedVehicleId);
	}

	public VehicleCategory[] getCategories() {
		return VehicleCategory.values();
	}

	public VehicleStatus[] getStatuses() {
		return VehicleStatus.values();
	}

	public List<Vehicle> getVehicles() {
		return vehicles;
	}

	public List<Agency> getAgencies() {
		return agencies;
	}
	
	public VehicleCategory getSelectedCategory() {
		return selectedCategory;
	}

	public void setSelectedCategory(VehicleCategory selectedCategory) {
		this.selectedCategory = selectedCategory;
	}

	public Long getSelectedAgencyId() {
		return selectedAgencyId;
	}

	public void setSelectedAgencyId(Long selectedAgencyId) {
		this.selectedAgencyId = selectedAgencyId;
	}

	public VehicleStatus getSelectedStatus() {
		return selectedStatus;
	}

	public void setSelectedStatus(VehicleStatus selectedStatus) {
		this.selectedStatus = selectedStatus;
	}

	public Long getSelectedVehicleId() {
		return selectedVehicleId;
	}

	public void setSelectedVehicleId(Long selectedVehicleId) {
		this.selectedVehicleId = selectedVehicleId;
	}

	public Vehicle getSelectedVehicle() {
		return selectedVehicle;
	}

	public void setSelectedVehicle(Vehicle selectedVehicle) {
		this.selectedVehicle = selectedVehicle;
	}

}
