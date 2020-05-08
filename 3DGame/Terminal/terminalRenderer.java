package Terminal;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.util.vector.Vector2f;

import postProcessor.ImageRenderer;
import toolbox.Maths;

public class terminalRenderer {

	private final terminalShader shader = new terminalShader();
	private ImageRenderer renderer;
	
	public terminalRenderer() {
		renderer = new ImageRenderer();
	}
	
	public void render(int colorTexture) {
		shader.start();
		shader.setIResolution();
		shader.setiTime();
		shader.setTransformationMatrix(Maths.createTransformationMatrix(new Vector2f(0,0), new Vector2f(1,1)));
		//testing if texture stuff is the problem
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, colorTexture);
		
		renderer.renderQuad();
		shader.stop();
	}
	
	public void cleanUp() {
		shader.cleanUp();
		renderer.cleanUp();
	}
}
