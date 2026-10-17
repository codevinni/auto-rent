package br.autorent.sessionbeans.interfaces;

import br.autorent.model.User;

public interface IUserAuth {

	public boolean login(String cpf, String password);
	public void logout();
	public User getLoggedUser();
	public boolean isAuthenticated();
	
}