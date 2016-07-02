package com.rharel.pong.test.core;


import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.rharel.pong.core.Paddle;
import com.rharel.pong.geometry.Size;


public class PaddleTest
{
	private static final float PRECISION = 0.001f;

	private final Size size = new Size(1, 2);
	private final float speed = 0.5f;
	private Paddle paddle;

	@Before
	public void setUp() throws Exception
	{
		paddle = new Paddle(size, speed);
	}

	@Test
	public void testAttributes()
	{
		assertEquals(size, paddle.size);
		assertEquals(speed, paddle.speed, PRECISION);
	}
	
	@Test
	public void testMovement()
	{
		paddle.moveLeft();
		assertEquals(paddle.velocity.x, -paddle.speed, PRECISION);
		
		paddle.moveRight();
		assertEquals(paddle.velocity.x, paddle.speed, PRECISION);
		
		paddle.dontMove();
		assertEquals(paddle.velocity.x, 0.0f, PRECISION);
	}
}
