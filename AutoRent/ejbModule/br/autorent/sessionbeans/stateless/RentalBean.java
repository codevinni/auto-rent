package br.autorent.sessionbeans.stateless;

import java.util.List;

import br.autorent.enums.RentalStatus;
import br.autorent.model.Rental;
import br.autorent.sessionbeans.interfaces.IRental;
import jakarta.ejb.Stateless;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

/**
 * EJB Stateless que implementa operações CRUD e consultas para a entidade {@link Rental}.
 */
@Stateless
public class RentalBean extends AbstractCrudBean<Rental> implements IRental {

	/** Inicializa o bean com a classe {@link Rental}. */
	public RentalBean() {
		super(Rental.class);
	}

	/**
	 * {@inheritDoc}
	 * Carrega veículo e agências via JOIN FETCH.
	 */
	@Override
	public List<Rental> findByUser(Long userId) {
		
		String jpql = "SELECT r FROM Rental r JOIN FETCH r.vehicle JOIN FETCH r.pickupAgency JOIN FETCH r.returnAgency WHERE r.user.id = :userId ORDER BY r.pickupDate DESC";
		TypedQuery<Rental> query = getEntityManager().createQuery(jpql, Rental.class);
		
		return query.setParameter("userId", userId)
					.getResultList();
	}

	/**
	 * {@inheritDoc}
	 * Filtra locações com status {@code IN_PROGRESS}.
	 */
	@Override
	public List<Rental> findActiveByUser(Long userId) {
		
		String jpql = "SELECT r FROM Rental r JOIN FETCH r.vehicle JOIN FETCH r.pickupAgency JOIN FETCH r.returnAgency WHERE r.user.id = :userId AND r.status = :status ORDER BY r.pickupDate DESC";
		TypedQuery<Rental> query = getEntityManager().createQuery(jpql, Rental.class);
		
		return query.setParameter("userId", userId)
					.setParameter("status", RentalStatus.IN_PROGRESS)
					.getResultList();
	}

	/**
	 * {@inheritDoc}
	 * Filtra locações com status {@code RESERVED}, ordenadas por data de retirada (asc).
	 */
	@Override
	public List<Rental> findReservationsByUser(Long userId) {
		
		String jpql = "SELECT r FROM Rental r JOIN FETCH r.vehicle JOIN FETCH r.pickupAgency JOIN FETCH r.returnAgency WHERE r.user.id = :userId AND r.status = :status ORDER BY r.pickupDate ASC";
		TypedQuery<Rental> query = getEntityManager().createQuery(jpql, Rental.class);
		
		return query.setParameter("userId", userId)
					.setParameter("status", RentalStatus.RESERVED)
					.getResultList();
	}

	/**
	 * {@inheritDoc}
	 * Retorna no máximo um resultado; {@code null} se nenhuma locação ativa for encontrada.
	 */
	@Override
	public Rental findCurrentRentedByUser(Long userId) {
		
		String jpql = "SELECT r FROM Rental r JOIN FETCH r.vehicle JOIN FETCH r.pickupAgency JOIN FETCH r.returnAgency WHERE r.user.id = :userId AND r.status = :status";
		TypedQuery<Rental> query = getEntityManager().createQuery(jpql, Rental.class)
				.setParameter("userId", userId)
				.setParameter("status", RentalStatus.IN_PROGRESS)
				.setMaxResults(1);

		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
		
	}

}