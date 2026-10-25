package br.autorent.sessionbeans.interfaces;

import br.autorent.model.User;
import jakarta.ejb.Remote;

@Remote
public interface IUser extends ICrudService<User> {

}