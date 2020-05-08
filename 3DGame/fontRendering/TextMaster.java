package fontRendering;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fontMeshCreator.FontType;
import fontMeshCreator.GUIText;
import fontMeshCreator.TextMeshData;
import loaders.modelLoader;
import models.TDModel;

public class TextMaster {
	
	private static modelLoader loader;
	private static Map<FontType, List<GUIText>> texts = new HashMap<FontType, List<GUIText>>();
	private static FontRenderer renderer;
	
	public static void init(modelLoader theLoader){
		renderer = new FontRenderer();
		loader = theLoader;
	}
	
	//selfmade so bug prone
	public static void clear() {
		texts.clear();
	}
	
	public static void render(){
		renderer.render(texts);
	}
	
	public static void loadText(GUIText text){
		FontType font = text.getFont();
		TextMeshData data = font.loadText(text);
		TDModel model = loader.loadToVao(data.getVertexPositions(), data.getTextureCoords());
		int vao = model.getVAOID();
		text.setMeshInfo(vao, data.getVertexCount());
		// new list equal to already loaded list of texts for the font corresponding to the new text
		List<GUIText> textBatch = texts.get(font);
		// if there was no list do this
		if(textBatch == null){
			// make a new empty list
			textBatch = new ArrayList<GUIText>();
			//put it in the hashmap
			texts.put(font, textBatch);
		}
		//add text to list
		textBatch.add(text);
		//IS THIS BYREF??
	}
	
	public static void removeText(GUIText text){
		texts.get(text.getFont()).remove(text);
//		if(texts.get(text.getFont()).isEmpty()){
//			texts.remove(texts.get(text.getFont()));
//		}
	}
	
	public static void cleanUp(){
		renderer.cleanUp();
	}

}
