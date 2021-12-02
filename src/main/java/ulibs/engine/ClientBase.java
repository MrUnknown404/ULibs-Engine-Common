package main.java.ulibs.engine;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallbackI;
import org.lwjgl.glfw.GLFWKeyCallbackI;
import org.lwjgl.glfw.GLFWMouseButtonCallbackI;
import org.lwjgl.glfw.GLFWScrollCallbackI;
import org.lwjgl.glfw.GLFWWindowPosCallbackI;
import org.lwjgl.glfw.GLFWWindowSizeCallbackI;

import main.java.ulibs.common.math.Vec2i;
import main.java.ulibs.common.utils.Console;
import main.java.ulibs.common.utils.Console.WarningType;
import main.java.ulibs.engine.init.Shaders;
import main.java.ulibs.engine.input.EnumKeyInput;
import main.java.ulibs.engine.input.EnumMouseInput;
import main.java.ulibs.engine.input.IInputHandler;
import main.java.ulibs.engine.input.IScrollHandler;
import main.java.ulibs.engine.input.Inputs;
import main.java.ulibs.engine.render.IRenderer;
import main.java.ulibs.engine.render.ScreenLoading;
import main.java.ulibs.engine.utils.EnumScreenTearFix;
import main.java.ulibs.engine.utils.ITickable;
import main.java.ulibs.gl.gl.GLH;
import main.java.ulibs.gl.gl.Shader;
import main.java.ulibs.gl.utils.exceptions.GLException;

public abstract class ClientBase extends CommonBase {
	private static int hudWidth, hudHeight;
	
	/** A {@link Vec2i} for the current mouse position */
	public static final Vec2i MOUSE_POS = new Vec2i();
	
	private final IInputHandler<EnumKeyInput> keyHandler;
	private final IInputHandler<EnumMouseInput> mouseHandler;
	private final IScrollHandler scrollHandler;
	
	private static int aspectWidth, aspectHeight, aspectX, aspectY;
	private static int lastWidth, lastHeight, windowX, windowY, lastWindowX, lastWindowY;
	
	private static long window;
	private static boolean isFullscreen;
	
	private ScreenLoading loadingScreen;
	
	private static EnumScreenTearFix screenFix = EnumScreenTearFix.vsync;
	
	private final List<IRenderer> renderers = new ArrayList<IRenderer>();
	private final Supplier<List<Shader>> shadersToSetup;
	
