package com.rharel.pong.ai;


import com.rharel.pong.core.Ball;
import com.rharel.pong.core.Paddle;
import com.rharel.pong.core.Player;
import com.rharel.pong.core.Table;
import com.rharel.pong.geometry.Vector2;
import com.rharel.pong.util.MathUtility;


/**
 * The aggressive-AI predicts the position of the ball the next time it
 * approaches the player and aims to position itself such that the ball
 * will bounce as further away from the opposing players as possible.
 * 
 * @author Raoul Harel
 */
public class AggressiveAI extends PredictorAI
{
	/**
	 * @see com.rharel.pong.ai.ArtificialPlayer#ArtificialPlayer(Position)
	 */
	public AggressiveAI(final Position position)
	{
		this(position, DEFAULT_BRAKING_DISTANCE);
	}
	/**
	 * @see com.rharel.pong.ai.ArtificialPlayer#ArtificialPlayer(Position, float)
	 */
	public AggressiveAI(
		final Position position,
		final float brakingDistance)
	{
		this(position, brakingDistance, AVERAGE_HUMAN_RESPONSE_TIME);
	}
	public AggressiveAI(
		final Position position,
		final float brakingDistance,
		final float opponentResponseTime)
	{
		super(position, brakingDistance);
		this.opponentResponseTime = opponentResponseTime;
	}
	
	/**
	 * Moves paddle towards the next predicted contact point between the ball
	 * and the player's goal, with an offset that will send the ball to a
	 * difficult spot for the opposing player to reach in time.
	 * 
	 * @see com.rharel.pong.core.Player#play(Paddle, Table)
	 */
	@Override
	public void play(
		final Paddle paddle,
		final Table table)
	{
		computeDesiredPaddlePosition(table);
		movePaddleTowards(paddle, desiredPaddlePosition);
	}
	
	private float computeDesiredPaddlePosition(final Table table)
	{
		final Paddle opponent = table.getOtherPaddle(this.position);
		final float predictedBallPosition = predictBallPosition(table);
		
		// lead the opponent a bit
		final float opponentPosition =
			opponent.position.x + opponent.velocity.x * opponentResponseTime;
		
		if (!getPredictionChanged() &&
			opponentPosition == previousOpponentPosition)
		{
			return desiredPaddlePosition;
		}
		
		final Ball ball = table.ball;
		final float desiredBallPosition =
			opponentPosition < 0.5f * table.size.width ?
			ball.radius :
			table.size.width - ball.radius;
		
		serveDirectly(table, predictedBallPosition, desiredBallPosition);
		previousOpponentPosition = opponentPosition;
		
		return desiredPaddlePosition;
	}
	private boolean serveDirectly(
		final Table table,
		final float sourceX,
		final float targetX)
	{
		final Paddle self = table.getPaddle(this.position);
		final Ball ball = table.ball;

		final float r = ball.radius;
		final float hT = table.size.height;
		final float hS = self.size.height;
		final Vector2 source, target;
		if (this.position == Player.Position.FIRST)
		{
			source = new Vector2(sourceX, hS + r);
			target = new Vector2(targetX, hT - r);
		}
		else
		{
			source = new Vector2(sourceX, hT - hS - r);
			target = new Vector2(targetX, r);
		}
		
		final Vector2 direction = target.subtract(source).normalize();
		final float bounceAngle = (float) (
			0.5f * Math.PI - Math.acos(direction.dot(Vector2.X)));
		final float normalizedDistance = bounceAngle / ball.maxBounceAngle;
		final float clampedNormalizedDistance =
			MathUtility.clamp(normalizedDistance, -1, 1);
		final float offset =
			clampedNormalizedDistance * 0.5f * self.size.width;
		desiredPaddlePosition = sourceX + offset;
		
		final boolean isFeasible =
			Math.abs(normalizedDistance) < 1 &&
		    0 < desiredPaddlePosition &&
		    desiredPaddlePosition < table.size.width;

		desiredPaddlePosition = MathUtility.clamp(
			desiredPaddlePosition, 0, table.size.width);
		
		return isFeasible;
	}
	
	private static final float AVERAGE_HUMAN_RESPONSE_TIME = 0.25f;
	
	private final float opponentResponseTime;
	private float desiredPaddlePosition;
	private float previousOpponentPosition = -1;
}
