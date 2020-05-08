package fontRendering;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import renderSystem.windowManager;
import shaders.shaderProgram;

public class FontShader extends shaderProgram{

	private static final String VERTEX_FILE = "fontRendering/fontVertex.txt";
	private static final String FRAGMENT_FILE = "fontRendering/fontFragment.txt";
	
	private int location_colour;
	private int location_translation;
	private int location_iTime;
	private int location_iResolution;
	
	private float runTime;
	
	public FontShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
		runTime = 0;
	}

	@Override
	protected void getAllUniformLocations() {
		location_colour = super.getUniformLocation("colour");
		location_translation = super.getUniformLocation("translation");
	}

	@Override
	protected void bindAttribs() {
		super.bindAttrib(0, "position");
		super.bindAttrib(1, "textureCoords");
	}
	
	protected void loadColour(Vector3f colour){
		super.loadVector(location_colour, colour);
	}
	
	protected void loadTranslation(Vector2f translation){
		super.loadVector(location_translation, translation);
	}



}
