package br.autorent.sessionbeans.interfaces;

import java.util.List;

import br.autorent.enums.VehicleCategory;
import br.autorent.enums.VehicleStatus;
import br.autorent.model.Agency;
import br.autorent.model.Vehicle;

public interface IVehicle extends ICrudService<Vehicle> {

	List<Vehicle> findAvailableByAgency(Long agencyId);
	List<Vehicle> findRentedVehicles();
	List<Vehicle> findByFilter(VehicleCategory category, Long agencyId, VehicleStatus status);
	List<Agency> findAgenciesWithAvailableVehiclesByCategory(VehicleCategory category);
}