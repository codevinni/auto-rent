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
	Agency getPickupAgency();
	void setPickupAgency(Agency agency);
	Agency getReturnAgency();
	void setReturnAgency(Agency agency);
	Vehicle getVehicle();
	void setVehicle(Vehicle vehicle);
	LocalDate getPickupDate();
	LocalDate getExpectedReturnDate();
	void setPeriod(LocalDate pickupDate, LocalDate expectedReturnDate);
	void addAccessory(Accessory accessory);
    void removeAccessory(Accessory accessory);
    List<Accessory> getSelectedAccessories();
	Double calculateTotalValue();
	Rental confirmRental();
	Rental confirmReservation();
	Rental getRental();
	void cancel();
}