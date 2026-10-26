package br.autorent.sessionbeans.interfaces;

import br.autorent.model.User;
import jakarta.ejb.Remote;

/**
 * Interface remota EJB para autenticação de usuários via sessão stateful.
 */
@Remote
public interface IUserAuth {

	/**
	 * Autentica um usuário com CPF e senha.
	 *
	 * @param cpf CPF do usuário
	 * @param password senha em texto 
	 * @return {@code true} se autenticado com sucesso
	 */
	public boolean login(String cpf, String password);

	/** Encerra a sessão do usuário autenticado. */
	public void logout();

	/** @return usuário autenticado ou {@code null} */
	public User getLoggedUser();

	/** @return {@code true} se há um usuário autenticado na sessão */
	public boolean isAuthenticated();
	
}