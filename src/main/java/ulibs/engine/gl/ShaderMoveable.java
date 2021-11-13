package main.java.ulibs.engine.gl;

import main.java.ulibs.gl.gl.Shader;
import main.java.ulibs.gl.math.Matrix4f;

public class ShaderMoveable extends Shader {
	public ShaderMoveable(Matrix4f prMatrix) {
		super("moveable", "ulibs/engine", prMatrix);
	}
	
	@Override
	protected void internalSetup() {
		
	}
	
	public void setViewMatrix(Matrix4f matrix) {
		set("view_matrix", matrix);
	}
	
	public void setTransformMatrix(Matrix4f matrix) {
		set("transform_matrix", matrix);
	}
}
