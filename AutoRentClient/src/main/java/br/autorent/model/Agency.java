package br.autorent.model;

import br.autorent.utils.Address;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

/**
 * Entidade JPA que representa uma agência de locação.
 * Cada agência possui endereço e telefone para contato.
 */
@Entity
@Table(name = "agency_table")
public class Agency implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "agency_gen", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "agency_gen",sequenceName = "agency_seq" , allocationSize = 1)
	private Long id;
	private String name;
	@Embedded
	private Address address;
	private String phoneNumber;
	
	/** Construtor padrão exigido pelo JPA. */
	public Agency() {}
	
	/**
	 * Cria uma agência com os dados informados.
	 *
	 * @param name nome da agência
	 * @param address endereço da agência
	 * @param phoneNumber telefone de contato
	 */
	public Agency(String name, Address address, String phoneNumber) {
		this.name = name;
		this.address = address;
		this.phoneNumber = phoneNumber;
	}

	/** @return identificador único da agência */
	public Long getId() {
		return id;
	}

	/** @param id identificador único da agência */
	public void setId(Long id) {
		this.id = id;
	}

	/** @return nome da agência */
	public String getName() {
		return name;
	}

	/** @param name nome da agência */
	public void setName(String name) {
		this.name = name;
	}

	/** @return endereço da agência */
	public Address getAddress() {
		return address;
	}

	/** @param address endereço da agência */
	public void setAddress(Address address) {
		this.address = address;
	}

	/** @return telefone de contato da agência */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/** @param phoneNumber telefone de contato da agência */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
}