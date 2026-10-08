package br.autorent.model;

import java.util.List;

import br.autorent.enums.VehicleCategory;
import br.autorent.enums.VehicleStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

/**
 * 
 */
@Entity
@Table(name = "vehicle_table")
public class Vehicle {

	@Id
	@GeneratedValue(generator = "vehicle_gen", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "vehicle_gen",sequenceName = "vehicle_seq" , allocationSize = 1)
	private Long id;
	private String licensePlate;
	private String brand;
	private String model;
	private Integer year;
	@Enumerated(EnumType.STRING)
	private VehicleCategory category;
	private Double dailyPrice;
	@Enumerated(EnumType.STRING)
	private VehicleStatus status;
	@ManyToOne
	private Agency agency;
	@ManyToMany
	private List<Accessory> accessories;
	
	public Vehicle() {}

	public Vehicle(String licensePlate, String brand, String model, Integer year, VehicleCategory category,
			       Double dailyPrice, VehicleStatus status, Agency agency, List<Accessory> accessories) {
		
		this.licensePlate = licensePlate;
		this.brand = brand;
		this.model = model;
		this.year = year;
		this.dailyPrice = dailyPrice;
		this.status = status;
		this.agency = agency;
		this.accessories = accessories;
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
	 * @return the licensePlate
	 */
	public String getLicensePlate() {
		return licensePlate;
	}

	/**
	 * @param licensePlate the licensePlate to set
	 */
	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	/**
	 * @return the brand
	 */
	public String getBrand() {
		return brand;
	}

	/**
	 * @param brand the brand to set
	 */
	public void setBrand(String brand) {
		this.brand = brand;
	}

	/**
	 * @return the model
	 */
	public String getModel() {
		return model;
	}

	/**
	 * @param model the model to set
	 */
	public void setModel(String model) {
		this.model = model;
	}

	/**
	 * @return the year
	 */
	public Integer getYear() {
		return year;
	}
	
	/**
	 * @param year the year to set
	 */
	public void setYear(Integer year) {
		this.year = year;
	}
	
	/**
	 * @return the category
	 */
	public VehicleCategory getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(VehicleCategory category) {
		this.category = category;
	}
	
	/**
	 * @return the dailyPrice
	 */
	public Double getDailyPrice() {
		return dailyPrice;
	}

	/**
	 * @param dailyPrice the dailyPrice to set
	 */
	public void setDailyPrice(Double dailyPrice) {
		this.dailyPrice = dailyPrice;
	}

	/**
	 * @return the status
	 */
	public VehicleStatus getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(VehicleStatus status) {
		this.status = status;
	}

	/**
	 * @return the agency
	 */
	public Agency getAgency() {
		return agency;
	}

	/**
	 * @param agency the agency to set
	 */
	public void setAgency(Agency agency) {
		this.agency = agency;
	}

	/**
	 * @return the accessories
	 */
	public List<Accessory> getAccessories() {
		return accessories;
	}

	/**
	 * @param accessories the accessories to set
	 */
	public void setAccessories(List<Accessory> accessories) {
		this.accessories = accessories;
	}
	
}