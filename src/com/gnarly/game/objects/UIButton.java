package com.gnarly.game.objects;

import com.gnarly.engine.components.Shader;
import com.gnarly.engine.components.Texture;
import com.gnarly.engine.display.Camera;
import com.gnarly.engine.display.Window;
import com.gnarly.engine.utils.Library;

public class UIButton extends UIImage {

	private Window window;
	
	private Camera camera;
	
	private Texture up, down, hover;
	
	private int type;
	
	public UIButton(Camera camera, Texture texture, Shader shader, Window window, Texture up, Texture down, Texture hover, float x, float y, float width, float height, int type) {
		super(camera, texture, shader, x, y, width, height);
		this.window = window;
		this.camera = camera;
		this.up = up;
		this.down = down;
		this.hover = hover;
		this.type = type;
	}

	public void update() {
		if ((window.getMouseCoords().x > this.getX() && window.getMouseCoords().x < getX()+getWidth()) && (window.getMouseCoords().y > this.getY() && window.getMouseCoords().y < getY()+getHeight())) {
			onHover();
			if (window.isMousePressed(0)) {
				onPress();
			}
		} else {
			onLeave();
			if (window.isMousePressed(0)) {
				onRelease();
			}
		}
	}
	
	public void onPress() {
		setTexture(down);
	}
	
	public void onRelease() {
		setTexture(up);
	}
	
	public void onHover() {
		setTexture(hover);
	}
	
	public void onLeave() {
		setTexture(up);
	}
	
}
