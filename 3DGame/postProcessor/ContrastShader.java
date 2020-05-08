package postProcessor;

import shaders.shaderProgram;

public class ContrastShader extends shaderProgram {

	private static final String VERTEX_FILE = "/postProcessing/contrastVertex.txt";
	private static final String FRAGMENT_FILE = "/postProcessing/contrastFragment.txt";
	
	public ContrastShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void getAllUniformLocations() {	
	}

	@Override
	protected void bindAttribs() {
		super.bindAttrib(0, "position");
	}

}
