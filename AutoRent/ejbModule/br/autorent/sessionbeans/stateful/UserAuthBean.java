package br.autorent.sessionbeans.stateful;

import br.autorent.model.User;
import br.autorent.sessionbeans.interfaces.IUserAuth;
import br.autorent.utils.PasswordUtils;
import jakarta.ejb.Remove;
import jakarta.ejb.Stateful;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

/**
 * EJB Stateful que gerencia a autenticação de usuários.
 * Mantém o usuário logado na sessão até o logout.
 */
@Stateful
public class UserAuthBean implements IUserAuth {

	@PersistenceContext(unitName = "autorent")
	private EntityManager entityManager;
	
	private User loggedUser;
	
	/** Inicializa o bean sem usuário autenticado. */
	public UserAuthBean() {
		this.loggedUser = null;
	}
	
	/**
	 * Autentica um usuário verificando CPF e senha.
	 * Busca o usuário pela named query e compara o hash da senha.
	 *
	 * @param cpf CPF do usuário
	 * @param password senha em texto
	 * @return {@code true} se autenticado com sucesso
	 */
	@Override
	public boolean login(String cpf, String password) {
		
		try {
			
			TypedQuery<User> query = entityManager.createNamedQuery("User.authenticate", User.class).setParameter("cpf", cpf);
			User user = query.getSingleResult();
			
			
			if(PasswordUtils.checkPassword(password, user.getPasswordHash())) {
				this.loggedUser = user;
			}
			
		} catch (NoResultException e) {
		}
		
		return isAuthenticated();
	}

	/**
	 * Encerra a sessão do usuário e remove o bean stateful.
	 */
	@Remove
	@Override
	public void logout() {
		this.loggedUser = null;
	}

	/** {@inheritDoc} */
	@Override
	public User getLoggedUser() {
		return loggedUser;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isAuthenticated() {
		return loggedUser != null;
	}

}