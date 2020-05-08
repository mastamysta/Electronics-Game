package models;

import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class TDModel {

	private int VAOID;
	private int vertexCount;
	
	public TDModel(int VAOID, int vertexCount) {
		this.VAOID = VAOID;
		this.vertexCount = vertexCount;
	}

	public int getVAOID() {
		return VAOID;
	}

	public int getVertexCount() {
		return vertexCount;
	}

	public void unbind(int... attributes){
		for (int i : attributes) {
			GL20.glDisableVertexAttribArray(i);
		}
		unbind();
	}
	
	public void bind(int... attributes){
		bind();
		for (int i : attributes) {
			GL20.glEnableVertexAttribArray(i);
		}
	}
	
	private void bind() {
		GL30.glBindVertexArray(VAOID);
	}

	private void unbind() {
		GL30.glBindVertexArray(0);
	}
	
	
}
