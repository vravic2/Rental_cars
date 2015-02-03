//Server of rental car company
//Also acts as client to request implementing Bank server methods

package com.server;

import java.io.Serializable;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.bank.interf.BankInterface;
import com.client.ClientInfo;
import com.interf.*;

public class RemoteServer implements RemoteInterface, Serializable {

	private static final long serialVersionUID = 1L;

	public static void main(String args[]) throws RemoteException,
			AlreadyBoundException {
		try {
			RemoteServer rems = new RemoteServer();

			RemoteInterface stub = (RemoteInterface) UnicastRemoteObject
					.exportObject(rems, 0);

			Registry reg = LocateRegistry.createRegistry(Const.RMI_PORT);
			reg.bind(Const.RMI_ID, stub);
			System.err.println("Car-Rentals Server ready");
		} catch (Exception e) {
			System.err.println("Server exception: " + e.toString());
			e.printStackTrace();
		}

	}

	/*
	 * Remote method implementation to return the user-details(login)
	 * @see com.interf.RemoteInterface#displayUserDetails(com.client.ClientInfo)
	 */
	public Map<String, String> displayUserDetails(ClientInfo cli)
			throws RemoteException {
		// TODO Auto-generated method stub
		// Has all the patient info
		Map<String, String> userDetails = new LinkedHashMap<String, String>();
		userDetails.put("Name", "Venkat");
		userDetails.put("Age", "25");
		userDetails.put("BryId", "bry123");
		userDetails.put("Sex", "Male");
		userDetails.put("Insurance amount", "2000");
		userDetails.put("Bank account", "123456");
		userDetails.put("License Number", "ILL123");
		if (cli.bryId.equals(userDetails.get("BryId"))) {
		} else
			userDetails.clear();
		return userDetails;
	}

	/*
	 * Remote method implementation to return all car-types
	 * @see com.interf.RemoteInterface#displayCarType()
	 */
	@Override
	public List<String> displayCarType() throws RemoteException {
		// TODO Auto-generated method stub
		CarTypes car = new CarTypes();
		return car.execute();
	}

	/*
	 * Remote method implementation to return all cars
	 * @see com.interf.RemoteInterface#displayCars(com.client.ClientInfo)
	 */
	@Override
	public List<String> displayCars(ClientInfo cli) throws RemoteException {
		// TODO Auto-generated method stub
		CarTypes car = new CarTypes();
		return car.carList(cli.carType);
	}

	/*
	 * Remote method implementation to return billing details
	 * @see com.interf.RemoteInterface#cost(com.client.ClientInfo, com.client.ClientInfo)
	 */
	@Override
	public Map<String, String> cost(ClientInfo cli1, ClientInfo cli2)
			throws RemoteException, Exception {

		// Calculating the Rental charges
		int rentalCharge = Integer.valueOf(cli1.hours) * 15 + 20;

		// Summarizing the bill
		Map<String, String> billSummary = new LinkedHashMap<String, String>();
		billSummary.put("--------Rental cars", "Response----------");
		billSummary.put("Client-1 ID", cli1.bryId);
		billSummary.put("Client-1 Hours", cli1.hours);
		billSummary.put("Client-1 objectID",
				String.valueOf(System.identityHashCode(cli1)));
		billSummary.put("Client-1 Booked Car", cli1.carName);
		billSummary.put("Client-2 ID", cli2.bryId);
		billSummary.put("Client-2 Hours", cli2.hours);
		billSummary.put("Client-2 objectID",
				String.valueOf(System.identityHashCode(cli2)));
		billSummary.put("Client-2 Booked Car", cli2.carName);
		billSummary.put("-------------------", "---------------------\n");
		if (cli1.bryId == cli2.bryId)
			rentalCharge = rentalCharge * 2;
		billSummary
				.put("Total Rental charge is ", String.valueOf(rentalCharge));

		
		if(this.bankBalance(rentalCharge).isEmpty())
			billSummary.clear();
		else
			billSummary.putAll(this.bankBalance(rentalCharge));
		return billSummary;
	}
	/*
	 * Remote method implementation to return billing details
	 * @see com.interf.RemoteInterface#cost(com.client.ClientInfo)
	 */
	@Override
	public Map<String, String> cost(ClientInfo cli) throws RemoteException,
			Exception {

		int rentalCharge = Integer.valueOf(cli.hours) * 15 + 20;
		Map<String, String> billSummary = new LinkedHashMap<String, String>();
		billSummary.put("Client ID", cli.bryId);
		billSummary.put("Rented Hours", cli.hours);
		billSummary.put("Client objectID",
				String.valueOf(System.identityHashCode(cli)));
		billSummary.putAll(this.bankBalance(rentalCharge));
		billSummary.put("Client-2 Booked Car", cli.carName);
		return billSummary;
	}
    /*
     *Remote method call to Bank Server
     *@param: amt - Rental amount to charge the user from thier account 
     *@return MAP- bank account details
     */
	public Map<String, String> bankBalance(int amt) throws RemoteException,
			NotBoundException {

		// calls method to bank server

		Registry reg = LocateRegistry.getRegistry("localhost",
				BankInterface.RMI_PORT);
		BankInterface bankRemote = (BankInterface) reg
				.lookup(BankInterface.RMI_ID);

		Map<String, String> bankDetails = bankRemote.deductAmount(amt);
		return bankDetails;
	}
}
