package main.java.ulibs.engine;

import java.awt.Color;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallbackI;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowPosCallbackI;
import org.lwjgl.glfw.GLFWWindowSizeCallbackI;
import org.lwjgl.system.MemoryUtil;

import main.java.ulibs.common.utils.Console;
import main.java.ulibs.common.utils.Console.WarningType;
import main.java.ulibs.engine.init.Shaders;
import main.java.ulibs.engine.input.InputCursor;
import main.java.ulibs.engine.input.InputHolder;
import main.java.ulibs.engine.render.ScreenLoading;
import main.java.ulibs.engine.utils.EnumScreenTearFix;
import main.java.ulibs.gl.gl.GLH;
import main.java.ulibs.gl.utils.exceptions.GLException;
import main.java.ulibs.gl.utils.exceptions.GLException.Reason;

public abstract class ClientBase extends CommonBase {
	private static int hudWidth, hudHeight;
	
	private static InputHolder inputHolder;
	private static GLFWCursorPosCallbackI cursorInput;
	
	private static int aspectWidth, aspectHeight, aspectX, aspectY;
	private static float xScale = 1, yScale = 1;
	private static int lastWidth, lastHeight, windowX, windowY, lastWindowX, lastWindowY;
	
	private static long window;
	private static boolean isFullscreen;
	
	private ScreenLoading loadingScreen;
	
	private static EnumScreenTearFix screenFix = EnumScreenTearFix.vsync;
	
	//TODO make this take in a input holder class with a builder
	
	protected ClientBase(String title, String internalTitle, int hudWidth, int hudHeight, InputHolder inputHolder, boolean isDebug, WarningType[] warnings) {
		super(title, internalTitle, isDebug, warnings);
		
		ClientBase.hudWidth = hudWidth;
		ClientBase.hudHeight = hudHeight;
		ClientBase.inputHolder = inputHolder;
		ClientBase.cursorInput = new InputCursor();
		
		ClientBase.aspectWidth = hudWidth;
		ClientBase.aspectHeight = hudHeight;
		
		Thread.currentThread().setName("Client");
	}
	
	@Override
	protected void preRun() {
		glSetup();
		loadingScreen = new ScreenLoading();
		loadingScreen.setupGL();
	}
	
	@Override
	protected void renderWrap() {
		int err = GLH.getError();
		if (GLH.isError(err)) {
			Console.print(WarningType.Error, "OpenGL Error: " + err);
		}
		
		GLH.clearColorDepthBuffer();
		
		if (!isLoading()) {
			render();
		} else {
			loadingRender();
		}
		
		GLFW.glfwSwapBuffers(window);
	}
	
	protected void loadingRender() {
		loadingScreen.renderAll();
	}
	
