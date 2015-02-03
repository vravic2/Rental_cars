package com.server2;
//Bank server implements the Bank Interface
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedHashMap;
import java.util.Map;

import com.bank.interf.BankInterface;

public class server implements BankInterface, Serializable {

	/**
	 *  Bank Server interacts with Car-Rentals to pay their rental amount
	 */
	private static final long serialVersionUID = 1L;
	public int bankBalance = 5000;

	public static void main(String[] args) throws RemoteException {
		// TODO Auto-generated method stub
		try {
			server serv = new server();

			BankInterface stub = (BankInterface) UnicastRemoteObject
					.exportObject(serv, 0);

			Registry reg = LocateRegistry
					.createRegistry(BankInterface.RMI_PORT);
			reg.bind(BankInterface.RMI_ID, stub);
			System.err.println("Bank Server ready");
		} catch (Exception e) {
			System.err.println("Server exception: " + e.toString());
			e.printStackTrace();
		}

	}
	/*
	 * Remote method implementation returns the user account Details
	 * @return- HASHMAP
	 * @see com.bank.interf.BankInterface#deductAmount(int)
	 */
	@Override
	public Map<String, String> deductAmount(int amt) throws RemoteException {
		// TODO Auto-generated method stub
		System.out.println();
		Map<String, String> userAccnt = new LinkedHashMap<String, String>();
		userAccnt.put("-------Bank", "Response---------");
		userAccnt.put("Name", "Venkat");
		userAccnt.put("Account Number", "***456");
		userAccnt.put("Initial Account Balance", "5000");
		userAccnt.put("Recent Activity", "Car Rentals: $" + amt);
		userAccnt.put("Current Account Balance: ",
				"$" + String.valueOf(bankBalance - amt));
		userAccnt.put("-------------", "---------------");
		return userAccnt;
	}
}
