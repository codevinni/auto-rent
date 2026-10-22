package br.autorent.sessionbeans.interfaces;

import java.time.LocalDate;
import java.util.List;

import br.autorent.model.Accessory;
import br.autorent.model.Agency;
import br.autorent.model.Rental;
import br.autorent.model.User;
import br.autorent.model.Vehicle;

public interface IRentalProcess {

	User getCustomer();
	void setCustomer(User user);
	void setPickupAgency(Agency agency);
	void selectReturnAgency(Agency agency);
	void setVehicle(Vehicle vehicle);
	void setPeriod(LocalDate pickupDate, LocalDate expectedReturnDate);
	void addAccessory(Accessory accessory);
    void removeAccessory(Accessory accessory);
    List<Accessory> getSelectedAccessories();
	Double calculateTotalValue();
	Rental confirmRental();
	void cancel();
}