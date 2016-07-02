package com.rharel.pong.core;


import java.util.Random;

import com.rharel.pong.geometry.Collision;
import com.rharel.pong.geometry.Vector2;
import com.rharel.pong.util.MathUtility;
import com.rharel.pong.util.Pair;


/**
 * The Game class represents a pong game. It simulates a match between
 * two players on a pong table.
 * 
 * @author Raoul Harel
 */
public class Game
{
	/**
	 * Creates a new game simulation.
	 * 
	 * @param table
	 * @param players
	 */
	public Game(
		final Table table,
		final Pair<Player> players)
	{
		this.table = table;
		this.players = players;
		
		setup();
	}
	
	public final Table table;
	public final Pair<Player> players;
	
	/**
	 * Advances the simulation.
	 * @param dt In seconds.
	 * @return If the win condition was met in this simulation step,
	 * 		   return the winning player's position. Otherwise, returns null.
	 */
	public Player.Position advance(final float dt)
	{
		advancePaddles(dt);
		advanceBall(dt);
		
		final Player.Position winner = checkForWinners();
		if (winner != null)
		{
			score.increment(winner);
		}
		return winner;
	}
	/**
	 * Lets players make their moves based on the current table state.
	 */
	public void updatePlayers()
	{
		updatePlayer(players.first);
		updatePlayer(players.second);
	}
	
	public Pair<Integer> getScore()
	{
		return new Pair<Integer>(
			score.getFirst(),
			score.getSecond());
	}
	
	private class Score
	{
		public void increment(final Player.Position position)
		{
			if (position == Player.Position.FIRST)
			{
				++ firstPlayer;
			}
			else  // if (position == Player.Position.SECOND)
			{
				++ secondPlayer;
			}
		}
		
		public int getFirst() { return firstPlayer; }
		public int getSecond() { return secondPlayer; }
		
		private int firstPlayer = 0;
		private int secondPlayer = 0;
	}
	
	public void setup()
	{
		setupPaddles();
		setupBall();
	}
	private void setupPaddles()
	{
		final float half_width = 0.5f * table.size.width;
		
		final Paddle pad_a = table.paddles.first;
		pad_a.position.x = half_width;
		pad_a.position.y = 0.5f * pad_a.size.height;
		
		final Paddle pad_b = table.paddles.second;
		pad_b.position.x = half_width;
		pad_b.position.y = table.size.height - 0.5f * pad_b.size.height;

	}
	private void setupBall()
	{
		final float half_width = 0.5f * table.size.width;
		final float half_height = 0.5f * table.size.height;
		final Ball ball = table.ball;
		
		ball.position = new Vector2(half_width, half_height);
		ball.velocity = table.paddles.second.position.subtract(ball.position);
		ball.velocity.x *= 0.5f + random.nextFloat();
		ball.velocity = ball.velocity.normalize().multiply(ball.speed);
	}
	
	private void advancePaddles(final float dt)
	{
		advancePaddle(table.paddles.first, dt);
		advancePaddle(table.paddles.second, dt);
	}
	private void advancePaddle(
		final Paddle paddle,
		final float dt)
	{
		final float half_width = 0.5f * paddle.size.width;
		
		paddle.advance(dt);
		paddle.position.x = MathUtility.clamp(
			paddle.position.x,
			half_width,
			table.size.width - half_width);
	}
	private void advanceBall(final float dt)
	{
		table.ball.advance(dt);
		
		Collision.handle(
			table.ball,
			table.paddles.first);
		Collision.handle(
			table.ball,
			table.paddles.second);
		Collision.handle(table.ball, table);
		
		// Account for accumulated float-arithmetic imprecision
		table.ball.velocity = table.ball.velocity
			.normalize()
			.multiply(table.ball.speed);
	}

	private void updatePlayer(final Player player)
	{
		final Paddle paddle = table.getPaddle(player.position);
		player.play(paddle, new Table(table));
	}

	private Player.Position checkForWinners()
	{
		final Ball ball = table.ball;
		
		if (ball.position.y <= 0)
		{
			return Player.Position.SECOND;
		}
		else if (ball.position.y >= table.size.height)
		{
			return Player.Position.FIRST;
		}
		else
		{
			return null;
		}
	}
	
	private final Random random = new Random();
	private final Score score = new Score();
}
