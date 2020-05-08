package models;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import Terminal.Terminal;
import set.ICamera;
import toolbox.Maths;

public class camera implements ICamera{
	private int WIDTH;
	private int HEIGHT;
	private Vector3f position;
	private float pitch, yaw, roll;
	private static final int MARGIN = 5;
	private Matrix4f projectionMatrix;
	private Matrix4f viewMatrix;
	private static float FOV = 120;
	private static float NEARPLANEDISTANCE = (float) 0.1f;
	private static float FARPLANEDISTANCE = 300;
	
	private boolean cameraLocked;
	private boolean lKeyDown;
	
	private boolean terminalUp;
	private boolean f1KeyDown;
	private boolean f2KeyDown;
	private List<Character> keyBoardQueue = new ArrayList<Character>();
	
	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public boolean isCameraLocked() {
		return cameraLocked;
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
		createProjectionMatrix();
		
		cameraLocked = false;
		lKeyDown = cameraLocked;
		Mouse.setGrabbed(!cameraLocked);
		
		terminalUp = false;
		f1KeyDown = terminalUp;
		f2KeyDown = terminalUp;
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
		if(!cameraLocked && !terminalUp) {
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
			if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {	
				Vector3f.add(position, new Vector3f(0,1,0), position);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {	
				Vector3f.sub(position, new Vector3f(0,1,0), position);
			}
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {	
			System.exit(0);
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_L)){
			lKeyDown = true;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_F1)){
			f1KeyDown = true;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_F2)){
			f2KeyDown = true;
		}
		
		if (!Keyboard.isKeyDown(Keyboard.KEY_L) && lKeyDown){
			this.cameraLocked = !this.cameraLocked;
			Mouse.setGrabbed(!Mouse.isGrabbed());
			lKeyDown = false;
		}
		if (!Keyboard.isKeyDown(Keyboard.KEY_F1) && f1KeyDown){
			this.terminalUp = !this.terminalUp;
			while(Keyboard.next()) {
				
			}
			f1KeyDown = false;
		}
		if (!Keyboard.isKeyDown(Keyboard.KEY_F2) && f2KeyDown){
			Terminal.clearTerminal();
			f2KeyDown = false;
		}
	}
	
	
	public List<Character> getKeyBoardQueue() {
		return keyBoardQueue;
	}

	private void updateCameraAngle() {
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
	
	private void createProjectionMatrix(){
        float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
        float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV/2f))) * aspectRatio);
        float x_scale = y_scale / aspectRatio;
        float frustum_length = FARPLANEDISTANCE - NEARPLANEDISTANCE;
        
        projectionMatrix = new Matrix4f();
        projectionMatrix.m00 = x_scale;
        projectionMatrix.m11 = y_scale;
        projectionMatrix.m22 = -((FARPLANEDISTANCE + NEARPLANEDISTANCE) / frustum_length);
        projectionMatrix.m23 = -1;
        projectionMatrix.m32 = -((2 * NEARPLANEDISTANCE * FARPLANEDISTANCE) / frustum_length);
        projectionMatrix.m33 = 0;
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

	
	public void updateViewMatrix() {
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		Matrix4f.rotate((float)Math.toRadians(this.getPitch()), new Vector3f(1,0,0), matrix, matrix);
		Matrix4f.rotate((float)Math.toRadians(this.getYaw()), new Vector3f(0,1,0), matrix, matrix);
		Matrix4f.rotate((float)Math.toRadians(this.getRoll()), new Vector3f(0,0,1), matrix, matrix);
		Vector3f neg = new Vector3f(-this.getPosition().x, -this.getPosition().y, -this.getPosition().z);
		Matrix4f.translate(neg, matrix, matrix);
		viewMatrix = matrix;
	}
	
	@Override
	public Matrix4f getViewMatrix() {
		return viewMatrix;
	}

	@Override
	public Matrix4f getProjectionMatrix() {
		return projectionMatrix;
	}

	@Override
	public Matrix4f getProjectionViewMatrix() {
		return Matrix4f.mul(projectionMatrix, viewMatrix, null);
	}

	@Override
	public void move() {
		relativeKeyBoardNav();
		updateViewMatrix();
	}

	public boolean isTerminalUp() {
		return terminalUp;
	}
}
