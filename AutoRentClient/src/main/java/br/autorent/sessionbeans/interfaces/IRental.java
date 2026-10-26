package br.autorent.sessionbeans.interfaces;

import java.util.List;

import br.autorent.model.Rental;
import jakarta.ejb.Remote;

/**
 * Interface remota EJB para operações de locação com consultas especializadas.
 */
@Remote
public interface IRental extends ICrudService<Rental> {

	/**
	 * Busca todas as locações de um usuário, ordenadas por data de retirada (desc).
	 *
	 * @param userId identificador do usuário
	 * @return lista de locações do usuário
	 */
	List<Rental> findByUser(Long userId);

	/**
	 * Busca locações em andamento de um usuário.
	 *
	 * @param userId identificador do usuário
	 * @return lista de locações com status {@code IN_PROGRESS}
	 */
	List<Rental> findActiveByUser(Long userId);

	/**
	 * Busca reservas pendentes de um usuário.
	 *
	 * @param userId identificador do usuário
	 * @return lista de locações com status {@code RESERVED}
	 */
	List<Rental> findReservationsByUser(Long userId);

	/**
	 * Busca a locação em andamento atual de um usuário.
	 *
	 * @param userId identificador do usuário
	 * @return locação em andamento ou {@code null} se não houver
	 */
	Rental findCurrentRentedByUser(Long userId);
}