package Controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Sende {

	private DataInputStream din = null;
	private DataOutputStream dout = null;
	private Socket socket = null;
	public Scanner sc = new Scanner(System.in);

	public Sende()
	{
		String address = InetAddress.getLoopbackAddress().getHostAddress();

		try {

			socket = new Socket(address, 5000);
			din = new DataInputStream(socket.getInputStream());
			dout = new DataOutputStream(socket.getOutputStream());

			goBackN();

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Exception caught in constructor");
			System.err.println(e);
		}

	}

	public void goBackN()
	{
		try {

			boolean err;

			System.out.println("Enter your choice :\n 1.with error\n 2.without error");
			int ch = sc.nextInt();

			if(ch == 1)
			{
				err = true;
			}
			else
			{
				err = false;
			}

			int win = 4;

			System.out.print("Enter no of frames : ");
			int frames = sc.nextInt();
			dout.writeInt(frames);

			int i=0;
			int t = -1;

			while(i < frames)
			{
				if(err)
				{
					System.out.print("Enter no of frame you want to lose : ");
					t = sc.nextInt();
				}
				for(int j=0; (i+j)<frames && j<win; j++)
				{
					System.out.println("packet " + (i+j) + " sent");
					if(j!=t)
					{
						dout.writeInt(i+j);
						Thread.sleep(500);
					}
				}
				Thread.sleep(300);

				System.out.println("\n\n");

				int cnt = 0;

				for(int j=0; (i+j)<frames && j<win; j++)
				{
					socket.setSoTimeout(3000);
					try
					{
						if(din.readInt() == i+j)
						{
							System.out.println("Acknowlwdgement for packet " + (i+j) + " is received");
							Thread.sleep(500);
							cnt++;
						}
						else
						{
							Thread.sleep(500);
						}
					}
					catch (Exception e) {
						// TODO: handle exception
						if(i+cnt < frames)
						{
							System.out.println("Acknowlwdgement for packet " + (i+cnt) + " is not received");
							System.out.println("\nResending\n");
							//break;
						}
						j = win;
					}
				}
				dout.flush();
				i += cnt;
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
			din.close();
			dout.close();
			socket.close();
			sc.close();

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Exception caught in closeAll");
			System.err.println(e);
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Sende sender = new Sende();
		sender.closeAll();

	}

}
