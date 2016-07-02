package com.rharel.pong.core;


import com.rharel.pong.geometry.Size;


/**
 * <p>
 * 		The Ball class represents the pong ball. It holds its own dimensions,
 * 		speed, and bouncing-angle properties.
 * </p>
 * <p>
 * 		The bouncing angle of the ball upon contact with a paddle depends on
 * 		the contact point's distance from the paddle's center. A collision
 * 	    downright the middle will send the ball back up in a pure vertical
 * 		direction, while a collision on the outermost edge will send it
 * 		bouncing in angle, determined by the maxBounceAngle field.
 * </p>
 * @author Raoul Harel
 */
public class Ball extends GameObject
{
	/**
	 * Creates a ball with given radius, speed, and bouncing angle.
	 * 
	 * @param radius
	 * @param speed
	 * @param maxBounceAngle In radians.
	 */
	public Ball(
		final float radius,
		final float speed,
		final float maxBounceAngle)
	{
		super(new Size(2 * radius, 2 * radius));
		this.radius = radius;
		this.speed = speed;
		this.maxBounceAngle = maxBounceAngle;
	}
	/**
	 * Creates a ball with given radius, speed, and default bouncing angle.
	 * 
	 * @param radius
	 * @param speed
	 */
	public Ball(
		final float radius,
		final float speed)
	{
		this(radius, speed, DEFAULT_MAX_BOUNCE_ANGLE);
	}
	public Ball(final Ball source)
	{
		super(source);
		this.radius = source.radius;
		this.speed = source.speed;
		this.maxBounceAngle = source.maxBounceAngle;
	}
	
	public final float radius;
	public final float speed;
	public final float maxBounceAngle;
	
	private static final float DEFAULT_MAX_BOUNCE_ANGLE =
		(float) Math.toRadians(50);
}