	protected ClientBase(String title, String internalTitle, int hudWidth, int hudHeight, boolean isDebug, int logCount, WarningType[] warnings,
			Supplier<List<Shader>> shadersToSetup) {
		super(title, internalTitle, isDebug, logCount, warnings);
		this.shadersToSetup = shadersToSetup;
		
		ClientBase.hudWidth = hudWidth;
		ClientBase.hudHeight = hudHeight;
		ClientBase.aspectWidth = hudWidth;
		ClientBase.aspectHeight = hudHeight;
		
		this.keyHandler = setKeyHandler();
		this.mouseHandler = setMouseHandler();
		this.scrollHandler = setScrollHandler();
		
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
		
		if (getKeyHandler() != null) {
			GLFW.glfwSetKeyCallback(window, new GLFWKeyCallbackI() {
				@Override
				public void invoke(long window, int key, int scancode, int action, int mods) {
					switch (action) {
						case GLFW.GLFW_PRESS:
							getKeyHandler().onPress(Inputs.getKeyFromInt(key));
							break;
						case GLFW.GLFW_RELEASE:
							getKeyHandler().onRelease(Inputs.getKeyFromInt(key));
							break;
						case GLFW.GLFW_REPEAT:
							getKeyHandler().onRepeat(Inputs.getKeyFromInt(key));
							break;
					}
				}
			});
		}
		
		if (getMouseHandler() != null) {
			GLFW.glfwSetMouseButtonCallback(window, new GLFWMouseButtonCallbackI() {
				@Override
				public void invoke(long window, int button, int action, int mods) {
					switch (action) {
						case GLFW.GLFW_PRESS:
							getMouseHandler().onPress(Inputs.getMouseFromInt(button));
							break;
						case GLFW.GLFW_RELEASE:
							getMouseHandler().onRelease(Inputs.getMouseFromInt(button));
							break;
						case GLFW.GLFW_REPEAT:
							getMouseHandler().onRepeat(Inputs.getMouseFromInt(button));
							break;
					}
				}
			});
		}
		
		if (getScrollHandler() != null) {
			GLFW.glfwSetScrollCallback(window, new GLFWScrollCallbackI() {
				@Override
				public void invoke(long window, double xoffset, double yoffset) {
					if (yoffset == 1) {
						getScrollHandler().onScrollUp();
					} else {
						getScrollHandler().onScrollDown();
					}
				}
			});
		}
		
		GLFW.glfwSetCursorPosCallback(window, new GLFWCursorPosCallbackI() {
			@Override
			public void invoke(long window, double xpos, double ypos) {
				if (isLoading()) {
					return;
				}
				
				switch (getViewportResizeType()) {
					case scale:
						float mousePosX = (((float) xpos - getViewportX()) / (getViewportWidth() / getHudWidth()));
						float mousePosY = (((float) ypos - getViewportY()) / (getViewportHeight() / getHudHeight()));
						MOUSE_POS.set(Math.round(mousePosX), Math.round(mousePosY));
						break;
					case stretch:
						MOUSE_POS.set((int) Math.round(xpos), (int) Math.round(ypos));
						break;
				}
			}
		});
		
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
				switch (getViewportResizeType()) {
					case scale:
						aspectWidth = width;
						aspectHeight = (int) (aspectWidth / (16f / 9f));
						
						if (aspectHeight > height) {
							aspectHeight = height;
							aspectWidth = (int) (aspectHeight * (16f / 9f));
						}
						
						aspectX = (int) ((width / 2f) - (aspectWidth / 2f));
						aspectY = (int) ((height / 2f) - (aspectHeight / 2f));
						
						GLH.setViewport(aspectX, aspectY, aspectWidth, aspectHeight);
						break;
					case stretch:
						aspectWidth = width;
						aspectHeight = height;
						GLH.setViewport(0, 0, aspectWidth, aspectHeight);
						break;
				}
				
				for (Shader s : Shaders.getAll()) { //This may cause problems?
					s.bind();
					s.onResize();
				}
				GLH.unbindShader();
			}
		});
		
		GLFW.glfwMakeContextCurrent(window);
		GLFW.glfwShowWindow(window);
		
		GLH.createCapabilities();
		
		GLH.swapInterval(0);
		GLH.clearColor(new Color(20, 20, 20, 1));
		GLH.enableDepth();
		GLH.enableBlend();
		GLH.setActiveTexture0();
		
		Shaders.getAll().addAll(shadersToSetup.get());
		Shaders.registerAll();
		
		Console.print(WarningType.Info, "OpenGL setup finished! Running OpenGL version: " + GLH.getVersion() + "!");
	}
	
	public static final void toggleFullScreen() {
		Console.print(WarningType.Debug, "Toggling Fullscreen!");
		isFullscreen = !isFullscreen;
		refreshFullscreen();
	}
	
	public static final void refreshFullscreen() {
		if (isFullscreen) {
			lastWidth = (aspectX * 2) + aspectWidth;
			lastHeight = (aspectY * 2) + aspectHeight;
			lastWindowX = windowX;
			lastWindowY = windowY;
			
			GLH.disableWindowDecorations(window);
			switch (screenFix) {
				case experimental:
					GLH.swapInterval(0);
					GLH.setWindowData(window, 0, -1, GLH.getMonitorSize().addY(2));
					break;
				case off:
					GLH.swapInterval(0);
					GLH.setWindowData(window, 0, 0, GLH.getMonitorSize());
					break;
				case vsync:
					GLH.swapInterval(1);
					GLH.setWindowData(window, 0, 0, GLH.getMonitorSize());
					break;
			}
		} else {
			GLH.swapInterval(0);
			GLH.enableWindowDecorations(window);
			GLH.setWindowData(window, lastWindowX, lastWindowY, lastWidth, lastHeight);
		}
	}
	
	protected abstract IInputHandler<EnumKeyInput> setKeyHandler();
	
	protected abstract IInputHandler<EnumMouseInput> setMouseHandler();
	
	protected abstract IScrollHandler setScrollHandler();
	
	protected abstract EnumViewportResizeType getViewportResizeType();
	
	public final IInputHandler<EnumKeyInput> getKeyHandler() {
		return keyHandler;
	}
	
	public final IInputHandler<EnumMouseInput> getMouseHandler() {
		return mouseHandler;
	}
	
	public final IScrollHandler getScrollHandler() {
		return scrollHandler;
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
	
	protected enum EnumViewportResizeType {
		scale,
		stretch;
	}
}
