package br.autorent.sessionbeans.stateless;

import java.util.List;

import br.autorent.enums.RentalStatus;
import br.autorent.model.Rental;
import br.autorent.sessionbeans.interfaces.IRental;
import jakarta.ejb.Stateless;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

@Stateless
public class RentalBean extends AbstractCrudBean<Rental> implements IRental {

	public RentalBean() {
		super(Rental.class);
	}

	@Override
	public List<Rental> findByUser(Long userId) {
		
		String jpql = "SELECT r FROM Rental r JOIN FETCH r.vehicle JOIN FETCH r.pickupAgency JOIN FETCH r.returnAgency WHERE r.user.id = :userId ORDER BY r.pickupDate DESC";
		TypedQuery<Rental> query = getEntityManager().createQuery(jpql, Rental.class);
		
		return query.setParameter("userId", userId)
					.getResultList();
	}

	@Override
	public List<Rental> findActiveByUser(Long userId) {
		
		String jpql = "SELECT r FROM Rental r JOIN FETCH r.vehicle JOIN FETCH r.pickupAgency JOIN FETCH r.returnAgency WHERE r.user.id = :userId AND r.status = :status ORDER BY r.pickupDate DESC";
		TypedQuery<Rental> query = getEntityManager().createQuery(jpql, Rental.class);
		
		return query.setParameter("userId", userId)
					.setParameter("status", RentalStatus.IN_PROGRESS)
					.getResultList();
	}

	@Override
	public List<Rental> findReservationsByUser(Long userId) {
		
		String jpql = "SELECT r FROM Rental r JOIN FETCH r.vehicle JOIN FETCH r.pickupAgency JOIN FETCH r.returnAgency WHERE r.user.id = :userId AND r.status = :status ORDER BY r.pickupDate ASC";
		TypedQuery<Rental> query = getEntityManager().createQuery(jpql, Rental.class);
		
		return query.setParameter("userId", userId)
					.setParameter("status", RentalStatus.RESERVED)
					.getResultList();
	}

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