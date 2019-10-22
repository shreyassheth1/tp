

import java.io.*;
import java.net.*;
import java.util.*;

public class readthread extends Thread{
	
	private Socket socket;
	private client ss;
	
	public readthread(Socket socket,client ss)
	{
		this.socket = socket;
		this.ss = ss;
	}
	
	public void run()
	{
		try
		{
		InputStream input = socket.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		String msg;
		do 
		{
			msg = reader.readLine();
			System.out.println("client:-" + msg);
			
		}while(!msg.equals("bye"));
		
		System.out.println("OOut");
		
		socket.close();
		System.exit(0);
		
		
		}
		catch(Exception ex)
		{
			System.out.println("read thread error");
			ex.printStackTrace();
		}
		
	}

}
