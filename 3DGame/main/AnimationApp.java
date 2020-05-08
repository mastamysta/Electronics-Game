package main;

import java.util.Optional;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import GUI.guiTexture;
import Terminal.terminalRenderer;
import gameEngine.gameState;
import gameEngine.gameStateLoader;
import models.unAnimatedModel;
import parts.motor;
import parts.part;
import renderSystem.windowManager;
import toolbox.GeneralSettings;
import toolbox.Maths;
import Terminal.Terminal;

public class AnimationApp {

	/**
	 * Initialises the engine and loads the scene. For every frame it updates the
	 * camera, updates the animated entity (which updates the animation),
	 * renders the scene to the screen, and then updates the display. When the
	 * display is close the engine gets cleaned up.
	 * 
	 * @param args
	 */
	
	private static float runtime = 0;
	
	public static void main(String[] args) {
		RenderEngine engine = RenderEngine.init();
		terminalRenderer ren = new terminalRenderer();

		gameState scene = gameStateLoader.loadScene(GeneralSettings.RES_FOLDER);
		engine.bindTerrain(scene);
		for (part mod:scene.getParts()) {
			mod.getModel().addModel(engine);
			System.out.println(mod.getModel().getLocation());
		}
		for (unAnimatedModel mod:scene.getUnAnimatedModels()) {
			mod.addModel(engine);
			System.out.println(mod.getLocation());
		}
		for (guiTexture obj:scene.getGuiObjects()) {
			engine.addGuiObject(obj);
		}
		
		//see if this works
		Optional<part> intersectedPart;
		
		while (!Display.isCloseRequested()) {
			runtime += windowManager.getFrameTime();
			scene.getCamera().move();
			
			for (part mod:scene.getParts()) {
				mod.getModel().update();
			}
			
			//remove entity on click functionality
//			if (scene.getCamera().isCameraLocked()) {
//				scene.getPicker().update();
//				intersectedPart = scene.getPicker().pickedModel(scene.getParts(), scene.getCamera());
//				if (intersectedPart.isPresent() && Mouse.isButtonDown(0)){
//					engine.removeAnimatedModel(intersectedPart.get().getModel());
//				}
//			}
			
			
			//control for a single motor moving forward or backward
			if(Terminal.isForward() && !(((motor)scene.getParts().get(0)).isRunningForward()) ) {
				((motor)scene.getParts().get(0)).motorForward();
			}
			else if(Terminal.isBackward() && !(((motor)scene.getParts().get(0)).isRunningBackward()) ) {
				((motor)scene.getParts().get(0)).motorBackward();
			}
			else if (!Terminal.isBackward() && !Terminal.isForward()){
				((motor)scene.getParts().get(0)).motorStop();
			}
			
			//drag and drop tool
			if(!scene.getCamera().isCameraLocked()) {
				
			}
			else {
				
				scene.getPicker().update();
				intersectedPart = scene.getPicker().pickedModel(scene.getParts(), scene.getCamera());
				if (intersectedPart.isPresent() && Mouse.isButtonDown(0)){
					scene.setHeldPart(intersectedPart.get());
				}
				if (!Mouse.isButtonDown(0)) {
					scene.setHeldPart(null);
				}
				if (scene.getHeldPart() != null) {
					scene.getHeldPart().getModel().setLocation(Maths.rayGroundIntersection(scene.getPicker().getCurrentRay().normalise(null),
							scene.getCamera().getPosition(), 100));
			
				}
			}
			
			
			//update models when they are running
			if(((motor)scene.getParts().get(0)).isRunningForward() && ((motor)scene.getParts().get(0) != scene.getHeldPart())) {
				Vector3f xyz = ((motor)scene.getParts().get(0)).getModel().getLocation();
				scene.getParts().get(0).getModel().setLocation(new Vector3f((float) (xyz.x + 0.08), 0, (float) (xyz.z + 0.08)));
			}
			else if(((motor)scene.getParts().get(0)).isRunningBackward() && ((motor)scene.getParts().get(0) != scene.getHeldPart())) {
				Vector3f xyz = ((motor)scene.getParts().get(0)).getModel().getLocation();
				scene.getParts().get(0).getModel().setLocation(new Vector3f((float) (xyz.x - 0.08), 0, (float) (xyz.z - 0.08)));
			}
				
				
			engine.renderScene(scene);
			engine.update();
		}

		engine.close();

	}

}
