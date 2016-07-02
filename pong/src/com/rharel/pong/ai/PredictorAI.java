package com.rharel.pong.ai;


import com.rharel.pong.core.Ball;
import com.rharel.pong.core.Paddle;
import com.rharel.pong.core.Player;
import com.rharel.pong.core.Table;
import com.rharel.pong.core.Table.Side;
import com.rharel.pong.geometry.Collision;
import com.rharel.pong.geometry.Vector2;


/**
 * The predictor-AI predicts the position of the ball the next time it
 * approaches the player and aims to be there first.
 * 
 * @author Raoul Harel
 */
public class PredictorAI extends ArtificialPlayer
{
	/**
	 * @see com.rharel.pong.ai.ArtificialPlayer#ArtificialPlayer(Position)
	 */
	public PredictorAI(final Position position)
	{
		this(position, DEFAULT_BRAKING_DISTANCE);
	}
	/**
	 * @see com.rharel.pong.ai.ArtificialPlayer#ArtificialPlayer(Position, float)
	 */
	public PredictorAI(
		final Position position,
		final float brakingDistance)
	{
		super(position, brakingDistance);
	}
	
	/**
	 * Moves paddle towards the next predicted contact point between the ball
	 * and the player's goal.
	 * 
	 * @see com.rharel.pong.core.Player#play(Paddle, Table)
	 */
	@Override
	public void play(
		final Paddle paddle,
		final Table table)
	{
		predictBallPosition(table);
		movePaddleTowards(paddle, predictedBallPosition);
	}
	
	protected float predictBallPosition(final Table table)
	{
		final Ball initialState = table.ball;
		
		final Side currentBallHeading =
			initialState.velocity.y < 0 ?
			Side.BOTTOM : Side.TOP;
		if (currentBallHeading == previousBallHeading)
		{
			predictionChanged = false;
			return predictedBallPosition;  // previous prediction still applies
		}
		
		final Player.Position playerPosition = this.position;
		final Table.Side goalSide =
			playerPosition == Player.Position.FIRST ?
			Table.Side.BOTTOM : Table.Side.TOP;
		
		final Ball stateAfter = Collision.computeNext(
			initialState, table.size, goalSide);
		
		/*
		 * 'stateAfter.position' now indicates where the ball would be if it
		 * were allowed to touch the goal wall (A). However, we are interested
		 * in its position right before: when there is enough space for the
		 * paddle to come between the ball and the wall (B).
		 * 
		 * See illustration below:
		 * A:    ___________
		 *          o  ####
		 * 
		 * B:
		 *       ___________
		 *         ####
		 *          o
		 * 
         * Therefore, we must trace back the ball's position just enough to
         * create that space for the paddle.
		 */
		final Vector2 position = stateAfter.position;
		final Vector2 direction = stateAfter.velocity.normalize();
		final Paddle paddle = table.getPaddle(playerPosition);
		final float t = paddle.size.height / Math.abs(direction.y);
		predictedBallPosition = position.x - direction.x * t;
		predictionChanged = true;
		previousBallHeading = currentBallHeading;
		
		return predictedBallPosition;
	}
	protected boolean getPredictionChanged()
	{
		return predictionChanged;
	}
	
	private Side previousBallHeading = null;
	private float predictedBallPosition;
	private boolean predictionChanged;
}
