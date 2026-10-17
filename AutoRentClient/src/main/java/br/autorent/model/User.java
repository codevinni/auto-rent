package br.autorent.model;

import br.autorent.enums.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

/**
 * 
 */
@Entity
@Table(name = "user_table")
@NamedQueries({
	@NamedQuery(name = "User.authenticate", query = "SELECT u FROM User u WHERE u.cpf = :cpf")
})
public class User {

	@Id
	@GeneratedValue(generator = "user_gen", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "user_gen",sequenceName = "user_seq" , allocationSize = 1)
	private Long id;
	private String name;
	private String cpf;
	private String email;
	private String passwordHash;
	@Transient
	private String plainPassword;
	@Enumerated(EnumType.STRING)
	private Role role;
	
	public User() {}
	
	public User(String name, String cpf, String email, String plainPassword, Role role) {
		this.name = name;
		this.cpf = cpf;
		this.email = email;
		this.plainPassword = plainPassword;
		this.role = role;
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
	 * @return the cpf
	 */
	public String getCpf() {
		return cpf;
	}

	/**
	 * @param cpf the cpf to set
	 */
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the passwordHash
	 */
	public String getPasswordHash() {
		return passwordHash;
	}

	/**
	 * @param passwordHash the passwordHash to set
	 */
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	
	/**
	 * @return the plainPassword
	 */
	public String getPlainPassword() {
		return plainPassword;
	}

	/**
	 * @param plainPassword the plainPassword to set
	 */
	public void setPlainPassword(String plainPassword) {
		this.plainPassword = plainPassword;
	}

	/**
	 * @return the role
	 */
	public Role getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(Role role) {
		this.role = role;
	}
	
}