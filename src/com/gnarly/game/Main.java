package com.gnarly.game;

import com.gnarly.engine.display.Camera;
import com.gnarly.engine.display.Window;
import com.gnarly.engine.utils.Hitbox;
import com.gnarly.engine.utils.Library;
import com.gnarly.game.panels.PlayPanel;

public class Main implements Runnable {

	private Window window;
	private Library library;
	private Camera camera;
	private PlayPanel panel;
	private Thread gameLoop;
	private int[][] map = new int[32][32];
	
	public Main() {
		gameLoop = new Thread(this);
		gameLoop.start();
	}
	
	public void run() {
		init();
		while(!window.shouldClose()) {
			update();
			render();
		}
		window.cleanup();
	}
	
	private void init() {
		camera = new Camera(960, 640);
		window = new Window(camera, "Clatab Bunky Bab Mazgam", 1, true, false, false);
		library = new Library("res/img", "res/shader");
		panel = new PlayPanel(camera, window, library);
		//Creates the map and loads it into panel
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				if(i == 0 || j == 0 || i == map.length - 1 || j == map.length - 1)
					map[i][j] = 1;
				else if(i == j || i - 2 == j + 1)
					map[i][j] = 2;
				else
					map[i][j] = 0;
			}
		}
		panel.setMap(map);
	}
	
	private void update() {
		window.update();
		panel.update();
	}
	
	private void render() {
		window.clear();
		panel.render();
		window.swap();
	} 
	
	public static void main(String[] args) {
		new Main();
	}
}
