package main.java.ulibs.engine.utils;

import main.java.ulibs.engine.CommonBase;

/**
 * A timer class for allowing code to run after the given {@link #time}. <br>
 * Should be used with {@link CommonBase#addTimer(IRunnable, TimerType, long)}. <br>
 * Will run {@link #runnable}'s {@link IRunnable#run()} after the given {@link #time}'s up.
 * @author -Unknown-
 */
public class Timer {
	/** The {@link Runnable} to run after time's up */
	public final Runnable runnable;
	/** The type of time unit of measurement to use for this timer */
	public final TimerType timerType;
	/** The time to wait until it should run {@link #runnable}'s {@link IRunnable#run()}. <br>
	 * Whether or not this is ticks/seconds/minutes/etc depends on {@link #timerType}! */
	public long time;
	/** Whether or not this timer should repeat */
	public final boolean repeats;
	
	private final long maxTime;
	
	/**
	 * Should not be constructed by user. See {@link CommonBase#addTimer(Runnable, TimerType, long, boolean)}!
	 * @param runnable See {@link #runnable}
	 * @param timerType See {@link #timerType}
	 * @param time See {@link #time}
	 * @param repeats See {@link #repeats}
	 */
	public Timer(Runnable runnable, TimerType timerType, long time, boolean repeats) {
		this.runnable = runnable;
		this.timerType = timerType;
		this.maxTime = time;
		this.time = time;
		this.repeats = repeats;
	}
	
	/** Resets the time */
	public void resetTime() {
		time = maxTime;
	}
	
	@SuppressWarnings("javadoc")
	public enum TimerType {
		/** 1/60 of a second */
		tick,
		second,
		minute;
	}
}
