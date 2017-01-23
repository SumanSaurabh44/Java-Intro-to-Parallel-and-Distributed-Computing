//Server
import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Receiver {
	public static void main(String[] args) throws IOException {
		InetAddress group = InetAddress.getByName("239.1.2.3");
		MulticastSocket s = new MulticastSocket(3456);
		System.out.println("Joined group at 239.1.2.3 port 3456");
		s.joinGroup(group);
		ArrayList<String> Array = new ArrayList<String>();
		int c = 0;
		int arrayIndex = 0;
		int total = 0;           
		while (c == 0)
		{
			byte[] buffer = new byte[100];
			DatagramPacket recvPacket = new DatagramPacket(buffer, buffer.length);    			
			s.receive(recvPacket);  			
			Array.add(new String(buffer).trim());
			if (Integer.parseInt(Array.get(arrayIndex).toString()) == 0) {
				c = 1;
			} else {
				arrayIndex = arrayIndex + 1;
			}
		}
		while (Array.size() != 0) {
			total += Integer.parseInt(Array.get(arrayIndex).toString());
			Array.remove(arrayIndex);
			arrayIndex = arrayIndex - 1;
		}
		System.out.println("Last client sent input number as Zero to trigger addition of all input numbers");
		System.out.println("The Sum is:" + total);
		s.close();
	} 
}



//The client class:

import java.io.*;
import java.net.*;
import java.util.*;

public class Sender {
	public static void main(String[] args) throws IOException {
		System.out.println("Sender.");		
		InetAddress group = InetAddress.getByName("239.1.2.3");
		MulticastSocket s = new MulticastSocket(3456);
		s.setTimeToLive(32);
		s.joinGroup(group);
		System.out.println("Joined group at 239.1.2.3 port 3456");
		System.out.print("Enter a number: ");
		Scanner keyboard = new Scanner(System.in);		
		String message = keyboard.nextLine();
		byte[] buffer = message.getBytes();
		DatagramPacket sendPacket=new DatagramPacket(buffer,buffer.length,group,3456);	
		s.send(sendPacket);    		
		s.close();    		    
	}
}

