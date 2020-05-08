package models;

import java.util.UUID;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import main.RenderEngine;
import models.Joint;
import renderSystem.Animation;
import renderSystem.Animator;
import texture.Texture;

public class animatedModel extends model{
	
	private final TDModel vao;
	private final Texture texture;
	private final Joint rootJoint;
	
	private UUID animatedModelID;

	private final int jointCount;	
	private final Animator animator;

	public animatedModel(TDModel vao, Texture texture, Joint rootJoint, int jointCount, int x, int y, float rx, float ry, float rz, float scale) {
		super(x, y, rx, ry, rz, scale);
		this.vao = vao;
		this.texture = texture;
		this.rootJoint = rootJoint;
		this.jointCount = jointCount;
		this.animator = new Animator(this);
		this.animatedModelID =  UUID.randomUUID();
		rootJoint.calcInverseBindTransform(new Matrix4f());
	}
	
	public TDModel getVao() {
		return vao;
	}

	public Texture getTexture() {
		return texture;
	}

	public Joint getRootJoint() {
		return rootJoint;
	}
	
	public void delete() {
		//TODO
	}
	
	public void doAnimation(Animation animation) {
		animator.doAnimation(animation);
	}
	
	public void update() {
		animator.update();
	}
	
	//search for calls of this
	public Matrix4f[] getJointTransforms() {
		Matrix4f[] jointMatrices = new Matrix4f[jointCount];
		addJointsToArray(rootJoint, jointMatrices);
		return jointMatrices;
	}
	
	private void addJointsToArray(Joint headJoint, Matrix4f[] jointMatrices) {
		jointMatrices[headJoint.index] = headJoint.getAnimatedTransform();
		for (Joint childJoint : headJoint.children) {
			addJointsToArray(childJoint, jointMatrices);
		}
	}

	public UUID getAnimatedModelID() {
		return animatedModelID;
	}

	@Override
	public void addModel(RenderEngine renderer) {
		renderer.addAnimatedModel(this);
		
	}

	@Override
	public void removeModel(RenderEngine renderer) {
		renderer.removeAnimatedModel(this);
		
	}
}