	private void glSetup() {
		Console.print(WarningType.Info, "OpenGL setup started...");
		
		if (!GLFW.glfwInit()) {
			Console.print(WarningType.FatalError, "Failed to initialize OpenGL!", new GLException(Reason.failedToInitGL));
			return;
		}
		
		GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLH.TRUE);
		GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
		GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 2);
		GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, GLH.TRUE);
		GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);
		
		GLFWVidMode v = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
		GLFW.glfwWindowHint(GLFW.GLFW_RED_BITS, v.redBits());
		GLFW.glfwWindowHint(GLFW.GLFW_GREEN_BITS, v.greenBits());
		GLFW.glfwWindowHint(GLFW.GLFW_BLUE_BITS, v.blueBits());
		GLFW.glfwWindowHint(GLFW.GLFW_REFRESH_RATE, v.refreshRate());
		
		window = GLFW.glfwCreateWindow(getHudWidth(), getHudHeight(), title, MemoryUtil.NULL, MemoryUtil.NULL);
		if (window == MemoryUtil.NULL) {
			Console.print(WarningType.FatalError, "Failed to initialize Window!", new GLException(Reason.failedToInitWindow));
			return;
		}
		
		windowX = v.width() / 2 - getHudWidth() / 2;
		windowY = v.height() / 2 - getHudHeight() / 2;
		
		GLFW.glfwSetWindowPos(window, windowX, windowY);
		
		if (inputHolder.getKeyInput() != null) {
			GLFW.glfwSetKeyCallback(window, inputHolder.getKeyInput());
		}
		if (inputHolder.getCharInput() != null) {
			GLFW.glfwSetCharCallback(window, inputHolder.getCharInput());
		}
		if (inputHolder.getMouseInput() != null) {
			GLFW.glfwSetMouseButtonCallback(window, inputHolder.getMouseInput());
		}
		if (inputHolder.getScrollInput() != null) {
			GLFW.glfwSetScrollCallback(window, inputHolder.getScrollInput());
		}
		if (cursorInput != null) {
			GLFW.glfwSetCursorPosCallback(window, cursorInput);
		}
		
		GLFW.glfwSetWindowPosCallback(window, new GLFWWindowPosCallbackI() {
			@Override
			public void invoke(long window, int xpos, int ypos) {
				windowX = xpos;
				windowY = ypos;
			}
		});
		GLFW.glfwSetWindowSizeCallback(window, new GLFWWindowSizeCallbackI() {
			@Override
			public void invoke(long window, int width, int height) {
				aspectWidth = width;
				aspectHeight = (int) (aspectWidth / (16f / 9f));
				
				if (aspectHeight > height) {
					aspectHeight = height;
					aspectWidth = (int) (aspectHeight * (16f / 9f));
				}
				
				aspectX = (int) ((width / 2f) - (aspectWidth / 2f));
				aspectY = (int) ((height / 2f) - (aspectHeight / 2f));
				
				yScale = 1 + (float) (aspectHeight - getHudHeight()) / getHudHeight();
				xScale = 1 + (float) (aspectWidth - getHudWidth()) / getHudWidth();
				
				InputCursor.updateValues();
				GLH.setViewport(aspectX, aspectY, aspectWidth, aspectHeight);
			}
		});
		
		InputCursor.updateValues();
		GLFW.glfwMakeContextCurrent(window);
		GLFW.glfwShowWindow(window);
		
		GLH.createCapabilities();
		
		GLH.clearColor(new Color(20,20,20, 1));
		GLH.enableDepth();
		GLH.enableBlend();
		GLH.setActiveTexture0();
		
		Shaders.registerAll();
		
		Console.print(WarningType.Info, "OpenGL setup finished! Running OpenGL version: " + GLH.getVersion() + "!");
	}
	
	public static void toggleFullScreen() {
		Console.print(WarningType.Debug, "Toggling Fullscreen!");
		isFullscreen = !isFullscreen;
		refreshFullscreen();
	}
	
	public static void refreshFullscreen() {
		GLFWVidMode v = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
		if (isFullscreen) {
			lastWidth = (aspectX * 2) + aspectWidth;
			lastHeight = (aspectY * 2) + aspectHeight;
			lastWindowX = windowX;
			lastWindowY = windowY;
			
			GLFW.glfwSetWindowAttrib(window, GLFW.GLFW_DECORATED, GLFW.GLFW_FALSE);
			switch (screenFix) {
				case experimental:
					GLFW.glfwSwapInterval(0);
					GLFW.glfwSetWindowMonitor(window, MemoryUtil.NULL, 0, -1, v.width(), v.height() + 2, v.refreshRate());
					break;
				case off:
					GLFW.glfwSwapInterval(0);
					GLFW.glfwSetWindowMonitor(window, MemoryUtil.NULL, 0, 0, v.width(), v.height(), v.refreshRate());
					break;
				case vsync:
					GLFW.glfwSwapInterval(1);
					GLFW.glfwSetWindowMonitor(window, MemoryUtil.NULL, 0, 0, v.width(), v.height(), v.refreshRate());
					break;
			}
		} else {
			GLFW.glfwSwapInterval(0);
			GLFW.glfwSetWindowAttrib(window, GLFW.GLFW_DECORATED, GLFW.GLFW_TRUE);
			GLFW.glfwSetWindowMonitor(window, 0, lastWindowX, lastWindowY, lastWidth, lastHeight, v.refreshRate());
		}
	}
	
	@Override
	protected boolean shouldClose() {
		return GLFW.glfwWindowShouldClose(window);
	}
	
	public static int getHudWidth() {
		return hudWidth;
	}
	
	public static int getHudHeight() {
		return hudHeight;
	}
	
	public static int getViewportX() {
		return aspectX;
	}
	
	public static int getViewportY() {
		return aspectY;
	}
	
	public static int getViewportWidth() {
		return aspectWidth;
	}
	
	public static int getViewportHeight() {
		return aspectHeight;
	}
	
	public static float getXScale() {
		return xScale;
	}
	
	public static float getYScale() {
		return yScale;
	}
}
