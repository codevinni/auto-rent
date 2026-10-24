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

	public DashboardMB() {}

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

	public List<Rental> getActiveRentals() {
		return activeRentals;
	}

	public List<Rental> getReservations() {
		return reservations;
	}

	public Rental getCurrentRented() {
		return currentRented;
	}

	public Long getTotalAvailableFleet() {
		return totalAvailableFleet;
	}

	public boolean hasCurrentRented() {
		return currentRented != null;
	}

}
