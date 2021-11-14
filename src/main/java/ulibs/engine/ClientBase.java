package main.java.ulibs.engine;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallbackI;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowPosCallbackI;
import org.lwjgl.glfw.GLFWWindowSizeCallbackI;
import org.lwjgl.system.MemoryUtil;

import main.java.ulibs.common.math.Vec2i;
import main.java.ulibs.common.utils.Console;
import main.java.ulibs.common.utils.Console.WarningType;
import main.java.ulibs.engine.init.Shaders;
import main.java.ulibs.engine.input.InputCursor;
import main.java.ulibs.engine.input.InputHolder;
import main.java.ulibs.engine.render.IRenderer;
import main.java.ulibs.engine.render.ScreenLoading;
import main.java.ulibs.engine.utils.EnumScreenTearFix;
import main.java.ulibs.engine.utils.ITickable;
import main.java.ulibs.gl.gl.GLH;
import main.java.ulibs.gl.gl.Shader;
import main.java.ulibs.gl.utils.exceptions.GLException;

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
	
	private final List<IRenderer> renderers = new ArrayList<IRenderer>();
	private final List<Shader> shadersToSetup;
	
	//TODO make this take in a input holder class with a builder
	
	protected ClientBase(String title, String internalTitle, int hudWidth, int hudHeight, InputHolder inputHolder, boolean isDebug, int logCount, WarningType[] warnings,
			List<Shader> shadersToSetup) {
		super(title, internalTitle, isDebug, logCount, warnings);
		this.shadersToSetup = shadersToSetup;
		
		ClientBase.hudWidth = hudWidth;
		ClientBase.hudHeight = hudHeight;
		ClientBase.inputHolder = inputHolder;
		ClientBase.cursorInput = new InputCursor();
		
		ClientBase.aspectWidth = hudWidth;
		ClientBase.aspectHeight = hudHeight;
		
		Thread.currentThread().setName("Client");
	}
	
	@Override
	protected final void preRun() {
		glSetup();
		loadingScreen = new ScreenLoading();
		loadingScreen.setupGL();
	}
	
	@Override
	protected final void renderWrap() {
		int err = GLH.getError();
		if (GLH.isError(err)) {
			Console.print(WarningType.Error, "OpenGL Error: " + err);
		}
		
		GLH.clearColorDepthBuffer();
		
		if (!isLoading()) {
			render();
		} else {
			renderLoadingScreen();
		}
		
		GLH.swapBuffers(window);
	}
	
	@Override
	protected final void rendererSetup() {
		for (IRenderer r : renderers) {
			r.setupGL();
		}
	}
	
	@Override
	protected final void preTick() {
		tick();
		
		for (IRenderer r : renderers) {
			if (r instanceof ITickable) {
				((ITickable) r).tick();
			}
		}
	}
	
	protected void render() {
		for (IRenderer r : renderers) {
			r.renderAll();
		}
	}
	
	/** Adds an {@link IRenderer} to a list allowing automatic handling
	 * @param r The renderer to add
	 */
	protected final void addRenderer(IRenderer r) {
		renderers.add(r);
		Console.print(WarningType.RegisterDebug, "Registered '" + r.getClass().getSimpleName() + "' as a renderer!");
	}
	
	/** @return The list of {@link IRenderer}s */
	protected final List<IRenderer> getRenderers() {
		return renderers;
	}
	
	protected void renderLoadingScreen() {
		loadingScreen.renderAll();
	}
	
	private final void glSetup() {
		Console.print(WarningType.Info, "OpenGL setup started...");
		
		try {
			GLH.initGL();
		} catch (GLException e) {
			return;
		}
		
		Vec2i monSize = GLH.setWindowHints(true);
		
		try {
			window = GLH.createWindow(title, getHudWidth(), getHudHeight());
		} catch (GLException e) {
			return;
		}
		
		windowX = monSize.getX() / 2 - getHudWidth() / 2;
		windowY = monSize.getY() / 2 - getHudHeight() / 2;
		
		GLH.setWindowPos(window, windowX, windowY);
		
		if (inputHolder != null) {
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
		
		GLH.clearColor(new Color(20, 20, 20, 1));
		GLH.enableDepth();
		GLH.enableBlend();
		GLH.setActiveTexture0();
		
		Shaders.registerAll();
		for (Shader s : shadersToSetup) {
			s.setup();
		}
		
		Console.print(WarningType.Info, "OpenGL setup finished! Running OpenGL version: " + GLH.getVersion() + "!");
	}
	
	public static final void toggleFullScreen() {
		Console.print(WarningType.Debug, "Toggling Fullscreen!");
		isFullscreen = !isFullscreen;
		refreshFullscreen();
	}
	
	public static final void refreshFullscreen() {
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
	protected final boolean shouldClose() {
		return GLH.shouldWindowClose(window);
	}
	
	public static final int getHudWidth() {
		return hudWidth;
	}
	
	public static final int getHudHeight() {
		return hudHeight;
	}
	
	public static final int getViewportX() {
		return aspectX;
	}
	
	public static final int getViewportY() {
		return aspectY;
	}
	
	public static final int getViewportWidth() {
		return aspectWidth;
	}
	
	public static final int getViewportHeight() {
		return aspectHeight;
	}
	
	public static final float getXScale() {
		return xScale;
	}
	
	public static final float getYScale() {
		return yScale;
	}
}
