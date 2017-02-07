package com.gnarly.engine.components;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

public class VAO {
	
	private int count, vbo, ibo, tcbo;
	
	public VAO(float[] vertices, int[] indices, int[] texCoords) {
		count = indices.length;
		init(vertices, indices, texCoords);
	}
	
	private void init(float[] vertices, int[] indices, int[] texCoords) {
		vbo = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, createFloatBuffer(vertices), GL_STATIC_DRAW);

		ibo = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, createIntBuffer(indices), GL_STATIC_DRAW);
		
		tcbo = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, tcbo);
		glBufferData(GL_ARRAY_BUFFER, createIntBuffer(texCoords), GL_STATIC_DRAW);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}
	
	public void render() {
		glEnableVertexAttribArray(Shader.VERT_ATTRIB);
		glEnableVertexAttribArray(Shader.TEX_COORD_ATTRIB);

		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glVertexAttribPointer(Shader.VERT_ATTRIB, 3, GL_FLOAT, false, 0, 0);

		glBindBuffer(GL_ARRAY_BUFFER, tcbo);
		glVertexAttribPointer(Shader.TEX_COORD_ATTRIB, 2, GL_INT, false, 0, 0);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
		glDrawElements(GL_TRIANGLES, count, GL_UNSIGNED_INT, 0);

		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		glDisableVertexAttribArray(Shader.VERT_ATTRIB);
		glDisableVertexAttribArray(Shader.TEX_COORD_ATTRIB);
	}
	
	public FloatBuffer createFloatBuffer(float[] data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	
	public IntBuffer createIntBuffer(int[] data) {
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
}