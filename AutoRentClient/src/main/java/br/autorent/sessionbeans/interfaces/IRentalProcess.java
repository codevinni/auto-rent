package br.autorent.sessionbeans.interfaces;

import java.time.LocalDate;
import java.util.List;

import br.autorent.model.Accessory;
import br.autorent.model.Agency;
import br.autorent.model.Rental;
import br.autorent.model.User;
import br.autorent.model.Vehicle;
import jakarta.ejb.Remote;

/**
 * Interface remota EJB para o fluxo stateful de locação/reserva de veículos.
 * Mantém o estado da locação em construção durante a sessão do usuário.
 */
@Remote
public interface IRentalProcess {

	/** @return cliente associado ao processo */
	User getCustomer();

	/** @param user cliente a ser associado ao processo */
	void setCustomer(User user);

	/** @return agência de retirada selecionada */
	Agency getPickupAgency();

	/** @param agency agência de retirada */
	void setPickupAgency(Agency agency);

	/** @return agência de devolução selecionada */
	Agency getReturnAgency();

	/** @param agency agência de devolução */
	void setReturnAgency(Agency agency);

	/** @return veículo selecionado para locação */
	Vehicle getVehicle();

	/** @param vehicle veículo selecionado */
	void setVehicle(Vehicle vehicle);

	/** @return data de retirada definida */
	LocalDate getPickupDate();

	/** @return data prevista de devolução */
	LocalDate getExpectedReturnDate();

	/**
	 * Define o período da locação.
	 *
	 * @param pickupDate data de retirada
	 * @param expectedReturnDate data prevista de devolução
	 */
	void setPeriod(LocalDate pickupDate, LocalDate expectedReturnDate);

	/**
	 * Adiciona um acessório à locação.
	 *
	 * @param accessory acessório a ser adicionado
	 */
	void addAccessory(Accessory accessory);

	/**
	 * Remove um acessório da locação.
	 *
	 * @param accessory acessório a ser removido
	 */
    void removeAccessory(Accessory accessory);

	/** @return lista de acessórios selecionados */
    List<Accessory> getSelectedAccessories();

	/**
	 * Calcula o valor total da locação (diárias do veículo + acessórios).
	 *
	 * @return valor total calculado
	 */
	Double calculateTotalValue();

	/**
	 * Confirma a locação, marcando o veículo como alugado.
	 *
	 * @return locação confirmada e persistida
	 * @throws IllegalStateException se cliente ou veículo não estiverem definidos
	 */
	Rental confirmRental();

	/**
	 * Confirma uma reserva, marcando o veículo como reservado.
	 *
	 * @return reserva confirmada e persistida
	 * @throws IllegalStateException se cliente ou veículo não estiverem definidos
	 */
	Rental confirmReservation();

	/** @return locação em construção */
	Rental getRental();

	/** Cancela o processo atual e reinicia o estado. */
	void cancel();
}