package com.rharel.pong.core;


import com.rharel.pong.geometry.BoundingBox;
import com.rharel.pong.geometry.Size;
import com.rharel.pong.geometry.Vector2;


/**
 * Game objects are entities in the game world. These are the player-controlled
 * paddles and the ball. The responsibility of the GameObject class is to model
 * their physical properties such as size, position, and velocity.
 * 
 * @author Raoul Harel
 */
public abstract class GameObject
{
	/**
	 * Creates a new game object with given size, position, and velocity.
	 * 
	 * @param size
	 * @param position
	 * @param velocity
	 */
	public GameObject(
		final Size size,
		final Vector2 position,
		final Vector2 velocity)
	{
		this.size = new Size(size);
		this.position = new Vector2(position);
		this.velocity = new Vector2(velocity);
	}
	/**
	 * Creates a new game object with given size.
	 * @param size
	 */
	public GameObject(final Size size)
	{
		this(size, new Vector2(0, 0), new Vector2(0, 0));
	}
	public GameObject(final GameObject source)
	{
		this(source.size, source.position, source.velocity);
	}
	
	public Size size;
	public Vector2 position;
	public Vector2 velocity;
	
	/**
	 * Computes the position of this after given time step.
	 * @param dt In seconds.
	 */
	public void advance(final float dt)
	{
		position = position.add(velocity.multiply(dt));
	}
	
	public BoundingBox getBoundingBox()
	{
		return new BoundingBox(
			position.subtract(
				new Vector2(size.width, size.height)
				.multiply(0.5f)),
			size);
	}
}
