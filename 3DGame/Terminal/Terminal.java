package Terminal;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import fontMeshCreator.FontType;
import fontMeshCreator.GUIText;
import fontRendering.TextMaster;
import loaders.modelLoader;

public class Terminal {
	
	private static int lineNumber;
	private static final float lineHeight = 0.07f;
	private static final float maxLineLength = 5f;
	private static final int fontSize = 2;
	private static final int maxLines = (int) Math.ceil(1/lineHeight);
	private static modelLoader loader;
	
	private static int textureatlas;
	private static File fontFile;
	private static final Vector3f fontColor = new Vector3f(1,1,1);
	private static FontType font;
	private static GUIText currentText;
	private static String currentString;
	
	private static kernel kernel;
	
	//only valid for a kernel which suppots 1 motor
	public static boolean isForward() {
		return kernel.isMotorForward();
	}
	
	public static boolean isBackward() {
		return kernel.isMotorBackward();
	}
	
	public static void init() {
		lineNumber = 0;
		
		loader = new modelLoader();
		textureatlas = loader.loadTexture("tahoma");
		fontFile = new File("res/tahoma.fnt");
		font = new FontType(textureatlas, fontFile);
		currentString = "";
		currentText = new GUIText(currentString, fontSize, font, new Vector2f(0, lineHeight * lineNumber), maxLineLength, false);
		currentText.setColour(fontColor.x, fontColor.y, fontColor.z);
		for (int i = 0; i < maxLines; i ++) {
			GUIText pipe = new GUIText("|", fontSize, font, new Vector2f(0.725f, lineHeight * i), maxLineLength, false);
			pipe.setColour(fontColor.x, fontColor.y, fontColor.z);
		}
		kernel = new kernel();
		int i = 0;
		GUIText possibleCommand = new GUIText("Valid Commands:", fontSize, font, new Vector2f(0.75f, lineHeight * i), maxLineLength, false);
		possibleCommand.setColour(fontColor.x, fontColor.y, fontColor.z);
		for (token t: kernel.getAllPossible()) {
			i += 1;
			possibleCommand = new GUIText(t.getName(), fontSize, font, new Vector2f(0.75f, lineHeight * i), maxLineLength, false);
			possibleCommand.setColour(fontColor.x, fontColor.y, fontColor.z);
			
		}		
	}
	
	public static void newLine(String s) {
		newLine();
		currentString = s;
		currentText = new GUIText(currentString, fontSize, font, new Vector2f(0, lineHeight * lineNumber), maxLineLength, false);
		currentText.setColour(fontColor.x, fontColor.y, fontColor.z);
	}
	
	public static void newLine() {
		currentString = "";
		currentText = new GUIText(currentString, fontSize, font, new Vector2f(0, lineHeight * lineNumber), maxLineLength, false);
		lineNumber += 1;
	}
	
	public static void clearTerminal() {
		TextMaster.clear();
		currentString = "";
		currentText = new GUIText(currentString, fontSize, font, new Vector2f(0, lineHeight * lineNumber), maxLineLength, false);
		lineNumber = 0;
	}
	
	public static void updateTerminal() {
		currentText.remove();
		while(Keyboard.next()) {
			if(Keyboard.getEventKeyState() && !Keyboard.isRepeatEvent() && 
					(Character.isLetter(Keyboard.getEventCharacter()) || Keyboard.getEventKey() == Keyboard.KEY_SPACE || Keyboard.getEventKey() == Keyboard.KEY_PERIOD)) {
				currentString = currentString + Keyboard.getEventCharacter();
			}
			if(Keyboard.getEventKeyState() && !Keyboard.isRepeatEvent() && Keyboard.getEventKey() == Keyboard.KEY_BACK && currentString.length() != 0) {
				currentString = currentString.substring(0, currentString.length() - 1);
			}
			if(Keyboard.getEventKeyState() && !Keyboard.isRepeatEvent() && Keyboard.getEventKey() == Keyboard.KEY_RETURN) {
				TextMaster.loadText(currentText);
				kernel.execute(currentString);
				newLine();
			}
		}
		currentText = new GUIText(currentString, fontSize, font, new Vector2f(0, lineHeight * lineNumber), maxLineLength, false);
		currentText.setColour(fontColor.x, fontColor.y, fontColor.z);
	}
	
	
}
