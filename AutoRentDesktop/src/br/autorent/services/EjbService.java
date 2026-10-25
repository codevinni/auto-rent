package br.autorent.services;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import br.autorent.sessionbeans.interfaces.IAgency;
import br.autorent.sessionbeans.interfaces.IRental;
import br.autorent.sessionbeans.interfaces.IRentalProcess;
import br.autorent.sessionbeans.interfaces.IUser;
import br.autorent.sessionbeans.interfaces.IUserAuth;
import br.autorent.sessionbeans.interfaces.IVehicle;

public class EjbService {

	public static IUserAuth userAuthBean;
	public static IUser userBean;
	public static IVehicle vehicleBean;
	public static IAgency agencyBean;
	public static IRental rentalBean;
	public static IRentalProcess rentalProcessBean;

	public static void init() throws NamingException {
		
		Properties props = new Properties();
		props.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
		props.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");

		InitialContext ic = new InitialContext(props);

		userAuthBean = (IUserAuth) ic.lookup("ejb:AutoRentEAR/AutoRent/UserAuthBean!br.autorent.sessionbeans.interfaces.IUserAuth?stateful");
		userBean = (IUser) ic.lookup("ejb:AutoRentEAR/AutoRent/UserBean!br.autorent.sessionbeans.interfaces.IUser");
		vehicleBean = (IVehicle) ic.lookup("ejb:AutoRentEAR/AutoRent/VehicleBean!br.autorent.sessionbeans.interfaces.IVehicle");
		agencyBean = (IAgency) ic.lookup("ejb:AutoRentEAR/AutoRent/AgencyBean!br.autorent.sessionbeans.interfaces.IAgency");
		rentalBean = (IRental) ic.lookup("ejb:AutoRentEAR/AutoRent/RentalBean!br.autorent.sessionbeans.interfaces.IRental");
		rentalProcessBean = (IRentalProcess) ic.lookup("ejb:AutoRentEAR/AutoRent/RentalProcessBean!br.autorent.sessionbeans.interfaces.IRentalProcess?stateful");
	}

}
