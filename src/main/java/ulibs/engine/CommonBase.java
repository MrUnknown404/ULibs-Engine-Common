package main.java.ulibs.engine;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.glfw.GLFW;

import main.java.ulibs.common.utils.Console;
import main.java.ulibs.common.utils.Console.WarningType;
import main.java.ulibs.engine.render.IRenderer;
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
	private static boolean running = false, isLoading, shouldClose;
	private static Thread mainThread;
	
	private final List<Timer> timers = new ArrayList<Timer>(), timersToKill = new ArrayList<Timer>();
	protected int loadingState = 0;
	
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
		createThreads();
		startThreads();
		running = true;
	}
	
	/** A method for printing any info about the JAR location or any other folders created */
	protected void printJarInfo() {
		Console.print(WarningType.Debug, " - Logs Location -> '\\Logs'");
	}
	
	/** A method for adding any extra threads that may be needed */
	protected void createThreads() {
		mainThread = new Thread(this, "Client");
	}
	
	/** A method for starting any extra threads that may be needed */
	protected void startThreads() {
		mainThread.start();
	}
	
	/** Adds a timer based off the given information to the tick loop. Ticking is handled internally. <br>
	 * Once the given time has run out it will run the given {@link Runnable}
	 * @param run an {@link Runnable} to run after the given time
	 * @param timerType The type of time unit of measurement to use for this timer
	 * @param time The amount of time to wait to run the given {@link Runnable}
	 * @param repeats Whether or not the timer will repeat. Note that there is no way to remove the timer if this is true
	 */
	public final void addTimer(Runnable run, TimerType timerType, long time, boolean repeats) {
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
		
		timers.add(new Timer(run, timerType, time, repeats));
	}
	
	/** For anything that needs to run before anything else. (Such as OpenGL stuff)
	 * @return True if everything worked and should continue running the program. False will automatically run {@link System#exit(int)}
	 */
	protected abstract boolean preRun();
	
	/** First initialization method that'll run. */
	protected abstract void preInit();
	
	/** Second initialization method that'll run. Should setup {@link IRenderer}s here */
	protected abstract void init();
	
	/** Third initialization method that'll run. */
	protected abstract void postInit();
	
	protected abstract void tick();
	
	protected abstract void internalRender();
	
	/** @return True of the window should close, otherwise false */
	protected boolean shouldClose() {
		return shouldClose;
	}
	
	/** Should avoid overriding these */
	protected void preInitWrap() {
		Console.print(WarningType.Info, "Pre-Initialization started...");
		loadingState++;
		preInit();
		Console.print(WarningType.Info, "Pre-Initialization finished!");
	}
	
	/** Should avoid overriding these */
	protected void initWrap() {
		Console.print(WarningType.Info, "Initialization started...");
		loadingState++;
		init();
		Console.print(WarningType.Info, "Initialization finished!");
	}
	
	/** Should avoid overriding these */
	protected void postInitWrap() {
		Console.print(WarningType.Info, "Post-Initialization started...");
		loadingState++;
		postInit();
		Console.print(WarningType.Info, "Post-Initialization finished!");
	}
	
	/** Should avoid overriding these */
	protected void onFinishInitWrap() {
		Console.print(WarningType.Info, "All Initialization has been finished!");
		loadingState++;
		onFinishInit();
	}
	
	/** Run after all initialization is done. */
	protected void onFinishInit() {
		
	}
	
	/** Runs right before the game closes. */
	protected void onExit() {
		
	}
	
	@Override
	public final void run() {
		if (!preRun()) {
			Console.print(WarningType.FatalError, "Pre-Run failed!");
			System.exit(0);
		}
		isLoading = true;
		
		Console.print(WarningType.Info, "Started " + mainThread.getName() + "'s run loop!");
		
		long lastTime = System.nanoTime(), timer = System.currentTimeMillis();
		double amountOfTicks = 60.0, ns = 1000000000 / amountOfTicks, delta = 0;
		int frames = 0;
		boolean ranOnce = false;
		
		while (running) {
			if (shouldClose()) {
				Console.print(WarningType.Info, "Goodbye!");
				onExit();
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
					timerTick();
					tick();
				}
				
				delta--;
			}
			
			internalRender();
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
				onFinishInitWrap();
				isLoading = false;
				ranOnce = true;
			}
		}
	}
	
	private void timerTick() {
		if (!timers.isEmpty()) {
			for (Timer t : timers) {
				t.time--;
				
				if (t.time <= 0) {
					if (!t.repeats) {
						timersToKill.add(t);
					} else {
						t.resetTime();
					}
					
					t.runnable.run();
				}
			}
			
			if (!timersToKill.isEmpty()) {
				timers.removeAll(timersToKill);
				timersToKill.clear();
			}
		}
	}
	
	public static final void close() {
		shouldClose = true;
	}
	
	/** Toggles debug mode */
	public static final void toggleDebug() {
		isDebug = !isDebug;
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
		return "/main/resources/" + getInternalTitle() + "/assets/";
	}
	
	/** @return The game's internal title. See {@link #internalTitle} */
	public static final String getInternalTitle() {
		return internalTitle;
	}
	
	/** @return Returns an integer representing the game's FPS. If this number is lower than 60 problems may occur! */
	public static final int getFPS() {
		return fps;
	}
	
	protected static Thread getMainThread() {
		return mainThread;
	}
}
