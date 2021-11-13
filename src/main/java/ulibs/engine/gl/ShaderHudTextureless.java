package main.java.ulibs.engine.gl;

import java.awt.Color;

import main.java.ulibs.common.helpers.ColorH;
import main.java.ulibs.gl.gl.Shader;
import main.java.ulibs.gl.math.Matrix4f;

public class ShaderHudTextureless extends Shader {
	public ShaderHudTextureless(Matrix4f prMatrix) {
		super("hud_textureless", "ulibs/engine", prMatrix);
	}
	
	@Override
	protected void internalSetup() {
		setColor(Color.WHITE);
	}
	
	public void setViewMatrix(Matrix4f matrix) {
		set("view_matrix", matrix);
	}
	
	public void setTransformMatrix(Matrix4f matrix) {
		set("transform_matrix", matrix);
	}
	
	public void setColor(Color color) {
		set("color_vec4", ColorH.colorToVec4(color));
	}
}
