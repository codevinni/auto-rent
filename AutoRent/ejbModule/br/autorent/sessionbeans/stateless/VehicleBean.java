package br.autorent.sessionbeans.stateless;

import java.util.List;

import br.autorent.enums.VehicleCategory;
import br.autorent.enums.VehicleStatus;
import br.autorent.model.Agency;
import br.autorent.model.Vehicle;
import br.autorent.sessionbeans.interfaces.IVehicle;
import jakarta.ejb.Stateless;
import jakarta.persistence.TypedQuery;

@Stateless
public class VehicleBean extends AbstractCrudBean<Vehicle> implements IVehicle {

	public VehicleBean() {
		super(Vehicle.class);
	}

	@Override
	public List<Vehicle> findAvailableByAgency(Long agencyId) {
		
		String jpql = "SELECT v FROM Vehicle v WHERE v.agency.id = :agencyId AND v.status = :status";
		TypedQuery<Vehicle> query = getEntityManager().createQuery(jpql, Vehicle.class);
		
		return query.setParameter("agencyId", agencyId)
					.setParameter("status", VehicleStatus.AVAILABLE)
					.getResultList();
	}

	@Override
	public List<Vehicle> findRentedVehicles() {
		
		String jpql = "SELECT v FROM Vehicle v WHERE v.status = :status";
		TypedQuery<Vehicle> query = getEntityManager().createQuery(jpql, Vehicle.class);
		
		return query.setParameter("status", VehicleStatus.RENTED)
					.getResultList();
	}

	@Override
	public List<Vehicle> findByFilter(VehicleCategory category, Long agencyId, VehicleStatus status) {

		StringBuilder jpql = new StringBuilder("SELECT v FROM Vehicle v WHERE 1=1");

		if (category != null) 
			jpql.append(" AND v.category = :category");
		
		if (agencyId != null) 
			jpql.append(" AND v.agency.id = :agencyId");
		
		if (status != null)
			jpql.append(" AND v.status = :status");
		
		TypedQuery<Vehicle> query = getEntityManager().createQuery(jpql.toString(), Vehicle.class);
		
		if (category != null) 
			query.setParameter("category", category);
		
		if (agencyId != null) 
			query.setParameter("agencyId", agencyId);
		
		if (status != null)
			query.setParameter("status", status);
		
		return query.getResultList();
	}

	@Override
	public List<Agency> findAgenciesWithAvailableVehiclesByCategory(VehicleCategory category) {

		String jpql = "SELECT DISTINCT v.agency FROM Vehicle v WHERE v.category = :category AND v.status = :status";
		TypedQuery<Agency> query = getEntityManager().createQuery(jpql, Agency.class);
		
		return query.setParameter("category", category)
					.setParameter("status", VehicleStatus.AVAILABLE)
	                .getResultList();
	}

}
