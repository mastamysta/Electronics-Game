package skybox;

import loaders.modelLoader;
import models.TDModel;

public class CubeGenerator {

	private static final int[] INDICES = { 0, 1, 3, 1, 2, 3, 1, 5, 2, 2, 5, 6, 4, 7, 5, 5, 7, 6, 0,
			3, 4, 4, 3, 7, 7, 3, 6, 6, 3, 2, 4, 5, 0, 0, 5, 1 };

	private static final modelLoader loader = new modelLoader();

	//edit to allow skybox offset
	public static TDModel generateCube(float size, int offsetX, int offsetY) {
		TDModel vao = new TDModel(loader.createVao(), INDICES.length);
		vao.bind();
		loader.bindIndecesBuffer(INDICES);
		loader.storeDataInAttributeList(0, 3, getVertexPositions(size));
		vao.unbind();
		return vao;
	}

	private static float[] getVertexPositions(float size) {
		return new float[] { -size, size, size, size, size, size, size, -size, size, -size, -size,
				size, -size, size, -size, size, size, -size, size, -size, -size, -size, -size,
				-size };
	}

}
