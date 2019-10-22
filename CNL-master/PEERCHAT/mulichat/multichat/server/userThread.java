

import java.net.*;
import java.io.*;


public class userThread extends Thread {
	
	private Socket socket;
	private server ss;
	private PrintWriter writer;
	
	public userThread(Socket socket,server ss)
	{
		this.socket = socket;
		this.ss = ss;
	//	System.out.println("in userThread");
		
	}
	
	public void send(String send_msg)
	{
		writer.println(send_msg);
	}
	
	public void run()
	{
		try {
		InputStream input = socket.getInputStream();
		OutputStream output = socket.getOutputStream();
		String msg;
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		writer = new PrintWriter(output,true);
		String username = reader.readLine();
		ss.setusername(username);
		
		do
		{
			msg = reader.readLine();
			String smsg = username + ":-" + msg;
			ss.broadcast(smsg,this);
			
		
		}while(!msg.equals("stop"));

		ss.removeuser(username, this);
		socket.close();
		
		}
		catch (IOException ex) {
 
			System.out.println("Error in userThread: " + ex.getMessage());
			ex.printStackTrace();
        }
	}

}
