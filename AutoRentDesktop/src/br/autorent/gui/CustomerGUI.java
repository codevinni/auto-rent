package br.autorent.gui;

import java.util.List;

import javax.swing.JOptionPane;

import br.autorent.enums.Role;
import br.autorent.model.User;
import br.autorent.services.EjbService;

public class CustomerGUI {

	public static void listCustomers() {
		
		List<User> users = EjbService.userBean.listAll();
		StringBuilder sb = new StringBuilder();
		
		if(users.isEmpty()) {
			sb.append("Nenhum cliente cadastrado.");
		}
		else {
		
			sb.append("--- CLIENTES CADASTRADOS ---\n");
			
			for (User user : users) {
				if (user.getRole() == Role.CUSTOMER) {
					sb.append("ID: ").append(user.getId())
					  .append(" | Nome: ").append(user.getName())
					  .append(" | CPF: ").append(user.getCpf())
					  .append(" | E-mail: ").append(user.getEmail()).append("\n");
				}
			}
		}
		
		JOptionPane.showMessageDialog(null, sb.toString());
	}

	public static void registerCustomer() {
		
		String name = JOptionPane.showInputDialog("Nome do cliente:");
		if (name == null || name.isBlank()) 
			return;

		String cpf = JOptionPane.showInputDialog("CPF do cliente:");
		if (cpf == null || cpf.isBlank())
			return;

		String email = JOptionPane.showInputDialog("E-mail do cliente:");
		if (email == null || email.isBlank())
			return;

		String password = JOptionPane.showInputDialog("Senha:");
		if (password == null || password.isBlank())
			return;

		User customer = new User(name,cpf,email,password,Role.CUSTOMER);
		EjbService.userBean.create(customer);
		
		JOptionPane.showMessageDialog(null, "Cliente cadastrado com sucesso!");
	}

}
