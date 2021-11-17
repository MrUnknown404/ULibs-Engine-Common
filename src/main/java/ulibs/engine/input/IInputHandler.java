package main.java.ulibs.engine.input;

public interface IInputHandler<T extends IInput<T>> {
	public abstract void onPress(T input);
	public abstract void onRelease(T input);
	public abstract void onRepeat(T input);
}
