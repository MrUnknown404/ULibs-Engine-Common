#version 460 core

layout (location = 0) out vec4 color;

in vec4 f_cl;

uniform sampler2D tex;

void main() {
	color = f_cl;
	if (color.w == 0) {
		discard;
	}
}