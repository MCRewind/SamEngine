#version 450

uniform mat4 projection;

in vec3 vertices;
in vec2 texCoords;

out vec2 textureCoords;

void main() {
	textureCoords = texCoords;
	gl_Position = projection * vec4(vertices, 1);
}