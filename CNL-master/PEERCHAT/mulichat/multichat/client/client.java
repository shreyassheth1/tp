
import java.io.*;
import java.net.*;
import java.util.*;

public class client {
	
	private Socket socket;
	
	
	
	public client(int port)
	{
		try {
		socket = new Socket("localhost",port);
		writeThread w1 = new writeThread(socket,this);
		readThread r1 = new readThread(socket,this);
		w1.start();
		r1.start();
		
		}
		catch (UnknownHostException ex) {
			System.out.println("Server not found: " + ex.getMessage());
			ex.printStackTrace();
        }
		catch (IOException ex) {
 
			System.out.println("Error in client: " + ex.getMessage());
			ex.printStackTrace();
        }
	}
	
	public static void main(String args[])
	{
		client c1 = new client(8000);
	}
	
	

}
