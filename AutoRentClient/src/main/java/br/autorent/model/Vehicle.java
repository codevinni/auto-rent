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
 * Entidade JPA que representa um veículo da frota.
 * Contém dados do veículo, categoria, valor da diária, status e agência atual.
 */
@Entity
@Table(name = "vehicle_table")
public class Vehicle implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

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
	
	/** Construtor padrão exigido pelo JPA. */
	public Vehicle() {}

	/**
	 * Cria um veículo com os dados informados.
	 *
	 * @param licensePlate placa do veículo
	 * @param brand marca do veículo
	 * @param model modelo do veículo
	 * @param year ano de fabricação
	 * @param category categoria do veículo
	 * @param dailyPrice valor da diária
	 * @param status status atual do veículo
	 * @param agency agência onde o veículo está
	 * @param accessories lista de acessórios do veículo
	 */
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

	/** @return identificador único do veículo */
	public Long getId() {
		return id;
	}

	/** @param id identificador único do veículo */
	public void setId(Long id) {
		this.id = id;
	}

	/** @return placa do veículo */
	public String getLicensePlate() {
		return licensePlate;
	}

	/** @param licensePlate placa do veículo */
	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	/** @return marca do veículo */
	public String getBrand() {
		return brand;
	}

	/** @param brand marca do veículo */
	public void setBrand(String brand) {
		this.brand = brand;
	}

	/** @return modelo do veículo */
	public String getModel() {
		return model;
	}

	/** @param model modelo do veículo */
	public void setModel(String model) {
		this.model = model;
	}

	/** @return ano de fabricação do veículo */
	public Integer getYear() {
		return year;
	}
	
	/** @param year ano de fabricação do veículo */
	public void setYear(Integer year) {
		this.year = year;
	}
	
	/** @return categoria do veículo */
	public VehicleCategory getCategory() {
		return category;
	}

	/** @param category categoria do veículo */
	public void setCategory(VehicleCategory category) {
		this.category = category;
	}
	
	/** @return valor da diária do veículo */
	public Double getDailyPrice() {
		return dailyPrice;
	}

	/** @param dailyPrice valor da diária do veículo */
	public void setDailyPrice(Double dailyPrice) {
		this.dailyPrice = dailyPrice;
	}

	/** @return status atual do veículo */
	public VehicleStatus getStatus() {
		return status;
	}

	/** @param status status atual do veículo */
	public void setStatus(VehicleStatus status) {
		this.status = status;
	}

	/** @return agência onde o veículo está localizado */
	public Agency getAgency() {
		return agency;
	}

	/** @param agency agência onde o veículo está localizado */
	public void setAgency(Agency agency) {
		this.agency = agency;
	}

	/** @return lista de acessórios associados ao veículo */
	public List<Accessory> getAccessories() {
		return accessories;
	}

	/** @param accessories lista de acessórios do veículo */
	public void setAccessories(List<Accessory> accessories) {
		this.accessories = accessories;
	}
	
}