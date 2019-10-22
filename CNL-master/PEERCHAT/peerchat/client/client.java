

import java.io.*;
import java.net.*;
import java.util.*;



public class client {
	
	private Socket socket;
	private int port;
	
	public client(int port)
	{
		this.port = port;
		
	}
	
	public void execute()
	{
		try
		{
		socket = new Socket("localhost",port);
		System.out.println("Client connected");
		readthread r1 = new readthread(socket,this);
		writethread w1 = new writethread(socket,this);
		r1.start();
		w1.start();
		System.out.println("YOO");
		
		}
		catch(Exception ex)
		{
			System.out.println("Excpetion in client");
			ex.printStackTrace();
		}
		
	}

	public static void main(String args[])
	{
		client client1 = new client(8000);
		client1.execute();
		

	}

}
