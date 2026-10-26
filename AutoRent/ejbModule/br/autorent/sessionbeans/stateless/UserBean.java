package br.autorent.sessionbeans.stateless;

import br.autorent.model.User;
import br.autorent.sessionbeans.interfaces.IUser;
import br.autorent.utils.PasswordUtils;
import jakarta.ejb.Stateless;

/**
 * EJB Stateless que implementa operações CRUD para a entidade {@link User}.
 * Sobrescreve a criação para gerar hash da senha antes de persistir.
 */
@Stateless
public class UserBean extends AbstractCrudBean<User> implements IUser {

	/** Inicializa o bean com a classe {@link User}. */
	public UserBean() {
		super(User.class);
	}
	
	/**
	 * Cria um novo usuário, gerando o hash SHA-256 da senha em modo texto.
	 *
	 * @param user usuário a ser persistido (deve conter {@code plainPassword})
	 * @throws IllegalArgumentException se a senha estiver vazia ou nula
	 */
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