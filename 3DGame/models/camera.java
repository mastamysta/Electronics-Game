package models;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import toolbox.Maths;

public class camera {
	private int WIDTH;
	private int HEIGHT;
	private Vector3f position;
	private float pitch, yaw, roll;
	private static final int MARGIN = 5;
	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public void setPitch(float pitch) {
		this.pitch = pitch;
	}

	public void setYaw(float yaw) {
		this.yaw = yaw;
	}

	public void setRoll(float roll) {
		this.roll = roll;
	}

	private float mouseX,mouseY,dX,dY;
	
	public camera(Vector3f position , float pitch, float yaw, float roll) {
		this.position = position;
		this.pitch = pitch;
		this.yaw = yaw;
		this.roll = roll;
		mouseX = Mouse.getX();
		mouseY = Mouse.getY();
		dX = 0;
		dY = 0;
		WIDTH = Display.getWidth();
		HEIGHT = Display.getHeight();
	}
	
	private Vector3f getUnitDirection() {
		Vector3f vec = new Vector3f(0,0,0);
		vec.x -= (Math.sin(Math.toRadians(yaw)));
		vec.y += (Math.sin(Math.toRadians(pitch)));
		vec.z += (Math.sin(Math.toRadians(pitch)) + Math.cos(Math.toRadians(yaw)));
		vec.normalise();
		return new Vector3f(vec.x, vec.y, vec.z);
	}
	
	private Vector3f getLPerpendicular() {
		Vector3f vec = new Vector3f(0,0,0);
		vec.x -= Math.sin(Math.toRadians(yaw + 90));
		//vec.y += (Math.sin(Math.toRadians(pitch)));
		vec.z += Math.cos(Math.toRadians(yaw + 90));
		vec.normalise();
		return new Vector3f(vec.x, vec.y, vec.z);
	}
	
	private Vector3f getRPerpendicular() {
		Vector3f vec = new Vector3f(0,0,0);
		vec.x -= Math.sin(Math.toRadians(yaw - 90));
		//vec.y += (Math.sin(Math.toRadians(pitch)));
		vec.z += Math.cos(Math.toRadians(yaw - 90));
		vec.normalise();
		return new Vector3f(vec.x, vec.y, vec.z);
	}
	
	public void moveCameraForward() {
		Vector3f vec = getUnitDirection();
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			position.x += vec.x * 0.1f;
			position.y += vec.y * 0.1f;
			position.z += vec.z * 0.1f;
		}
	}
	
	public void updateCamera() {
		moveCamera();
		updateCameraAngle();
	}
	
	public void lookingAt(Vector3f target) {
		Vector3f view = new Vector3f();
		Vector3f.sub(target, position, view);
		float dx = 0, dy = 0, dz = 0;
		float dotx, doty, dotz;
		dotx = Vector3f.dot(view, Maths.unitX);
		doty = Vector3f.dot(view, Maths.unitY);
		dotz = Vector3f.dot(view, Maths.unitZ);
		dx = (float) Math.acos(dotx/view.length());
		dy = (float) Math.acos(doty/view.length());
		dz = (float) Math.acos(dotz/view.length());
		pitch = dx;
		yaw = dy;
		roll = dz;
	}
	
	public void moveCamera() {
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			position.y += 0.1f;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			position.y -= 0.1f;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			position.x -= 0.1f;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {	
			position.x += 0.1f;
		}
		position.z -= Mouse.getDWheel() / 100;
	}
	
	public void relativeKeyBoardNav() {
		updateCameraAngle();
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			Vector3f.sub(position, getUnitDirection(), position);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			Vector3f.add(position, getUnitDirection(), position);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			Vector3f.add(position, getLPerpendicular(), position);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {	
			Vector3f.add(position, getRPerpendicular(), position);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_E)) {	
			System.exit(0);
		}
	}
	
	
	private void updateCameraAngle() {
		Mouse.setGrabbed(true);
		dX = mouseX - Mouse.getX();
		dY = mouseY - Mouse.getY();
		yaw -= (dX * 0.2);
		pitch += (dY * 0.2);
		mouseX = Mouse.getX();
		mouseY = Mouse.getY();
		if (mouseX >= WIDTH - MARGIN) {
			mouseX = MARGIN + 1;
			Mouse.setCursorPosition((int) mouseX, (int) mouseY);
		}
		else if (mouseX <= 0 + MARGIN) {
			mouseX = WIDTH - (MARGIN + 1);
			Mouse.setCursorPosition((int) mouseX, (int) mouseY);
		}
		if (mouseY >= HEIGHT - MARGIN) {
			mouseY = MARGIN + 1;
			Mouse.setCursorPosition((int) mouseX, (int) mouseY);
		}
		else if (mouseY <= 0 + MARGIN) {
			mouseY = HEIGHT - (MARGIN + 1);
			Mouse.setCursorPosition((int) mouseX, (int) mouseY);
		}
	}
	
	public void increaseCameraPosition(Vector3f movement) {
		Vector3f.add(position, movement, position);
		
	}
	
	public void increaseCameraAngle(float pitch, float yaw, float roll) {
		this.yaw += yaw;
		this.pitch += pitch;
		this.roll += roll;
	}

	public Vector3f getPosition() {
		return position;
	}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public float getRoll() {
		return roll;
	}
}
