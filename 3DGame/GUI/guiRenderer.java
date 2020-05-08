package GUI;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import loaders.modelLoader;
import models.TDModel;
import toolbox.Maths;

public class guiRenderer {

	private final TDModel quad;
	private final modelLoader loader = new modelLoader();
	private final GuiShader shader = new GuiShader();
	private List<guiTexture> guis = new ArrayList<guiTexture>();
	
	public guiRenderer() {
		float[] positions = {-1, 1, -1, -1, 1, 1, 1, -1};
		quad = loader.loadToVao(positions);
	}
	
	public void addGuiObject(guiTexture obj) {
		guis.add(obj);
	}
		
	public void removeGuiObject(guiTexture obj) {
		guis.remove(obj);
	}
	
	public void render() {
		shader.start();
		GL30.glBindVertexArray(quad.getVAOID());
		GL20.glEnableVertexAttribArray(0);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		for (guiTexture texture: guis) {
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());
			Matrix4f transformationMatrix = Maths.createTransformationMatrix(texture.getLocation(), texture.getScale());
			shader.loadTransformation(transformationMatrix);
			GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, quad.getVertexCount());
		}
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_BLEND);
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
		shader.stop();
	}
}
