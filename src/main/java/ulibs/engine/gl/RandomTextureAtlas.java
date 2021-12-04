package main.java.ulibs.engine.gl;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import main.java.ulibs.common.helpers.MathH;
import main.java.ulibs.common.math.Vec2f;
import main.java.ulibs.common.utils.Console;
import main.java.ulibs.common.utils.Console.WarningType;
import main.java.ulibs.engine.utils.GetResource;
import main.java.ulibs.gl.gl.Texture;

public class RandomTextureAtlas {
	private final List<Vec2f> coordinateList = new ArrayList<Vec2f>();
	private final int size;
	private final Texture texture;
	
	public RandomTextureAtlas(String name, int count, int cellSize, String folder) {
		this.size = MathH.ceil(Math.sqrt(count));
		Console.print(WarningType.TextureDebug, "Creating atlas using folder '" + folder + "' that is " + size + "x" + size);
		
		BufferedImage img = new BufferedImage(size * cellSize, size * cellSize, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = img.createGraphics();
		
		float size = this.size, s = 1f / size; //For future me, size needs to be a float cuz math
		for (int i = 0; i < count; i++) {
			coordinateList.add(new Vec2f((i % size) / size, -MathH.floor(i / size) / size - s));
			
			g.drawImage(getTexture(folder, name + "_" + i), (i % this.size) * cellSize, MathH.floor(i / this.size) * cellSize, cellSize, cellSize, null);
		}
		g.dispose();
		
		texture = new Texture(img);
	}
	
	/** Same as {@link Texture#bind()} */
	public void bind() {
		texture.bind();
	}
	
	/** @return The Width/Height of the atlas */
	public int size() {
		return size;
	}
	
	public float[] getRandomTextureCoords() {
		Vec2f texCoords = coordinateList.get(MathH.randomInt(coordinateList.size()));
		float s = 1f / size;
		
		return new float[] { texCoords.getX(), texCoords.getY() + s, texCoords.getX(), texCoords.getY(), texCoords.getX() + s, texCoords.getY(), texCoords.getX() + s,
				texCoords.getY() + s };
	}
	
	private static BufferedImage getTexture(String folder, String name) {
		BufferedImage i = GetResource.getTexture(folder, name);
		Console.print(WarningType.TextureDebug, (i == GetResource.NIL ? "Unable to " : "") + "register '" + name + "' for " + RandomTextureAtlas.class.getSimpleName() + "!");
		return i;
	}
}
