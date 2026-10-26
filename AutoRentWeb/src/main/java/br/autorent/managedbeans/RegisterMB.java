package br.autorent.managedbeans;

import java.io.Serializable;

import br.autorent.enums.Role;
import br.autorent.model.User;
import br.autorent.sessionbeans.interfaces.IUser;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

/**
 * Managed Bean JSF para a tela de cadastro de novos usuários (clientes).
 * Valida os campos, verifica confirmação de senha e persiste o novo usuário.
 */
@Named("registerMB")
@RequestScoped
public class RegisterMB implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private IUser userBean;

	private String name;
	private String cpf;
	private String email;
	private String password;
	private String confirmPassword;

	/** Construtor padrão. */
	public RegisterMB() {}

	/**
	 * Cadastra um novo cliente no sistema.
	 * Valida campos obrigatórios e confirmação de senha.
	 *
	 * @return redirecionamento de navegação para login em caso de sucesso, ou {@code null} em caso de erro
	 */
	public String register() {
		
		FacesContext context = FacesContext.getCurrentInstance();

		if (name == null || name.isBlank() || cpf == null || cpf.isBlank() || email == null || email.isBlank() || password == null || password.isBlank()) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Todos os campos são obrigatórios.", null));
			return null;
		}

		if (!password.equals(confirmPassword)) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "As senhas fornecidas são diferentes.", null));
			return null;
		}

		try {
			
			User newUser = new User(name.trim(), cpf.trim(), email.trim(), password, Role.CUSTOMER);
			
			userBean.create(newUser);
			context.getExternalContext().getFlash().setKeepMessages(true);
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Cadastro realizado com sucesso! Faça login.", null));
			
			return "login.xhtml?faces-redirect=true";
			
		} catch (Exception e) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao cadastrar usuário.", null));
			return null;
		}
	}

	/** @return nome informado no formulário */
	public String getName() {
		return name;
	}

	/** @param name nome do cliente */
	public void setName(String name) {
		this.name = name;
	}

	/** @return CPF informado no formulário */
	public String getCpf() {
		return cpf;
	}

	/** @param cpf CPF do cliente */
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	/** @return e-mail informado no formulário */
	public String getEmail() {
		return email;
	}

	/** @param email e-mail do cliente */
	public void setEmail(String email) {
		this.email = email;
	}

	/** @return senha informada no formulário */
	public String getPassword() {
		return password;
	}

	/** @param password senha do cliente */
	public void setPassword(String password) {
		this.password = password;
	}

	/** @return confirmação de senha informada no formulário */
	public String getConfirmPassword() {
		return confirmPassword;
	}

	/** @param confirmPassword confirmação de senha */
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

}
