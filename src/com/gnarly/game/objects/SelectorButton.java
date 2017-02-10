package com.gnarly.game.objects;

import com.gnarly.engine.components.Shader;
import com.gnarly.engine.components.Texture;
import com.gnarly.engine.display.Camera;
import com.gnarly.engine.display.Window;
import com.gnarly.engine.utils.Library;

public class SelectorButton extends UIButton {

	Selected selected;
	
	int id;
	
	public SelectorButton(Camera camera, Texture texture, Shader shader, Window window, Texture up, Texture down, Texture hover, float x, float y, float width, float height, int id, Selected selected) {
		super(camera, texture, shader, window, up, down, hover, x, y, width, height, id);
		this.selected = selected;
		this.id = id;
	}
	
	public void onPress(Library library) {
		super.onPress(library);
		selected.setId(id);
	}
	
	public void onRelease(Library library) {
		super.onRelease(library);	
	}
	
	public void onHover(Library library) {
		super.onHover(library);
	}
	
	public void onLeave(Library library) {
		super.onLeave(library);
	}

}
