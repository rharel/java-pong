package com.rharel.pong.ai;


import com.rharel.pong.core.Paddle;
import com.rharel.pong.core.Table;


/**
 * The tracker-AI simply tracks the ball's position at all times.
 * 
 * @author Raoul Harel
 */
public class TrackerAI extends ArtificialPlayer
{
	/**
	 * @see com.rharel.pong.ai.ArtificialPlayer#ArtificialPlayer(Position)
	 */
	public TrackerAI(final Position position)
	{
		this(position, DEFAULT_BRAKING_DISTANCE);
	}
	/**
	 * @see com.rharel.pong.ai.ArtificialPlayer#ArtificialPlayer(Position, float)
	 */
	public TrackerAI(
		final Position position,
		final float brakingDistance)
	{
		super(position, brakingDistance);
	}
	
	/**
	 * Moves paddle towards the current ball's position.
	 * 
	 * @see com.rharel.pong.core.Player#play(Paddle, Table)
	 */
	@Override
	public void play(
		final Paddle paddle,
		final Table table)
	{
		movePaddleTowards(paddle, table.ball.position.x);
	}
}
