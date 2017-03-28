import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;

public class Client
{
	//Variables
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private String serverIP;
	private Socket connection;
	private Client_Vector2f data;
	
	//Constructor
	public Client(String host)
	{
		serverIP = host;
	}
	
	//Starts the connection
	public void startRunning()
	{
			try
			{
				connectToServer();
				setUpStreams();
				whileChatting();
			}
			catch(EOFException e)
			{
				System.out.println("Problem!");
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
			finally
			{
				closeDown();
			}
	}
	//Connecting to the server
	private void connectToServer() throws IOException
	{
		try
		{
			//Initialising the connection
			connection = new Socket(InetAddress.getByName(serverIP), 3000);
			//A Check
			System.out.print("CLIENT: Connected to Server!");
		}
		catch(ConnectException e)
		{
			System.out.println("Server not online!");
			closeDown();
			Client_Main.cleanUp();
		}
	}
	
	private void setUpStreams() throws IOException
	{
		try
		{
			output = new ObjectOutputStream(connection.getOutputStream());
			output.flush();
			input = new ObjectInputStream(connection.getInputStream());
		}
		catch(NullPointerException e)
		{
			System.out.print("");
		}
	}
	
	private void whileChatting() throws IOException
	{
		do
		{
			try
			{
				data = ((Client_Vector2f) input.readObject());
				Client_Main.server.setX(data.getX());
				Client_Main.server.setY(data.getY());
				sendCoordinates();
			}
			catch(ClassNotFoundException e)
			{
				e.printStackTrace();
			}
			catch(SocketException e)
			{
				System.out.println("\nCLIENT: Connection Closed");
				Client_Main.cleanUp();
			}
			catch(NullPointerException e)
			{
				System.out.print("");
			}
			Client_Main.getInput();
			Client_Main.update();
			Client_Main.render();
		}
		while(true);
	}
	
	public void closeDown()
	{
		try
		{
			output.close();
			input.close();
			connection.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		catch(NullPointerException e)
		{
			System.out.print("");
		}
	}
	
	private void sendCoordinates()
	{
		try
		{
			output.writeObject(new Client_Vector2f(Client_Main.client.getX(), Client_Main.client.getY()));
			output.flush();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}