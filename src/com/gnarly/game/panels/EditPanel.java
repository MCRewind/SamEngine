package com.gnarly.game.panels;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_J;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_K;

import com.gnarly.engine.display.Camera;
import com.gnarly.engine.display.Window;
import com.gnarly.engine.utils.Library;
import com.gnarly.engine.utils.MapManager;
import com.gnarly.game.objects.Tile;
import com.gnarly.game.objects.UIButton;
import com.gnarly.game.objects.UIImage;

public class EditPanel {

	private final float SCALE = 64;
	private int width = 32, height = 32;
	private Camera camera;
	private Library library;
	private Tile[][] map;
	private UIImage hud;
	private UIImage plate;
	private UIButton button, button1, button2, button3, button4, button5, button6, button7, arrowButton;
	private Window window;
	MapManager mapManager = new MapManager();
	
	public EditPanel(Camera camera, Window window, Library library) {
		this.camera = camera;
		this.library = library;
		this.window = window;
		hud = new UIImage(camera, library.getTexture("HUD.png"), library.getShader("default"), 10, 10, SCALE / 16 * 50, SCALE / 16 * 25);
		plate = new UIImage(camera, library.getTexture("Plate.png"), library.getShader("default"), 350, 10, SCALE / 16 * 316, SCALE / 16 * 40);
		button = new UIButton(camera, library.getTexture("ButtonUp.png"), library.getShader("default"), window, library.getTexture("ButtonUp.png"), library.getTexture("ButtonDown.png"), library.getTexture("ButtonUpHover.png"), 385, 25, SCALE / 16 * 32, SCALE / 16 * 32);
		button1 = new UIButton(camera, library.getTexture("ButtonUp.png"), library.getShader("default"), window, library.getTexture("ButtonUp.png"), library.getTexture("ButtonDown.png"), library.getTexture("ButtonUpHover.png"), 525, 25, SCALE / 16 * 32, SCALE / 16 * 32);
		button2 = new UIButton(camera, library.getTexture("ButtonUp.png"), library.getShader("default"), window, library.getTexture("ButtonUp.png"), library.getTexture("ButtonDown.png"), library.getTexture("ButtonUpHover.png"), 675, 25, SCALE / 16 * 32, SCALE / 16 * 32);
		button3 = new UIButton(camera, library.getTexture("ButtonUp.png"), library.getShader("default"), window, library.getTexture("ButtonUp.png"), library.getTexture("ButtonDown.png"), library.getTexture("ButtonUpHover.png"), 825, 25, SCALE / 16 * 32, SCALE / 16 * 32);
		button4 = new UIButton(camera, library.getTexture("ButtonUp.png"), library.getShader("default"), window, library.getTexture("ButtonUp.png"), library.getTexture("ButtonDown.png"), library.getTexture("ButtonUpHover.png"), 975, 25, SCALE / 16 * 32, SCALE / 16 * 32);
		button5 = new UIButton(camera, library.getTexture("ButtonUp.png"), library.getShader("default"), window, library.getTexture("ButtonUp.png"), library.getTexture("ButtonDown.png"), library.getTexture("ButtonUpHover.png"), 1125, 25, SCALE 	/ 16 * 32, SCALE / 16 * 32);
		button6 = new UIButton(camera, library.getTexture("ButtonUp.png"), library.getShader("default"), window, library.getTexture("ButtonUp.png"), library.getTexture("ButtonDown.png"), library.getTexture("ButtonUpHover.png"), 1275, 25, SCALE / 16 * 32, SCALE / 16 * 32);
		button7 = new UIButton(camera, library.getTexture("ButtonUp.png"), library.getShader("default"), window, library.getTexture("ButtonUp.png"), library.getTexture("ButtonDown.png"), library.getTexture("ButtonUpHover.png"), 1425, 25, SCALE / 16 * 32, SCALE / 16 * 32);
		arrowButton = new UIButton(camera, library.getTexture("ArrowButtonUp.png"), library.getShader("default"), window, library.getTexture("ArrowButtonUp.png"), library.getTexture("ArrowButtonDown.png"), library.getTexture("ArrowButtonUpHover.png"), 1563, 120, SCALE / 32 * 16, SCALE / 32 * 16);
	}
	
	//Updates all the elements of the panel nad the camera
	public void update() {
		//Updates Button
		button.update(library);
		button1.update(library);
		button2.update(library);
		button3.update(library);
		button4.update(library);
		button5.update(library);
		button6.update(library);
		button7.update(library);
		
		setTile();
		
		//Handles inputs and moves player with wasd
		float speed = 7, xChange = 0, yChange = 0;
		if(window.isKeyPressed(GLFW_KEY_W))
			yChange -= speed;
		if(window.isKeyPressed(GLFW_KEY_A))
			xChange -= speed;
		if(window.isKeyPressed(GLFW_KEY_S))
			yChange += speed;
		if(window.isKeyPressed(GLFW_KEY_D))
			xChange += speed;
		camera.translate(xChange, yChange, 0);
		
		if (window.isKeyPressed(GLFW_KEY_J))
			mapManager.saveMap(map, "map");
		if (window.isKeyPressed(GLFW_KEY_K))
			this.map = mapManager.loadMap("map", camera, library, (int) SCALE);
		
		arrowButton.update(library);
		//Checks that the camera is notgoing past the edge of the map and adjusting the camera position if neccessary
		if(camera.getX() < 0)
			camera.setPosition(0, camera.getY(), 0);
		else if(camera.getX() > width * SCALE - camera.getWidth())
			camera.setPosition(width * SCALE - camera.getWidth(), camera.getY(), 0);
		if(camera.getY() < 0)
			camera.setPosition(camera.getX(), 0, 0);
		else if(camera.getY() > height * SCALE - camera.getHeight())
			camera.setPosition(camera.getX(), height * SCALE - camera.getHeight(), 0);
	}
	
	//Render all the panel components
	public void render() {
		//Determines the area of the map that needs to be rendered
		int minX = (int) (camera.getX() / SCALE);
		int maxX = (int) ((camera.getX() + camera.getWidth()) / SCALE);
		int minY = (int) (camera.getY() / SCALE);
		int maxY = (int) ((camera.getY() + camera.getHeight()) / SCALE);
		//Renders teh map
		for (int i = Math.max(minX, 0); i <= maxX && i < map.length; i++) {
			for (int j = Math.max(minY, 0); j <= maxY && j < map[0].length; j++) {
				map[i][j].render();
			}
		}
		//Renders and the HUD
		hud.render();
		button.render();
		button1.render();
		button2.render();
		button3.render();
		button4.render();
		button5.render();
		button6.render();
		button7.render();
		arrowButton.render();
		plate.render();
	}

	public void setTile() {
		int x = (int)((window.getMouseCoords().x + camera.getX()) / SCALE);
		int y = (int)((window.getMouseCoords().y + camera.getY()) / SCALE);
		
		if (window.isMousePressed(0)) {
			map[x][y] = new Tile(camera, library.getTexture("Brick Wall.png"), library.getShader("default"), x * SCALE, y * SCALE, SCALE, SCALE, true);
		} else if (window.isMousePressed(1)) {
			map[x][y] = new Tile(camera, library.getTexture("Stone Floor.png"), library.getShader("default"), x * SCALE, y * SCALE, SCALE, SCALE, false);
		}
	}
	
	//Loads a map based on an array of ints
	//0 = Stone Floor
	//1 = Brick Wall
	//2 = Circular Wall
	public void setMap() {
		this.map = mapManager.loadMap("map", camera, library, (int) SCALE);
	}
}
