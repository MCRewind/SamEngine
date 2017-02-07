#version 450

uniform sampler2D sampler;

in vec2 textureCoords;

void main() {
	gl_FragColor = texture2D(sampler, textureCoords);
}