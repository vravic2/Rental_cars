package com.bank.interf;
//Interface for Bank Server

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

public interface BankInterface extends Remote {

	public static final String RMI_ID = "MY_RM";
	public static final int RMI_PORT = 402;
	public Map<String,String> deductAmount(int amt)throws RemoteException;
}
