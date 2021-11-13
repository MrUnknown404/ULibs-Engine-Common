package main.java.ulibs.engine.init;

import java.util.ArrayList;
import java.util.List;

import main.java.ulibs.common.utils.Console;
import main.java.ulibs.common.utils.Console.WarningType;
import main.java.ulibs.engine.ClientBase;
import main.java.ulibs.engine.gl.ShaderHud;
import main.java.ulibs.engine.gl.ShaderHudTextureless;
import main.java.ulibs.engine.gl.ShaderMoveable;
import main.java.ulibs.gl.gl.Shader;
import main.java.ulibs.gl.math.Matrix4f;

public class Shaders {
	private static final List<Shader> ALL = new ArrayList<Shader>();
	
	private static ShaderMoveable moveableObject;
	
	private static ShaderHud hud;
	private static ShaderHudTextureless hudTextureless;
	
	private static final Matrix4f WORLD_PR = Matrix4f.orthographic(0f, 16f, 9f, 0f, -1f, 10f); //H_TODO make this editable!
	private static final Matrix4f HUD_PR = Matrix4f.orthographic(0f, ClientBase.getHudWidth(), ClientBase.getHudHeight(), 0f, -1f, 10f);
	
	public static void registerAll() {
		moveableObject = add(new ShaderMoveable(WORLD_PR));
		hud = add(new ShaderHud(HUD_PR));
		hudTextureless = add(new ShaderHudTextureless(HUD_PR));
		
		for (Shader s : ALL) {
			s.setup();
		}
	}
	
	private static <T extends Shader> T add(T s) {
		if (ALL.isEmpty()) {
			Console.print(WarningType.Info, "Started registering " + Shaders.class.getSimpleName() + "!");
		}
		
		ALL.add(s);
		Console.print(Console.WarningType.RegisterDebug, "'" + s.name + "' was registered!");
		return s;
	}
	
	public static ShaderMoveable MoveableObject() {
		return moveableObject;
	}
	
	public static ShaderHud Hud() {
		return hud;
	}
	
	public static ShaderHudTextureless HudTextureless() {
		return hudTextureless;
	}
}
