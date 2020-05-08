package Terminal;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;

import renderSystem.windowManager;
import shaders.shaderProgram;

public class terminalShader extends shaderProgram{
	
	private static final String VERTEX_FILE = "Terminal/TerminalVertexShader.glsl";
	private static final String FRAGMENT_FILE = "Terminal/TerminalFragmentShader.glsl";
            // shader playback time (in seconds)
	private int location_transformationMatrix;
	private int location_iResolution;
	private int location_iTime;
	
	private float runTime;

	public terminalShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
		runTime = 0;
	}

	@Override
	protected void bindAttribs() {
		super.bindAttrib(0, "position");
	}

	@Override
	protected void getAllUniformLocations() {
		location_transformationMatrix = getUniformTransformationMatrixLocation();
		location_iResolution = getIResolutionLocation();
		location_iTime = getiTimeLocation();
	}
	
	private int getiTimeLocation() {
		return super.getUniformLocation("iTime");
	}

	private int getIResolutionLocation() {
		return super.getUniformLocation("iResolution");
	}
	
	private int getUniformTransformationMatrixLocation() {
		return super.getUniformLocation("transformationMatrix");
	}
	
	public void setiTime() {
		super.loadFloat(location_iTime, runTime);
		runTime += windowManager.getFrameTime();
	}
	
	public void setIResolution() {
		super.loadVector(location_iResolution, new Vector2f(Display.getWidth(), Display.getHeight()));
	}
		
	public void setTransformationMatrix(Matrix4f transformationMatrix) {
		super.loadMatrix(location_transformationMatrix, transformationMatrix);
	}
}
