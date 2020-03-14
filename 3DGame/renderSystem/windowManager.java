package renderSystem;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

public class windowManager {
	
	private static final int WIDTH = 1200;
	private static final int HEIGHT = 700;
	private static final int MAX_FPS = 30;

	public static void createWindow() {
		
		ContextAttribs attributes = new ContextAttribs(3,2)
		.withForwardCompatible(true)
		.withProfileCore(true);
		
		try {
			Display.setDisplayMode(new DisplayMode(WIDTH,HEIGHT));
			Display.create(new PixelFormat(), attributes);
		}catch(LWJGLException e) {
			Display.destroy();
			e.printStackTrace();		
		}
		
		GL11.glViewport(0, 0, WIDTH, HEIGHT);
	}
	
	public static void closeWindow() {
		Display.destroy();
	}
	
	public static void updateWindow() {
		Display.sync(MAX_FPS);
		Display.update();
	}
}
