

import java.io.*;
import java.net.*;
import java.util.*;

public class writeThread extends Thread {
	
	private Socket socket;
	private PrintWriter writer;
	private client client1;
	
	public writeThread(Socket socket,client client1)
	{

		this.socket = socket;
		this.client1 = client1;
		try {
			
			
			OutputStream output = socket.getOutputStream();
			
			
			writer = new PrintWriter(output,true);
			}
			catch (IOException ex) {
				System.out.println("Error getting output stream: " + ex.getMessage());
				ex.printStackTrace();
			}
	}
	
	public void run()
	{
		
		Console console = System.console();
		System.out.println("Enter username");
		String username = console.readLine();
		writer.println(username);
		
		String msg;
		do
		{
			
			 msg = console.readLine();
			writer.println(msg);
		}while(!msg.equals("stop"));
		
		try {
			socket.close();
			System.out.println("Quit");
			}
			catch (IOException ex) {
 
				System.out.println("Error CLosing socket: " + ex.getMessage());
				ex.printStackTrace();
			}
		
		
	}
	

}
