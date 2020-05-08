package loaders;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import models.TDModel;

public class modelLoader {
	
	private static final int BYTES_PER_FLOAT = 4;
	private static final int BYTES_PER_INT = 4;
	
	private List<Integer> vaos = new ArrayList<Integer>();
	private List<Integer> vbos = new ArrayList<Integer>();
	private List<Integer> textures = new ArrayList<Integer>();

	public TDModel loadToVao(float[] positions, float[] texturecoords, int[] indeces, float[] normals) {
		int vaoID = createVao();
		bindIndecesBuffer(indeces);
		storeDataInAttributeList(0, 3,positions);	
		storeDataInAttributeList(1, 2, texturecoords);
		storeDataInAttributeList(2, 3, normals);
		unbindVao();
		return new TDModel(vaoID, indeces.length);
	}
	
	public TDModel loadToVao(float[] position) {
		int vaoID = createVao();
		this.storeDataInAttributeList(0, 2, position);
		unbindVao();
		
		return new TDModel(vaoID, position.length/2);
	}
	
	public TDModel loadToVao(float[] position, float[] texturecoords) {
		int vaoID = createVao();
		this.storeDataInAttributeList(0, 2, position);
		storeDataInAttributeList(1, 2, texturecoords);
		unbindVao();
		
		return new TDModel(vaoID, position.length/2);
	}
	
	public int loadTexture(String filename) {
		Texture tx = null;
		try{
			tx = TextureLoader.getTexture("PNG", new FileInputStream("res/" + filename + ".png"));
		}catch (FileNotFoundException e) {
			e.printStackTrace();	
		}catch (IOException e){
			e.printStackTrace();
		}
		int textureid = tx.getTextureID();
		textures.add(textureid);
		return textureid;		
	}
	
	public int createVao() {
		int vaoID = GL30.glGenVertexArrays();
		vaos.add(vaoID);
		GL30.glBindVertexArray(vaoID);
		return vaoID;
	}
	
	public void bindIndecesBuffer(int[] indeces) {
			int vboID = GL15.glGenBuffers();
			vbos.add(vboID);
			GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
			IntBuffer ib = intListToIntBuffer(indeces);
			GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, ib, GL15.GL_STATIC_DRAW);
	}
	
	private IntBuffer intListToIntBuffer(int[] data) {
		IntBuffer ib = BufferUtils.createIntBuffer(data.length);
		ib.put(data);
		ib.flip();
		return ib;
	}
	
	public void storeDataInAttributeList(int attributeNumber,int coordsize, float[] data) {
		int vboID = GL15.glGenBuffers();
		vbos.add(vboID);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		FloatBuffer fb = floatListToFloatBuffer(data);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, fb, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(attributeNumber, coordsize, GL11.GL_FLOAT, false, coordsize * BYTES_PER_FLOAT, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}
	
	public void storeIntInAttributeList(int attributeNumber,int coordsize, int[] data) {
		int vboID = GL15.glGenBuffers();
		vbos.add(vboID);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		IntBuffer fb = intListToIntBuffer(data);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, fb, GL15.GL_STATIC_DRAW);
		GL30.glVertexAttribIPointer(attributeNumber, coordsize, GL11.GL_INT, coordsize * BYTES_PER_INT, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}
	
//	public void storeIntInAttributeList(int attributeNumber,int coordsize, int[] data) {
//		int vboID = GL15.glGenBuffers();
//		vbos.add(vboID);
//		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
//		IntBuffer fb = intListToIntBuffer(data);
//		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, fb, GL15.GL_STATIC_DRAW);
//		GL20.glVertexAttribPointer(attributeNumber, coordsize, GL11.GL_INT, false, 0, 0);
//		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
//	}
	
	private void unbindVao() {
		GL30.glBindVertexArray(0);
	}
	
	private FloatBuffer floatListToFloatBuffer(float[] indeces) {
		FloatBuffer fb = BufferUtils.createFloatBuffer(indeces.length);
		fb.put(indeces);
		fb.flip();		
		return fb;
	}
	
	public void cleanup() {
		deleteVaos();
		deleteVbos();
		deleteTextures();
	}
	
	private void deleteTextures() {
		for (int texture: textures) {
			GL11.glDeleteTextures(texture);
		}
	}
	
	private void deleteVaos() {
		for (int va: vaos) {
			GL30.glDeleteVertexArrays(va);
		}
	}
	
	private void deleteVbos() {
		for (int vb: vbos) {
			GL15.glDeleteBuffers(vb);
		}
	}
}
