package br.autorent.managedbeans;

import java.io.Serializable;
import java.util.List;

import br.autorent.model.Rental;
import br.autorent.sessionbeans.interfaces.IRental;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named("myRentalsMB")
@RequestScoped
public class MyRentalsMB implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private IRental rentalBean;

	@Inject
	private AuthMB authMB;

	private List<Rental> rentals;

	public MyRentalsMB() {}

	@PostConstruct
	public void init() {
		if (authMB != null && authMB.isAuthenticated() && authMB.getLoggedUser() != null) 
			rentals = rentalBean.findByUser(authMB.getLoggedUser().getId());
	}

	public List<Rental> getRentals() {
		return rentals;
	}

}
