package main.java.ulibs.engine.input;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.glfw.GLFW;

/**
 * Class that holds all types of inputs. Really only used for comparing inside your {@link IInputHandler} methods. <br>
 * You shouldn't need to use the {@link Inputs#findKey(int)} or {@link Inputs#findMouse(int)} methods as those are just used for conversion.
 * @author -Unknown-
 */
@SuppressWarnings("javadoc")
public class Inputs {
	private static final Map<Integer, KeyInput> KEY_MAP = new HashMap<Integer, KeyInput>();
	private static final Map<Integer, MouseInput> MOUSE_MAP = new HashMap<Integer, MouseInput>();
	
	public static final KeyInput KEY_UNKNOWN = addKey(-1);
	public static final MouseInput MOUSE_UNKNOWN = addMouse(-1);
	
	public static final KeyInput KEY_SPACE = addKey(GLFW.GLFW_KEY_SPACE, ' ');
	public static final KeyInput KEY_APOSTROPHE = addKey(GLFW.GLFW_KEY_APOSTROPHE, '\'');
	public static final KeyInput KEY_COMMA = addKey(GLFW.GLFW_KEY_COMMA, ',');
	public static final KeyInput KEY_MINUS = addKey(GLFW.GLFW_KEY_MINUS, '-');
	public static final KeyInput KEY_PERIOD = addKey(GLFW.GLFW_KEY_PERIOD, '.');
	public static final KeyInput KEY_SLASH = addKey(GLFW.GLFW_KEY_SLASH, '/');
	public static final KeyInput KEY_0 = addKey(GLFW.GLFW_KEY_0, '0');
	public static final KeyInput KEY_1 = addKey(GLFW.GLFW_KEY_1, '1');
	public static final KeyInput KEY_2 = addKey(GLFW.GLFW_KEY_2, '2');
	public static final KeyInput KEY_3 = addKey(GLFW.GLFW_KEY_3, '3');
	public static final KeyInput KEY_4 = addKey(GLFW.GLFW_KEY_4, '4');
	public static final KeyInput KEY_5 = addKey(GLFW.GLFW_KEY_5, '5');
	public static final KeyInput KEY_6 = addKey(GLFW.GLFW_KEY_6, '6');
	public static final KeyInput KEY_7 = addKey(GLFW.GLFW_KEY_7, '7');
	public static final KeyInput KEY_8 = addKey(GLFW.GLFW_KEY_8, '8');
	public static final KeyInput KEY_9 = addKey(GLFW.GLFW_KEY_9, '9');
	public static final KeyInput KEY_SEMICOLON = addKey(GLFW.GLFW_KEY_SEMICOLON, ';');
	public static final KeyInput KEY_EQUAL = addKey(GLFW.GLFW_KEY_EQUAL, '=');
	public static final KeyInput KEY_A = addKey(GLFW.GLFW_KEY_A, 'a');
	public static final KeyInput KEY_B = addKey(GLFW.GLFW_KEY_B, 'b');
	public static final KeyInput KEY_C = addKey(GLFW.GLFW_KEY_C, 'c');
	public static final KeyInput KEY_D = addKey(GLFW.GLFW_KEY_D, 'd');
	public static final KeyInput KEY_E = addKey(GLFW.GLFW_KEY_E, 'e');
	public static final KeyInput KEY_F = addKey(GLFW.GLFW_KEY_F, 'f');
	public static final KeyInput KEY_G = addKey(GLFW.GLFW_KEY_G, 'g');
	public static final KeyInput KEY_H = addKey(GLFW.GLFW_KEY_H, 'h');
	public static final KeyInput KEY_I = addKey(GLFW.GLFW_KEY_I, 'i');
	public static final KeyInput KEY_J = addKey(GLFW.GLFW_KEY_J, 'j');
	public static final KeyInput KEY_K = addKey(GLFW.GLFW_KEY_K, 'k');
	public static final KeyInput KEY_L = addKey(GLFW.GLFW_KEY_L, 'l');
	public static final KeyInput KEY_M = addKey(GLFW.GLFW_KEY_M, 'm');
	public static final KeyInput KEY_N = addKey(GLFW.GLFW_KEY_N, 'n');
	public static final KeyInput KEY_O = addKey(GLFW.GLFW_KEY_O, 'o');
	public static final KeyInput KEY_P = addKey(GLFW.GLFW_KEY_P, 'p');
	public static final KeyInput KEY_Q = addKey(GLFW.GLFW_KEY_Q, 'q');
	public static final KeyInput KEY_R = addKey(GLFW.GLFW_KEY_R, 'r');
	public static final KeyInput KEY_S = addKey(GLFW.GLFW_KEY_S, 's');
	public static final KeyInput KEY_T = addKey(GLFW.GLFW_KEY_T, 't');
	public static final KeyInput KEY_U = addKey(GLFW.GLFW_KEY_U, 'u');
	public static final KeyInput KEY_V = addKey(GLFW.GLFW_KEY_V, 'v');
	public static final KeyInput KEY_W = addKey(GLFW.GLFW_KEY_W, 'w');
	public static final KeyInput KEY_X = addKey(GLFW.GLFW_KEY_X, 'x');
	public static final KeyInput KEY_Y = addKey(GLFW.GLFW_KEY_Y, 'y');
	public static final KeyInput KEY_Z = addKey(GLFW.GLFW_KEY_Z, 'z');
	public static final KeyInput KEY_LEFT_BRACKET = addKey(GLFW.GLFW_KEY_LEFT_BRACKET, '[');
	public static final KeyInput KEY_BACKSLASH = addKey(GLFW.GLFW_KEY_BACKSLASH, '\\');
	public static final KeyInput KEY_RIGHT_BRACKET = addKey(GLFW.GLFW_KEY_RIGHT_BRACKET, ']');
	public static final KeyInput KEY_GRAVE_ACCENT = addKey(GLFW.GLFW_KEY_GRAVE_ACCENT, '`');
	
