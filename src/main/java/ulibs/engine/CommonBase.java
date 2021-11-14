package main.java.ulibs.engine;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.glfw.GLFW;

import main.java.ulibs.common.utils.Console;
import main.java.ulibs.common.utils.Console.WarningType;
import main.java.ulibs.engine.utils.IRunnable;
import main.java.ulibs.engine.utils.Timer;
import main.java.ulibs.engine.utils.Timer.TimerType;

public abstract class CommonBase implements Runnable {
	private static boolean isDebug;
	/** The title to use for the game. IE the text on the top bar */
	protected static String title;
	/** The internal title to use for assets. Should be all lowercase! */
	protected static String internalTitle;
	
	private static final String JAR_LOCATION = new File("").getAbsolutePath();
	
	private static int fps;
	private static boolean running = false, isLoading;
	private static Thread gameThread;
	
	private final List<Timer> timers = new ArrayList<Timer>(), timersToKill = new ArrayList<Timer>();
	
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
	
	protected void printJarInfo() {
		Console.print(WarningType.Debug, " - Logs Location -> '\\Logs'");
	}
	
	protected void addThreads() {
		gameThread = new Thread(this, "Client");
		gameThread.start();
	}
	
	public final void addTimer(IRunnable run, TimerType timerType, long time) {
		switch (timerType) {
			case minute:
				time *= 3600;
				break;
			case second:
				time *= 60;
				break;
			case tick:
				break;
		}
		
		timers.add(new Timer(run, timerType, time));
	}
	
	protected abstract void preRun();
	
	protected abstract void preInit();
	
	protected abstract void rendererSetup();
	
	protected abstract void init();
	
	protected abstract void postInit();
	
	protected abstract void preTick();
	
	protected abstract void tick();
	
	protected abstract void renderWrap();
	
	protected abstract boolean shouldClose();
	
	private void preInitWrap() {
		Console.print(WarningType.Info, "Pre-Initialization started...");
		preInit();
		Console.print(WarningType.Info, "Pre-Initialization finished!");
	}
	
	private void initWrap() {
		Console.print(WarningType.Info, "Initialization started...");
		init();
		rendererSetup();
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
					if (!timers.isEmpty()) {
						for (Timer t : timers) {
							t.time--;
							
							if (t.time <= 0) {
								timersToKill.add(t);
								t.runnable.run();
							}
						}
						
						if (!timersToKill.isEmpty()) {
							timers.removeAll(timersToKill);
							timersToKill.clear();
						}
					}
					
					preTick();
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
	
	/** @return Whether or not the game is running in Debug mode */
	public static final boolean isDebug() {
		return isDebug;
	}
	
	/** @return Whether or not the game is current loading */
	public static final boolean isLoading() {
		return isLoading;
	}
	
	/** @return True of the main {@link Thread} is currently running, otherwise false */
	public static final boolean isRunning() {
		return running;
	}
	
	/** @return The game's JAR location */
	public static final String getJarLocation() {
		return JAR_LOCATION;
	}
	
	/** @return The game's internal asset package location. <br> Which should look like "/main/resources/{@link #internalTitle}/assets/" */
	public static final String getAssetLocation() {
		return "/main/resources/" + internalTitle + "/assets/";
	}
	
	/** @return Returns an integer representing the game's FPS. If this number is lower than 60 problems may occur! */
	public static final int getFPS() {
		return fps;
	}
}
