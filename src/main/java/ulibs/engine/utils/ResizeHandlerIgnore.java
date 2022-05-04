package main.java.ulibs.engine.utils;

import main.java.ulibs.common.math.Vec2i;
import main.java.ulibs.engine.ClientBase;
import main.java.ulibs.gl.gl.GLH;

public class ResizeHandlerIgnore extends ResizeHandler {
	@Override
	public void onResize(int width, int height) {
		GLH.setViewport(0, height - ClientBase.getDefaultHeight(), ClientBase.getDefaultWidth(), ClientBase.getDefaultHeight());
	}
	
	@Override
	public Vec2i setMousePos(int x, int y) {
		return new Vec2i(x, y);
	}
	
	@Override
	public boolean usesViewportPos() {
		return false;
	}
}
