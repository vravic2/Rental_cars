package com.server;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
//import java.util.HashMap;
import java.util.List;
//import java.util.Map;






import java.util.Map;

import com.bank.interf.BankInterface;
import com.interf.Const;
import com.interf.RemoteInterface;

public class CarTypes extends RemoteServer  {
	// Map<String,List<String>> carTypeList = new HashMap<String,List<String>>();
	 List<String> carTypes = new ArrayList<String>();
	 List<String> carList = new ArrayList<String>();
	 public static int rentalAmount;
	
	public CarTypes(){
		this.carTypes.add("Cargo Van");
		this.carTypes.add("SUV");
		this.carTypes.add("Mini van");
		this.carTypes.add("Wagon");
	}
	public CarTypes(String hours){
		
		this.rentalAmount = Integer.parseInt(hours)*22+45;
	}
	
	public List<String> carList(String type){
		switch(type){
		case "1": 
			this.carList.add("GMC Savana rental");
			this.carList.add("2015 Ford transit");
			this.carList.add("Ford Cargo Van");
			break;
			
		case "2":
			this.carList.add("2014 Honda CR-V");
			this.carList.add("2014 Buick Encore");
			this.carList.add("2014 Audi Q5");
			this.carList.add("2015 Infiniti QX50");
			this.carList.add("2014 Lexus RX 350");
			break;
			
		case "3":
			this.carList.add("Honda Odyssey");
			this.carList.add("Toyota Sienna");
			this.carList.add("Dodge Grand Caravan");
			this.carList.add("Mazda Mazda5");
			this.carList.add("Nissan Quest");
			break;
			
		case "4":
			this.carList.add("Subaru Outback");
			this.carList.add("Volkswagen Jetta SportWagen");
			this.carList.add("Acura TSX Sport Wagon");
			this.carList.add("Toyota Prius V");
			this.carList.add("Audi Allroad");
			break;
		}
		return carList;
	}
	 public List<String> execute() {
	        return carTypes;
	    }

	
	
}
