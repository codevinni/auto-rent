package br.autorent.gui;

import javax.swing.JOptionPane;

import br.autorent.enums.Role;
import br.autorent.model.User;
import br.autorent.services.EjbService;

/**
 * Tela de login do aplicativo desktop.
 * Permite apenas funcionários acessarem o sistema.
 */
public class LoginGUI {

	/**
	 * Exibe o diálogo de login em loop até autenticação bem-sucedida ou cancelamento.
	 * Verifica se o usuário é um funcionário.
	 *
	 * @return {@code true} se o login for realizado com sucesso
	 */
	public static boolean login() {
		
		while (true) {
			
			String cpf = JOptionPane.showInputDialog(null, "Digite seu CPF:", "Login do Funcionário", JOptionPane.QUESTION_MESSAGE);
			
			if (cpf == null)
				return false;

			String password = JOptionPane.showInputDialog(null, "Digite sua senha:", "Login do Funcionário", JOptionPane.QUESTION_MESSAGE);
			
			if (password == null)
				return false;

			boolean authenticated = EjbService.userAuthBean.login(cpf.trim(), password.trim());

			if (!authenticated) {
				JOptionPane.showMessageDialog(null, "CPF ou senha inválidos.");
				continue;
			}

			User loggedUser = EjbService.userAuthBean.getLoggedUser();
			
			if (loggedUser == null || loggedUser.getRole() != Role.EMPLOYEE) {
				EjbService.userAuthBean.logout();
				JOptionPane.showMessageDialog(null, "Acesso negado: login exclusivo para funcionários.");
				continue;
			}

			JOptionPane.showMessageDialog(null, "Bem-vindo(a), " + loggedUser.getName() + "!");
			
			return true;
		}
	}

}
