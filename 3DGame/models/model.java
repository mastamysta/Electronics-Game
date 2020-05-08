package models;

import org.lwjgl.util.vector.Vector3f;

import gameEngine.gameState;
import main.RenderEngine;

//literally just a linking class to place animated and non animated models in a grid
public abstract class model {
	
	protected Vector3f location;
	protected float rx, ry, rz, scale;
	private int x, y;
	
	private collisionSphere hitbox;
	
	private boolean dragging;
	
	public model(int x,int y, float rx, float ry, float rz, float scale) {
		this.location = new Vector3f(x * gameState.BOARDSPACE + gameState.OFFSET, 0, y * gameState.BOARDSPACE + gameState.OFFSET);
		this.x = x;
		this.ry = y;
		this.rx = rx;
		this.ry = ry;
		this.rz = rz;
		this.scale = scale;
		updateHitbox();
	}
	
	private void updateHitbox() {
		//this.hitbox = new collisionSphere(gameState.BOARDSPACE * scale, this.location);
		this.hitbox = new collisionSphere(2 * scale, this.location);
	}
	
	public abstract void addModel(RenderEngine engine);
	
	public abstract void removeModel(RenderEngine engine);
	
	public void rotateModel(float rx, float ry, float rz) {
		this.rx += rx;
		this.ry += ry;
		this.rz += rz;
	}
	
	public void setBoardPosition(int x, int y) {
		this.x = x;
		this.y = y;
		this.location = new Vector3f(x * gameState.BOARDSPACE + gameState.OFFSET, 0,
				y * gameState.BOARDSPACE + gameState.OFFSET);
		updateHitbox();
	}
	
	private void moveModel(Vector3f translation) {
		this.location.x += translation.x;
		this.location.y += translation.y;
		this.location.z += translation.z;
		updateHitbox();
	}
	
	public void setLocation(Vector3f location) {
		this.location = location;
		this.x = (int)Math.floor((location.x - gameState.OFFSET) / gameState.BOARDSPACE );
		this.y = (int)Math.floor((location.y - gameState.OFFSET) / gameState.BOARDSPACE );
		updateHitbox();
	}
	
	public void setScale(float scale) {
		this.scale = scale;
		updateHitbox();
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

	public collisionSphere getHitbox() {
		return hitbox;
	}
}
