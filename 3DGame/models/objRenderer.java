package models;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import renderSystem.superRenderer;
import shaders.light;
import shaders.objShader;
import toolbox.Maths;

public class objRenderer{
	
	private Map<texturedModel, ArrayList<unAnimatedModel>>  map = new HashMap<texturedModel, ArrayList<unAnimatedModel>>();
	private List<texturedModel> textureList = new ArrayList<texturedModel>();
	
	public void addModel(unAnimatedModel ent) {
		texturedModel texMod = ent.getTexMod();
		if (!map.containsKey(texMod)){
			ArrayList<unAnimatedModel> newList = new ArrayList<unAnimatedModel>();
			newList.add(ent);
			map.put(texMod, newList);
			textureList.add(texMod);
		} else {			
			map.get(texMod).add(ent);
		}		
	}
	
	public void removeModel(unAnimatedModel ent) {
		texturedModel texMod = ent.getTexMod();
		if (map.containsKey(texMod)){
			map.get(texMod).remove(ent);
			textureList.remove(texMod);
		}
	}
	
	public void render(objShader shader, camera cam, light sun) {
		for(texturedModel t: textureList) {
			for(unAnimatedModel ent: map.get(t)) {
				texturedModel texMod = ent.getTexMod();
				TDModel tdm = texMod.getTdm();
				GL30.glBindVertexArray(tdm.getVAOID());
				GL20.glEnableVertexAttribArray(0);
				GL20.glEnableVertexAttribArray(1);
				GL20.glEnableVertexAttribArray(2);
				Matrix4f transformationMatrix = Maths.createTransformationMatrix(ent.getLocation(),ent.getScale(),ent.getRx(),ent.getRy(),ent.getRz());
				shader.loadTransformationMatrix(transformationMatrix);
				shader.loadSkyColor(superRenderer.RED, superRenderer.GREEN, superRenderer.BLUE);
				shader.loadLight(sun);
				shader.loadviewMatrix(Maths.createViewMatrix(cam));
				shader.loadSpecularLighting(texMod.getTxtr().getShineDamper(), texMod.getTxtr().getReflectivity());
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
