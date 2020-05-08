package Terminal;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class executor {
	private List<token> currentLine;
	private String encodedLine;
	private boolean validLine;
	
	private final String validRegex = "(a*b*c*)*";
	
	//temp stuff that only works for a single motor
	private boolean runningForward;
	private boolean runningBackward;
	
	public executor() {
		currentLine = new ArrayList<token>();
		encodedLine = "";
		validLine = false;
	}
	
	public void execute(List<token> currentLine) {
		this.encodedLine = "";
		this.validLine = false;
		this.currentLine = currentLine;
		for (token t:this.currentLine) {
			this.encodedLine = this.encodedLine + Character.toString(t.getEncodedVal());
		}
		validLine = Pattern.matches(validRegex, this.encodedLine);
		if (validLine) {
			System.out.println("The encoding is: " + encodedLine);
			Terminal.newLine("valid");
			executeValidLine();
		}
		else {
			Terminal.newLine("inValid");
		}
	}
	
	private void executeValidLine() {
		for(int i = 0; i < encodedLine.length(); i ++) {
			executeToken(encodedLine.charAt(i));
		}
	}
	
	private void executeToken(char c) {
		switch(c) {
		case ('a'):
			this.runningBackward = false;
			this.runningForward = true;
			break;
		case ('b'):
			this.runningForward = false;
			this.runningBackward = true;
			break;
		case ('c'):
			this.runningBackward = false;
			this.runningForward = false;
			break;
		default:
			break;
		}	
	}

	public boolean isRunningForward() {
		return runningForward;
	}

	public boolean isRunningBackward() {
		return runningBackward;
	}
}
