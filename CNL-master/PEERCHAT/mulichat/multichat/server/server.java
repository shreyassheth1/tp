
import java.util.*;
import java.io.*;
import java.net.*;

public class server {
	
	private int port;
	public Set<userThread> clients = new HashSet<>();
	public Set<String> username  = new HashSet<>();
	
	public server(int port)
	{
		this.port = port;
	}

	public void runserver()
	{

		try
		{
			ServerSocket serversocket = new ServerSocket(port);
			System.out.println("Server running");
			
			while(true)
			{
				Socket socket = serversocket.accept();
				System.out.println("New user connected");
				userThread u1 = new userThread(socket,this);
				clients.add(u1);
				
				u1.start();
				
			}
		}
		catch (IOException ex) {
 
			System.out.println("Error in runserver: " + ex.getMessage());
			ex.printStackTrace();
        }
	}
	
	public void setusername(String username)
	{
		this.username.add(username);
	}
	
	public void broadcast(String msg , userThread current_user)
	{
		for(userThread user : clients)
		{
			if(current_user != user)
			{
				user.send(msg);
			}
		}
	}

	public void removeuser(String uname,userThread t1)
	{
		boolean removed = username.remove(uname);
        if (removed) {
			clients.remove(t1);
            System.out.println("YO");
        }
	}
	
	
	
	public static void main(String args[])
	{
		server s1 = new server(8000);
		s1.runserver();
		
	}

}
