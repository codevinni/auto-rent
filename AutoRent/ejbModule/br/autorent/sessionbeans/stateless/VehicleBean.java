package br.autorent.sessionbeans.stateless;

import br.autorent.model.Vehicle;
import br.autorent.sessionbeans.interfaces.IVehicle;
import jakarta.ejb.Stateless;

@Stateless
public class VehicleBean extends AbstractCrudBean<Vehicle> implements IVehicle {

	public VehicleBean() {
		super(Vehicle.class);
	}

}