package loaders;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import texture.TextureData;
import texture.TextureUtils;
import toolbox.MyFile;

public class skyBoxLoader {

	public int loadCubeMap(String[] textures_faces) {
		int textureID = GL11.glGenTextures();
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, textureID);
		 
		for(int i = 0; i < textures_faces.length; i++)
		{
		    	TextureData data = TextureUtils.decodeTextureFile(new MyFile("res/" + textures_faces[i] + ".png"));
		        GL11.glTexImage2D(GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_X + i, 0, GL11.GL_RGBA, data.getWidth(), 
		        		data.getHeight(), 
		        		0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, data.getBuffer());
		}
		
		GL11.glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		
		return textureID;
	}
	
}
