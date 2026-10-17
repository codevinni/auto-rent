package br.autorent.sessionbeans.stateless;

import br.autorent.model.User;
import br.autorent.sessionbeans.interfaces.IUser;
import jakarta.ejb.Stateless;

@Stateless
public class UserBean extends AbstractCrudBean<User> implements IUser {

	public UserBean() {
		super(User.class);
	}
	
}