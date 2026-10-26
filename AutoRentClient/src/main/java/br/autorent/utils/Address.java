package br.autorent.utils;

import jakarta.persistence.Embeddable;

/**
 * Objeto embutível JPA que representa um endereço completo.
 * Utilizado como componente embarcado em entidades como {@link br.autorent.model.Agency}.
 */
@Embeddable
public class Address implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String country, state, city, neighborhood, street, number, complement, code;

	/** Construtor padrão exigido pelo JPA. */
	public Address() {}
	
	/**
	 * Cria um endereço sem complemento.
	 *
	 * @param country país
	 * @param state estado/UF
	 * @param city cidade
	 * @param neighborhood bairro
	 * @param street logradouro
	 * @param number número
	 * @param code CEP
	 */
	public Address(String country, String state, String city, String neighborhood,
				   String street, String number, String code) {
		
		this.country = country;
		this.state = state;
		this.city = city;
		this.neighborhood = neighborhood;
		this.street = street;
		this.number = number;
		this.code = code;
	}

	/**
	 * Cria um endereço com complemento.
	 *
	 * @param country país
	 * @param state estado/UF
	 * @param city cidade
	 * @param neighborhood bairro
	 * @param street logradouro
	 * @param number número
	 * @param complement complemento (ex: apto, bloco)
	 * @param code CEP
	 */
	public Address(String country, String state, String city, String neighborhood, 
			       String street, String number, String complement, String code) {
		
		this.country = country;
		this.state = state;
		this.city = city;
		this.neighborhood = neighborhood;
		this.street = street;
		this.number = number;
		this.complement = complement;
		this.code = code;
	}

	/** @return país */
	public String getCountry() {
		return country;
	}

	/** @param country país */
	public void setCountry(String country) {
		this.country = country;
	}

	/** @return estado/UF */
	public String getState() {
		return state;
	}

	/** @param state estado/UF */
	public void setState(String state) {
		this.state = state;
	}

	/** @return cidade */
	public String getCity() {
		return city;
	}

	/** @param city cidade */
	public void setCity(String city) {
		this.city = city;
	}

	/** @return bairro */
	public String getNeighborhood() {
		return neighborhood;
	}

	/** @param neighborhood bairro */
	public void setNeighborhood(String neighborhood) {
		this.neighborhood = neighborhood;
	}

	/** @return logradouro */
	public String getStreet() {
		return street;
	}

	/** @param street logradouro */
	public void setStreet(String street) {
		this.street = street;
	}

	/** @return número do endereço */
	public String getNumber() {
		return number;
	}

	/** @param number número do endereço */
	public void setNumber(String number) {
		this.number = number;
	}

	/** @return complemento (ex: apto, bloco) */
	public String getComplement() {
		return complement;
	}

	/** @param complement complemento (ex: apto, bloco) */
	public void setComplement(String complement) {
		this.complement = complement;
	}

	/** @return CEP */
	public String getCode() {
		return code;
	}

	/** @param code CEP */
	public void setCode(String code) {
		this.code = code;
	}
	
}