import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import static org.lwjgl.opengl.GL11.*;

public class Server_Main
{
	public static Server_Block server, client;
	public static Server serverToClient;
	
	public static void main(String[] args)
	{
		initDisplay();
		initGL();
		
		initEntities();
		
		serverToClient.startRunning();
		
		cleanUp();
	}
	
	public static void initDisplay()
	{
		Display.setTitle("Game: Server");
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
	}
	
	public static void initEntities()
	{
		serverToClient = new Server();
		server = new Server_Block(0, Display.getHeight() - 32);
		client = new Server_Block(Display.getWidth() - 32, 0);
	}
	
	public static void getInput()
	{
		if(Keyboard.isKeyDown(Keyboard.KEY_W))
			server.increaseY(1);
		if(Keyboard.isKeyDown(Keyboard.KEY_A))
			server.increaseX(-1);
		if(Keyboard.isKeyDown(Keyboard.KEY_S))
			server.increaseY(-1);
		if(Keyboard.isKeyDown(Keyboard.KEY_D))
			server.increaseX(1);
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
		serverToClient.closeDown();
		System.exit(0);
	}
}