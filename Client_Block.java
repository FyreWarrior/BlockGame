import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2f;

public class Client_Block
{
	private float x, y;
	
	public Client_Block(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	
	public void draw()
	{
		glPushMatrix();
		{
			glTranslatef(x, y, 0);
			glBegin(GL_QUADS);
			{
				glVertex2f(0, 0);
				glVertex2f(0, 32);
				glVertex2f(32, 32);
				glVertex2f(32, 0);
			}
			glEnd();
		}
		glPopMatrix();
	}

	public float getX()
	{
		return x;
	}

	public void setX(float x)
	{
		this.x = x;
	}

	public float getY()
	{
		return y;
	}

	public void setY(float y)
	{
		this.y = y;
	}
	
	public void increaseX(float amt)
	{
		this.x += amt;
	}
	
	public void increaseY(float amt)
	{
		this.y += amt;
	}
}