package animatedRenderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import models.animatedModel;
import renderSystem.superRenderer;
import set.ICamera;
import toolbox.Maths;
import toolbox.OpenGlUtils;



/**
 * 
 * This class deals with rendering an animated entity. Nothing particularly new
 * here. The only exciting part is that the joint transforms get loaded up to
 * the shader in a uniform array.
 * 
 * @author Karl
 *
 */

//TODO: ADD POSITIONS OF ENTITIES USING HASH MAP
public class AnimatedModelRenderer{

	private AnimatedModelShader shader;

	private Map<UUID, animatedModel>  map = new HashMap<UUID, animatedModel>();
	private List<UUID> models = new ArrayList<UUID>();
	
	/**
	 * Initializes the shader program used for rendering animated models.
	 */
	public AnimatedModelRenderer() {
		this.shader = new AnimatedModelShader();
	}

	/**
	 * Renders an animated entity. The main thing to note here is that all the
	 * joint transforms are loaded up to the shader to a uniform array. Also 5
	 * attributes of the VAO are enabled before rendering, to include joint
	 * indices and weights.
	 * 
	 * @param unAnimatedModel
	 *            - the animated entity to be rendered.
	 * @param camera
	 *            - the camera used to render the entity.
	 * @param lightDir
	 *            - the direction of the light in the scene.
	 */
	public void render(ICamera camera, Vector3f lightDir) {
		prepare(camera, lightDir);
		for (UUID id: models) {
			animatedModel entity = map.get(id);
			Vector3f rot = new Vector3f(entity.getRx(), entity.getRy(), entity.getRz());
			Matrix4f projectionViewMatrix = camera.getProjectionViewMatrix();
			
			
			Matrix4f translationMatrix = Maths.createTransformationMatrix(entity.getLocation(), entity.getScale(), 
					rot.x, rot.y, rot.z);
			Matrix4f projectionViewTranslationMatrix = new Matrix4f();			
			Matrix4f.mul(projectionViewMatrix, translationMatrix, projectionViewTranslationMatrix);
			shader.projectionViewMatrix.loadMatrix(projectionViewTranslationMatrix);
			shader.viewMatrix.loadMatrix(camera.getViewMatrix());
			shader.transformationMatrix.loadMatrix(translationMatrix);
			shader.skyColor.loadVec3(superRenderer.RED, superRenderer.GREEN, superRenderer.BLUE);
			
			entity.getTexture().bindToUnit(0);
			entity.getVao().bind(0,1,2,3,4,5);
			shader.jointTransforms.loadMatrixArray(entity.getJointTransforms());			
			GL11.glDrawElements(GL11.GL_TRIANGLES, entity.getVao().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
			entity.getVao().unbind(0, 1, 2,3,4,5);	
			
		}
		finish(); 
	}
	
	public void addModel(animatedModel model) {
		models.add(model.getAnimatedModelID());
		map.put(model.getAnimatedModelID(), model);
	}
	
	public void removeModel(animatedModel model) {
		UUID id = model.getAnimatedModelID();
		if (map.containsKey(id)){
			map.remove(id, model);
			models.remove(id);
		}
	}

	/**
	 * Deletes the shader program when the game closes.
	 */
	public void cleanUp() {
		shader.cleanUp();
	}

	/**
	 * Starts the shader program and loads up the projection view matrix, as
	 * well as the light direction. Enables and disables a few settings which
	 * should be pretty self-explanatory.
	 * 
	 * @param camera
	 *            - the camera being used.
	 * @param lightDir
	 *            - the direction of the light in the scene.
	 */
	private void prepare(ICamera camera, Vector3f lightDir) {
		//add matrix for translation of entity (animated model)
		shader.start();
		Matrix4f projectionViewMatrix = camera.getProjectionViewMatrix();
		shader.projectionViewMatrix.loadMatrix(projectionViewMatrix);
		shader.lightDirection.loadVec3(lightDir);
		OpenGlUtils.antialias(true);
		OpenGlUtils.disableBlending();
		OpenGlUtils.enableDepthTesting(true);
	}

	/**
	 * Stops the shader program after rendering the entity.
	 */
	private void finish() {
		shader.stop();
	}

}
