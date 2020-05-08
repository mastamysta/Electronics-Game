package shaders;

import org.lwjgl.util.vector.Vector3f;

public class light {
		private Vector3f location;
		private Vector3f color;
		
		public light(Vector3f location, Vector3f color) {
			this.location = location;
			this.color = color;
		}
		
		
		public Vector3f getLocation() {
			return location;
		}
		public void setLocation(Vector3f location) {
			this.location = location;
		}
		public Vector3f getColor() {
			return color;
		}
		public void setColor(Vector3f color) {
			this.color = color;
		}
}
