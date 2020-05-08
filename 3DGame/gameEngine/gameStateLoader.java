package gameEngine;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import GUI.guiTexture;
import loaders.AnimatedModelLoader;
import loaders.modelLoader;
import loaders.objLoader;
import models.TDModel;
import models.camera;
import models.modelTexture;
import models.texturedModel;
import models.unAnimatedModel;
import parts.motor;
import parts.part;
import toolbox.GeneralSettings;
import toolbox.MyFile;

public class gameStateLoader {

	/**
	 * Sets up the scene. Loads the entity, load the animation, tells the entity
	 * to do the animation, sets the light direction, creates the camera, etc...
	 * 
	 * @param resFolder
	 *            - the folder containing all the information about the animated entity
	 *            (mesh, animation, and texture info).
	 * @return The entire scene.
	 */
	
	static modelLoader loader = new modelLoader();
	
	public static gameState loadScene(MyFile resFolder) {
		camera camera = new camera(new Vector3f(gameState.OFFSET + 10,0,gameState.OFFSET + 10),0,0,0);
		
		List<part> motors = new ArrayList<part>();
		for (int i = 0; i < 1; i++) {
			for (int j = 0; j < 1; j++) {
				motors.add(new motor(i, j));
			}
		}
		
		
		List<guiTexture> guis = new ArrayList<guiTexture>();
		guiTexture eyes = new guiTexture(loader.loadTexture("eyes"),new Vector2f(0.85f,0.70f),new Vector2f(0.15f,0.15f));
		guis.add(eyes);
		guiTexture speechBubble = new guiTexture(loader.loadTexture("speechbubble"),new Vector2f(0.65f,0.5f),new Vector2f(0.5f,0.5f));
		guis.add(speechBubble);
		
		List<unAnimatedModel> ents = new ArrayList<unAnimatedModel>();
		TDModel mod = objLoader.loadObjModel("dragon", loader);
		modelTexture modtex = new modelTexture(loader.loadTexture("stallTexture"));
		modtex.setShineDamper(50);
		modtex.setReflectivity(1);
		texturedModel texmod = new texturedModel(mod, modtex);
		unAnimatedModel ent = new unAnimatedModel(texmod, 4, 3, 0, 0, 0, 1);
		ents.add(ent);
		
		gameState scene = new gameState(motors, ents, guis, camera);
		scene.setLightDirection(GeneralSettings.LIGHT_DIR);
		return scene;
	}

}
