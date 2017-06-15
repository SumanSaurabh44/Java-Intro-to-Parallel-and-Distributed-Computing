/**************************************************My Server*****************************************************/
//MyServer :

import java.io.*;
import java.net.*;

public class MyServer {
	public static void main(String[] args) throws IOException {
		//

		try {
			int port = 16790;
			BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
			ServerSocket serverSocket = new ServerSocket(port);
			Socket clientSocket = serverSocket.accept();
			System.out.println("server started"); 
			String message = "Client started\n";
			while (true) {
				DataOutputStream dataOut = new DataOutputStream(clientSocket.getOutputStream());
				dataOut.writeUTF(message); 
				DataInputStream dataIn = new DataInputStream(clientSocket.getInputStream());
				String clientMessage = dataIn.readUTF();
				if (clientMessage.equals("bye"))
					break;
				System.out.println(clientMessage);
				System.out.print("Enter a line for the Server: ");
				message = console.readLine();
			}
			clientSocket.close();
		}
		catch (IOException e) {
			System.out.println("An error has occured: " + e);
			System.exit(0);
		}
	}
}




/***********************************************************MY Client*********************************************/




//MyClient:

import java.io.*;
import java.net.*;

public class MyClient {
	public static void main(String[] args){
		String host = "localhost"; // vulcan.seidenberg.pace.edu
		int port = 16790; 
		try {
			BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
			Socket clientSocket = new Socket(InetAddress.getByName(host),port);
			while (true) {
				DataInputStream dataIn = new DataInputStream(clientSocket.getInputStream());
				System.out.println(dataIn.readUTF());
				System.out.print(" Enter a line or a bye to quit from the client:");
				String message = console.readLine();
				DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
				out.writeUTF(message);
			}
		}
		catch (EOFException e) {
			//Connection is closed by server.
		}
		catch (IOException e) {
			System.out.println("An error has occured: " + e);
			System.exit(0);
		}
	}
}
