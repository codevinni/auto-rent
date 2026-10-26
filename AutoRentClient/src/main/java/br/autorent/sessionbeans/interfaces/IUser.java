package br.autorent.sessionbeans.interfaces;

import br.autorent.model.User;
import jakarta.ejb.Remote;

/**
 * Interface remota EJB para operações CRUD de usuários.
 */
@Remote
public interface IUser extends ICrudService<User> {

}