package SkyBoxDEPROCATED;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class skyBoxShader extends shaderProgram {
	
	private static final String fragShaderPath = "/home/ben/Programming/Java/Java Project Space/3DGame/shaders/fragmentShader";
	private static final String vertShaderPath = "/home/ben/Programming/Java/Java Project Space/3DGame/shaders/vertexShader";

	private int location_transformationMatrix;
	private int location_projectionMatrix;
	private int location_viewMatrix;
	private int location_lightLocation;
	private int location_lightColor;
	private int location_shineDamper;
	private int location_reflectivity;
	private int location_skyColor;
	
	public skyBoxShader() {
		super(vertShaderPath, fragShaderPath);
	}

	@Override
	protected void bindAttribs() {
		super.bindAttrib(0, "position");
		super.bindAttrib(1, "tex_coords");
		super.bindAttrib(2, "normal");
		
	}

	@Override
	protected void getAllUniformLocations() {
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_viewMatrix = super.getUniformLocation("viewMatrix");
		location_lightLocation = super.getUniformLocation("lightLocation");
		location_lightColor = super.getUniformLocation("lightColor");
		location_shineDamper = super.getUniformLocation("shineDamper");
		location_reflectivity = super.getUniformLocation("reflectivity");
		location_skyColor = super.getUniformLocation("skyColor");
	}
	
	public void loadSkyColor(float r, float g, float b) {
		super.loadVector(location_skyColor, new Vector3f(r, g, b));
	}
	
	public void loadSpecularLighting(float shineDamper, float reflectivity) {
		super.loadFloat(location_shineDamper, shineDamper);
		super.loadFloat(location_reflectivity, reflectivity);
	}
	
	public void loadLight(light light) {
		super.loadVector(location_lightLocation, light.getLocation());
		super.loadVector(location_lightColor, light.getColor());
	}
	
	public void loadTransformationMatrix(Matrix4f matrix) {
		super.loadMatraix(location_transformationMatrix, matrix);
	}

	public void loadProjectionMatrix(Matrix4f projectionMatrix) {
		super.loadMatraix(location_projectionMatrix, projectionMatrix);
		
	}
	
	public void loadviewMatrix(Matrix4f projectionMatrix) {
		super.loadMatraix(location_viewMatrix, projectionMatrix);
		
	}

}
