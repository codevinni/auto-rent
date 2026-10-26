package br.autorent.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

/**
 * Entidade JPA que representa um acessório opcional para veículos.
 * Acessórios possuem valor diário adicional cobrado na locação.
 */
@Entity
@Table(name = "accessory_table")
public class Accessory implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "accessory_gen", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "accessory_gen",sequenceName = "accessory_seq" , allocationSize = 1)
	private Long id;
	private String name;
	private String description;
	private Double dailyPrice;
	
	/** Construtor padrão exigido pelo JPA. */
	public Accessory() {}

	/**
	 * Cria um acessório com os dados informados.
	 *
	 * @param name nome do acessório
	 * @param description descrição do acessório
	 * @param dailyPrice valor da diária do acessório
	 */
	public Accessory(String name, String description, Double dailyPrice) {
		this.name = name;
		this.description = description;
		this.dailyPrice = dailyPrice;
	}

	/** @return identificador único do acessório */
	public Long getId() {
		return id;
	}

	/** @param id identificador único do acessório */
	public void setId(Long id) {
		this.id = id;
	}

	/** @return nome do acessório */
	public String getName() {
		return name;
	}

	/** @param name nome do acessório */
	public void setName(String name) {
		this.name = name;
	}

	/** @return descrição do acessório */
	public String getDescription() {
		return description;
	}

	/** @param description descrição do acessório */
	public void setDescription(String description) {
		this.description = description;
	}

	/** @return valor da diária do acessório */
	public Double getDailyPrice() {
		return dailyPrice;
	}

	/** @param dailyPrice valor da diária do acessório */
	public void setDailyPrice(Double dailyPrice) {
		this.dailyPrice = dailyPrice;
	}
}