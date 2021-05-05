package main.java.ulibs.engine.utils;
import main.java.ulibs.common.math.Vec2f;
import main.java.ulibs.common.math.Vec2i;
import main.java.ulibs.common.math.Vec4f;

public class HitBox {
	public final float x, y, w, h;
	
	public HitBox(Vec4f vec) {
		this(vec.getX(), vec.getY(), vec.getZ(), vec.getW());
	}
	
	public HitBox(float x, float y, float w, float h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	public boolean intersects(HitBox box) {
		float tw = this.w, th = this.h, rw = box.w, rh = box.h;
		
		if (rw <= 0 || rh <= 0 || tw <= 0 || th <= 0) {
			return false;
		}
		
		float tx = this.x, ty = this.y, rx = box.x, ry = box.y;
		
		rw += rx;
		rh += ry;
		tw += tx;
		th += ty;
		
		return ((rw < rx || rw > tx) && (rh < ry || rh > ty) && (tw < tx || tw > rx) && (th < ty || th > ry));
	}
	
	public boolean intersectsPoint(Vec2i vec) {
		return intersectsPoint(vec.getX(), vec.getY());
	}
	
	public boolean intersectsPoint(int x, int y) {
		float tw = this.w, th = this.h, rw = 1, rh = 1;
		
		if (rw <= 0 || rh <= 0 || tw <= 0 || th <= 0) {
			return false;
		}
		
		float tx = this.x, ty = this.y;
		
		rw += x;
		rh += y;
		tw += tx;
		th += ty;
		
		return ((rw < x || rw > tx) && (rh < y || rh > ty) && (tw < tx || tw > x) && (th < ty || th > y));
	}
	
	public HitBox addXY(Vec2f vec) {
		return new HitBox(x + vec.getX(), y + vec.getY(), w, h);
	}
	
	public HitBox addXY(float x, float y) {
		return new HitBox(this.x + x, this.y + y, w, h);
	}
	
	public HitBox addX(float x) {
		return new HitBox(this.x + x, y, w, h);
	}
	
	public HitBox addY(float y) {
		return new HitBox(x, this.y + y, w, h);
	}
	
	public HitBox addW(float w) {
		return new HitBox(x, y, this.w + w, h);
	}
	
	public HitBox addH(float h) {
		return new HitBox(x, y, w, this.h + h);
	}
	
	@Override
	public String toString() {
		return "(" + x + ", " + y + ", " + w + ", " + h + ")";
	}
}
