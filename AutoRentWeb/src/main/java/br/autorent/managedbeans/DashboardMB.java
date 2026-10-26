package br.autorent.managedbeans;

import java.io.Serializable;
import java.util.List;

import br.autorent.model.Rental;
import br.autorent.sessionbeans.interfaces.IFleetStatus;
import br.autorent.sessionbeans.interfaces.IRental;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 * Managed Bean JSF para a tela de dashboard do usuário.
 * Carrega locações ativas, reservas e estatísticas da frota ao inicializar.
 */
@Named("dashboardMB")
@RequestScoped
public class DashboardMB implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private IRental rentalBean;

	@EJB
	private IFleetStatus fleetStatus;

	@Inject
	private AuthMB authMB;

	private List<Rental> activeRentals;
	private List<Rental> reservations;
	private Rental currentRented;
	private Long totalAvailableFleet;

	/** Construtor padrão. */
	public DashboardMB() {}

	/**
	 * Carrega os dados do dashboard para o usuário logado.
	 * Busca locações ativas, reservas e a locação em andamento atual.
	 */
	@PostConstruct
	public void init() {
		
		if (authMB != null && authMB.isAuthenticated() && authMB.getLoggedUser() != null) {
			
			Long userId = authMB.getLoggedUser().getId();
			activeRentals = rentalBean.findActiveByUser(userId);
			reservations = rentalBean.findReservationsByUser(userId);
			currentRented = rentalBean.findCurrentRentedByUser(userId);
		}
		
		if (fleetStatus != null)
			totalAvailableFleet = fleetStatus.getTotalAvailable();
		
	}

	/** @return lista de locações em andamento do usuário */
	public List<Rental> getActiveRentals() {
		return activeRentals;
	}

	/** @return lista de reservas do usuário */
	public List<Rental> getReservations() {
		return reservations;
	}

	/** @return locação em andamento atual do usuário */
	public Rental getCurrentRented() {
		return currentRented;
	}

	/** @return total de veículos disponíveis na frota */
	public Long getTotalAvailableFleet() {
		return totalAvailableFleet;
	}

	/** @return {@code true} se o usuário possui uma locação em andamento */
	public boolean hasCurrentRented() {
		return currentRented != null;
	}

}