	public static final KeyInput KEY_ESCAPE = addKey(GLFW.GLFW_KEY_ESCAPE);
	public static final KeyInput KEY_ENTER = addKey(GLFW.GLFW_KEY_ENTER);
	public static final KeyInput KEY_TAB = addKey(GLFW.GLFW_KEY_TAB);
	public static final KeyInput KEY_BACKSPACE = addKey(GLFW.GLFW_KEY_BACKSPACE);
	public static final KeyInput KEY_INSERT = addKey(GLFW.GLFW_KEY_INSERT);
	public static final KeyInput KEY_DELETE = addKey(GLFW.GLFW_KEY_DELETE);
	public static final KeyInput KEY_RIGHT = addKey(GLFW.GLFW_KEY_RIGHT);
	public static final KeyInput KEY_LEFT = addKey(GLFW.GLFW_KEY_LEFT);
	public static final KeyInput KEY_DOWN = addKey(GLFW.GLFW_KEY_DOWN);
	public static final KeyInput KEY_UP = addKey(GLFW.GLFW_KEY_UP);
	public static final KeyInput KEY_PAGE_UP = addKey(GLFW.GLFW_KEY_PAGE_UP);
	public static final KeyInput KEY_PAGE_DOWN = addKey(GLFW.GLFW_KEY_PAGE_DOWN);
	public static final KeyInput KEY_HOME = addKey(GLFW.GLFW_KEY_HOME);
	public static final KeyInput KEY_END = addKey(GLFW.GLFW_KEY_END);
	public static final KeyInput KEY_CAPS_LOCK = addKey(GLFW.GLFW_KEY_CAPS_LOCK);
	public static final KeyInput KEY_SCROLL_LOCK = addKey(GLFW.GLFW_KEY_SCROLL_LOCK);
	public static final KeyInput KEY_NUM_LOCK = addKey(GLFW.GLFW_KEY_NUM_LOCK);
	public static final KeyInput KEY_PRINT_SCREEN = addKey(GLFW.GLFW_KEY_PRINT_SCREEN);
	public static final KeyInput KEY_PAUSE = addKey(GLFW.GLFW_KEY_PAUSE);
	public static final KeyInput KEY_F1 = addKey(GLFW.GLFW_KEY_F1);
	public static final KeyInput KEY_F2 = addKey(GLFW.GLFW_KEY_F2);
	public static final KeyInput KEY_F3 = addKey(GLFW.GLFW_KEY_F3);
	public static final KeyInput KEY_F4 = addKey(GLFW.GLFW_KEY_F4);
	public static final KeyInput KEY_F5 = addKey(GLFW.GLFW_KEY_F5);
	public static final KeyInput KEY_F6 = addKey(GLFW.GLFW_KEY_F6);
	public static final KeyInput KEY_F7 = addKey(GLFW.GLFW_KEY_F7);
	public static final KeyInput KEY_F8 = addKey(GLFW.GLFW_KEY_F8);
	public static final KeyInput KEY_F9 = addKey(GLFW.GLFW_KEY_F9);
	public static final KeyInput KEY_F10 = addKey(GLFW.GLFW_KEY_F10);
	public static final KeyInput KEY_F11 = addKey(GLFW.GLFW_KEY_F11);
	public static final KeyInput KEY_F12 = addKey(GLFW.GLFW_KEY_F12);
	public static final KeyInput KEY_F13 = addKey(GLFW.GLFW_KEY_F13);
	public static final KeyInput KEY_F14 = addKey(GLFW.GLFW_KEY_F14);
	public static final KeyInput KEY_F15 = addKey(GLFW.GLFW_KEY_F15);
	public static final KeyInput KEY_F16 = addKey(GLFW.GLFW_KEY_F16);
	public static final KeyInput KEY_F17 = addKey(GLFW.GLFW_KEY_F17);
	public static final KeyInput KEY_F18 = addKey(GLFW.GLFW_KEY_F18);
	public static final KeyInput KEY_F19 = addKey(GLFW.GLFW_KEY_F19);
	public static final KeyInput KEY_F20 = addKey(GLFW.GLFW_KEY_F20);
	public static final KeyInput KEY_F21 = addKey(GLFW.GLFW_KEY_F21);
	public static final KeyInput KEY_F22 = addKey(GLFW.GLFW_KEY_F22);
	public static final KeyInput KEY_F23 = addKey(GLFW.GLFW_KEY_F23);
	public static final KeyInput KEY_F24 = addKey(GLFW.GLFW_KEY_F24);
	public static final KeyInput KEY_F25 = addKey(GLFW.GLFW_KEY_F25);
	public static final KeyInput KEY_KP_0 = addKey(GLFW.GLFW_KEY_KP_0);
	public static final KeyInput KEY_KP_1 = addKey(GLFW.GLFW_KEY_KP_1);
	public static final KeyInput KEY_KP_2 = addKey(GLFW.GLFW_KEY_KP_2);
	public static final KeyInput KEY_KP_3 = addKey(GLFW.GLFW_KEY_KP_3);
	public static final KeyInput KEY_KP_4 = addKey(GLFW.GLFW_KEY_KP_4);
	public static final KeyInput KEY_KP_5 = addKey(GLFW.GLFW_KEY_KP_5);
	public static final KeyInput KEY_KP_6 = addKey(GLFW.GLFW_KEY_KP_6);
	public static final KeyInput KEY_KP_7 = addKey(GLFW.GLFW_KEY_KP_7);
	public static final KeyInput KEY_KP_8 = addKey(GLFW.GLFW_KEY_KP_8);
	public static final KeyInput KEY_KP_9 = addKey(GLFW.GLFW_KEY_KP_9);
	public static final KeyInput KEY_KP_DECIMAL = addKey(GLFW.GLFW_KEY_KP_DECIMAL);
	public static final KeyInput KEY_KP_DIVIDE = addKey(GLFW.GLFW_KEY_KP_DIVIDE);
	public static final KeyInput KEY_KP_MULTIPLY = addKey(GLFW.GLFW_KEY_KP_MULTIPLY);
	public static final KeyInput KEY_KP_SUBTRACT = addKey(GLFW.GLFW_KEY_KP_SUBTRACT);
	public static final KeyInput KEY_KP_ADD = addKey(GLFW.GLFW_KEY_KP_ADD);
	public static final KeyInput KEY_KP_ENTER = addKey(GLFW.GLFW_KEY_KP_ENTER);
	public static final KeyInput KEY_KP_EQUAL = addKey(GLFW.GLFW_KEY_KP_EQUAL);
	public static final KeyInput KEY_LEFT_SUPER = addKey(GLFW.GLFW_KEY_LEFT_SUPER);
	public static final KeyInput KEY_RIGHT_SUPER = addKey(GLFW.GLFW_KEY_RIGHT_SUPER);
	public static final KeyInput KEY_MENU = addKey(GLFW.GLFW_KEY_MENU);
	
