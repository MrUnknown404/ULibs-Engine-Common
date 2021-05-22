package main.java.ulibs.engine.input;

import org.lwjgl.glfw.GLFWCharCallbackI;
import org.lwjgl.glfw.GLFWKeyCallbackI;
import org.lwjgl.glfw.GLFWMouseButtonCallbackI;
import org.lwjgl.glfw.GLFWScrollCallbackI;

public class InputHolder {
	private GLFWKeyCallbackI keyInput;
	private GLFWCharCallbackI charInput;
	private GLFWMouseButtonCallbackI mouseInput;
	private GLFWScrollCallbackI scrollInput;
	
	public InputHolder set(GLFWKeyCallbackI keyInput) {
		this.keyInput = keyInput;
		return this;
	}
	
	public InputHolder set(GLFWCharCallbackI charInput) {
		this.charInput = charInput;
		return this;
	}
	
	public InputHolder set(GLFWMouseButtonCallbackI mouseInput) {
		this.mouseInput = mouseInput;
		return this;
	}
	
	public InputHolder set(GLFWScrollCallbackI scrollInput) {
		this.scrollInput = scrollInput;
		return this;
	}
	
	public GLFWKeyCallbackI getKeyInput() {
		return keyInput;
	}
	
	public GLFWCharCallbackI getCharInput() {
		return charInput;
	}
	
	public GLFWMouseButtonCallbackI getMouseInput() {
		return mouseInput;
	}
	
	public GLFWScrollCallbackI getScrollInput() {
		return scrollInput;
	}
}
