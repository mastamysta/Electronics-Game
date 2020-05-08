package terrain;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import shaders.light;
import shaders.shaderProgram;

public class terrainShader extends shaderProgram{
	
	private static final String fragTerrainShaderPath = "/home/ben/Programming/Java/Java Project Space/3DGame/shaders/terrainFragmentShader";
	private static final String vertTerrainShaderPath = "/home/ben/Programming/Java/Java Project Space/3DGame/shaders/terrainVertexShader";

	private int location_transformationMatrix;
	private int location_projectionMatrix;
	private int location_viewMatrix;
	private int location_lightLocation;
	private int location_lightColor;
	private int location_skyColor;
	
	public terrainShader() {
		super(vertTerrainShaderPath, fragTerrainShaderPath);
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
		location_skyColor = super.getUniformLocation("skyColor");
	}
	
	public void loadLight(light light) {
		super.loadVector(location_lightLocation, light.getLocation());
		super.loadVector(location_lightColor, light.getColor());
	}
	
	public void loadTransformationMatrix(Matrix4f matrix) {
		super.loadMatrix(location_transformationMatrix, matrix);
	}

	public void loadProjectionMatrix(Matrix4f projectionMatrix) {
		super.loadMatrix(location_projectionMatrix, projectionMatrix);
		
	}
	
	public void loadviewMatrix(Matrix4f projectionMatrix) {
		super.loadMatrix(location_viewMatrix, projectionMatrix);
		
	}
	
	public void loadSkyColor(Vector3f skyColor) {
		super.loadVector(location_skyColor, skyColor);
	}

}
