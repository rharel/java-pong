package com.rharel.pong.test.core;


import com.rharel.pong.core.Ball;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class BallTest
{
	private static final float PRECISION = 0.001f;

	private final float radius = 1.0f;
	private final float speed = 0.5f;
	private Ball ball;

	@Before
	public void setUp() throws Exception
	{
		ball = new Ball(radius, speed);
	}

	@Test
	public void testAttributes()
	{
		assertEquals(radius, ball.radius, PRECISION);
		assertEquals(speed, ball.speed, PRECISION);
	}
}
