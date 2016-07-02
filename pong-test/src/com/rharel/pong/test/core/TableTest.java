package com.rharel.pong.test.core;


import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.rharel.pong.core.Ball;
import com.rharel.pong.core.Paddle;
import com.rharel.pong.core.Table;
import com.rharel.pong.geometry.Size;
import com.rharel.pong.util.Pair;


public class TableTest
{
	private static final float PRECISION = 0.001f;
	
	private final Pair<Paddle> paddles = new Pair<Paddle>(
		new Paddle(new Size(1, 2), 3),
		new Paddle(new Size(4, 5), 6));
	private final Ball ball = new Ball(1, 2);
	private final Size size = new Size(1, 2);
	private Table table;

	@Before
	public void setUp() throws Exception
	{
		table = new Table(size, paddles, ball);
	}

	@Test
	public void testAttributes()
	{
		assertEquals(size, table.size);
		assertEquals(
			paddles.first.size,
			table.paddles.first.size);
		assertEquals(
			paddles.first.speed,
			table.paddles.first.speed, PRECISION);
		assertEquals(
			paddles.second.size,
			table.paddles.second.size);
		assertEquals(
			paddles.second.speed,
			table.paddles.second.speed, PRECISION);
		assertEquals(ball.radius, table.ball.radius, PRECISION);
	}
}
