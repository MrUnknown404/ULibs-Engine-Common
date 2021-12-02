package main.java.ulibs.engine.render;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.java.ulibs.engine.ClientBase;
import main.java.ulibs.engine.init.Shaders;
import main.java.ulibs.engine.utils.GetResource;
import main.java.ulibs.gl.gl.GLH;
import main.java.ulibs.gl.gl.QuadData;
import main.java.ulibs.gl.gl.Texture;
import main.java.ulibs.gl.gl.VertexArray;
import main.java.ulibs.gl.gl.ZConstant;

public class ScreenLoading implements IRenderer {
	private static final Texture LOADING_SCREEN = new Texture(getDefaultTexture());
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
		GLH.unbindTexture();
		GLH.unbindShader();
	}
	
	private static BufferedImage getDefaultTexture() {
		try {
			return ImageIO.read(GetResource.class.getResourceAsStream("/main/resources/ulibs/engine/assets/textures/gui/loading_screen.png"));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
