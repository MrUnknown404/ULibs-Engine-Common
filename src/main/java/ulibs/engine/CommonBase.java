package main.java.ulibs.engine;

import java.io.File;

import org.lwjgl.glfw.GLFW;

import main.java.ulibs.common.utils.Console;
import main.java.ulibs.common.utils.Console.WarningType;

abstract class CommonBase implements Runnable {
	private static boolean isDebug;
	protected static String title, internalTitle;
	
	private static final String JAR_LOCATION = new File("").getAbsolutePath();
	
	private static int fps;
	private static boolean running = false, isLoading;
	private static  Thread gameThread;
	
	//TODO document
	protected CommonBase(String title, String internalTitle, boolean isDebug, int logCount, WarningType[] warnings) {
		CommonBase.isDebug = isDebug;
		CommonBase.title = title;
		CommonBase.internalTitle = internalTitle;
		
		Console.disabledTypes = warnings;
		Thread.currentThread().setName("Common");
		Console.setupLogFile(new File(JAR_LOCATION + "\\Logs"), logCount);
		Console.getTimeExample();
		Console.print(WarningType.Info, "Welcome to " + title + "!");
		Console.print(WarningType.Debug, "Jar Location is -> '" + JAR_LOCATION + "'");
		printJarInfo();
		
		Console.print(WarningType.Debug, "Starting threads!");
		addThreads();
		running = true;
	}
	
	//TODO document
	protected void printJarInfo() {
		Console.print(WarningType.Debug, " - Logs Location -> '\\Logs'");
	}
	
	//TODO document
	protected void addThreads() {
		gameThread = new Thread(this, "Client");
		gameThread.start();
	}
	
	//TODO document
	protected abstract void preRun();
	
	//TODO document
	protected abstract void preInit();
	
	//TODO document
	protected abstract void rendererSetup();
	
	//TODO document
	protected abstract void init();
	
	//TODO document
	protected abstract void postInit();
	
	//TODO document
	protected abstract void tickWrap();
	
	//TODO document
	protected abstract void tick();
	
	//TODO document
	protected abstract void renderWrap();
	
	//TODO document
	protected abstract boolean shouldClose();
	
	private void preInitWrap() {
		Console.print(WarningType.Info, "Pre-Initialization started...");
		preInit();
		Console.print(WarningType.Info, "Pre-Initialization finished!");
	}
	
	private void initWrap() {
		Console.print(WarningType.Info, "Initialization started...");
		rendererSetup();
		init();
		Console.print(WarningType.Info, "Initialization finished!");
	}
	
	private void postInitWrap() {
		Console.print(WarningType.Info, "Post-Initialization started...");
		postInit();
		Console.print(WarningType.Info, "Post-Initialization finished!");
	}
	
	@Override
	public final void run() {
		preRun();
		isLoading = true;
		
		Console.print(WarningType.Info, "Started " + gameThread.getName() + "'s run loop!");
		
		long lastTime = System.nanoTime(), timer = System.currentTimeMillis();
		double amountOfTicks = 60.0, ns = 1000000000 / amountOfTicks, delta = 0;
		int frames = 0;
		boolean ranOnce = false;
		
		while (running) {
			if (shouldClose()) {
				running = false;
				System.exit(0);
				break;
			}
			
			GLFW.glfwPollEvents();
			
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			
			while (delta >= 1) {
				if (!isLoading) {
					tickWrap();
				}
				delta--;
			}
			
			renderWrap();
			frames++;
			
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				fps = frames;
				frames = 0;
			}
			
			if (!ranOnce) {
				preInitWrap();
				initWrap();
				postInitWrap();
				isLoading = false;
				ranOnce = true;
			}
		}
	}
	
	//TODO document
	public static final boolean isDebug() {
		return isDebug;
	}
	
	//TODO document
	public static final boolean isLoading() {
		return isLoading;
	}
	
	//TODO document
	public static final boolean isRunning() {
		return running;
	}
	
	//TODO document
	public static final String getJarLocation() {
		return JAR_LOCATION;
	}
	
	//TODO document
	public static final String getAssetLocation() {
		return "/main/resources/" + internalTitle + "/assets/";
	}
	
	//TODO document
	public static final int getFPS() {
		return fps;
	}
}
