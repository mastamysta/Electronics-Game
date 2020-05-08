package toolbox;

import org.lwjgl.util.vector.Vector4f;
import toolbox.colorPackage;

public class colorPicker {
	public static Vector4f color(colorPackage color) {
		if (color == colorPackage.HotOrange) {
			return new Vector4f(245/255f, 78/255f, 27/255f, 0.5f);
		}
		if (color == colorPackage.LightOrange) {
			return new Vector4f(235/255f, 117/255f, 45/255f, 0.5f);
		}
		if (color == colorPackage.DeepBlue) {
			return new Vector4f(2/255f, 8/255f, 74/255f, 0.5f);
		}
		if (color == colorPackage.Cyan) {
			return new Vector4f(22/255f, 145/255f, 69/255f, 0.5f);
		}
		if (color == colorPackage.Perrywinkle) {
			return new Vector4f(198/255f, 216/255f, 255/255f, 0.5f);
		}
		return new Vector4f(0,0,0,0);
		
	}
}
