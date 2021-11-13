package main.java.ulibs.engine.utils;

import main.java.ulibs.engine.CommonBase;

/**
 * A timer class for allowing code to run after the given {@link #time}. <br>
 * Should be used with {@link CommonBase#addTimer(IRunnable, TimerType, long)}. <br>
 * Will run {@link #runnable}'s {@link IRunnable#run()} after the given {@link #time}'s up.
 * @author -Unknown-
 */
public class Timer {
	/** The {@link IRunnable} to run after time's up */
	public final IRunnable runnable;
	/** The type of time unit of measurement to use for this timer */
	public final TimerType timerType;
	/** The time to wait until it should run {@link #runnable}'s {@link IRunnable#run()}. <br>
	 * Whether or not this is ticks/seconds/minutes/etc depends on {@link #timerType}! */
	public long time;
	
	/**
	 * Should not be constructed by user. See {@link CommonBase#addTimer(IRunnable, TimerType, long)}!
	 * @param runnable See {@link #runnable}
	 * @param timerType See {@link #timerType}
	 * @param time See {@link #time}
	 */
	public Timer(IRunnable runnable, TimerType timerType, long time) {
		this.runnable = runnable;
		this.timerType = timerType;
		this.time = time;
	}
	
	@SuppressWarnings("javadoc")
	public enum TimerType {
		/** 1/60 of a second */
		tick,
		second,
		minute;
	}
}
