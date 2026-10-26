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

/**
 * Serviço de localização e inicialização dos EJBs remotos via JNDI.
 * Conecta ao servidor WildFly e disponibiliza os beans como estáticos.
 */
public class EjbService {

	/** Bean de autenticação de usuário. */
	public static IUserAuth userAuthBean;
	/** Bean de operações CRUD de usuários. */
	public static IUser userBean;
	/** Bean de operações CRUD e consultas de veículos. */
	public static IVehicle vehicleBean;
	/** Bean de operações CRUD de agências. */
	public static IAgency agencyBean;
	/** Bean de operações CRUD e consultas de locações. */
	public static IRental rentalBean;
	/** Bean de fluxo de locação/reserva. */
	public static IRentalProcess rentalProcessBean;

	/**
	 * Inicializa todos os EJBs remotos via lookup JNDI no WildFly.
	 *
	 * @throws NamingException se ocorrer erro na localização dos beans
	 */
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
