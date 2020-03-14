package models;
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

import shaders.staticShader;
import toolbox.Maths;

public class entityRenderer{
	
	private Map<texturedModel, ArrayList<entity>>  map = new HashMap<texturedModel, ArrayList<entity>>();
	private List<texturedModel> textureList = new ArrayList<texturedModel>();
	
	public void prep() {	 
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glClearColor(0, 0, 1, 1);
	}
	
	public void addEntity(entity ent) {
		texturedModel texMod = ent.getTexMod();
		if (!map.containsKey(texMod)){
			ArrayList<entity> newList = new ArrayList<entity>();
			newList.add(ent);
			map.put(texMod, newList);
			textureList.add(texMod);
		} else {			
			map.get(texMod).add(ent);
		}		
	}
	
	public void removeEntity(entity ent) {
		texturedModel texMod = ent.getTexMod();
		if (map.containsKey(texMod)){
			map.get(texMod).remove(ent);
			textureList.remove(texMod);
		}
	}
	
	public void render(staticShader shader, camera cam) {
		for(texturedModel t: textureList) {
			for(entity ent: map.get(t)) {
				texturedModel texMod = ent.getTexMod();
				TDModel tdm = texMod.getTdm();
				GL30.glBindVertexArray(tdm.getVAOID());
				GL20.glEnableVertexAttribArray(0);
				GL20.glEnableVertexAttribArray(1);
				GL20.glEnableVertexAttribArray(2);
				Matrix4f transformationMatrix = Maths.createTransformationMatrix(ent.getLocation(),ent.getScale(),ent.getRx(),ent.getRy(),ent.getRz());
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
	}
}
