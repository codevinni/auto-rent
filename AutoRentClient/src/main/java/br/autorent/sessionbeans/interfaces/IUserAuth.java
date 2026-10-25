package br.autorent.sessionbeans.interfaces;

import br.autorent.model.User;
import jakarta.ejb.Remote;

@Remote
public interface IUserAuth {

	public boolean login(String cpf, String password);
	public void logout();
	public User getLoggedUser();
	public boolean isAuthenticated();
	
}