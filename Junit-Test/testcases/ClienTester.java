package testcases;

//Testing all Remote method calls from client to servers

import static org.junit.Assert.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;
import org.junit.Test;
import com.bank.interf.BankInterface;
import com.client.ClientInfo;
import com.interf.Const;
import com.interf.RemoteInterface;

public class ClienTester {

	//Testing Remote method Login
		@Test
		public void testLogin() throws RemoteException, NotBoundException {
			Registry patreg = LocateRegistry.getRegistry("localhost",Const.RMI_PORT);
			RemoteInterface remote = (RemoteInterface) patreg.lookup(Const.RMI_ID);
			ClientInfo cli= new ClientInfo();
			
			cli.bryId = "bry123";
			Map<String, String> userInfo = new HashMap<String, String>();
			userInfo = remote.displayUserDetails(cli);
			Map<String, String> testInfo = new HashMap<String, String>();
			testInfo.put("Name", "Venkat");
			testInfo.put("Age", "25");
			testInfo.put("BryId", "bry123");
			testInfo.put("Sex", "Male");
			testInfo.put("Insurance amount", "2000");
			testInfo.put("Bank account", "123456");
			testInfo.put("License Number", "ILL123");
			
			Set<String> values1 = new HashSet<String>(userInfo.values());
			Set<String> values2 = new HashSet<String>(testInfo.values());
			
			boolean equal = values1.equals(values2);
			if(equal) {
				assertTrue(true);
			}
			else
				assertTrue(false);
		}
		
		//Testing Remote method to display all car models
		@Test
		public void testDisplayCarModels() throws Exception {
			Registry patreg1 = LocateRegistry.getRegistry("localhost",Const.RMI_PORT);
			RemoteInterface remote = (RemoteInterface) patreg1.lookup(Const.RMI_ID);
			List<String> Cars = remote.displayCarType();
		List<String> testCars = new ArrayList<String>();
		testCars.add("Cargo Van");
		testCars.add("SUV");
		testCars.add("Mini van");
		testCars.add("Wagon");
			if(testCars.equals(Cars))
			{
				assertTrue(true);
			}
			else
				assertTrue(false);
		}
		
		//Testing Remote method displayCars
		@Test
		public void testDisplayCars() throws RemoteException, NotBoundException {
			Registry patreg = LocateRegistry.getRegistry("localhost",Const.RMI_PORT);
			RemoteInterface remote = (RemoteInterface) patreg.lookup(Const.RMI_ID);
			ClientInfo cli= new ClientInfo();
			cli.carType="2";
			 
			List<String> userInfo = remote.displayCars(cli);
			List<String> testInfo = new ArrayList<String>();
			testInfo.add("2014 Honda CR-V");
			testInfo.add("2014 Buick Encore");
			testInfo.add("2014 Audi Q5");
			testInfo.add("2015 Infiniti QX50");
			testInfo.add("2014 Lexus RX 350");
			if(userInfo.equals(testInfo)){
				assertTrue(true);
			}
			else
				assertTrue(false);
		}
		
		//Testing Remote method call for referential integrity
		@Test
		public void testReferentialIntegrity() throws Exception {
			Registry patreg1 = LocateRegistry.getRegistry("localhost",Const.RMI_PORT);
			RemoteInterface remote1 = (RemoteInterface) patreg1.lookup(Const.RMI_ID);
			ClientInfo cli= new ClientInfo();
			cli.hours = "14";
			cli.bryId="bryid";
			cli.carName="Mazda Mazda5";
			ClientInfo cli2 = cli;
			Map<String, String> bankInfo = remote1.cost(cli,cli2);
			String objectID_1 = bankInfo.get("Client-1 objectID");
			String objectID_2 = bankInfo.get("Client-2 objectID");
			if(objectID_1.equalsIgnoreCase(objectID_2))
			{
				assertTrue(true);
			}
			else
				assertTrue(false);
		}
		
		//Testing Remote method call to display the correct cosT		
		@Test
		public void testRentalCost() throws Exception {
			Registry patreg1 = LocateRegistry.getRegistry("localhost",Const.RMI_PORT);
			RemoteInterface remote1 = (RemoteInterface) patreg1.lookup(Const.RMI_ID);
			ClientInfo cli= new ClientInfo();
			cli.hours = "14";
			cli.bryId="bryid";
			cli.carName="Mazda Mazda5";
			Map<String, String> bankInfo = remote1.cost(cli);
			Map<String, String> testInfo = new HashMap<String, String>();
			// Removing the Object ID ->key it will create new for e.a method call
			bankInfo.remove("Client objectID");
			testInfo.put("Client ID", "bryid");
			testInfo.put("Rented Hours", "14");
			testInfo.put("-------Bank", "Response---------");
			testInfo.put("Name", "Venkat");
			testInfo.put("Account Number", "***456");
			testInfo.put("Initial Account Balance", "5000");
			testInfo.put("Recent Activity", "Car Rentals: $230");
			testInfo.put("Current Account Balance:", "$4770");
			testInfo.put("-------------", "---------------");
			testInfo.put("Client-2 Booked Car", "Mazda Mazda5");
			
			
			Set<String> values1 = new HashSet<String>(bankInfo.values());
			Set<String> values2 = new HashSet<String>(testInfo.values());
			
			boolean equal = values1.equals(values2);
			if(equal) {
				assertTrue(true);
			}
			else
				assertTrue(false);
			}
		
		//Testing Remote method call to display the current details (from bank server)
		@Test
		public void testBankDetails() throws RemoteException, NotBoundException{
			Registry reg = LocateRegistry.getRegistry("localhost",
					BankInterface.RMI_PORT);
			BankInterface bankRemote = (BankInterface) reg
					.lookup(BankInterface.RMI_ID);

			int amt=3200;
			Map<String, String> bankDetails = bankRemote.deductAmount(amt);
			Map<String, String> userTest = new HashMap<String,String>();
			userTest.put("-------Bank", "Response---------");
			userTest.put("Name", "Venkat");
			userTest.put("Account Number", "***456");
			userTest.put("Initial Account Balance", "5000");
			userTest.put("Recent Activity", "Car Rentals: $3200");
			userTest.put("Current Account Balance: ",
					"$1800" );
			userTest.put("-------------", "---------------");
			Set<String> values1 = new HashSet<String>(bankDetails.values());
			Set<String> values2 = new HashSet<String>(userTest.values());
			
			boolean equal = values1.equals(values2);
			if(equal) {
				assertTrue(true);
			}
			else
				assertTrue(false);
		}
		
		
}
