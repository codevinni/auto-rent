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

	public RegisterMB() {}

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

}
