package com.rharel.pong.core;


import com.rharel.pong.geometry.Size;
import com.rharel.pong.util.Pair;


/**
 * The Table class represents the virtual table on which pong is being played.
 * It holds its own dimensions and references to two paddles and a ball.
 * 
 * @author Raoul Harel
 */
public class Table
{
	public enum Side { LEFT, RIGHT, TOP, BOTTOM }
	
	/**
	 * Creates a new table.
	 * 
	 * @param size
	 * @param paddles
	 * @param ball
	 */
	public Table(
		final Size size,
		final Pair<Paddle> paddles,
		final Ball ball)
	{
		this.size = new Size(size);
		this.paddles = new Pair<Paddle>(
			new Paddle(paddles.first),
			new Paddle(paddles.second));
		this.ball = new Ball(ball);
	}
	public Table(final Table source)
	{
		this(source.size, source.paddles, source.ball);
	}
	
	public final Size size;
	public final Pair<Paddle> paddles;
	public final Ball ball;
	
	public Paddle getPaddle(final Player.Position position)
	{
		return
			position == Player.Position.FIRST ?
			paddles.first : paddles.second;
	}
	public Paddle getOtherPaddle(final Player.Position position)
	{
		return
			position == Player.Position.FIRST ?
			paddles.second : paddles.first;
	}
}
