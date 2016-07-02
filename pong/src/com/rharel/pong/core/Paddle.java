package com.rharel.pong.core;


import com.rharel.pong.geometry.Size;


/**
 * The Paddle class represents the player-controlled paddles.
 * It holds its own dimensions, position and maneuverability properties.
 * 
 * @author Raoul Harel
 */
public class Paddle extends GameObject
{
	/**
	 * Creates a new paddle with given size and speed.
	 * 
	 * @param size
	 * @param speed
	 */
	public Paddle(
		final Size size,
		final float speed)
	{
		super(size);
		this.speed = speed;
	}
	public Paddle(final Paddle source)
	{
		super(source);
		this.speed = source.speed;
	}
	
	/**
	 * Moves the paddle left at maximum speed.
	 */
	public void moveLeft()
	{
		moveLeft(speed);
	}
	/**
	 * Moves the paddle left at most a given distance.
	 * 
	 * @param maxDistance
	 */
	public void moveLeft(final float maxDistance)
	{
		velocity.x = -Math.min(maxDistance, speed);
	}
	/**
	 * Moves the paddle right at maximum speed.
	 */
	public void moveRight()
	{
		moveRight(speed);
	}
	/**
	 * Moves the paddle right at most a given distance.
	 * 
	 * @param maxDistance
	 */
	public void moveRight(final float maxDistance)
	{
		velocity.x = Math.min(maxDistance, speed);
	}
	/**
	 * Holds the paddle in place.
	 */
	public void dontMove()
	{
		velocity.x = 0;
	}
	
	public final float speed;
}
