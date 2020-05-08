package parts;

import models.animatedModel;

public abstract class part {
	
	private animatedModel model;
	public part(animatedModel model) {
		this.model = model;
	}
	public animatedModel getModel() {
		return model;
	}
}
