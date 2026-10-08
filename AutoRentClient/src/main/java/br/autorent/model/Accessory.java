package br.autorent.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

/**
 * 
 */
@Entity
@Table(name = "accessory_table")
public class Accessory {

	@Id
	@GeneratedValue(generator = "accessory_gen", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "accessory_gen",sequenceName = "accessory_seq" , allocationSize = 1)
	private Long id;
	private String name;
	private String description;
	private Double dailyPrice;
	
	public Accessory() {}

	public Accessory(String name, String description, Double dailyPrice) {
		this.name = name;
		this.description = description;
		this.dailyPrice = dailyPrice;
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
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
}