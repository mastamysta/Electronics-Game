package testEngine;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import models.TDModel;
import models.camera;
import models.entity;
import models.modelTexture;
import models.texturedModel;
import renderSystem.modelLoader;
import renderSystem.objLoader;
import renderSystem.superRenderer;
import renderSystem.windowManager;
import shaders.light;
import shaders.staticShader;
import terrain.terrain;
import toolbox.Maths;

public class temp {
	

	public static void main(String[] args) {
		windowManager.createWindow();
		
		modelLoader ld = new modelLoader();
		staticShader sd = new staticShader();
		superRenderer rd = new superRenderer(sd);
		camera cam = new camera(new Vector3f(10,5,10),0,0,0);
		
		
		TDModel model = objLoader.loadObjModel("stall", ld);
		int textureID = ld.loadTexture("stallTexture");
		modelTexture md = new modelTexture(textureID);
		texturedModel texturedModel = new texturedModel(model, md);
		entity ent = new entity(texturedModel, new Vector3f(0,0,25),0,180,0,1);
		entity ent2 = new entity(texturedModel, new Vector3f(50,0,50),0,180,0,1);
		entity ent3 = new entity(texturedModel, new Vector3f(0,0,0),0,180,0,1);
		entity ent4 = new entity(texturedModel, new Vector3f(50,0,0),0,180,0,1);
		light sun = new light(new Vector3f(0,-10,-25), new Vector3f(1,1,1));
		rd.addEntity(ent3);
		rd.addEntity(ent2);
		rd.addEntity(ent);
		rd.addEntity(ent4);
		

		int terrainTexID = ld.loadTexture("STOP_sign");
		modelTexture terrainTexture = new modelTexture(terrainTexID);
		terrain flat = new terrain(0, 0, ld, terrainTexture); 
		rd.bindTerrain(flat);
	 		
		while(!Display.isCloseRequested()) {
			cam.relativeKeyBoardNav();
			sd.start();
			sd.loadLight(sun);
			sd.loadviewMatrix(Maths.createViewMatrix(cam));
			rd.render(sd, cam);
			sd.stop();
			windowManager.updateWindow();
		}
		sd.cleanUp();
		ld.cleanup();
		windowManager.closeWindow();
	}

}
