package toolbox;

import java.util.List;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import models.animatedModel;
import models.camera;
import parts.part;

public class Maths {
	
	public static final Vector3f unitX = new Vector3f(1, 0, 0);
	public static final Vector3f unitY = new Vector3f(0, 1, 0);
	public static final Vector3f unitZ = new Vector3f(0, 0, 1);
	
	private static final float OBJMARGIN = 0.2f;
	private static final float OBJHEIGHT = 3.5f;
	
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
	
	public static Matrix4f createTransformationMatrix(Vector2f translation, Vector2f scale) {
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		Matrix4f.translate(translation, matrix, matrix);
		Matrix4f.scale(new Vector3f(scale.x, scale.y, 1f), matrix, matrix);
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
	
	public static animatedModel nearestModel(List<animatedModel> models, camera cam) {
		animatedModel nearest = models.get(0);
		float distance = Vector3f.sub(nearest.getLocation(), cam.getPosition(), null).length();
		for (animatedModel mod: models) {
			if (Vector3f.sub(mod.getLocation(), cam.getPosition(), null).length() < distance) {
				distance = Vector3f.sub(mod.getLocation(), cam.getPosition(), null).length();
				nearest = mod;
			}
		}
		return nearest;
	}
	
	public static part nearestPart(List<part> parts, camera cam) {
		animatedModel nearest = parts.get(0).getModel();
		part nearestPart = parts.get(0);
		float distance = Vector3f.sub(nearest.getLocation(), cam.getPosition(), null).length();
		for (part mod: parts) {
			if (Vector3f.sub(mod.getModel().getLocation(), cam.getPosition(), null).length() < distance) {
				distance = Vector3f.sub(mod.getModel().getLocation(), cam.getPosition(), null).length();
				nearest = mod.getModel();
				nearestPart = mod;
			}
		}
		return nearestPart;
	}
	
	public static Vector3f rayGroundIntersection(Vector3f unitRay, Vector3f location, float rayLength) {
		if(location.y > OBJHEIGHT + OBJMARGIN) {
			Vector3f addedRay = new Vector3f(unitRay.x * rayLength, unitRay.y * rayLength, unitRay.z * rayLength);
			Vector3f newLocation = Vector3f.add(location, addedRay, null);
			return rayGroundIntersection(unitRay, newLocation, rayLength / 2);
		}
		else if (location.y <  OBJHEIGHT -OBJMARGIN) {
			Vector3f addedRay = new Vector3f(unitRay.x * rayLength, unitRay.y * rayLength, unitRay.z * rayLength);
			Vector3f.sub(location, addedRay, location);
			return rayGroundIntersection(unitRay, location, rayLength / 2);
		}
		else {
			Vector3f out = new Vector3f(location.x, 0, location.z);
			return out;
		}
	}
}
