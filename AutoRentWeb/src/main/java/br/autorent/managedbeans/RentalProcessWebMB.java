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

/**
 * Managed Bean JSF para o fluxo de locação/reserva na aplicação web.
 * Gerencia a seleção de veículo, agências, período, acessórios e confirmação.
 * Escopo de sessão para manter o estado durante as etapas do fluxo.
 */
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

	/** Construtor padrão. */
	public RentalProcessWebMB() {}

	/**
	 * Inicia o fluxo de locação para o veículo selecionado.
	 * Verifica autenticação e disponibilidade do veículo.
	 * Carrega agências e acessórios disponíveis.
	 *
	 * @param selectedVehicleId identificador do veículo escolhido
	 * @return redirecionamento de navegação para a tela de locação ou login
	 */
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

	/**
	 * Recalcula o valor total estimado da locação com base nas seleções atuais.
	 */
	public void calculateTotal() {
		syncProcessBeanState();
		this.estimatedTotal = rentalProcessBean.calculateTotalValue();
	}

	/**
	 * Sincroniza o estado local do managed bean com o EJB stateful de processo de locação.
	 * Transfere cliente, veículo, agências, período e acessórios selecionados.
	 */
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

	/**
	 * Confirma a locação ou reserva após validação.
	 *
	 * @param asReservation {@code true} para reserva futura, {@code false} para locação imediata
	 * @return redirecionamento de navegação para "minhas locações" ou {@code null} em caso de erro
	 */
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

	/**
	 * Cancela o fluxo de locação e redireciona para o catálogo de veículos.
	 *
	 * @return redirecionamento de navegação para a listagem de veículos
	 */
	public String cancel() {
		rentalProcessBean.cancel();
		resetState();
		return "vehicles.xhtml?faces-redirect=true";
	}

	/**
	 * Reinicia o estado local do managed bean após confirmação ou cancelamento.
	 */
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

	/** @return identificador do veículo selecionado */
	public Long getVehicleId() {
		return vehicleId;
	}

	/** @param vehicleId identificador do veículo selecionado */
	public void setVehicleId(Long vehicleId) {
		this.vehicleId = vehicleId;
	}

	/** @return veículo selecionado para locação */
	public Vehicle getVehicle() {
		return vehicle;
	}

	/** @param vehicle veículo selecionado */
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	/** @return identificador da agência de retirada */
	public Long getPickupAgencyId() {
		return pickupAgencyId;
	}

	/** @param pickupAgencyId identificador da agência de retirada */
	public void setPickupAgencyId(Long pickupAgencyId) {
		this.pickupAgencyId = pickupAgencyId;
	}

	/** @return identificador da agência de devolução */
	public Long getReturnAgencyId() {
		return returnAgencyId;
	}

	/** @param returnAgencyId identificador da agência de devolução */
	public void setReturnAgencyId(Long returnAgencyId) {
		this.returnAgencyId = returnAgencyId;
	}

	/** @return data de retirada selecionada */
	public LocalDate getPickupDate() {
		return pickupDate;
	}

	/** @param pickupDate data de retirada */
	public void setPickupDate(LocalDate pickupDate) {
		this.pickupDate = pickupDate;
	}

	/** @return data prevista de devolução */
	public LocalDate getExpectedReturnDate() {
		return expectedReturnDate;
	}

	/** @param expectedReturnDate data prevista de devolução */
	public void setExpectedReturnDate(LocalDate expectedReturnDate) {
		this.expectedReturnDate = expectedReturnDate;
	}

	/** @return lista de IDs dos acessórios selecionados */
	public List<Long> getSelectedAccessoryIds() {
		return selectedAccessoryIds;
	}

	/** @param selectedAccessoryIds lista de IDs dos acessórios selecionados */
	public void setSelectedAccessoryIds(List<Long> selectedAccessoryIds) {
		this.selectedAccessoryIds = selectedAccessoryIds;
	}

	/** @return valor total estimado da locação */
	public Double getEstimatedTotal() {
		return estimatedTotal;
	}

	/** @param estimatedTotal valor total estimado */
	public void setEstimatedTotal(Double estimatedTotal) {
		this.estimatedTotal = estimatedTotal;
	}

	/** @return lista de agências disponíveis */
	public List<Agency> getAgencies() {
		return agencies;
	}

	/** @return lista de acessórios disponíveis */
	public List<Accessory> getAvailableAccessories() {
		return availableAccessories;
	}

}
