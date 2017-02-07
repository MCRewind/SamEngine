package com.gnarly.engine.components;

import static org.lwjgl.opengl.GL20.*;

import org.lwjgl.BufferUtils;

import java.io.*;
import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Shader {

	private String name;
	private int program, vs, fs;
	public static int VERT_ATTRIB = 0, TEX_COORD_ATTRIB = 1;
	boolean enabled;
	Map<String, Integer> uniforms;
	
	public Shader(String vertPath, String fragPath) {
		name = new File(new File(vertPath).getParent()).getName();
		uniforms = new HashMap<>();
		String vert = load(vertPath);
		String frag = load(fragPath);
		create(vert, frag);
	}
	
	private String load(String path) {
		StringBuilder file = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File(path)));
			String line;
			while((line = reader.readLine()) != null)
				file.append(line + '\n');
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file.toString();
	}
	
	public void create(String vert, String frag) {
		program = glCreateProgram();
		
		vs = glCreateShader(GL_VERTEX_SHADER);
		glShaderSource(vs, vert);
		glCompileShader(vs);
		if(glGetShaderi(vs, GL_COMPILE_STATUS) != 1)
			throw new RuntimeException("Failed to compile shader! " + glGetShaderInfoLog(vs));

		fs = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(fs, frag);
		glCompileShader(fs);
		if(glGetShaderi(fs, GL_COMPILE_STATUS) != 1)
			throw new RuntimeException("Failed to compile shader! " + glGetShaderInfoLog(fs));
		
		glAttachShader(program, vs);
		glAttachShader(program, fs);

		glBindAttribLocation(program, VERT_ATTRIB, "vertices");
		glBindAttribLocation(program, TEX_COORD_ATTRIB, "texCoords");
		
		glLinkProgram(program);
		glValidateProgram(program);
	}
	
	public int getLocation(String name) {
		if(uniforms.containsKey(name))
			return uniforms.get(name);
		int location = glGetUniformLocation(program, name);
		uniforms.put(name, location);
		if(location != -1)
			return location;
		else
			throw new RuntimeException("Could not find uniform: " + name);
	}
	
	public void setUniform1i(String name, int value) {
		enable();
		glUniform1i(getLocation(name), value);
		disable();
	}
	
	public void setUniform1f(String name, float value) {
		enable();
		glUniform1f(getLocation(name), value);
		disable();
	}

	public void setUniform2f(String name, float x, float y) {
		enable();
		glUniform2f(getLocation(name), x, y);
		disable();
	}

	public void setUniform3f(String name, Vector3f vector) {
		enable();
		glUniform3f(getLocation(name), vector.x, vector.y, vector.z);
		disable();
	}
	
	public void setUniformMat4f(String name, Matrix4f matrix) {
		enable();
		FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
		matrix.get(buffer);
		glUniformMatrix4fv(getLocation(name), false, buffer);
		disable();
	}
	
	public String getName() {
		return name;
	}
	
	public void enable() {
		enabled = true;
		glUseProgram(program);
	}
	
	public void disable() {
		enabled = false;
		glUseProgram(0);
	}
}
