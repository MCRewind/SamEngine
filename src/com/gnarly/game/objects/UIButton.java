package com.gnarly.game.objects;

import com.gnarly.engine.components.Shader;
import com.gnarly.engine.components.Texture;
import com.gnarly.engine.display.Camera;
import com.gnarly.engine.display.Window;
import com.gnarly.engine.utils.Library;

public class UIButton extends UIImage {

	private Window window;
	
	private Texture up, down, hover;
	
	public UIButton(Camera camera, Texture texture, Shader shader, Window window, Texture up, Texture down, Texture hover, float x, float y, float width, float height) {
		super(camera, texture, shader, x, y, width, height);
		this.window = window;
		this.up = up;
		this.down = down;
		this.hover = hover;
	}

	public void update(Library library) {
		if ((window.getMouseCoords().x > this.getX() && window.getMouseCoords().x < getX()+getWidth()) && (window.getMouseCoords().y > this.getY() && window.getMouseCoords().y < getY()+getHeight())) {
			onHover(library);
			if (window.isMousePressed(0)) {
				onPress(library);
			}
		} else {
			onLeave(library);
			if (window.isMousePressed(0)) {
				onRelease(library);
			}
		}
	}
	
	public void onPress(Library library) {
		setTexture(down);
	}
	
	public void onRelease(Library library) {
		setTexture(up);
	}
	
	public void onHover(Library library) {
		setTexture(hover);
	}
	
	public void onLeave(Library library) {
		setTexture(up);
	}
	
}