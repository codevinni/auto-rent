package br.autorent.gui;

import java.time.LocalDate;

import javax.swing.JOptionPane;

import br.autorent.enums.RentalStatus;
import br.autorent.enums.VehicleStatus;
import br.autorent.model.Agency;
import br.autorent.model.Rental;
import br.autorent.model.User;
import br.autorent.model.Vehicle;
import br.autorent.services.EjbService;

public class RentalGUI {

	public static void createRental() {
		
		String customerIdStr = JOptionPane.showInputDialog("ID do Cliente:");
		if (customerIdStr == null || customerIdStr.isBlank())
			return;
		
		User customer = EjbService.userBean.searchByPrimaryKey(Long.parseLong(customerIdStr.trim()));

		String pickupAgencyIdStr = JOptionPane.showInputDialog("ID da Agência de Retirada:");
		if (pickupAgencyIdStr == null || pickupAgencyIdStr.isBlank())
			return;
		
		Agency pickupAgency = EjbService.agencyBean.searchByPrimaryKey(Long.parseLong(pickupAgencyIdStr.trim()));

		StringBuilder availableVehiclesText = new StringBuilder("--- VEÍCULOS DISPONÍVEIS NA AGÊNCIA ---\n");
		
		for (Vehicle vehicle : EjbService.vehicleBean.findAvailableByAgency(pickupAgency.getId())) {
			
			availableVehiclesText.append(vehicle.getId()).append(" - ").append(vehicle.getBrand()).append(" ")
			                     .append(vehicle.getModel()).append(" [").append(vehicle.getLicensePlate())
			                     .append("] - R$ ").append(vehicle.getDailyPrice()).append("/dia\n");
		}

		String vehicleIdStr = JOptionPane.showInputDialog(availableVehiclesText.toString() + "\nID do Veículo escolhido:");
		if (vehicleIdStr == null || vehicleIdStr.isBlank())
			return;
		
		Vehicle vehicle = EjbService.vehicleBean.searchByPrimaryKey(Long.parseLong(vehicleIdStr.trim()));

		String returnAgencyIdStr = JOptionPane.showInputDialog("ID da Agência de Devolução prevista:", pickupAgency.getId().toString());
		if (returnAgencyIdStr == null || returnAgencyIdStr.isBlank())
			return;
		
		Agency returnAgency = EjbService.agencyBean.searchByPrimaryKey(Long.parseLong(returnAgencyIdStr.trim()));

		String daysStr = JOptionPane.showInputDialog("Quantidade de diárias:", "1");
		if (daysStr == null || daysStr.isBlank())
			return;
		
		int days = Integer.parseInt(daysStr.trim());

		LocalDate pickupDate = LocalDate.now();
		LocalDate returnDate = pickupDate.plusDays(days);

		EjbService.rentalProcessBean.cancel();
		EjbService.rentalProcessBean.setCustomer(customer);
		EjbService.rentalProcessBean.setVehicle(vehicle);
		EjbService.rentalProcessBean.setPickupAgency(pickupAgency);
		EjbService.rentalProcessBean.setReturnAgency(returnAgency);
		EjbService.rentalProcessBean.setPeriod(pickupDate, returnDate);

		Double totalValue = EjbService.rentalProcessBean.calculateTotalValue();

		int choice = JOptionPane.showConfirmDialog(
			null,
			"Valor total calculado: R$ " + totalValue + "\nConfirmar como Locação Imediata?\n(Sim = Locação, Não = Reserva, Cancelar = Abortar)",
			"Confirmar",
			JOptionPane.YES_NO_CANCEL_OPTION
		);

		if (choice == JOptionPane.YES_OPTION) {
			Rental confirmedRental = EjbService.rentalProcessBean.confirmRental();
			JOptionPane.showMessageDialog(null, "Locação #" + confirmedRental.getId() + " confirmada com sucesso!");
		} 
		else if (choice == JOptionPane.NO_OPTION) {
			Rental confirmedReservation = EjbService.rentalProcessBean.confirmReservation();
			JOptionPane.showMessageDialog(null, "Reserva #" + confirmedReservation.getId() + " confirmada com sucesso!");
		} 
		else {
			EjbService.rentalProcessBean.cancel();
		}
	}

