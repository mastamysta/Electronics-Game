package toolbox;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import models.camera;

public class Maths {
	
	public static final Vector3f unitX = new Vector3f(1, 0, 0);
	public static final Vector3f unitY = new Vector3f(0, 1, 0);
	public static final Vector3f unitZ = new Vector3f(0, 0, 1);
	
	public static Matrix4f createTransformationMatrix(Vector3f translation, float scale, float rx, float ry, float rz) {
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();		
		Matrix4f.translate(translation, matrix, matrix);
		Matrix4f.rotate((float)Math.toRadians(rx), new Vector3f(1,0,0), matrix, matrix);
		Matrix4f.rotate((float)Math.toRadians(ry), new Vector3f(0,1,0), matrix, matrix);
		Matrix4f.rotate((float)Math.toRadians(rz), new Vector3f(0,0,1), matrix, matrix);
		Matrix4f.scale(new Vector3f(scale,scale,scale), matrix, matrix);
		//System.out.print(matrix);
		return matrix;
	}
	
	public static Matrix4f createViewMatrix(camera cam) {
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		Matrix4f.rotate((float)Math.toRadians(cam.getPitch()), new Vector3f(1,0,0), matrix, matrix);
		Matrix4f.rotate((float)Math.toRadians(cam.getYaw()), new Vector3f(0,1,0), matrix, matrix);
		Matrix4f.rotate((float)Math.toRadians(cam.getRoll()), new Vector3f(0,0,1), matrix, matrix);
		Vector3f neg = new Vector3f(-cam.getPosition().x, -cam.getPosition().y, -cam.getPosition().z);
		Matrix4f.translate(neg, matrix, matrix);
		//System.out.print(matrix);
		return matrix;
	}
	
	public static Matrix4f lookingAt(camera cam, Vector3f target) {
		Vector3f view = new Vector3f();
		Vector3f.sub(target, cam.getPosition(), view);
		float dx = 0, dy = 0, dz = 0;
		float dotx, doty, dotz;
		dotx = Vector3f.dot(view, unitX);
		doty = Vector3f.dot(view, unitY);
		dotz = Vector3f.dot(view, unitZ);
		dx = (float) Math.acos(dotx/view.length());
		dy = (float) Math.acos(doty/view.length());
		dz = (float) Math.acos(dotz/view.length());
		cam.setPitch(dx);
		cam.setYaw(dy);
		cam.setRoll(dz);
		return createViewMatrix(cam);
	}
}
