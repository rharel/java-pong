package com.rharel.pong.geometry;


/**
 * Axis-aligned bounding box.
 * 
 * @author Raoul Harel
 */
public class BoundingBox
{
	/**
	 * Creates new box from position and dimensions.
	 * 
	 * @param position Bottom-left.
	 * @param size
	 */
	public BoundingBox(
		final Vector2 position,
		final Size size)
	{
		this.bottomLeft = new Vector2(position);
		this.size = new Size(size);
	}
	/**
	 * Creates new box from a position and dimensions.
	 * 
	 * @param x Left.
	 * @param y Bottom.
	 * @param width
	 * @param height
	 */
	public BoundingBox(
		final float x,
		final float y,
		final float width,
		final float height)
	{
		this.bottomLeft = new Vector2(x, y);
        this.size = new Size(width, height);
	}
	public BoundingBox(final BoundingBox source)
	{
		this(source.bottomLeft, source.size);
	}
	
	public final Vector2 bottomLeft;
	public final Size size;
	
	public float getLeft()
	{
		return bottomLeft.x;
	}
	public float getRight()
	{
		return bottomLeft.x + size.width;
	}
	public float getBottom()
	{
		return bottomLeft.y;
	}
	public float getTop()
	{
		return bottomLeft.y + size.height;
	}
	
	public Vector2 getCenter()
	{
		return new Vector2(
			getLeft() + getRight(), getBottom() + getTop())
			.multiply(0.5f);
	}
}
