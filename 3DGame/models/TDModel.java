package models;

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
}
