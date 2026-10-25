package br.autorent.sessionbeans.interfaces;

import java.util.List;

import br.autorent.model.Rental;
import jakarta.ejb.Remote;

@Remote
public interface IRental extends ICrudService<Rental> {

	List<Rental> findByUser(Long userId);
	List<Rental> findActiveByUser(Long userId);
	List<Rental> findReservationsByUser(Long userId);
	Rental findCurrentRentedByUser(Long userId);
}