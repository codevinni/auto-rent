package br.autorent.sessionbeans.stateless;

import br.autorent.model.User;
import br.autorent.sessionbeans.interfaces.IUser;
import br.autorent.utils.PasswordUtils;
import jakarta.ejb.Stateless;

@Stateless
public class UserBean extends AbstractCrudBean<User> implements IUser {

	public UserBean() {
		super(User.class);
	}
	
	@Override
	public void create(User user) {
		
		String plainPassword = user.getPlainPassword();
		
		if (plainPassword != null && !plainPassword.isBlank()) {
			
	        String hash = PasswordUtils.hash(plainPassword);
	        user.setPasswordHash(hash);
	        user.setPlainPassword(null);
	        
	    } else {
	        throw new IllegalArgumentException("Senha obrigatória.");
	    }
		
	    super.create(user);
	}
	
}