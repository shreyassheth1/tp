
import java.io.*;
import java.net.*;
import java.util.*;

public class readThread extends Thread{
	
	private Socket socket;
	private client client1;
	private BufferedReader reader;
	
	
	public readThread(Socket socket,client client1)
	{
		
		try {
		this.socket = socket;
		this.client1 = client1;
		
		InputStream input = socket.getInputStream();
		
		
		reader = new BufferedReader(new InputStreamReader(input));
		}
		catch (IOException ex) {
 
			System.out.println("Error in readThread: " + ex.getMessage());
			ex.printStackTrace();
        }
		
		
		
	}
	
	public void run()
	{
		
		while(true)
		{
			try
			{
			String msg = reader.readLine();
			System.out.println(msg);
			}
			catch(Exception e)
			{
				System.out.println("Error in run");
				e.printStackTrace();
				break;
			}
		}
		
	}
	

}
