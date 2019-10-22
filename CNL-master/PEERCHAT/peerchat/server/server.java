

import java.io.*;
import java.net.*;
import java.util.*;

public class server {
	
	private ServerSocket serversocket;
	private int port;
	
	public server(int port)
	{
		this.port = port;
	}
	
	public void runserver()
	{
		System.out.println("Server Running");
		try {
		serversocket = new ServerSocket(port);
		Socket socket = serversocket.accept();
		System.out.println("Client connected");
		readthread r1 = new readthread(socket,this);
		writethread w1 = new writethread(socket,this);
		
		r1.start();
		w1.start();
		serversocket.close();
		System.out.println("YOO");
		
		}
		catch(Exception ex)
		{
			System.out.println("Error in server run");
			ex.printStackTrace();
		}
		
		
	}
	
	public static void main(String args[])
	{
		server s1 = new server(8000);
		s1.runserver();
		
	}

}
