package com.gnarly.engine.display;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera {
	
	private int width, height;
	private Vector3f position;
	private Matrix4f projection;
	
	public Camera(int width, int height) {
		this.width = width;
		this.height = height;
		position = new Vector3f(0,0,0);
		setProjection(width, height);
	}
	
	public void setProjection(int width, int height) {
		projection = new Matrix4f().setOrtho2D(0, width, height, 0);
	}
	
	public void setPosition(Vector3f position) {
		this.position.x = -position.x;
		this.position.y = -position.y;
		this.position.z = -position.z;
	}
	
	public void setPosition(float x, float y, float z) {
		position.x = -x;
		position.y = -y;
		position.z = -z;
	}

	public void translate(Vector3f position) {
		this.position.x -= position.x;
		this.position.y -= position.y;
		this.position.z -= position.z;
	}

	public void translate(float x, float y, float z) {
		position.x -= x;
		position.y -= y;
		position.z -= z;
	}
	
	public Matrix4f getUnatransformedProjection() {
		return projection;
	}
	
	public Matrix4f getProjection() {
		return projection.translate(position, new Matrix4f());
	}
	
	public float getX() {
		return -position.x;
	}
	
	public float getY() {
		return -position.y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
}
