package terrain;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import models.TDModel;
import models.camera;
import models.entity;
import models.texturedModel;
import shaders.staticShader;
import toolbox.Maths;

public class terrainRenderer{
	
	private terrain terrain;
	
	public void bindTerrain(terrain ter) {
		this.terrain = ter;	
	}
	
	public void unbindTerrain() {
		this.terrain = null;
	}
	
	public void render(staticShader shader, camera cam) {
		texturedModel texMod = terrain.getTexturedTerrainModel();
		TDModel tdm = texMod.getTdm();
		GL30.glBindVertexArray(tdm.getVAOID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		Matrix4f transformationMatrix = Maths.createTransformationMatrix(new Vector3f(terrain.getX(),terrain.getY() ,terrain.getX()),1,0,0,0);
		shader.loadTransformationMatrix(transformationMatrix);
		shader.loadviewMatrix(Maths.createViewMatrix(cam));
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texMod.getTxtr().getTextureID());
		GL11.glDrawElements(GL11.GL_TRIANGLES, tdm.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
			
	}
}
