package main.java.ulibs.engine.utils;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.java.ulibs.common.utils.Console;
import main.java.ulibs.engine.ClientBase;

public class GetResource {
	public static final BufferedImage NIL = getNilTexture();
	
	private static BufferedImage getNilTexture() {
		try {
			return ImageIO.read(GetResource.class.getResourceAsStream("/main/resources/ulibs/engine/assets/textures/nil.png"));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static BufferedImage getTexture(String folder, String textureName) {
		if (!folder.isEmpty() && !folder.endsWith("/")) {
			folder += "/";
		}
		
		if (GetResource.class.getResourceAsStream(ClientBase.getAssetLocation() + "textures/" + folder + textureName + ".png") == null) {
			Console.print(Console.WarningType.Error, "Cannot find texture : '" + ClientBase.getAssetLocation() + "textures/" + folder + textureName + ".png'");
			return NIL;
		}
		
		try {
			return ImageIO.read(GetResource.class.getResourceAsStream(ClientBase.getAssetLocation() + "textures/" + folder + textureName + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
			return NIL;
		}
	}
	
	public static BufferedImage getTexture(String textureName) {
		return getTexture("", textureName);
	}
}
