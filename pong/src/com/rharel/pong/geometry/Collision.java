package com.rharel.pong.geometry;


import com.rharel.pong.core.Ball;
import com.rharel.pong.core.Paddle;
import com.rharel.pong.core.Table;
import com.rharel.pong.core.Table.Side;


/**
 * Collection of collision-related methods.
 * 
 * @author Raoul Harel
 */
public class Collision
{
	private static final float EPSILON = 0.1f;
	
	/**
	 * Processes ball-paddle collisions. The ball bounces off in an angle
	 * dependent on the collision-point's distance from the paddle's center.
	 * 
	 * @param ball
	 * @param paddle
	 */
	public static void handle(
		final Ball ball,
		final Paddle paddle)
	{
		final BoundingBox box = paddle.getBoundingBox();
		
		if (detect(ball, box))
		{
			separate(ball, box);
			bounce(ball, box);
		}
	}
	/**
	 * Processes ball-table collisions. The ball's bounce direction is a
	 * reflection through the normal to the table's colliding edge.
	 * 
	 * @param ball
	 * @param table
	 */
	public static void handle(
		final Ball ball,
		final Table table)
	{
		final Vector2 p = ball.position;
		final Vector2 v = ball.velocity;
		final float radius = ball.radius;
		
		if (p.x - radius < 0)
		{
			p.x = radius;
			v.x *= -1;
		}
		else if (p.x + radius > table.size.width)
		{
			p.x = table.size.width - radius;
			v.x *= -1;
		}
	}
	
	/**
	 * Predicts ball's next collision with given table side.
	 * 
	 * @param initialState Initial ball state.
	 * @param tableSize
	 * @param targetSide
	 * @return Ball state right after the collision.
	 */
	public static Ball computeNext(
		final Ball initialState,
		final Size tableSize,
		final Table.Side targetSide)
	{
		Ball stateBefore = new Ball(initialState);
		Ball stateAfter;
		Side collisionSide = null;
		do
		{
			stateAfter = computeNext(stateBefore, tableSize);
			if (stateAfter.velocity.x != stateBefore.velocity.x)
			{
				collisionSide =
					stateBefore.velocity.x < 0 ?
					Side.LEFT : Side.RIGHT;
			}
			else  // if (stateAfter.velocity.y != stateBefore.velocity.y)
			{
				collisionSide =
					stateBefore.velocity.y < 0 ?
					Side.BOTTOM : Side.TOP;
			}
			stateBefore = stateAfter;
		}
		while (collisionSide != targetSide);
		
		return stateAfter;
	}
	/**
	 * Predicts ball's state after its next collision with the table.
	 * 
	 * @param stateBefore Current ball state.
	 * @param tableSize
	 * @return Ball state right after the collision.
	 */
	private static Ball computeNext(
		final Ball stateBefore,
		final Size tableSize)
	{
		final Vector2 position = stateBefore.position;
		final Vector2 direction = stateBefore.velocity.normalize();
		final float radius = stateBefore.radius;
		
		final float dx =
			direction.x > 0 ?
			tableSize.width - radius - position.x :
		    -position.x + radius;
		final float dy =
			direction.y > 0 ?
			tableSize.height - radius - position.y :
			-position.y + radius;
		
		final float tx =
			direction.x != 0 ?
			dx / direction.x : Float.POSITIVE_INFINITY;
		final float ty =
			direction.y != 0 ?
			dy / direction.y : Float.POSITIVE_INFINITY;
		
		final Ball stateAfter = new Ball(stateBefore);
		if (tx < ty)
		{
			stateAfter.position.x += dx;
			stateAfter.position.y += tx * direction.y;
			
			stateAfter.velocity.x *= -1;
		}
		else
		{
			stateAfter.position.x += ty * direction.x;
			stateAfter.position.y += dy;
			
			stateAfter.velocity.y *= -1;
		}
		return stateAfter;
	}
	
	private static boolean detect(
		final Ball ball,
		final BoundingBox box)
	{
		// Point on the edge of the box (or inside), nearest to ball
		final Vector2 contact = ball.position.clamp(
			new Vector2(box.getLeft(), box.getBottom()),
			new Vector2(box.getRight(), box.getTop()));
		
		final float distanceSquared = ball.position.subtract(contact).length2();
		return distanceSquared < (ball.radius * ball.radius);
	}
	private static void separate(
		final Ball ball,
		final BoundingBox box)
	{
		// Point on the edge of the box (or inside), nearest to ball
		ball.position.clamp(
			new Vector2(box.getLeft(), box.getBottom()),
			new Vector2(box.getRight(), box.getTop()));
		
		final Vector2 direction = ball.velocity.normalize();
		
		final float targetY =
			direction.y < 0 ?
			box.getTop() + ball.radius + EPSILON :
			box.getBottom() - ball.radius - EPSILON;

		direction.y *= -1;
		final float t = Math.abs((ball.position.y - targetY) / direction.y);
		ball.position = ball.position.add(direction.multiply(t));
	}
	private static void bounce(
		final Ball ball,
		final BoundingBox box)
	{
		final float distance = ball.position.x - box.getCenter().x;
		final float normalizedDistance = distance / (0.5f * box.size.width);
		final float bounceAngle = (float) (
			0.5f * Math.PI -
			normalizedDistance * ball.maxBounceAngle);
		
		ball.velocity.x = (float) Math.cos(bounceAngle);
		ball.velocity.y = (float) (
			-Math.signum(ball.velocity.y) *
			 Math.sin(bounceAngle));
	}
}
