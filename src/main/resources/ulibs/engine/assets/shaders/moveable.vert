#version 460 core

layout (location = 0) in vec4 pos;
layout (location = 1) in vec2 tc;

uniform mat4 pr_matrix;
uniform mat4 vw_matrix = mat4(1);
uniform mat4 ml_matrix = mat4(1);

out vec2 f_tc;

void main() {
	gl_Position = pr_matrix * vw_matrix * ml_matrix * pos;
	f_tc = tc;
}