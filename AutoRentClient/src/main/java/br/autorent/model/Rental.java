package br.autorent.model;

import java.time.LocalDate;

import br.autorent.enums.RentalStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

/**
 * Entidade JPA que representa uma locação ou reserva de veículo.
 * Associa um cliente a um veículo, com agências de retirada/devolução e período.
 */
@Entity
@Table(name = "rental_table")
public class Rental implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "rental_gen", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "rental_gen",sequenceName = "rental_seq" , allocationSize = 1)
	private Long id;
	@ManyToOne
	private User user;
	@ManyToOne
	private Vehicle vehicle;
	@ManyToOne
	private Agency pickupAgency;
	@ManyToOne
	private Agency returnAgency;
	private LocalDate pickupDate;
	private LocalDate expectedReturnDate;
	private LocalDate returnDate;
	@Enumerated(EnumType.STRING)
	private RentalStatus status;
	private Double totalPrice;
	
	/** Construtor padrão exigido pelo JPA. */
	public Rental() {}

	/**
	 * Cria uma locação com todos os dados informados.
	 *
	 * @param user cliente associado
	 * @param vehicle veículo locado
	 * @param pickupAgency agência de retirada
	 * @param returnAgency agência de devolução
	 * @param pickupDate data de retirada
	 * @param expectedReturnDate data prevista de devolução
	 * @param returnDate data efetiva de devolução
	 * @param status status da locação
	 * @param totalPrice valor total da locação
	 */
	public Rental(User user, Vehicle vehicle, Agency pickupAgency, Agency returnAgency, LocalDate pickupDate,
		    	  LocalDate expectedReturnDate, LocalDate returnDate, RentalStatus status, Double totalPrice) {

		this.user = user;
		this.vehicle = vehicle;
		this.pickupAgency = pickupAgency;
		this.returnAgency = returnAgency;
		this.pickupDate = pickupDate;
		this.expectedReturnDate = expectedReturnDate;
		this.returnDate = returnDate;
		this.status = status;
		this.totalPrice = totalPrice;
	}

	/** @return identificador único da locação */
	public Long getId() {
		return id;
	}

	/** @param id identificador único da locação */
	public void setId(Long id) {
		this.id = id;
	}

	/** @return cliente associado à locação */
	public User getUser() {
		return user;
	}

	/** @param user cliente associado à locação */
	public void setUser(User user) {
		this.user = user;
	}

	/** @return veículo locado */
	public Vehicle getVehicle() {
		return vehicle;
	}

	/** @param vehicle veículo locado */
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	/** @return agência de retirada */
	public Agency getPickupAgency() {
		return pickupAgency;
	}

	/** @param pickupAgency agência de retirada */
	public void setPickupAgency(Agency pickupAgency) {
		this.pickupAgency = pickupAgency;
	}

	/** @return agência de devolução */
	public Agency getReturnAgency() {
		return returnAgency;
	}

	/** @param returnAgency agência de devolução */
	public void setReturnAgency(Agency returnAgency) {
		this.returnAgency = returnAgency;
	}

	/** @return data de retirada do veículo */
	public LocalDate getPickupDate() {
		return pickupDate;
	}

	/** @param pickupDate data de retirada do veículo */
	public void setPickupDate(LocalDate pickupDate) {
		this.pickupDate = pickupDate;
	}

	/** @return data prevista de devolução */
	public LocalDate getExpectedReturnDate() {
		return expectedReturnDate;
	}

	/** @param expectedReturnDate data prevista de devolução */
	public void setExpectedReturnDate(LocalDate expectedReturnDate) {
		this.expectedReturnDate = expectedReturnDate;
	}

	/** @return data efetiva de devolução */
	public LocalDate getReturnDate() {
		return returnDate;
	}

	/** @param returnDate data efetiva de devolução */
	public void setReturnDate(LocalDate returnDate) {
		this.returnDate = returnDate;
	}

	/** @return status atual da locação */
	public RentalStatus getStatus() {
		return status;
	}

	/** @param status status atual da locação */
	public void setStatus(RentalStatus status) {
		this.status = status;
	}

	/** @return valor total da locação */
	public Double getTotalPrice() {
		return totalPrice;
	}

	/** @param totalPrice valor total da locação */
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	
}