package Terminal;

import java.util.ArrayList;
import java.util.List;

public class kernel {
	
	private String rawInput;
	private List<String> splitInput;
	private List<token> tokens;
	private executor executor;
	private boolean validTokens;
	
	private List<token>	allPossible = new ArrayList<token>();
	
	public kernel() {
		rawInput = "";
		splitInput = new ArrayList<String>();
		tokens = new ArrayList<token>();
		executor = new executor();
		validTokens = false;
		allPossible.add(new token("motor.Forward"));
		allPossible.add(new token("motor.Backward"));
		allPossible.add(new token("motor.Stop"));
	}
	
	protected List<token> getAllPossible() {
		return allPossible;
	}

	protected void execute(String input) {
		rawInput = input;
		splitInput.clear();
		tokens.clear();
		validTokens = true;
		
		for (String S: rawInput.split(" ")) {
			splitInput.add(S);
		}
		for(String S: splitInput) {
			tokens.add(new token(S));
		}
		for(token t: tokens) {
			if(!t.isValid()) {
				validTokens = false;
				Terminal.newLine("Invalid token: " + t.getName());
			}
		}
		if (validTokens) {
			executor.execute(tokens);
		}
	}
	
	protected boolean isMotorForward() {
		return executor.isRunningForward();
	}
	
	protected boolean isMotorBackward() {
		return executor.isRunningBackward();
	}
}
