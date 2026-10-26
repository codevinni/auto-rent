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
 * Entidade JPA que representa um usuário do sistema (cliente ou funcionário).
 * Contém dados pessoais, credenciais de acesso e papel do usuário.
 */
@Entity
@Table(name = "user_table")
@NamedQueries({
	@NamedQuery(name = "User.authenticate", query = "SELECT u FROM User u WHERE u.cpf = :cpf")
})
public class User implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

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
	
	/** Construtor padrão exigido pelo JPA. */
	public User() {}
	
	/**
	 * Cria um usuário com os dados informados.
	 *
	 * @param name nome completo
	 * @param cpf CPF do usuário
	 * @param email e-mail do usuário
	 * @param plainPassword senha em texto plano (será convertida em hash)
	 * @param role papel do usuário no sistema
	 */
	public User(String name, String cpf, String email, String plainPassword, Role role) {
		this.name = name;
		this.cpf = cpf;
		this.email = email;
		this.plainPassword = plainPassword;
		this.role = role;
	}

	/** @return identificador único do usuário */
	public Long getId() {
		return id;
	}

	/** @param id identificador único do usuário */
	public void setId(Long id) {
		this.id = id;
	}

	/** @return nome completo do usuário */
	public String getName() {
		return name;
	}

	/** @param name nome completo do usuário */
	public void setName(String name) {
		this.name = name;
	}

	/** @return CPF do usuário */
	public String getCpf() {
		return cpf;
	}

	/** @param cpf CPF do usuário */
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	/** @return e-mail do usuário */
	public String getEmail() {
		return email;
	}

	/** @param email e-mail do usuário */
	public void setEmail(String email) {
		this.email = email;
	}

	/** @return hash SHA-256 da senha */
	public String getPasswordHash() {
		return passwordHash;
	}

	/** @param passwordHash hash SHA-256 da senha */
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	
	/** @return senha em texto plano (campo transiente, não persistido) */
	public String getPlainPassword() {
		return plainPassword;
	}

	/** @param plainPassword senha em texto plano */
	public void setPlainPassword(String plainPassword) {
		this.plainPassword = plainPassword;
	}

	/** @return papel do usuário no sistema */
	public Role getRole() {
		return role;
	}

	/** @param role papel do usuário no sistema */
	public void setRole(Role role) {
		this.role = role;
	}
	
}