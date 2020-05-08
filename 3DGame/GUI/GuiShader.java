package GUI;

import org.lwjgl.util.vector.Matrix4f;

import shaders.shaderProgram;

public class GuiShader extends shaderProgram{
	
	private static final String VERTEX_FILE = "GUI/guiVertexShader.txt";
	private static final String FRAGMENT_FILE = "GUI/guiFragmentShader.txt";
	
	private int location_transformationMatrix;

	public GuiShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}
	
	public void loadTransformation(Matrix4f matrix){
		super.loadMatrix(location_transformationMatrix, matrix);
	}

	@Override
	protected void getAllUniformLocations() {
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
	}

	@Override
	protected void bindAttribs() {
		super.bindAttrib(0, "position");
	}
	
	
	

}
