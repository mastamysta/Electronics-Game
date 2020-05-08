package models;

import main.RenderEngine;

public class unAnimatedModel extends model{
	private texturedModel texMod;
	public unAnimatedModel(texturedModel texMod,int x,int y, float rx, float ry, float rz, float scale) {
		super(x, y, rx, ry, rz, scale);
		this.texMod = texMod;
	}

	public texturedModel getTexMod() {
		return texMod;
	}

	@Override
	public void addModel(RenderEngine engine) {
		engine.addUnAnimatedModel(this);
		
	}

	@Override
	public void removeModel(RenderEngine engine) {
		engine.removeUnAnimatedModel(this);
		
	}
}
