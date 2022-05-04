package main.java.ulibs.engine.init;

import java.util.ArrayList;
import java.util.List;

import main.java.ulibs.common.utils.Console;
import main.java.ulibs.common.utils.Console.WarningType;
import main.java.ulibs.engine.ClientBase;
import main.java.ulibs.engine.gl.ShaderTextured;
import main.java.ulibs.engine.gl.ShaderTextureless;
import main.java.ulibs.gl.gl.Shader;
import main.java.ulibs.gl.math.Matrix4f;

public class Shaders {
	private static final List<Shader> ALL = new ArrayList<Shader>();
	
	private static ShaderTextured hud;
	private static ShaderTextureless hudTextureless;
	private static ShaderTextured object;
	private static ShaderTextureless objectTextureless;
	
	private static final Matrix4f WORLD_PR = Matrix4f.orthographic(0f, 16f, 9f, 0f, -1f, 10f);
	private static final Matrix4f HUD_PR = Matrix4f.orthographic(0f, ClientBase.getDefaultWidth(), ClientBase.getDefaultHeight(), 0f, -1f, 10f);
	
	public static void registerAll() {
		Console.print(WarningType.Info, "Started registering " + Shaders.class.getSimpleName() + "!");
		hud = add(new ShaderTextured(HUD_PR));
		hudTextureless = add(new ShaderTextureless(HUD_PR));
		object = add(new ShaderTextured(WORLD_PR));
		objectTextureless = add(new ShaderTextureless(WORLD_PR));
		
		for (Shader s : ALL) {
			s.setup();
		}
	}
	
	private static <T extends Shader> T add(T s) {
		ALL.add(s);
		Console.print(Console.WarningType.RegisterDebug, "'" + s.name + "' was registered!");
		return s;
	}
	
	public static ShaderTextured Hud() {
		return hud;
	}
	
	public static ShaderTextureless HudTextureless() {
		return hudTextureless;
	}
	
	public static ShaderTextured Object() {
		return object;
	}
	
	public static ShaderTextureless ObjectTextureless() {
		return objectTextureless;
	}
	
	public static List<Shader> getAll() {
		return ALL;
	}
}
