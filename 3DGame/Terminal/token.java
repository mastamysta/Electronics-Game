package Terminal;

public class token {
	private String  name;
	private boolean isValid;
	private char encodedVal;
	
	public token(String name) {
		this.name = name;
		interpretToken();
	}
	
	private void interpretToken() {
		switch(this.name) {
		case "motor.Forward":
			this.isValid = true;
			this.encodedVal = 'a';
		break;
		case "motor.Backward":
			this.isValid = true;
			this.encodedVal = 'b';
		break;
		case "motor.Stop":
			this.isValid = true;
			this.encodedVal = 'c';
			break;
		default:
			this.encodedVal = '}';
			this.isValid = false;
		break;
		}
			
	}

	public String getName() {
		return name;
	}

	public boolean isValid() {
		return isValid;
	}

	public char getEncodedVal() {
		return encodedVal;
	}
	
}
