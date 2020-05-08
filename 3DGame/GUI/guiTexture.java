package GUI;

import org.lwjgl.util.vector.Vector2f;

public class guiTexture {
	
	private int textureID;
	private Vector2f location;
	private Vector2f scale;
	
	public guiTexture(int textureID, Vector2f location, Vector2f scale) {
		this.textureID = textureID;
		this.location = location;
		this.scale = scale;
	}

	public int getTextureID() {
		return textureID;
	}

	public Vector2f getLocation() {
		return location;
	}

	public Vector2f getScale() {
		return scale;
	}
}
