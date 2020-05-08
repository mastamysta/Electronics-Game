package renderSystem;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

public class windowManager {
	
	private static final int WIDTH = 1200;
	private static final int HEIGHT = 700;
	private static final int MAX_FPS = 30;
	
	private static long lastFrameTime;
	private static float delta;

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
		lastFrameTime = getCurrentTime();
	}
	
	public static void closeWindow() {
		Display.destroy();
	}
	
	public static void updateWindow() {
		Display.sync(MAX_FPS);
		Display.update();
		long currentFrameTime = getCurrentTime();
		delta = (currentFrameTime - lastFrameTime) / 1000f;
		lastFrameTime = currentFrameTime;
	}

	public static float getFrameTime() {
		return delta;
	}
	
	private static long getCurrentTime() {
		return Sys.getTime() * 1000 / Sys.getTimerResolution();
	}

}