	public static void registerPickup() {
		
		StringBuilder sb = new StringBuilder("--- RESERVAS PENDENTES DE RETIRADA ---\n");
		for (Rental rental : EjbService.rentalBean.searchByField("status", RentalStatus.RESERVED)) {
			sb.append("ID: ").append(rental.getId())
			  .append(" | Cliente: ").append(rental.getUser().getName())
			  .append(" | Veículo: ").append(rental.getVehicle().getModel())
			  .append(" [").append(rental.getVehicle().getLicensePlate()).append("]\n");
		}

		String rentalIdStr = JOptionPane.showInputDialog(sb.toString() + "\nDigite o ID da reserva para retirada:");
		if (rentalIdStr == null || rentalIdStr.isBlank())
			return;

		Rental rental = EjbService.rentalBean.searchByPrimaryKey(Long.parseLong(rentalIdStr.trim()));
		if (rental != null) {
			
			rental.setStatus(RentalStatus.IN_PROGRESS);
			rental.setPickupDate(LocalDate.now());
			EjbService.rentalBean.update(rental);

			Vehicle vehicle = rental.getVehicle();
			vehicle.setStatus(VehicleStatus.RENTED);
			EjbService.vehicleBean.update(vehicle);

			JOptionPane.showMessageDialog(null, "Retirada registrada com sucesso! Locação agora em andamento.");
		
		}
	}

	public static void registerReturn() {
		
		StringBuilder sb = new StringBuilder("--- LOCAÇÕES EM ANDAMENTO ---\n");
		
		for (Rental rental : EjbService.rentalBean.searchByField("status", RentalStatus.IN_PROGRESS)) {
			sb.append("ID: ").append(rental.getId())
			  .append(" | Cliente: ").append(rental.getUser().getName())
			  .append(" | Veículo: ").append(rental.getVehicle().getModel())
			  .append(" [").append(rental.getVehicle().getLicensePlate()).append("]\n");
		}

		String rentalIdStr = JOptionPane.showInputDialog(sb.toString() + "\nDigite o ID da locação a devolver:");
		if (rentalIdStr == null || rentalIdStr.isBlank())
			return;

		Rental rental = EjbService.rentalBean.searchByPrimaryKey(Long.parseLong(rentalIdStr.trim()));
		
		if (rental != null) {
			
			String defaultAgencyId = rental.getReturnAgency() != null ? rental.getReturnAgency().getId().toString() : "1";
			String agencyIdStr = JOptionPane.showInputDialog("ID da agência onde o veículo foi entregue:", defaultAgencyId);
			
			if (agencyIdStr == null || agencyIdStr.isBlank())
				return;
			
			Agency returnAgency = EjbService.agencyBean.searchByPrimaryKey(Long.parseLong(agencyIdStr.trim()));

			rental.setStatus(RentalStatus.COMPLETED);
			rental.setReturnDate(LocalDate.now());
			rental.setReturnAgency(returnAgency);
			
			EjbService.rentalBean.update(rental);

			Vehicle vehicle = rental.getVehicle();
			vehicle.setStatus(VehicleStatus.AVAILABLE);
			vehicle.setAgency(returnAgency);
			
			EjbService.vehicleBean.update(vehicle);

			JOptionPane.showMessageDialog(null, "Devolução registrada com sucesso! Veículo disponível na agência.");
		}
	}

	public static void queryActiveRentals() {
		
		StringBuilder sb = new StringBuilder("--- LOCAÇÕES EM ANDAMENTO ---\n");
		for (Rental rental : EjbService.rentalBean.searchByField("status", RentalStatus.IN_PROGRESS)) {
			
			sb.append("ID: ").append(rental.getId())
			  .append(" | Cliente: ").append(rental.getUser().getName())
			  .append(" | Veículo: ").append(rental.getVehicle().getModel())
			  .append(" [").append(rental.getVehicle().getLicensePlate()).append("]")
			  .append(" | Retirada: ").append(rental.getPickupDate())
			  .append(" | Prev. Devolução: ").append(rental.getExpectedReturnDate())
			  .append(" | Total: R$ ").append(rental.getTotalPrice()).append("\n");
			
		}
		
		JOptionPane.showMessageDialog(null, sb.toString());
	}

}
