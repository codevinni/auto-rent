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
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "rental_table")
public class Rental {

	@Id
	@GeneratedValue(generator = "rental_gen", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "rental_gen",sequenceName = "rental_seq" , allocationSize = 1)
	private Long id;
	@ManyToOne
	private User user;
	@OneToOne
	private Vehicle vehicle;
	@OneToOne
	private Agency pickupAgency;
	@OneToOne
	private Agency returnAgency;
	private LocalDate pickupDate;
	private LocalDate expectedReturnDate;
	private LocalDate returnDate;
	@Enumerated(EnumType.STRING)
	private RentalStatus status;
	private Double totalPrice;
	
	public Rental() {}

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

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the vehicle
	 */
	public Vehicle getVehicle() {
		return vehicle;
	}

	/**
	 * @param vehicle the vehicle to set
	 */
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	/**
	 * @return the pickupAgency
	 */
	public Agency getPickupAgency() {
		return pickupAgency;
	}

	/**
	 * @param pickupAgency the pickupAgency to set
	 */
	public void setPickupAgency(Agency pickupAgency) {
		this.pickupAgency = pickupAgency;
	}

	/**
	 * @return the returnAgency
	 */
	public Agency getReturnAgency() {
		return returnAgency;
	}

	/**
	 * @param returnAgency the returnAgency to set
	 */
	public void setReturnAgency(Agency returnAgency) {
		this.returnAgency = returnAgency;
	}

	/**
	 * @return the pickupDate
	 */
	public LocalDate getPickupDate() {
		return pickupDate;
	}

	/**
	 * @param pickupDate the pickupDate to set
	 */
	public void setPickupDate(LocalDate pickupDate) {
		this.pickupDate = pickupDate;
	}

	/**
	 * @return the expectedReturnDate
	 */
	public LocalDate getExpectedReturnDate() {
		return expectedReturnDate;
	}

	/**
	 * @param expectedReturnDate the expectedReturnDate to set
	 */
	public void setExpectedReturnDate(LocalDate expectedReturnDate) {
		this.expectedReturnDate = expectedReturnDate;
	}

	/**
	 * @return the returnDate
	 */
	public LocalDate getReturnDate() {
		return returnDate;
	}

	/**
	 * @param returnDate the returnDate to set
	 */
	public void setReturnDate(LocalDate returnDate) {
		this.returnDate = returnDate;
	}

	/**
	 * @return the status
	 */
	public RentalStatus getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(RentalStatus status) {
		this.status = status;
	}

	/**
	 * @return the totalPrice
	 */
	public Double getTotalPrice() {
		return totalPrice;
	}

	/**
	 * @param totalPrice the totalPrice to set
	 */
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	
}