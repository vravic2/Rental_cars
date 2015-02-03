/**
 * The Client program implements a distributed application 
 * that simply interacts with the servers 
 * car-rentals[server1.java] && Bank[server2.java].
 * 
 * @author  vravic2@uic.edu
 * @since   2014-09-15 
 */

package com.client;

import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.interf.*;

public class Client implements Serializable {

	private static final long serialVersionUID = 1L;
	static Map<String, String> bankInfo;

	public static void main(String[] args) throws RemoteException,
			NotBoundException {
		System.out.println("\t-----------------------------------------------");
		System.out.println("\n\t Welcome to Brylope Car Rentals Company !!!\n");
		System.out.println("\t-----------------------------------------------");

		ClientInfo cli = new ClientInfo();

		try {
			int i = 0;
			List<String> carTypes;
			Registry patreg = LocateRegistry.getRegistry("localhost",
					Const.RMI_PORT);
			RemoteInterface remote = (RemoteInterface) patreg
					.lookup(Const.RMI_ID);
			while (i == 0) {
				System.out
						.println("\n\t Please Enter your Rentals Id (try bry123)\n");
				Scanner user_input_scanner = new Scanner(System.in);
				cli.bryId = user_input_scanner.next();

				/*
				 * Remote method call to Call Rentals server
				 * 
				 * @param clientinfo object
				 * 
				 * @return Linked hash map - userdetails
				 */
				Map<String, String> userInfo = remote.displayUserDetails(cli);

				if (!userInfo.isEmpty()) {
					System.out.println("Welcome to Brylope Rentals");
					for (Map.Entry<String, String> entry : userInfo.entrySet()) {
						System.out.printf("\t%-30s \t\t%-30s %n",
								entry.getKey(), entry.getValue());
						System.out.println();
					}
					
					do{
						System.out.println("Select your car-Type");
						System.out
								.println("-----------------------------------------------");
	
						/*
						 * Remote method call to Call Rentals server
						 * 
						 * @param clientinfo object
						 * 
						 * @return List- All car models available on server
						 */
						carTypes = remote.displayCarType();
						int j = 0;
						for (String s : carTypes) {
							j++;
							System.out.println("\t\t" + j + " - " + s);
						}
						cli.carType = user_input_scanner.next();
					}while(Integer.valueOf(cli.carType)<1 || Integer.valueOf(cli.carType)>4);
					/*
					 * Remote method call to Call Rentals server
					 * 
					 * @param selected car type
					 * 
					 * @return List- All car models available on server
					 */
					List<String> Cars = remote.displayCars(cli);
					cli.carModelName = carTypes.get(Integer.valueOf(cli.carType)-1);
					do{
						System.out
								.println("Please select a car for the selected model :"
										+ cli.carModelName);
						System.out
								.println("-----------------------------------------------");
						int k = 0;
						for (String c : Cars) {
							k++;
							System.out.println("\t\t" + k + " - " + c);
						}
						Scanner in = new Scanner(System.in);
						cli.selectedCar = in.nextInt();
					}while(Integer.valueOf(cli.selectedCar)<1 || Integer.valueOf(cli.selectedCar)>5);
					
					cli.carName = Cars.get(cli.selectedCar - 1);
					// get the rented hours from the user
					do{
					System.out
							.println("How many hours(1-72) you need our rented car -'"
									+ cli.carName + "' ?\n");

					cli.hours = user_input_scanner.next();
					}while(Integer.valueOf(cli.hours)>72 ||Integer.valueOf(cli.hours)<1);
					
					System.out
							.println("Do you wanna book another car (same-model/time 20% discount)\n Reply with Y/N (yes/no)  (select 'y' for referential integrity)");
					String refInt = user_input_scanner.next();

					/*
					 * Remote method call to Call Rentals server && Bank Server
					 * (REFERENTIAL INTEGRITY)
					 * 
					 * @param clientinfo objects (method overloading on the car
					 * rentals server)
					 * 
					 * @return Map- bill summary from car rentals server &&
					 * account summary from bank server
					 */
					if (refInt.equals("y")) {
						// copy of the client-info object
						ClientInfo cli2 = cli;
						bankInfo = remote.cost(cli, cli2);
					} else {
						bankInfo = remote.cost(cli);
					}

					System.out
							.println("Rental charges will be deducted automatically from your linked bank account\n");
					System.out
							.println("\t-----------------------------------------------");
					for (Map.Entry<String, String> bankentry : bankInfo
							.entrySet()) {
						System.out.printf("\t%-30s \t\t%-30s %n",
								bankentry.getKey(), bankentry.getValue());
						System.out.println();
					}
					System.out
					.println("\tThank you for Renting our car!! Happy journey !!");
					System.out
							.println("\t-----------------------------------------------");
					i++;
				} else
					System.out.println("Invalid-Id please try - bry123");
			}
		} catch (Exception e) {
			System.err.println("Client exception!!!");
			e.printStackTrace();
		}
	}
}
