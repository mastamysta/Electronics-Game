package terrain;

import loaders.modelLoader;
import models.TDModel;
import models.modelTexture;
import models.texturedModel;

public class terrain {
	private static final int SIZE = 1000;
	private static final int VERTEX_COUNT = 120;

	private float x;
	private float y;
	private TDModel terainModel;
	private texturedModel texturedTerrainModel;
	private modelTexture terrainTexture;
	
	public terrain(int x, int y, modelLoader loader, modelTexture terrainTexture) {
		this.terrainTexture = terrainTexture;
		this.x = x * SIZE;
		this.y = y * SIZE;
		this.terainModel = generateTerrain(loader);
		this.texturedTerrainModel = new texturedModel(terainModel, terrainTexture);
		
	}
	
	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public TDModel getTerrainModel() {
		return terainModel;
	}

	public texturedModel getTexturedTerrainModel() {
		return texturedTerrainModel;
	}

	public modelTexture getTerrainTexture() {
		return terrainTexture;
	}

	private TDModel generateTerrain(modelLoader loader){
		int count = VERTEX_COUNT * VERTEX_COUNT;
		float[] vertices = new float[count * 3];
		float[] normals = new float[count * 3];
		float[] textureCoords = new float[count*2];
		int[] indices = new int[6*(VERTEX_COUNT-1)*(VERTEX_COUNT-1)];
		int vertexPointer = 0;
		for(int i=0;i<VERTEX_COUNT;i++){
			for(int j=0;j<VERTEX_COUNT;j++){
				vertices[vertexPointer*3] = (float)j/((float)VERTEX_COUNT - 1) * SIZE;
				vertices[vertexPointer*3+1] = 0;
				vertices[vertexPointer*3+2] = (float)i/((float)VERTEX_COUNT - 1) * SIZE;
				normals[vertexPointer*3] = 0;
				normals[vertexPointer*3+1] = 1;
				normals[vertexPointer*3+2] = 0;
				textureCoords[vertexPointer*2] = (float)j/((float)VERTEX_COUNT - 1);
				textureCoords[vertexPointer*2+1] = (float)i/((float)VERTEX_COUNT - 1);
				vertexPointer++;
			}
		}
		int pointer = 0;
		for(int gz=0;gz<VERTEX_COUNT-1;gz++){
			for(int gx=0;gx<VERTEX_COUNT-1;gx++){
				int topLeft = (gz*VERTEX_COUNT)+gx;
				int topRight = topLeft + 1;
				int bottomLeft = ((gz+1)*VERTEX_COUNT)+gx;
				int bottomRight = bottomLeft + 1;
				indices[pointer++] = topLeft;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = topRight;
				indices[pointer++] = topRight;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = bottomRight;
			}
		}
		return loader.loadToVao(vertices, textureCoords, indices, normals);
	}

}
