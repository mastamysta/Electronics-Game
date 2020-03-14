package renderSystem;
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

import models.TDModel;
import models.camera;
import models.entity;
import models.entityRenderer;
import models.texturedModel;
import shaders.staticShader;
import terrain.terrain;
import terrain.terrainRenderer;
import toolbox.Maths;

public class superRenderer {
	
	private static float FOV = 120;
	private static float NEARPLANEDISTANCE = (float) 0.1f;
	private static float FARPLANEDISTANCE = 300;
	private Matrix4f projectionMatrix;
	
	private terrainRenderer terrainRenderer = new terrainRenderer();
	private entityRenderer entityRenderer = new entityRenderer();
	
	private void prep() {
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glClearColor(0, 0, 1, 0.5f); 
	}
	
	private void createProjectionMatrix(){
        float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
        float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV/2f))) * aspectRatio);
        float x_scale = y_scale / aspectRatio;
        float frustum_length = FARPLANEDISTANCE - NEARPLANEDISTANCE;
        
        projectionMatrix = new Matrix4f();
        projectionMatrix.m00 = x_scale;
        projectionMatrix.m11 = y_scale;
        projectionMatrix.m22 = -((FARPLANEDISTANCE + NEARPLANEDISTANCE) / frustum_length);
        projectionMatrix.m23 = -1;
        projectionMatrix.m32 = -((2 * NEARPLANEDISTANCE * FARPLANEDISTANCE) / frustum_length);
        projectionMatrix.m33 = 0;
    }
	
	public superRenderer(staticShader shader) {
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		createProjectionMatrix();
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
	}
	
	public void bindTerrain(terrain ter) {
		terrainRenderer.bindTerrain(ter);
	}
	
	public void unbindTerrain() {
		terrainRenderer.unbindTerrain();
	}
	
	public void addEntity(entity ent) {
		entityRenderer.addEntity(ent);	
	}
	
	public void removeEntity(entity ent) {
		entityRenderer.removeEntity(ent);
	}
	
	public void render(staticShader shader, camera cam) {
		prep();
		entityRenderer.render(shader, cam);
		terrainRenderer.render(shader, cam);
		
	}
}
