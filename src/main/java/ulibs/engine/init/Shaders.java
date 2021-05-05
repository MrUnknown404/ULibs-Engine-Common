package main.java.ulibs.engine.init;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL46;

import main.java.ulibs.common.utils.Console;
import main.java.ulibs.common.utils.Console.WarningType;
import main.java.ulibs.engine.ClientBase;
import main.java.ulibs.gl.gl.Shader;
import main.java.ulibs.gl.math.Matrix4f;

public class Shaders {
	private static final List<Shader> ALL = new ArrayList<Shader>();
	
	private static Shader moveableObject;
	
	private static Shader hud;
	private static Shader hudTextureless;
	
	private static final Matrix4f WORLD_PR = Matrix4f.orthographic(0f, 16f, 9f, 0f, -1f, 10f); //H_TODO make this editable!
	private static final Matrix4f HUD_PR = Matrix4f.orthographic(0f, ClientBase.getHudWidth(), ClientBase.getHudHeight(), 0f, -1f, 10f);
	
	public static void registerAll() {
		moveableObject = add(new Shader("moveable", WORLD_PR, "ulibs/engine"));
		hud = add(new Shader("hud", HUD_PR, "ulibs/engine"));
		hudTextureless = add(new Shader("hud_textureless", HUD_PR, "ulibs/engine"));
		
		for (Shader s : ALL) {
			s.setup();
		}
	}
	
	private static Shader add(Shader s) {
		if (ALL.isEmpty()) {
			Console.print(WarningType.Info, "Started registering " + Shaders.class.getSimpleName() + "!");
		}
		
		ALL.add(s);
		Console.print(Console.WarningType.RegisterDebug, "'" + s.name + "' was registered!");
		return s;
	}
	
	public static void unbind() {
		GL46.glUseProgram(0);
	}
	
	public static Shader MoveableObject() {
		return moveableObject;
	}
	
	public static Shader Hud() {
		return hud;
	}
	
	public static Shader HudTextureless() {
		return hudTextureless;
	}
}
