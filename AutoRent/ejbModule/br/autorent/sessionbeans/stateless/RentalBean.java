package br.autorent.sessionbeans.stateless;

import br.autorent.model.Rental;
import br.autorent.sessionbeans.interfaces.IRental;
import jakarta.ejb.Stateless;

@Stateless
public class RentalBean extends AbstractCrudBean<Rental> implements IRental {

	public RentalBean() {
		super(Rental.class);
	}

}