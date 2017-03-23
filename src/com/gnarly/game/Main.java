package com.gnarly.game;

import com.gnarly.engine.display.Camera;
import com.gnarly.engine.display.Window;
import com.gnarly.engine.utils.Library;
import com.gnarly.game.panels.EditPanel;
import com.gnarly.game.panels.GenericPanel;
import com.gnarly.game.panels.MainMenu;
import com.gnarly.game.panels.PlayPanel;

public class Main implements Runnable {
 
	private static Window window;
	private static Library library;
	private static Camera camera;
	private static GenericPanel panel;
	private Thread gameLoop;
	private int[][] map = new int[32][32];
	
	static int noInput = 15;
	
	public Main() {
		gameLoop = new Thread(this);
		gameLoop.start();
	}
	
	public void run() {
		init();
		while(!window.shouldClose()) {
			if(noInput == 15) {
				update();
			} else {
				noInput++;
			}
			render();
		}
		window.cleanup();
	}
	
	private void init() {
		camera = new Camera(1920, 1080);
		window = new Window(camera, "Clatab Bunky Bab Mazgam", 1, true, false, true);
		library = new Library("res/img", "res/shader");
		panel = new MainMenu(camera, window, library);
	}
	
	public static void menuInit() {
		noInput = 0;
		panel = new MainMenu(camera, window, library);
	}
	
	public static void playInit() {
		panel = new PlayPanel(camera, window, library);
		panel.setMap();
	}
	
	public static void editInit() {
		panel = new EditPanel(camera, window, library);
		panel.setMap();
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
