package main.java.ulibs.engine.input;

import org.lwjgl.glfw.GLFWCursorPosCallback;

import main.java.ulibs.common.math.Vec2i;
import main.java.ulibs.engine.ClientBase;

public class InputCursor extends GLFWCursorPosCallback {
	public static final Vec2i HUD_MOUSE_POS = new Vec2i();
	
	private static float viewX, viewY, viewW, viewH, hudW, hudH;
	
	@Override
	public void invoke(long window, double xpos, double ypos) {
		if (ClientBase.isLoading()) {
			return;
		}
		
		float mousePosX = (((float) xpos - viewX) / (viewW / hudW));
		float mousePosY = (((float) ypos - viewY) / (viewH / hudH));
		
		HUD_MOUSE_POS.set((int) mousePosX, (int) mousePosY);
	}
	
	public static void updateValues() {
		viewX = ClientBase.getViewportX();
		viewY = ClientBase.getViewportY();
		viewW = ClientBase.getViewportWidth();
		viewH = ClientBase.getViewportHeight();
		hudW = ClientBase.getHudWidth();
		hudH = ClientBase.getHudHeight();
	}
}
