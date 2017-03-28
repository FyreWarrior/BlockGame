import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import static org.lwjgl.opengl.GL11.*;

public class Client_Main
{
	public static Client_Block client, server;
	public static Client clientToServer;
	
	public static void main(String[] args)
	{
		initDisplay();
		initGL();
		
		initEntities();
		
		clientToServer.startRunning();
		
		cleanUp();
	}
	
	public static void initDisplay()
	{
		Display.setTitle("Game: Client");
		try
		{
			Display.setDisplayMode(new DisplayMode(500, 500));
			Display.create();
			Display.setVSyncEnabled(true);
			Keyboard.create();
			Mouse.create();
		}
		catch(LWJGLException e){e.printStackTrace();}
	}
	
	public static void initGL()
	{
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, Display.getWidth(), 0, Display.getHeight(), -1, 1);
		glMatrixMode(GL_MODELVIEW);
		
		glEnable(GL_TEXTURE_2D | GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		glClearColor(0, 0, 0, 1);
		
		glDisable(GL_DEPTH_TEST);
	}
	
	public static void initEntities()
	{
		clientToServer = new Client("192.168.1.2");
		server = new Client_Block(0, Display.getHeight() - 32);
		client = new Client_Block(Display.getWidth() - 32, 0);
		clientToServer.startRunning();
	}
	
	public static void getInput()
	{
		if(Keyboard.isKeyDown(Keyboard.KEY_W))
			client.increaseY(1);
		if(Keyboard.isKeyDown(Keyboard.KEY_A))
			client.increaseX(-1);
		if(Keyboard.isKeyDown(Keyboard.KEY_S))
			client.increaseY(-1);
		if(Keyboard.isKeyDown(Keyboard.KEY_D))
			client.increaseX(1);
	}
	
	public static void update()
	{
		
	}
	
	public static void render()
	{
		glClear(GL_COLOR_BUFFER_BIT);
		glLoadIdentity();
		
		server.draw();
		client.draw();
		
		Display.update();
		Display.sync(60);
	}
	
	public static void cleanUp()
	{
		Display.destroy();
		Keyboard.destroy();
		Mouse.destroy();
		clientToServer.closeDown();
		System.exit(0);
	}
}