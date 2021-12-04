package main.java.ulibs.engine.render;

import main.java.ulibs.common.math.Vec2f;
import main.java.ulibs.common.math.Vec2i;
import main.java.ulibs.gl.gl.QuadData;
import main.java.ulibs.gl.gl.ZConstant;

public interface IRenderer {
	public void setupGL();
	
	public void renderPre();
	
	public void onResize();
	
	public default void renderPost() {
		
	}
	
	public default void renderAll() {
		renderPre();
		renderPost();
	}
	
	public default QuadData createLine(Vec2i start, Vec2i end, ZConstant z, float t) {
		return createLine(start.getX(), start.getY(), end.getX(), end.getY(), z, t);
	}
	
	public default QuadData createLine(Vec2f start, Vec2f end, ZConstant z, float t) {
		return createLine(start.getX(), start.getY(), end.getX(), end.getY(), z, t);
	}
	
	public default QuadData createLine(Vec2i start, Vec2f end, ZConstant z, float t) {
		return createLine(start.getX(), start.getY(), end.getX(), end.getY(), z, t);
	}
	
	public default QuadData createLine(Vec2f start, Vec2i end, ZConstant z, float t) {
		return createLine(start.getX(), start.getY(), end.getX(), end.getY(), z, t);
	}
	
	public default QuadData createLine(float startX, float startY, float endX, float endY, ZConstant z, float t) {
		float a1 = (float) Math.toRadians((Math.atan2(endY - startY, endX - startX) * 180 / Math.PI));
		float r45 = (float) (45f * Math.PI / 180f);
		
		float a2 = a1 - r45;
		float a3 = a1 + r45;
		
		float xx2 = (float) (endX - t * Math.cos(a2));
		float yy2 = (float) (endY - t * Math.sin(a2));
		float xx3 = (float) (endX - t * Math.cos(a3));
		float yy3 = (float) (endY - t * Math.sin(a3));
		float xx5 = (float) (startX + t * Math.cos(a2));
		float yy5 = (float) (startY + t * Math.sin(a2));
		float xx6 = (float) (startX + t * Math.cos(a3));
		float yy6 = (float) (startY + t * Math.sin(a3));
		
		float[] vertices = { endX, endY, z.z, xx2, yy2, z.z, xx3, yy3, z.z, startX, startY, z.z, xx5, yy5, z.z, xx6, yy6, z.z };
		int[] indices = { 0, 1, 2, 3, 4, 5, 1, 2, 4, 4, 5, 1 };
		return new QuadData().addAll(vertices, indices, QuadData.DEFAULT_TCS);
	}
}
