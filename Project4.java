1. Project4ServerInterface

import java.rmi.Remote;
public interface Project4ServerInterface extends Remote {
	public int calculateSum(int n ) throws java.rmi.RemoteException;
}


2. class CalculateSumServerImpl

import java.rmi.*;
import java.rmi.server.*;

public class CalculateSumServerImpl extends UnicastRemoteObject implements Project4ServerInterface {
	public CalculateSumServerImpl() throws RemoteException {
		super( );
	}
	public int calculateSum(int number) 
			throws RemoteException {
		int sum = 0;
		for (int i=0 ; i<=number ; i++)
		{
			sum =sum+ i;
		}
		return  sum;
	}

}


3. class Project4Server:


import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.io.IOException;
import java.net.*;

public class Project4Server{
	public static void main(String args[]) throws IOException{     
		int port = 16790; 
		String host = "localhost";
		CalculateSumServerImpl exportedObj = new CalculateSumServerImpl();
		startRegistry(port);
		String registryURL = "rmi://" + host + ":" + port + "/hello";
		LocateRegistry.getRegistry(port);
		Naming.rebind(registryURL, exportedObj);
		System.out.println("Hello Server ready.");
	}

	private static void startRegistry(int port)throws RemoteException{
		try{
			Registry registry = LocateRegistry.getRegistry(port);
			registry.list( ); 
		}catch (RemoteException e){ 
			System.out.println ("RMI registry cannot be located at port: " + port);
			Registry registry = LocateRegistry.createRegistry(port);
			System.out.println("RMI registry created at port: " + port);
		}
	}


}


The client class and interface:

1.	Project4ServerInterface

import java.rmi.Remote;
public interface Project4ServerInterface extends Remote {
	public int calculateSum(int n ) throws java.rmi.RemoteException;
}


2.	class Project4Client

import java.io.IOException;
import java.rmi.*;
import java.util.*;

public class Project4Client{
	@SuppressWarnings("resource")
	public static void main(String args[])throws IOException, NotBoundException{
		int port = 16790;         
		String host = "localhost";
		Scanner in=new Scanner(System.in);
		String registryURL = "rmi://" + host + ":" + port + "/hello";
		Project4ServerInterface h = (Project4ServerInterface)Naming.lookup(registryURL);
		System.out.println("Enter a positive natural integer:");
		int num=in.nextInt();
		System.out.println("Lookup completed " );
		int message = h.calculateSum(num);
		System.out.println("The total sum of first "+ num +" positive natural number is: " + message);
	}
}




