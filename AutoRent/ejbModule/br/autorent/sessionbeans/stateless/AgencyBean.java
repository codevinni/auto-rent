package br.autorent.sessionbeans.stateless;

import br.autorent.model.Agency;
import br.autorent.sessionbeans.interfaces.IAgency;
import jakarta.ejb.Stateless;

@Stateless
public class AgencyBean extends AbstractCrudBean<Agency> implements IAgency {

	public AgencyBean() {
		super(Agency.class);
	}

}