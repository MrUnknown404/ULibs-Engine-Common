#version 460 core

layout (location = 0) in vec4 pos;

uniform mat4 pr_matrix;
uniform mat4 vw_matrix = mat4(1);
uniform mat4 ml_matrix = mat4(1);
uniform vec4 cl;

out vec4 f_cl;

void main() {
	gl_Position = pr_matrix * vw_matrix * ml_matrix * pos;
	f_cl = cl;
}