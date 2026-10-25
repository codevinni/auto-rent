package br.autorent.gui;

import javax.swing.JOptionPane;

import br.autorent.services.EjbService;

public class Main {

	public static void main(String[] args) {
		
		try {
			EjbService.init();
		} 
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao conectar com o servidor WildFly: " + e.getMessage());
			return;
		}

		if (!LoginGUI.login())
			return;

		showMainMenu();
	}

	private static void showMainMenu() {
		
		String[] options = {
			"1. Consultar clientes",
			"2. Cadastrar cliente",
			"3. Consultar frota da agência",
			"4. Realizar locação",
			"5. Registro de retirada de veículo",
			"6. Registro de devolução",
			"7. Alterar status do veículo",
			"8. Consultar locações em andamento",
			"9. Sair"
		};

		while (true) {
			
			String choice = (String) JOptionPane.showInputDialog(
				null,
				"Selecione uma opção:",
				"AutoRent - Atendimento de Balcão",
				JOptionPane.PLAIN_MESSAGE,
				null,
				options,
				options[0]
			);

			if (choice == null || choice.startsWith("9")) {
				EjbService.userAuthBean.logout();
				break;
			}

			try {
				switch (choice.substring(0, 1)) {
				
					case "1" -> CustomerGUI.listCustomers();
					case "2" -> CustomerGUI.registerCustomer();
					case "3" -> VehicleGUI.queryFleet();
					case "4" -> RentalGUI.createRental();
					case "5" -> RentalGUI.registerPickup();
					case "6" -> RentalGUI.registerReturn();
					case "7" -> VehicleGUI.updateVehicleStatus();
					case "8" -> RentalGUI.queryActiveRentals();
				}
			} 
			catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Erro na operação: " + e.getMessage());
			}
			
		}
	}

}
