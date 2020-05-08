package gameEngine;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import GUI.guiTexture;
import loaders.modelLoader;
import models.animatedModel;
import models.camera;
import models.modelTexture;
import models.unAnimatedModel;
import mousePicker.mousePicker;
import parts.part;
import terrain.terrain;
import toolbox.colorPackage;
import toolbox.colorPicker;

/**
 * Represents all the stuff in the scene (board, the camera, light,
 * really).
 */
public class gameState {
	
	public static final int OFFSET = 75;
	public static final int BOARDSPACE = 25;
	
	private final shaders.light sun;
	private final models.camera camera;
	
	private final modelLoader mdl = new modelLoader();
	private mousePicker picker;
	
	private List<part> parts= new ArrayList<part>();
	private List<unAnimatedModel> unAnimatedModels = new ArrayList<unAnimatedModel>();
	private List<guiTexture> guiObjects = new ArrayList<guiTexture>();
	
	private final terrain terrain;

	private Vector3f lightDirection = new Vector3f(1, -1, 0);
	private Vector4f sunColor = colorPicker.color(colorPackage.LightOrange);
	
	private part heldPart;
	
	public gameState(List<part> parts, List<unAnimatedModel> ents, camera cam) {
		this.sun = new shaders.light(new Vector3f(125, 400, 125), new Vector3f(sunColor.x,sunColor.y,sunColor.z));
		this.camera = cam;
		picker = new mousePicker(camera);
		modelTexture terrainTexture = new modelTexture(mdl.loadTexture("wood"));
		terrain = new terrain(0, 0, mdl, terrainTexture);
		this.parts = parts;
		unAnimatedModels = ents;
		setHeldPart(null);
	}
	
	//WITH GUI INCLUDED IN PARAMS
	public gameState(List<part> parts, List<unAnimatedModel> ents, List<guiTexture> guis, camera cam) {
		this.sun = new shaders.light(new Vector3f(125, 400, 125), new Vector3f(sunColor.x,sunColor.y,sunColor.z));
		this.camera = cam;
		this.guiObjects = guis;
		picker = new mousePicker(camera);
		modelTexture terrainTexture = new modelTexture(mdl.loadTexture("wood"));
		terrain = new terrain(0, 0, mdl, terrainTexture);
		this.parts = parts;
		unAnimatedModels = ents;
	}

	/**
	 * @return The scene's camera.
	 */
	public models.camera getCamera() {
		return camera;
	}

	/**
	 * @return The direction of the light as a vector.
	 */
	public Vector3f getLightDirection() {
		return lightDirection;
	}

	public void setLightDirection(Vector3f lightDir) {
		this.lightDirection.set(lightDir);
	}

	public shaders.light getSun() {
		return sun;
	}

	public terrain getTerrain() {
		return terrain;
	}

	public List<part> getParts() {
		return parts;
	}

	public List<unAnimatedModel> getUnAnimatedModels() {
		return unAnimatedModels;
	}

	public List<guiTexture> getGuiObjects() {
		return guiObjects;
	}

	public mousePicker getPicker() {
		return picker;
	}

	public part getHeldPart() {
		return heldPart;
	}

	public void setHeldPart(part heldPart) {
		this.heldPart = heldPart;
	}

}
