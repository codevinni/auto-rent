package br.autorent.sessionbeans.stateless;

import br.autorent.model.Accessory;
import br.autorent.sessionbeans.interfaces.IAccessory;
import jakarta.ejb.Stateless;

/**
 * EJB Stateless que implementa operações CRUD para a entidade {@link Accessory}.
 */
@Stateless
public class AccessoryBean extends AbstractCrudBean<Accessory> implements IAccessory {

	/** Inicializa o bean com a classe {@link Accessory}. */
	public AccessoryBean() {
		super(Accessory.class);
	}

}