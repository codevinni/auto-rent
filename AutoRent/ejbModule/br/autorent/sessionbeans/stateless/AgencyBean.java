package br.autorent.sessionbeans.stateless;

import br.autorent.model.Agency;
import br.autorent.sessionbeans.interfaces.IAgency;
import jakarta.ejb.Stateless;

/**
 * EJB Stateless que implementa operações CRUD para a entidade {@link Agency}.
 */
@Stateless
public class AgencyBean extends AbstractCrudBean<Agency> implements IAgency {

	/** Inicializa o bean com a classe {@link Agency}. */
	public AgencyBean() {
		super(Agency.class);
	}

}