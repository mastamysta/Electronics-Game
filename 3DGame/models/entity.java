package models;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import toolbox.Maths;

public class entity {
	private texturedModel texMod;
	private Vector3f location;
	private float rx, ry, rz, scale;
	
	public entity(texturedModel texMod, Vector3f location, float rx, float ry, float rz, float scale) {
		this.texMod = texMod;
		this.location = location;
		this.rx = rx;
		this.ry = ry;
		this.rz = rz;
		this.scale = scale;
		
	}
	
	public void rotateModel(float rx, float ry, float rz) {
		this.rx += rx;
		this.ry += ry;
		this.rz += rz;
	}
	
	public void moveModel(Vector3f translation) {
		this.location.x += translation.x;
		this.location.y += translation.y;
		this.location.z += translation.z;
	}

	public texturedModel getTexMod() {
		return texMod;
	}

	public float getRx() {
		return rx;
	}

	public float getRy() {
		return ry;
	}

	public float getRz() {
		return rz;
	}

	public float getScale() {
		return scale;
	}
	
	public Vector3f getLocation() {
		return location;
	}
}
