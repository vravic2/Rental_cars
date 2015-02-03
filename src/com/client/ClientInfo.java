/*
 * Client-info class for the user details
 */

package com.client;
import java.io.Serializable;

public class ClientInfo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	public  String bryId;
	public String  hours;
	public String carType;
	public String carName;
	public String carModelName;
	public String carTypeName;
	public int selectedCar;
}
