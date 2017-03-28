import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import org.lwjgl.opengl.Display;

public class Server
{
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private ServerSocket server;
	private Socket connection;
	private Server_Vector2f data;
	
	public void startRunning()
	{
		try
		{
			server = new ServerSocket(3000, 100);
			try
			{
				waitForConnection();
				setUpStreams();
				whilePlaying();
			}
			catch(EOFException e)
			{
				System.out.println("EOFException thrown!!!!!!");
			}
			finally
			{
				System.out.println("Finally executed!!!");
				closeDown();
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private void waitForConnection() throws IOException
	{
		System.out.println("SERVER:  Waiting for someone to connect...\n");
		connection = server.accept();
		System.out.println("SERVER: Now connected to " + connection.getInetAddress().getHostAddress());
	}
	
	private void setUpStreams() throws IOException
	{
		output = new ObjectOutputStream(connection.getOutputStream());
		output.flush();
		input = new ObjectInputStream(connection.getInputStream());
		System.out.println("\nSERVER: Streams are now set up!\n");
	}
	
	private void whilePlaying() throws IOException
	{
		System.out.println("SERVER: Playing...");
		while(!(Display.isCloseRequested()))
		{
			System.out.println("SERVER: Main stuff...\n");
			Server_Main.getInput();
			Server_Main.update();
			Server_Main.render();
			try
			{
				System.out.println("SERVER: Getting and Sending...\n");
				//Stuff after this not running...
				data = (Server_Vector2f) input.readObject();
				Server_Main.client.setX(data.getX());
				Server_Main.client.setY(data.getY());
				sendCoordinates();
			}
			catch(ClassNotFoundException e)
			{
				System.out.println("\n Error: User sent unknown object");
			}
			catch(SocketException e)
			{
				System.out.println("SERVER: Client has left. Sorry!");
				Server_Main.cleanUp();
			}
			System.out.println("End of while executed...");
		}
		System.out.println("While exited");
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
	}
	
	private void sendCoordinates()
	{
		try
		{
			output.writeObject(new Server_Vector2f(Server_Main.server.getX(), Server_Main.server.getY()));
			output.flush();
		} catch (IOException e){e.printStackTrace();}
	}
}