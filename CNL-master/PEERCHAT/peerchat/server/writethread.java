


import java.io.*;
import java.net.*;
import java.util.*;

public class writethread extends Thread {
	
	private Socket socket;
	private server ss;
	
	public writethread(Socket socket,server ss)
	{
		this.socket = socket;
		this.ss = ss;
	}
	
	public void run()
	{
		try
		{
		OutputStream output = socket.getOutputStream();
		PrintWriter writer = new PrintWriter(output,true);
		Console console = System.console();
		
		String msg;
		
		do
		{
		
		msg = console.readLine();
		writer.println(msg);
		}while(!msg.equals("bye"));

		socket.close();
		System.exit(0);
		
		}
		catch(Exception ex)
		{
			System.out.println("Error in writeThread");
			ex.printStackTrace();
		}
		
	}

}
