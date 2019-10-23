package Controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Receive {

	private ServerSocket server = null;
	private Socket socket = null;
	private DataInputStream din = null;
	private DataOutputStream dout = null;

	public Receive()
	{
		try {

			server = new ServerSocket(5000);
			System.out.println("Server started");

			System.out.println("Waiting for client");

			socket = server.accept();
			System.out.println("Client connected to server");

			din = new DataInputStream(socket.getInputStream());
			dout = new DataOutputStream(socket.getOutputStream());

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error Caught In Receiver Constructor");
			System.err.println(e);
		}
	}

	public void dataTransfer()
	{
		try {

			int frames = din.readInt();
			int i;
			i=0;
			while(i < frames)
			{
				int k = din.readInt();
				if(k == i)
				{
					System.out.println("packet " + i + " is received");

					dout.writeInt(k);
					i++;
					Thread.sleep(500);
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Exception caught in data transfer");
			System.err.println(e);
		}
	}

	public void closeAll()
	{
		try {
			server.close();
			socket.close();
			din.close();
			dout.close();

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error Caught In Close All");
			System.err.println(e);

		}
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Receive receiver = new Receive();
		receiver.dataTransfer();
		receiver.closeAll();

	}

}
