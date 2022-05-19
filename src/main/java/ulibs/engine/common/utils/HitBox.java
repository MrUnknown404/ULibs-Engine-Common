package main.java.ulibs.engine.common.utils;

import main.java.ulibs.common.math.Vec2f;
import main.java.ulibs.common.math.Vec2i;
import main.java.ulibs.common.math.Vec4f;
import main.java.ulibs.common.utils.ICopyable;

/** Simple class for checking if areas intersect. <br> Note that W/H are NOT coordinates but instead just a width & height.
 * @author -Unknown-
 */
public class HitBox implements ICopyable<HitBox> {
	/** Coordinate values used for math */
	@SuppressWarnings("javadoc") //Bugs Eclipse out? shows  Y/W/H as undocumented?
	protected float x, y, w, h;
	
	/** Creates a new HitBox with the given values
	 * @param vec Vec4f to grab coordinates from
	 */
	public HitBox(Vec4f vec) {
		this(vec.getX(), vec.getY(), vec.getZ(), vec.getW());
	}
	
	/** Creates a new HitBox with the given values
	 * @param pos X/Y coordinate
	 * @param size Width/Height
	 */
	public HitBox(Vec2f pos, Vec2f size) {
		this.x = pos.getX();
		this.y = pos.getY();
		this.w = size.getX();
		this.h = size.getY();
	}
	
	/** Creates a new HitBox with the given values
	 * @param pos X/Y coordinate
	 * @param size Width/Height
	 */
	public HitBox(Vec2i pos, Vec2i size) {
		this.x = pos.getX();
		this.y = pos.getY();
		this.w = size.getX();
		this.h = size.getY();
	}
	
	/** Creates a new HitBox with the given values
	 * @param pos X/Y coordinate
	 * @param size Width/Height
	 */
	public HitBox(Vec2i pos, Vec2f size) {
		this.x = pos.getX();
		this.y = pos.getY();
		this.w = size.getX();
		this.h = size.getY();
	}
	
	/** Creates a new HitBox with the given values
	 * @param pos X/Y coordinate
	 * @param size Width/Height
	 */
	public HitBox(Vec2f pos, Vec2i size) {
		this.x = pos.getX();
		this.y = pos.getY();
		this.w = size.getX();
		this.h = size.getY();
	}
	
	/** Creates a new HitBox with the given values
	 * @param x X coordinate
	 * @param y Y coordinate
	 * @param w Width
	 * @param h Height
	 */
	public HitBox(float x, float y, float w, float h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	/**@param box The HitBox to check for intersection
	 * @return True if the given HitBox intersects with self
	 */
	public boolean intersects(HitBox box) {
		return intersectMath(box.x, box.y, box.w, box.h);
	}
	
	/**@param vec The Vec2i to check for intersection
	 * @return True if the given position intersects with self
	 */
	public boolean intersectsPoint(Vec2i vec) {
		return intersectsPoint(vec.getX(), vec.getY());
	}
	
	/**@param vec The Vec2i to check for intersection
	 * @return True if the given position intersects with self
	 */
	public boolean intersectsPoint(Vec2f vec) {
		return intersectsPoint(vec.getX(), vec.getY());
	}
	
	/**@param x The X coordinate to check for intersection
	 * @param y The Y coordinate to check for intersection
	 * @return True if the given position intersects with self
	 */
	public boolean intersectsPoint(int x, int y) {
		return intersectMath(x, y, 1, 1);
	}
	
	/**@param x The X coordinate to check for intersection
	 * @param y The Y coordinate to check for intersection
	 * @return True if the given position intersects with self
	 */
	public boolean intersectsPoint(float x, float y) {
		return intersectMath(x, y, 1, 1);
	}
	
	private boolean intersectMath(float x, float y, float w, float h) {
		float tw = this.w, th = this.h, rw = w, rh = h;
		
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
	
	/**@param vec new X/Y coordinates to add to the pre-existing X/Y
	 * @return Self with new values
	 */
	public HitBox addXY(Vec2f vec) {
		x += vec.getX();
		y += vec.getY();
		return this;
	}
	
	/**@param x new X coordinate to add to the pre-existing X
	 * @param y new Y coordinate to add to the pre-existing Y
	 * @return Self with new values
	 */
	public HitBox addXY(float x, float y) {
		this.x += x;
		this.y += y;
		return this;
	}
	
	/**@param x new X coordinate to add to the pre-existing X
	 * @return Self with new values
	 */
	public HitBox addX(float x) {
		this.x += x;
		return this;
	}
	
	/**@param y new Y coordinate to add to the pre-existing Y
	 * @return Self with new values
	 */
	public HitBox addY(float y) {
		this.y += y;
		return this;
	}
	
	/**@param vec new width/height to add to the pre-existing width/height
	 * @return Self with new values
	 */
	public HitBox addWH(Vec2f vec) {
		w += vec.getX();
		h += vec.getY();
		return this;
	}
	
	/**@param w new width to add to the pre-existing width
	 * @param h new height to add to the pre-existing height
	 * @return Self with new values
	 */
	public HitBox addWH(float w, float h) {
		this.w += w;
		this.h += h;
		return this;
	}
	
	/**@param w new width to add to the pre-existing width
	 * @return Self with new values
	 */
	public HitBox addW(float w) {
		this.w += w;
		return this;
	}
	
	/**@param h new height to add to the pre-existing height
	 * @return Self with new values
	 */
	public HitBox addH(float h) {
		this.h += h;
		return this;
	}
	
	@Override
	public HitBox copy() {
		return new HitBox(x, y, w, h);
	}
	
	@Override
	public String toString() {
		return "(" + x + ", " + y + ", " + w + ", " + h + ")";
	}
}
