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

/**
 * Managed Bean JSF responsável pela autenticação de usuários na aplicação web.
 * Mantém a sessão do usuário logado com escopo de sessão.
 */
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

	/** Construtor padrão. */
	public AuthMB() {}

	/**
	 * Realiza o login do usuário com CPF e senha.
	 * Redireciona para o dashboard em caso de sucesso.
	 *
	 * @return redirecionamento de navegação JSF ou {@code null} em caso de erro
	 */
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

	/**
	 * Realiza o logout do usuário e invalida a sessão HTTP.
	 *
	 * @return redirecionamento de navegação para a tela de login
	 */
	public String logout() {
		
		userAuthBean.logout();
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		
		return "login.xhtml?faces-redirect=true";
	}

	/** @return usuário autenticado na sessão */
	public User getLoggedUser() {
		return userAuthBean.getLoggedUser();
	}

	/** @return {@code true} se há um usuário autenticado */
	public boolean isAuthenticated() {
		return userAuthBean.isAuthenticated();
	}

	/** @return {@code true} se o usuário logado é um cliente */
	public boolean isCustomer() {
		return isAuthenticated() && getLoggedUser() != null && getLoggedUser().getRole() == Role.CUSTOMER;
	}

	/** @return CPF informado no formulário de login */
	public String getCpf() {
		return cpf;
	}

	/** @param cpf CPF informado no formulário de login */
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	/** @return senha informada no formulário de login */
	public String getPassword() {
		return password;
	}

	/** @param password senha informada no formulário de login */
	public void setPassword(String password) {
		this.password = password;
	}

}
