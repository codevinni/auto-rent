package br.autorent.managedbeans;

import java.io.Serializable;

import br.autorent.enums.Role;
import br.autorent.model.User;
import br.autorent.sessionbeans.interfaces.IUser;
import br.autorent.sessionbeans.interfaces.IUserAuth;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

@Named("authMB")
@SessionScoped
public class AuthMB implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private IUserAuth userAuthBean;

	@EJB
	private IUser userBean;

	private String cpf;
	private String password;

	public AuthMB() {}

	public String login() {
		
		if (cpf == null || cpf.isBlank() || password == null || password.isBlank()) {
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "Informe CPF e Senha.", null));
			return null;
		}

		boolean authenticated = userAuthBean.login(cpf, password);
		
		if (authenticated) {
			return "dashboard.xhtml?faces-redirect=true";
		} 
		else {
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "CPF ou senha inválidos.", null));
			return null;
		}
	}

	public String logout() {
		
		userAuthBean.logout();
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		
		return "login.xhtml?faces-redirect=true";
	}

	public User getLoggedUser() {
		return userAuthBean.getLoggedUser();
	}

	public boolean isAuthenticated() {
		return userAuthBean.isAuthenticated();
	}

	public boolean isCustomer() {
		return isAuthenticated() && getLoggedUser() != null && getLoggedUser().getRole() == Role.CUSTOMER;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
