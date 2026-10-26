package br.autorent.sessionbeans.singletons;

import br.autorent.enums.VehicleStatus;
import br.autorent.sessionbeans.interfaces.IFleetStatus;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

/**
 * EJB Singleton que mantém em cache as estatísticas da frota de veículos.
 * Inicializado automaticamente no startup da aplicação.
 * O cache é atualizado via {@link #refreshCache()} após operações de locação.
 */
@Startup
@Singleton
public class FleetStatusBean implements IFleetStatus {

	@PersistenceContext(unitName = "autorent")
    private EntityManager em;

    private Long totalVehicles = 0L;
    private Long totalAvailable = 0L;
    private Long totalRented = 0L;
    private Long totalReserved = 0L;
    private Long totalInMaintenance = 0L;
	
    /**
     * Carrega/recarrega todas as contagens de veículos por status.
     * Executado automaticamente no startup e sob demanda.
     */
    @PostConstruct
	@Override
	public void refreshCache() {
		this.totalVehicles = countVehiclesByStatus(null);
        this.totalAvailable = countVehiclesByStatus(VehicleStatus.AVAILABLE);
        this.totalRented = countVehiclesByStatus(VehicleStatus.RENTED);
        this.totalReserved = countVehiclesByStatus(VehicleStatus.RESERVED);
        this.totalInMaintenance = countVehiclesByStatus(VehicleStatus.MAINTENANCE);
	}
    
    /**
     * Conta veículos por status usando JPQL.
     *
     * @param status status para filtrar, ou {@code null} para contar todos
     * @return quantidade de veículos
     */
    private Long countVehiclesByStatus(VehicleStatus status) {
    	
        String jpql;
        
        if (status == null) {
            jpql = "SELECT count(v) FROM Vehicle v";
            Long count = em.createQuery(jpql, Long.class).getSingleResult();
            return count != null ? count : 0L;
        }

        jpql = "SELECT count(v) FROM Vehicle v WHERE v.status = :status";
        Long count = em.createQuery(jpql, Long.class).setParameter("status", status).getSingleResult();
        return count != null ? count : 0L;
    }
    
	/** {@inheritDoc} */
	@Override
	public Long getTotalVehicles() {
		return totalVehicles;
	}

	/** {@inheritDoc} */
	@Override
	public Long getTotalAvailable() {
		return totalAvailable;
	}

	/** {@inheritDoc} */
	@Override
	public Long getTotalRented() {
		return totalRented;
	}

	/** {@inheritDoc} */
	@Override
	public Long getTotalReserved() {
		return totalReserved;
	}

	/** {@inheritDoc} */
	@Override
	public Long getTotalInMaintenance() {
		return totalInMaintenance;
	}

	/** {@inheritDoc} */
	@Override
	public Long getAvailableByAgency(Long agencyId) {
		
		if (agencyId == null)
            return 0L;
        
        String jpql = "SELECT COUNT(v) FROM Vehicle v WHERE v.agency.id = :agencyId AND v.status = :status";
        Long count = em.createQuery(jpql, Long.class)
        			   .setParameter("agencyId", agencyId)
                       .setParameter("status", VehicleStatus.AVAILABLE)
                       .getSingleResult();

        return (count != null) ? count : 0L;
	}

}