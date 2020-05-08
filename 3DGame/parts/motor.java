package parts;

import loaders.AnimatedModelLoader;
import loaders.AnimationLoader;
import renderSystem.Animation;
import toolbox.GeneralSettings;
import toolbox.MyFile;

public class motor extends part{
	
	private boolean runningForward;
	private boolean runningBackward;
	private Animation runningForwardAnim;
	private Animation runningBackwardAnim;
	private Animation nullAnim;
		
	public motor(int x, int y) {
		super(AnimatedModelLoader.loadEntity(new MyFile(GeneralSettings.RES_FOLDER, GeneralSettings.MODEL_FILE),
				new MyFile(GeneralSettings.RES_FOLDER, GeneralSettings.DIFFUSE_FILE),x,y,0,50,0,1));
		runningForwardAnim = AnimationLoader.loadAnimation(new MyFile(GeneralSettings.RES_FOLDER, GeneralSettings.ANIM_FILE));
		runningBackwardAnim = AnimationLoader.loadAnimation(new MyFile(GeneralSettings.RES_FOLDER, GeneralSettings.ANIM_FILE));
		nullAnim = null;
	}
	
	public void motorForward() {
		runningForward = true;
		runningBackward = false;
		super.getModel().doAnimation(runningForwardAnim);
	}
	
// modify for a backwards animation
	public void motorBackward() {
		runningForward = false;
		runningBackward = true;
		super.getModel().doAnimation(runningBackwardAnim);
	}
	
	public void motorStop() {
		runningForward = false;
		runningBackward = false;
		super.getModel().doAnimation(nullAnim);
	}

	public boolean isRunningForward() {
		return runningForward;
	}

	public boolean isRunningBackward() {
		return runningBackward;
	}
}
