package models;

import org.lwjgl.util.vector.Vector3f;

public class collisionSphere {

	private float radius;
	private Vector3f location;

	public collisionSphere(float radius, Vector3f location) {
		this.setRadius(radius);
		this.setLocation(new Vector3f(location.x, location.y + 4, location.z));
	}

	public float getRadius() {
		return radius;
	}

	public void setRadius(float radius) {
		this.radius = radius;
	}

	public Vector3f getLocation() {
		return location;
	}

	public void setLocation(Vector3f location) {
		this.location = location;
	}
	
}
