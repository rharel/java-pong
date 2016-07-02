package com.rharel.pong.ai;


import com.rharel.pong.core.Paddle;
import com.rharel.pong.core.Player;


/**
 * Represents a player driven by artificial intelligence. Extend,
 * override play(), determine where the controlled paddle should move
 * to and invoke movePaddleTowards() with that position.
 * 
 * @author Raoul Harel
 */
public abstract class ArtificialPlayer extends Player
{
	/**
	 * @see com.rharel.pong.ai.Player#Player(Position)
	 */
	public ArtificialPlayer(final Position position)
	{
		this(position, DEFAULT_BRAKING_DISTANCE);
	}
	/**
	 * @see com.rharel.pong.ai.Player#Player(Position, float)
	 */
	public ArtificialPlayer(
		final Position position,
		final float brakingDistance)
	{
		super(position);
		this.brakingDistance = brakingDistance;
	}
	
	/**
	 * Moves paddles towards target horizontal position.
	 * @param paddle Paddle to control.
	 * @param target Horizontal (x) coordinate of target position.
	 */
	protected void movePaddleTowards(
		final Paddle paddle,
		final float target)
	{
		final float distance = Math.abs(paddle.position.x - target);
		
		if (distance < brakingDistance)
		{
			paddle.dontMove();
		}
		else if (paddle.position.x < target)
		{
			paddle.moveRight();
		}
		else // if (paddle.position.x > target)
		{
			paddle.moveLeft();
		}
	}
	
	protected static final float DEFAULT_BRAKING_DISTANCE = -1;
	protected final float brakingDistance;
}
