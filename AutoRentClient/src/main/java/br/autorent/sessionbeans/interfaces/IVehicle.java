package br.autorent.sessionbeans.interfaces;

import java.util.List;

import br.autorent.enums.VehicleCategory;
import br.autorent.enums.VehicleStatus;
import br.autorent.model.Agency;
import br.autorent.model.Vehicle;
import jakarta.ejb.Remote;

/**
 * Interface remota EJB para operações de veículos com consultas especializadas.
 */
@Remote
public interface IVehicle extends ICrudService<Vehicle> {

	/**
	 * Busca veículos disponíveis em uma agência específica.
	 *
	 * @param agencyId identificador da agência
	 * @return lista de veículos disponíveis na agência
	 */
	List<Vehicle> findAvailableByAgency(Long agencyId);

	/**
	 * Busca todos os veículos atualmente alugados.
	 *
	 * @return lista de veículos com status {@code RENTED}
	 */
	List<Vehicle> findRentedVehicles();

	/**
	 * Busca veículos aplicando filtros opcionais de categoria, agência e status.
	 *
	 * @param category categoria do veículo (pode ser {@code null})
	 * @param agencyId identificador da agência (pode ser {@code null})
	 * @param status status do veículo (pode ser {@code null})
	 * @return lista de veículos que atendem aos filtros
	 */
	List<Vehicle> findByFilter(VehicleCategory category, Long agencyId, VehicleStatus status);

	/**
	 * Busca agências que possuem veículos disponíveis de uma categoria específica.
	 *
	 * @param category categoria desejada
	 * @return lista de agências com veículos disponíveis da categoria
	 */
	List<Agency> findAgenciesWithAvailableVehiclesByCategory(VehicleCategory category);
}