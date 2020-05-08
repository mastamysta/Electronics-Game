package postProcessor;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import Terminal.terminalRenderer;
import loaders.modelLoader;
import models.TDModel;

public class PostProcessing {
	
	private static final float[] POSITIONS = { -1, 1, -1, -1, 1, 1, 1, -1 };	
	private static TDModel quad;
	private static terminalRenderer renderer;

	public static void init(modelLoader loader){
		quad = loader.loadToVao(POSITIONS);
		renderer = new terminalRenderer();
	}
	
	public static void doPostProcessing(int colourTexture){
		start();
		renderer.render(colourTexture);
		end();
	}
	
	public static void cleanUp(){
		renderer.cleanUp();
	}
	
	private static void start(){
		GL30.glBindVertexArray(quad.getVAOID());
		GL20.glEnableVertexAttribArray(0);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
	}
	
	private static void end(){
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
	}


}
