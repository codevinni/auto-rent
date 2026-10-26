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

/**
 * Managed Bean JSF para o catálogo de veículos na aplicação web.
 * Permite listar, filtrar e visualizar detalhes dos veículos disponíveis.
 */
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

	/** Construtor padrão. */
	public VehicleCatalogMB() {}

	/**
	 * Carrega todas as agências e veículos ao inicializar o bean.
	 */
	@PostConstruct
	public void init() {
		agencies = agencyBean.listAll();
		vehicles = vehicleBean.listAll();
	}

	/**
	 * Aplica os filtros selecionados (categoria, agência, status) na busca de veículos.
	 */
	public void search() {
		vehicles = vehicleBean.findByFilter(selectedCategory, selectedAgencyId, selectedStatus);
	}

	/**
	 * Limpa todos os filtros e recarrega a lista completa de veículos.
	 */
	public void clearFilters() {
		selectedCategory = null;
		selectedAgencyId = null;
		selectedStatus = null;
		vehicles = vehicleBean.listAll();
	}

	/**
	 * Carrega os detalhes de um veículo selecionado pelo ID.
	 */
	public void loadVehicleDetails() {
		if (selectedVehicleId != null) 
			selectedVehicle = vehicleBean.searchByPrimaryKey(selectedVehicleId);
	}

	/**
	 * Retorna todas as categorias de veículo disponíveis.
	 *
	 * @return array com os valores do enum {@link VehicleCategory}
	 */
	public VehicleCategory[] getCategories() {
		return VehicleCategory.values();
	}

	/**
	 * Retorna todos os status de veículo disponíveis.
	 *
	 * @return array com os valores do enum {@link VehicleStatus}
	 */
	public VehicleStatus[] getStatuses() {
		return VehicleStatus.values();
	}

	/** @return lista de veículos (filtrados ou todos) */
	public List<Vehicle> getVehicles() {
		return vehicles;
	}

	/** @return lista de agências cadastradas */
	public List<Agency> getAgencies() {
		return agencies;
	}
	
	/** @return categoria selecionada como filtro */
	public VehicleCategory getSelectedCategory() {
		return selectedCategory;
	}

	/** @param selectedCategory categoria para filtro */
	public void setSelectedCategory(VehicleCategory selectedCategory) {
		this.selectedCategory = selectedCategory;
	}

	/** @return identificador da agência selecionada como filtro */
	public Long getSelectedAgencyId() {
		return selectedAgencyId;
	}

	/** @param selectedAgencyId identificador da agência para filtro */
	public void setSelectedAgencyId(Long selectedAgencyId) {
		this.selectedAgencyId = selectedAgencyId;
	}

	/** @return status selecionado como filtro */
	public VehicleStatus getSelectedStatus() {
		return selectedStatus;
	}

	/** @param selectedStatus status para filtro */
	public void setSelectedStatus(VehicleStatus selectedStatus) {
		this.selectedStatus = selectedStatus;
	}

	/** @return identificador do veículo selecionado para detalhes */
	public Long getSelectedVehicleId() {
		return selectedVehicleId;
	}

	/** @param selectedVehicleId identificador do veículo para detalhes */
	public void setSelectedVehicleId(Long selectedVehicleId) {
		this.selectedVehicleId = selectedVehicleId;
	}

	/** @return veículo selecionado com detalhes carregados */
	public Vehicle getSelectedVehicle() {
		return selectedVehicle;
	}

	/** @param selectedVehicle veículo selecionado */
	public void setSelectedVehicle(Vehicle selectedVehicle) {
		this.selectedVehicle = selectedVehicle;
	}

}
