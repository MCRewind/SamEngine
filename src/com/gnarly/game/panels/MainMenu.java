package com.gnarly.game.panels;

import com.gnarly.engine.display.Camera;
import com.gnarly.engine.display.Window;
import com.gnarly.engine.utils.Library;
import com.gnarly.game.objects.SelectorButton;
import com.gnarly.game.objects.UIButton;
import com.gnarly.game.objects.UIImage;

public class MainMenu {
	
	private final float SCALE = 64;
	private int width = 32, height = 32;
	Camera camera;
	Library library;
	Window window;
	UIImage background;
	UIButton edit;
	UIButton play;
	public MainMenu(Camera camera, Window window, Library library) {
		this.camera = camera;
		this.library = library;
		this.window = window;
		background = new UIImage(camera, library.getTexture("background.png"), library.getShader("default"), 0, 0, camera.getWidth(), camera.getHeight());
		edit = new UIButton(camera, library.getTexture("ButtonUp.png"), library.getShader("default"), window, library.getTexture("ButtonUp.png"), library.getTexture("ButtonDown.png"), library.getTexture("ButtonUpHover.png"), camera.getWidth()/2, camera.getHeight()/2, SCALE / 16 * 32, SCALE / 16 * 32, 1);
		play = new UIButton(camera, library.getTexture("ButtonUp.png"), library.getShader("default"), window, library.getTexture("ButtonUp.png"), library.getTexture("ButtonDown.png"), library.getTexture("ButtonUpHover.png"), camera.getWidth()/2, camera.getHeight()/2, SCALE / 16 * 32, SCALE / 16 * 32, 1);
	}
	
	public void update() {
		edit.update();
		play.update();
	}
	
	public void render() {
		edit.render();
		play.render();
		background.render();
	}
	
}
