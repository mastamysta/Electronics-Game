package loaders;

import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector3f;

import dataStructures.AnimatedModelData;
import dataStructures.JointData;
import dataStructures.MeshData;
import dataStructures.SkeletonData;
import models.Joint;
import models.TDModel;
import models.animatedModel;
import texture.Texture;
import toolbox.GeneralSettings;
import toolbox.MyFile;

public class AnimatedModelLoader {

	/**
	 * Creates an AnimatedEntity from the data in an entity file. It loads up
	 * the collada model data, stores the extracted data in a VAO, sets up the
	 * joint heirarchy, and loads up the entity's texture.
	 * 
	 * @param entityFile
	 *            - the file containing the data for the entity.
	 * @return The animated entity (no animation applied though)
	 */
	public static animatedModel loadEntity(MyFile modelFile, MyFile textureFile, int X, int Y, float rotx, float roty, float rotz, float scale) {
		AnimatedModelData entityData = ColladaLoader.loadColladaModel(modelFile, GeneralSettings.MAX_WEIGHTS);
		TDModel model = createVao(entityData.getMeshData());
		Texture texture = loadTexture(textureFile);
		SkeletonData skeletonData = entityData.getJointsData();
		Joint headJoint = createJoints(skeletonData.headJoint);
		return new animatedModel(model, texture, headJoint, skeletonData.jointCount, X, Y, rotx, roty, rotz, scale);
	}

	private static Texture loadTexture(MyFile textureFile) {
		Texture diffuseTexture = Texture.newTexture(textureFile).anisotropic().create();
		return diffuseTexture;
	}

	private static Joint createJoints(JointData data) {
		Joint joint = new Joint(data.index, data.nameId, data.bindLocalTransform);
		for (JointData child : data.children) {
			joint.addChild(createJoints(child));
		}
		return joint;
	}


	private static TDModel createVao(MeshData data) {
		modelLoader mdl = new modelLoader();
		TDModel vao = mdl.loadToVao(data.getVertices(), data.getTextureCoords(), data.getIndices(), data.getNormals());
		GL30.glBindVertexArray(vao.getVAOID());
		mdl.storeIntInAttributeList(3, 3, data.getJointIds());
		mdl.storeDataInAttributeList(4, 3, data.getVertexWeights());
		
		GL30.glBindVertexArray(0);
		return vao;
	}

}
