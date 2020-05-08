package models;

public class modelTexture {
	private int textureID;
	
	
	private int shineDamper = 1;
	private int reflectivity = 0;
	
	
	
	public int getReflectivity() {
		return reflectivity;
	}

	public void setReflectivity(int reflectivity) {
		this.reflectivity = reflectivity;
	}

	
	
	
	public int getShineDamper() {
		return shineDamper;
	}

	public void setShineDamper(int shineDamper) {
		this.shineDamper = shineDamper;
	}

	public void setTextureID(int textureID) {
		this.textureID = textureID;
	}

	
	
	public modelTexture(int id) {
		textureID = id;
	}

	public int getTextureID() {
		return textureID;
	}
	
	
}
