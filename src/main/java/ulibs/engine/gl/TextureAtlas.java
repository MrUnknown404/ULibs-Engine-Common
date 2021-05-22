package main.java.ulibs.engine.gl;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.java.ulibs.common.helpers.MathH;
import main.java.ulibs.common.math.Vec2f;
import main.java.ulibs.common.utils.Console;
import main.java.ulibs.common.utils.Console.WarningType;
import main.java.ulibs.engine.utils.GetResource;
import main.java.ulibs.engine.utils.INameable;
import main.java.ulibs.gl.gl.Texture;

public class TextureAtlas<T extends INameable> {
	private final Map<T, Vec2f> map = new HashMap<T, Vec2f>();
	private final int size;
	private final Texture texture;
	
	public TextureAtlas(List<T> list, int cellSize, String folder) {
		size = MathH.ceil(Math.sqrt(list.size()));
		Console.print(WarningType.TextureDebug, "Creating atlas using folder '" + folder + "' that is " + size + "x" + size);
		
		BufferedImage img = new BufferedImage(size * cellSize, size * cellSize, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = img.createGraphics();
		
		float size = this.size, s = 1f / size;
		for (int i = 0; i < list.size(); i++) {
			map.put(list.get(i), new Vec2f((i % size) / size, -MathH.floor(i / size) / size - s));
			
			g.drawImage(getTexture(folder, list.get(i).getName()), (i % this.size) * cellSize, MathH.floor(i / this.size) * cellSize, cellSize, cellSize, null);
		}
		g.dispose();
		
		texture = new Texture(img);
		
	}
	
	public Vec2f get(T t) {
		return map.get(t);
	}
	
	public void bind() {
		texture.bind();
	}
	
	public int size() {
		return size;
	}
	
	private static BufferedImage getTexture(String folder, String name) {
		BufferedImage i = GetResource.getTexture(folder, name);
		Console.print(WarningType.TextureDebug, (i == GetResource.nil ? "Unable to " : "") + "register '" + name + "' for " + TextureAtlas.class.getSimpleName());
		return i;
	}
}