package renderSystem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import models.TDModel;

public class objLoader {
	
	public static TDModel loadObjModel(String filename, modelLoader loader) {
		FileReader fs = null;
		try {
			fs = new FileReader(new File("res/"+filename+".obj"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.print("File not found\n");
		}
		BufferedReader reader = new BufferedReader(fs);
		String line;
		List<Vector3f> vertices = new ArrayList<Vector3f>();
		List<Vector2f> textures = new ArrayList<Vector2f>();
		List<Vector3f> normals = new ArrayList<Vector3f>();
		List<Integer> indeces = new ArrayList<Integer>();
		
		float[] verticesarray = null;
		float[] texturesarray = null;
		float[] normalsarray = null;
		int[] indecesarray = null;
		
		try {
			while(true) {
				line = reader.readLine();
				String[] splitLine = line.split(" ");
				if(line.startsWith("v ")) {
					vertices.add(new Vector3f(Float.parseFloat(splitLine[1]), Float.parseFloat(splitLine[2]), Float.parseFloat(splitLine[3])));
				}else if(line.startsWith("vt ")) {
					textures.add(new Vector2f(Float.parseFloat(splitLine[1]), Float.parseFloat(splitLine[2])));
				}else if(line.startsWith("vn ")) {
					normals.add(new Vector3f(Float.parseFloat(splitLine[1]), Float.parseFloat(splitLine[2]), Float.parseFloat(splitLine[3])));
				}else if(line.startsWith("f ")) {
					texturesarray = new float[vertices.size() * 2];
					normalsarray = new float[vertices.size() * 3];
					break;
				}
				
				
			}
			
			while(line!=null) {
				if(!line.startsWith("f ")) {
					line = reader.readLine();
					continue;
				}
				String[] curline = line.split(" ");
				String[] vertex1 = curline[1].split("/");
				String[] vertex2 = curline[2].split("/");
				String[] vertex3 = curline[3].split("/");

				processVertex(vertex1, indeces, textures, normals, texturesarray, normalsarray);
				processVertex(vertex2, indeces, textures, normals, texturesarray, normalsarray);
				processVertex(vertex3, indeces, textures, normals, texturesarray, normalsarray);
				line = reader.readLine();
				
			}
			reader.close();
		}catch(Exception e) {
			System.out.print("Error loading from file");
			e.printStackTrace();
		}
		
		verticesarray = new float[vertices.size()*3];
		indecesarray = new int[indeces.size()];
		normalsarray = new float[normals.size()*3];
		
		int vertexpointer = 0;
		for(Vector3f vertex:vertices) {
			verticesarray[vertexpointer++] = vertex.x;
			verticesarray[vertexpointer++] = vertex.y;
			verticesarray[vertexpointer++] = vertex.z;
		}
		for(int i=0; i<indeces.size();i++) {
			indecesarray[i] = indeces.get(i);
		}
		int normalpointer = 0;
		for(Vector3f normal:normals) {
			normalsarray[normalpointer++] = normal.x;
			normalsarray[normalpointer++] = normal.y;
			normalsarray[normalpointer++] = normal.z;
		}
		return loader.loadToVao(verticesarray, texturesarray, indecesarray, normalsarray);
	}
	
	private static void processVertex(String[] vertexData, List<Integer> indeces, List<Vector2f> texCoords, 
			List<Vector3f> normals, float[] texturearray, float[] normalarray) {
		int currentIndex = Integer.parseInt(vertexData[0]) - 1;
		indeces.add(currentIndex);
		Vector2f currentTex = texCoords.get(Integer.parseInt(vertexData[1]) - 1);
		texturearray[currentIndex*2] = currentTex.x;
		texturearray[currentIndex*2 + 1] = 1 - currentTex.y;
		Vector3f currentNorm = normals.get(Integer.parseInt(vertexData[2]) - 1);
		normalarray[currentIndex*3] = currentNorm.x;
		normalarray[currentIndex*3 + 1] = currentNorm.y;
		normalarray[currentIndex*3 + 2] = currentNorm.z;

	}
	
}
