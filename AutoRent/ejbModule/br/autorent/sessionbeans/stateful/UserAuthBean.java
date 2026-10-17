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

@Stateful
public class UserAuthBean implements IUserAuth {

	@PersistenceContext(unitName = "autorent")
	private EntityManager entityManager;
	
	private User loggedUser;
	
	public UserAuthBean() {
		this.loggedUser = null;
	}
	
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

	@Remove
	@Override
	public void logout() {
		this.loggedUser = null;
	}

	@Override
	public User getLoggedUser() {
		return loggedUser;
	}

	@Override
	public boolean isAuthenticated() {
		return loggedUser != null;
	}

}