	public static final KeyInput KEY_LEFT_SHIFT = addKey(GLFW.GLFW_KEY_LEFT_SHIFT);
	public static final KeyInput KEY_LEFT_CONTROL = addKey(GLFW.GLFW_KEY_LEFT_CONTROL);
	public static final KeyInput KEY_LEFT_ALT = addKey(GLFW.GLFW_KEY_LEFT_ALT);
	public static final KeyInput KEY_RIGHT_SHIFT = addKey(GLFW.GLFW_KEY_RIGHT_SHIFT);
	public static final KeyInput KEY_RIGHT_CONTROL = addKey(GLFW.GLFW_KEY_RIGHT_CONTROL);
	public static final KeyInput KEY_RIGHT_ALT = addKey(GLFW.GLFW_KEY_RIGHT_ALT);
	
	/** Left Mouse Button */
	public static final MouseInput MOUSE_BUTTON_1 = addMouse(GLFW.GLFW_MOUSE_BUTTON_1);
	/** Right Mouse Button */
	public static final MouseInput MOUSE_BUTTON_2 = addMouse(GLFW.GLFW_MOUSE_BUTTON_2);
	/** Middle Mouse Button */
	public static final MouseInput MOUSE_BUTTON_3 = addMouse(GLFW.GLFW_MOUSE_BUTTON_3);
	public static final MouseInput MOUSE_BUTTON_4 = addMouse(GLFW.GLFW_MOUSE_BUTTON_4);
	public static final MouseInput MOUSE_BUTTON_5 = addMouse(GLFW.GLFW_MOUSE_BUTTON_5);
	public static final MouseInput MOUSE_BUTTON_6 = addMouse(GLFW.GLFW_MOUSE_BUTTON_6);
	public static final MouseInput MOUSE_BUTTON_7 = addMouse(GLFW.GLFW_MOUSE_BUTTON_7);
	public static final MouseInput MOUSE_BUTTON_8 = addMouse(GLFW.GLFW_MOUSE_BUTTON_8);
	
	private static KeyInput addKey(int id, char c) {
		KeyInput input = new KeyInput(id, c);
		KEY_MAP.put(id, input);
		return input;
	}
	
	private static KeyInput addKey(int id) {
		KeyInput input = new KeyInput(id);
		KEY_MAP.put(id, input);
		return input;
	}
	
	private static MouseInput addMouse(int id) {
		MouseInput input = new MouseInput(id);
		MOUSE_MAP.put(id, input);
		return input;
	}
	
	public static KeyInput findKey(int key) {
		return KEY_MAP.getOrDefault(key, KEY_UNKNOWN);
	}
	
	public static MouseInput findMouse(int key) {
		return MOUSE_MAP.getOrDefault(key, MOUSE_UNKNOWN);
	}
}
