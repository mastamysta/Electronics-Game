package renderSystem;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;

import GUI.guiRenderer;
import GUI.guiTexture;
import Terminal.Terminal;
import animatedRenderer.AnimatedModelRenderer;
import animatedRenderer.AnimatedModelShader;
import fontMeshCreator.GUIText;
import fontRendering.TextMaster;
import gameEngine.gameState;
import loaders.modelLoader;
import models.animatedModel;
import models.objRenderer;
import models.unAnimatedModel;
import postProcessor.Fbo;
import postProcessor.PostProcessing;
import shaders.objShader;
import skybox.SkyboxRenderer;
import terrain.terrain;
import terrain.terrainRenderer;
import terrain.terrainShader;
import toolbox.colorPackage;
import toolbox.colorPicker;

public class superRenderer{
	
	private static final colorPackage skyColor = colorPackage.Perrywinkle;
	
	public static final float RED = colorPicker.color(skyColor).x;
	public static final float GREEN = colorPicker.color(skyColor).y; 
	public static final float BLUE = colorPicker.color(skyColor).z;
	
	private static float FOV = 120;
	private static float NEARPLANEDISTANCE = (float) 0.1f;
	private static float FARPLANEDISTANCE = 400;
	private Matrix4f projectionMatrix;
	
	private terrainRenderer terrainRenderer = new terrainRenderer();
	private objRenderer entityRenderer = new objRenderer();
	private AnimatedModelRenderer anModRenderer = new AnimatedModelRenderer();
	private SkyboxRenderer skyboxRenderer = new SkyboxRenderer();
	private guiRenderer guiRenderer = new guiRenderer();
	
	private objShader shader = new objShader();
	private terrainShader terShad = new terrainShader();
	private AnimatedModelShader anModShad = new AnimatedModelShader();
	
	private Fbo fbo;
	
	private void prep() {
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glClearColor(RED, GREEN, BLUE, 1.0f);
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
	
	public superRenderer() {
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		createProjectionMatrix();
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
		terShad.start();
		terShad.loadProjectionMatrix(projectionMatrix);
		terShad.stop();
		TextMaster.init(new modelLoader());
		modelLoader loader = new modelLoader();
		fbo = new Fbo(Display.getWidth(), Display.getHeight(), Fbo.DEPTH_RENDER_BUFFER);
		PostProcessing.init(loader);
		Terminal.init();
	}
	
	public void addText(GUIText text) {
		TextMaster.loadText(text);
	}
	
	public void removeText(GUIText text) {
		TextMaster.removeText(text);
	}
	
	public void bindTerrain(terrain ter) {
		terrainRenderer.bindTerrain(ter);
	}
	
	public void unbindTerrain() {
		terrainRenderer.clearTerrain();
	}
	
	
	public void addStaticModel(unAnimatedModel ent) {
		entityRenderer.addModel(ent);	
	}
	
	public void removeStaticModel(unAnimatedModel ent) {
		entityRenderer.removeModel(ent);
	}
	
	public void addAnimatedModel(animatedModel model) {
		anModRenderer.addModel(model);
	}
	
	public void removeAnimatedModel(animatedModel model) {
		anModRenderer.removeModel(model);
	}
	
	public void addGuiObject(guiTexture obj) {
		guiRenderer.addGuiObject(obj);
	}
	
	public void removeGuiObject(guiTexture obj) {
		guiRenderer.removeGuiObject(obj);
	}
	
	public void render(gameState scene) {
		prep();
		if(!scene.getCamera().isTerminalUp()) {	
			terShad.start();
			terrainRenderer.render(terShad, scene.getCamera(), scene.getSun());
			terShad.stop();
			anModShad.start();
			anModRenderer.render(scene.getCamera(), scene.getLightDirection());
			anModShad.stop();
			shader.start();
			entityRenderer.render(shader, scene.getCamera(), scene.getSun());
			shader.stop();
			skyboxRenderer.render(scene.getCamera());
			guiRenderer.render();
		}
		else{
			Terminal.updateTerminal();
			fbo.bindFrameBuffer();
			TextMaster.render();
			fbo.unbindFrameBuffer();
			PostProcessing.doPostProcessing(fbo.getColourTexture());
		}
	}

	public void cleanUp() {
		terrainRenderer.clearTerrain();
		anModRenderer.cleanUp();
		terShad.cleanUp();
		anModShad.cleanUp();
		shader.cleanUp();
		TextMaster.cleanUp();
		fbo.cleanUp();
		PostProcessing.cleanUp();
	}
}
