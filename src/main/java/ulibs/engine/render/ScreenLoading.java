package main.java.ulibs.engine.render;

import java.awt.image.BufferedImage;

import org.lwjgl.opengl.GL46;

import main.java.ulibs.common.utils.Console;
import main.java.ulibs.common.utils.Console.WarningType;
import main.java.ulibs.engine.ClientBase;
import main.java.ulibs.engine.init.Shaders;
import main.java.ulibs.engine.utils.GetResource;
import main.java.ulibs.gl.gl.QuadData;
import main.java.ulibs.gl.gl.Texture;
import main.java.ulibs.gl.gl.VertexArray;
import main.java.ulibs.gl.gl.ZConstant;

public class ScreenLoading implements IRenderer {
	
	private static final Texture LOADING_SCREEN = new Texture(getTexture("gui", "loading_screen"));
	private VertexArray va;
	
	@Override
	public void setupGL() {
		va = new VertexArray().addVerticesWithDefaults(QuadData.createVertex(0, 0, ZConstant.Z_HUD_BASE, ClientBase.getHudWidth(), ClientBase.getHudHeight()));
		va.setup();
	}
	
	@Override
	public void renderPre() {
		Shaders.Hud().bind();
		LOADING_SCREEN.bind();
		va.bind();
		va.draw();
		va.unbind();
		GL46.glBindTexture(GL46.GL_TEXTURE_2D, 0);
		Shaders.unbind();
	}
	
	private static BufferedImage getTexture(String folder, String name) {
		BufferedImage i = GetResource.getTexture(folder, name);
		Console.print(WarningType.TextureDebug, (i == GetResource.nil ? "Unable to " : "") + "register '" + name + "' for " + ScreenLoading.class.getSimpleName());
		return i;
	}
}
