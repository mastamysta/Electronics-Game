package main;

import GUI.guiTexture;
import fontMeshCreator.GUIText;
import gameEngine.gameState;
import models.animatedModel;
import models.unAnimatedModel;
import renderSystem.superRenderer;
import renderSystem.windowManager;

/**
 * This class represents the entire render engine.
 * 
 * @author Karl
 *
 */
public class RenderEngine{

	private superRenderer renderer;

	private RenderEngine(superRenderer renderer) {
		this.renderer = renderer;
	}

	/**
	 * Updates the display.
	 */
	public void update() {
		windowManager.updateWindow();
	}

	/**
	 * Renders the scene to the screen.
	 * 
	 * @param scene
	 *            - the game scene.
	 */
	
	
	public void renderScene(gameState scene) {
		renderer.render(scene);
	}
	
	public void bindTerrain(gameState scene) {
		renderer.bindTerrain(scene.getTerrain());
	}
	
	public void addAnimatedModel(animatedModel model) {
		renderer.addAnimatedModel(model);
	}
	
	public void removeAnimatedModel(animatedModel model) {
		renderer.removeAnimatedModel(model);
	}
	
	public void addUnAnimatedModel(unAnimatedModel model) {
		renderer.addStaticModel(model);
	}
	
	public void removeUnAnimatedModel(unAnimatedModel model) {
		renderer.removeStaticModel(model);
	}
	
	public void addGuiObject(guiTexture obj) {
		renderer.addGuiObject(obj);
	}
	
	public void removeGuiObject(guiTexture obj) {
		renderer.removeGuiObject(obj);
	}
	
	public void addText(GUIText text) {
		renderer.addText(text);
	}
	
	public void removeText(GUIText text) {
		renderer.removeText(text);
	}
	
	/**
	 * Cleans up the renderers and closes the display.
	 */
	public void close() {
		renderer.cleanUp();
		windowManager.closeWindow();
	}

	/**
	 * Initializes a new render engine. Creates the display and inits the
	 * renderers.
	 * 
	 * @return
	 */
	public static RenderEngine init() {
		windowManager.createWindow();
		superRenderer renderer = new superRenderer();
		return new RenderEngine(renderer);
	}

}
