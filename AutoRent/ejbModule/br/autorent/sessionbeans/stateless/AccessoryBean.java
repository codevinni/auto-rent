package br.autorent.sessionbeans.stateless;

import br.autorent.model.Accessory;
import br.autorent.sessionbeans.interfaces.IAccessory;
import jakarta.ejb.Stateless;

@Stateless
public class AccessoryBean extends AbstractCrudBean<Accessory> implements IAccessory {

	public AccessoryBean() {
		super(Accessory.class);
	}

}