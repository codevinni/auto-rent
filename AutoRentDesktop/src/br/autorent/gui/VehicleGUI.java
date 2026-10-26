package br.autorent.gui;

import java.util.List;

import javax.swing.JOptionPane;

import br.autorent.enums.VehicleStatus;
import br.autorent.model.Agency;
import br.autorent.model.Vehicle;
import br.autorent.services.EjbService;

/**
 * Tela de gestão de veículos no aplicativo desktop.
 * Permite consultar a frota de uma agência e alterar o status de veículos.
 */
public class VehicleGUI {

	/**
	 * Consulta e exibe a frota de veículos de uma agência selecionada.
	 * Lista agências disponíveis e exibe os veículos da agência escolhida.
	 */
	public static void queryFleet() {
		
		StringBuilder agenciesText = new StringBuilder("--- AGÊNCIAS ---\n");
		List<Agency> agencies = EjbService.agencyBean.listAll();
		
		for (Agency agency : agencies) {
			agenciesText.append(agency.getId()).append(" - ").append(agency.getName()).append("\n");
		}

		String agencyIdStr = JOptionPane.showInputDialog(agenciesText.toString() + "\nDigite o ID da agência:");
		if (agencyIdStr == null || agencyIdStr.isBlank()) 
			return;

		Long agencyId = Long.parseLong(agencyIdStr.trim());
		
		StringBuilder fleetText = new StringBuilder();
		List<Vehicle> vehicles = EjbService.vehicleBean.findByFilter(null, agencyId, null);
		
		if(vehicles.isEmpty()) {
			fleetText.append("Essa agência ainda não tem veículos cadastrados.");
		}
		else {
			
			fleetText.append("--- FROTA DA AGÊNCIA ---\n");
			
			for (Vehicle vehicle : vehicles) {
				fleetText.append("ID: ").append(vehicle.getId())
				         .append(" | Placa: ").append(vehicle.getLicensePlate())
				         .append(" | Modelo: ").append(vehicle.getBrand()).append(" ").append(vehicle.getModel())
				         .append(" | Diária: R$ ").append(vehicle.getDailyPrice())
				         .append(" | Status: ").append(vehicle.getStatus().getDescription()).append("\n");
			}
		}
		
		JOptionPane.showMessageDialog(null, fleetText.toString());
		
	}

	/**
	 * Permite alterar o status de um veículo via diálogo de seleção.
	 * Opções disponíveis: AVAILABLE, MAINTENANCE e UNAVAILABLE.
	 */
	public static void updateVehicleStatus() {
		
		String vehicleIdStr = JOptionPane.showInputDialog("ID do Veículo a alterar:");
		if (vehicleIdStr == null || vehicleIdStr.isBlank()) 
			return;

		Vehicle vehicle = EjbService.vehicleBean.searchByPrimaryKey(Long.parseLong(vehicleIdStr.trim()));
		if (vehicle == null) {
			JOptionPane.showMessageDialog(null, "Veículo não encontrado.");
			return;
		}

		String[] statusOptions = {"AVAILABLE", "MAINTENANCE", "UNAVAILABLE"};
		String newStatus = (String) JOptionPane.showInputDialog(
			null,
			"Veículo: " + vehicle.getBrand() + " " + vehicle.getModel() + " [" + vehicle.getLicensePlate() + "]\n" +
			"Status atual: " + vehicle.getStatus() + "\nSelecione o novo status:",
			"Alterar Status",
			JOptionPane.QUESTION_MESSAGE,
			null,
			statusOptions,
			statusOptions[0]
		);

		if (newStatus != null) {
			vehicle.setStatus(VehicleStatus.valueOf(newStatus));
			EjbService.vehicleBean.update(vehicle);
			JOptionPane.showMessageDialog(null, "Status alterado para: " + newStatus);
		}
	}

}
