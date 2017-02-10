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
import com.gnarly.game.objects.Selected;
import com.gnarly.game.objects.SelectorButton;
import com.gnarly.game.objects.Tile;
import com.gnarly.game.objects.UIButton;
import com.gnarly.game.objects.UIImage;

public class EditPanel {

	private final float SCALE = 64;
	private int width = 32, height = 32;
	private Camera camera;
	private Library library;
	private Tile[][] map;
	private UIImage plate;
	private UIImage brickSelect;
	private UIImage circleBrickSelect;
	private UIImage floorSelect;
	private SelectorButton button, button1;
	private UIButton button2, button3, button4, button5, button6, button7, arrowButton;
	private Window window;
	MapManager mapManager = new MapManager();
	Selected selected = new Selected();
	
	public EditPanel(Camera camera, Window window, Library library) {
		this.camera = camera;
		this.library = library;
		this.window = window;
		plate = new UIImage(camera, library.getTexture("Plate.png"), library.getShader("default"), 350, 10, SCALE / 16 * 316, SCALE / 16 * 40);
		brickSelect = new UIImage(camera, library.getTexture("Brick Wall.png"), library.getShader("default"), 418, 58, SCALE / 16 * 16, SCALE / 16 * 16);
		circleBrickSelect = new UIImage(camera, library.getTexture("Circular Wall.png"), library.getShader("default"), 558, 58, SCALE / 16 * 16, SCALE / 16 * 16);
		floorSelect = new UIImage(camera, library.getTexture("Stone Floor.png"), library.getShader("default"), 708, 58, SCALE / 16 * 16, SCALE / 16 * 16);
		button = new SelectorButton(camera, library.getTexture("ButtonUp.png"), library.getShader("default"), window, library.getTexture("ButtonUp.png"), library.getTexture("ButtonDown.png"), library.getTexture("ButtonUpHover.png"), 385, 25, SCALE / 16 * 32, SCALE / 16 * 32, 0, selected);
		button1 = new SelectorButton(camera, library.getTexture("ButtonUp.png"), library.getShader("default"), window, library.getTexture("ButtonUp.png"), library.getTexture("ButtonDown.png"), library.getTexture("ButtonUpHover.png"), 525, 25, SCALE / 16 * 32, SCALE / 16 * 32, 1, selected);
		button2 = new UIButton(camera, library.getTexture("ButtonUp.png"), library.getShader("default"), window, library.getTexture("ButtonUp.png"), library.getTexture("ButtonDown.png"), library.getTexture("ButtonUpHover.png"), 675, 25, SCALE / 16 * 32, SCALE / 16 * 32, 1);
		button3 = new UIButton(camera, library.getTexture("ButtonUp.png"), library.getShader("default"), window, library.getTexture("ButtonUp.png"), library.getTexture("ButtonDown.png"), library.getTexture("ButtonUpHover.png"), 825, 25, SCALE / 16 * 32, SCALE / 16 * 32, 1);
		button4 = new UIButton(camera, library.getTexture("ButtonUp.png"), library.getShader("default"), window, library.getTexture("ButtonUp.png"), library.getTexture("ButtonDown.png"), library.getTexture("ButtonUpHover.png"), 975, 25, SCALE / 16 * 32, SCALE / 16 * 32, 1);
		button5 = new UIButton(camera, library.getTexture("ButtonUp.png"), library.getShader("default"), window, library.getTexture("ButtonUp.png"), library.getTexture("ButtonDown.png"), library.getTexture("ButtonUpHover.png"), 1125, 25, SCALE 	/ 16 * 32, SCALE / 16 * 32, 1);
		button6 = new UIButton(camera, library.getTexture("ButtonUp.png"), library.getShader("default"), window, library.getTexture("ButtonUp.png"), library.getTexture("ButtonDown.png"), library.getTexture("ButtonUpHover.png"), 1275, 25, SCALE / 16 * 32, SCALE / 16 * 32, 1);
		button7 = new UIButton(camera, library.getTexture("ButtonUp.png"), library.getShader("default"), window, library.getTexture("ButtonUp.png"), library.getTexture("ButtonDown.png"), library.getTexture("ButtonUpHover.png"), 1425, 25, SCALE / 16 * 32, SCALE / 16 * 32, 1);
		arrowButton = new UIButton(camera, library.getTexture("ArrowButtonUp.png"), library.getShader("default"), window, library.getTexture("ArrowButtonUp.png"), library.getTexture("ArrowButtonDown.png"), library.getTexture("ArrowButtonUpHover.png"), 1563, 120, SCALE / 32 * 16, SCALE / 32 * 16, 0);
	}
	
	//Updates all the elements of the panel nad the camera
	public void update() {
		//Updates Button
		button.update();
		button1.update();
		button2.update();
		button3.update();
		button4.update();
		button5.update();
		button6.update();
		button7.update();
		
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
		
		arrowButton.update();
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
		brickSelect.render();
		circleBrickSelect.render();
		floorSelect.render();
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
		
		if(!(plate.within((int) window.getMouseCoords().x, (int) window.getMouseCoords().y))) {
			if (window.isMousePressed(0)) {			
				switch (selected.getId()) {
				case 0:
					map[x][y] = new Tile(camera, library.getTexture("Brick Wall.png"), library.getShader("default"), x * SCALE, y * SCALE, SCALE, SCALE, true);
					break;
				case 1:
					map[x][y] = new Tile(camera, library.getTexture("Circular Wall.png"), library.getShader("default"), x * SCALE, y * SCALE, SCALE, SCALE, true);
					break;
				case 2:
					map[x][y] = new Tile(camera, library.getTexture("Stone Floor.png"), library.getShader("default"), x * SCALE, y * SCALE, SCALE, SCALE, false);
					break;
				}		
			} else if (window.isMousePressed(1)) {
				map[x][y] = new Tile(camera, library.getTexture("Stone Floor.png"), library.getShader("default"), x * SCALE, y * SCALE, SCALE, SCALE, false);
			}
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
