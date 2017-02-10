package com.gnarly.engine.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.gnarly.engine.display.Camera;
import com.gnarly.game.objects.Tile;

public class MapManager {
	
	public void saveMap(Tile[][] map, String fileName) {
		int[][] intMap = new int[map.length][map[0].length];
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				switch (map[i][j].getTexture().getName()) {
				case "Stone Floor.png" :
					intMap[i][j] = 0;
					break;
				case "Brick Wall.png" :
					intMap[i][j] = 1;
					break;
				case "Circular Wall.png" :
					System.out.println("circle");
					intMap[i][j] = 2;
					break;
				}
			}
		}
		
		try {
			//Write anarray to a binary file
		    ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName + ".dat"));
		    oos.writeObject(intMap);
		    oos.close();
		    
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Tile[][] loadMap(String fileName, Camera camera, Library library, int SCALE) {
		int[][] ia = null;
		
		try {
			//Read arrays from a binary file
		    ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName + ".dat"));
		    ia = (int[][]) (ois.readObject());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Tile[][] map = new Tile[ia.length][ia[0].length];
		
		for (int k = 0; k < ia.length; k++) {
			for (int k2 = 0; k2 < ia[0].length; k2++) {
				switch (ia[k][k2]) {
					case 0 :
						map[k][k2] = new Tile(camera, library.getTexture("Stone Floor.png"), library.getShader("default"), k * SCALE, k2 * SCALE, SCALE, SCALE, false);
						break;
					case 1 :
						map[k][k2] = new Tile(camera, library.getTexture("Brick Wall.png"), library.getShader("default"), k * SCALE, k2 * SCALE, SCALE, SCALE, true);
						break;
					case 2 :
						map[k][k2] = new Tile(camera, library.getTexture("Circular Wall.png"), library.getShader("default"), k * SCALE, k2 * SCALE, SCALE, SCALE, true);
						break;
				}
			}
		}
		return map;
	}
}
