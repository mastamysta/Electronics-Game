package mousePicker;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import models.animatedModel;
import models.camera;
import models.collisionSphere;
import parts.part;
import toolbox.Maths;

public class mousePicker {
	
	private Vector3f currentRay;
	private final float rayLength = 200;
	
	private Matrix4f projectionMatrix;
	private Matrix4f viewMatrix;
	private camera camera;
	
	private boolean isActive;
	
	public mousePicker(models.camera camera) {
		this.projectionMatrix = camera.getProjectionMatrix();
		this.viewMatrix = camera.getViewMatrix();
		this.camera = camera;
		this.isActive = true;
	}

	public Vector3f getCurrentRay() {
		return currentRay;
	}
	
	public void update() {
		if (this.isActive) {
			viewMatrix = camera.getViewMatrix();
			currentRay = calculateMouseRay();
		}
		
	}
	
	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	private Vector3f calculateMouseRay() {
		float mouseX = Mouse.getX();
		float mouseY = Mouse.getY();
		Vector2f normalisedCoords = getNormalisedCoordinates(mouseX, mouseY);
		Vector4f clipCoords = new Vector4f(normalisedCoords.x, normalisedCoords.y, -1f, 1f);
		Vector4f eyeCoords = toEyeCoords(clipCoords);
		return toWorldCoords(eyeCoords);
	}
	
	private Vector3f toWorldCoords(Vector4f eyeCoords) {
		Matrix4f invertedViewMatrix = Matrix4f.invert(viewMatrix, null);
		Vector4f worldCoords = Matrix4f.transform(invertedViewMatrix, eyeCoords, null);
		Vector3f mouseRay = new Vector3f(worldCoords.x, worldCoords.y, worldCoords.z);
		mouseRay.normalise();
		return mouseRay;
	}
	
	private Vector4f toEyeCoords(Vector4f clipCoords) {
		Matrix4f inverseProjectionMatrix = Matrix4f.invert(projectionMatrix, null);
		Vector4f eyeCoords = Matrix4f.transform(inverseProjectionMatrix , clipCoords, null);
		return new Vector4f(eyeCoords.x, eyeCoords.y, -1f, 0f);
	}
	
	private Vector2f getNormalisedCoordinates(float mouseX, float mouseY) {
		float x = (2f * mouseX) / Display.getWidth() - 1;
		float y = (2f * mouseY) / Display.getHeight() - 1f;
		return new Vector2f(x, y);
	}
	
	
	public Optional<part> pickedModel(List<part> parts, camera cam) {
		List<part> partList = new ArrayList<part>();
		for (part mod: parts) {
			if(intersectsWith(mod.getModel().getHitbox(), cam.getPosition())) {
				partList.add(mod);
			}
		}
		if (!partList.isEmpty()) {
			return Optional.of(Maths.nearestPart(partList, cam));
		}
		return Optional.empty();
	}
	
	private boolean intersectsWith(collisionSphere sphere, Vector3f rayOrigin) {
		Vector3f originToCircle = Vector3f.sub(sphere.getLocation(), rayOrigin, null);
		float angle = Vector3f.angle(originToCircle, currentRay);
		if(Math.sin(angle) * originToCircle.length() <= sphere.getRadius()) {
			return true;
		}
		return false;
	}
	
	//deprocated code messy and slow
	private boolean intersectsWith(collisionSphere sphere, float distance, Vector3f rayOrigin) {
		float pointToCentre = Vector3f.sub(sphere.getLocation(), rayOrigin, null).length();
		if(pointToCentre <= sphere.getRadius()) {
			//System.out.println("pointtocentrelessthansphereradius");
			return true;
		}
		else if (distance < 4) {
			return false;
		}
		else {
			Vector3f newOrigin;
			Vector3f endPoint = Vector3f.add(rayOrigin, new Vector3f(currentRay.x * distance, 
					currentRay.y * distance, currentRay.z * distance), null);
			float endToCentre = Vector3f.sub(sphere.getLocation(), endPoint, null).length();
			if (pointToCentre < endToCentre) {
				newOrigin = rayOrigin;
				//System.out.println("recursing near");
				//System.out.println(pointToCentre + " " + endToCentre);
			}
			else {
				newOrigin = Vector3f.add(rayOrigin, new Vector3f(currentRay.x * distance / 2, 
						currentRay.y * distance / 2, currentRay.z * distance / 2), null);
				//System.out.println("recursing far");
				//System.out.println(pointToCentre + " " + endToCentre);

			}
			System.out.println(pointToCentre + " " + endToCentre + " " + sphere.getRadius());
			return intersectsWith(sphere, distance / 2, newOrigin);
		}
	}
	
}
