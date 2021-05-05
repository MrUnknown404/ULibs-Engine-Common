package main.java.ulibs.engine.render;

import main.java.ulibs.common.math.Vec2f;
import main.java.ulibs.gl.gl.QuadData;
import main.java.ulibs.gl.gl.ZConstant;

public interface IRenderer {
	public void setupGL();
	public void renderPre();
	
	public default void renderPost() {
		
	}
	
	public default void renderAll() {
		renderPre();
		renderPost();
	}
	
	/**
	 * @param start Start pos
	 * @param end End pos
	 * @param z Z level
	 * @param t Line thickness
	 */
	public default QuadData createLine(Vec2f start, Vec2f end, ZConstant z, float t) {
		float x = start.getX(), x2 = end.getX(), y = start.getY(), y2 = end.getY();
		float tx = x > x2 ? t : -t, ty = y > y2 ? t : -t;
		
		float[] verts = new float[] { x, y - ty, z.z, x, y, z.z, x - tx, y, z.z, x2 + tx, y2, z.z, x2, y2, z.z, x2, y2 + ty, z.z };
		return new QuadData().addVerticesAndSafeIndices(verts, new int[] { 0, 1, 2, 3, 4, 5, 3, 0, 2, 3, 5, 2 });
	}
}
