package com.interf;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import com.client.ClientInfo;

public interface RemoteInterface extends Remote{
	
	public Map<String, String> displayUserDetails(ClientInfo cli) throws RemoteException;
	public List<String> displayCarType()throws RemoteException;
	public Map<String,String> cost(ClientInfo cli1,ClientInfo cli2) throws RemoteException, Exception;
	public Map<String,String> cost(ClientInfo cli) throws RemoteException, Exception;
	List<String> displayCars(ClientInfo cli) throws RemoteException;

}
