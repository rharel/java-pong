package com.rharel.pong.test.geometry;


import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.rharel.pong.geometry.Size;


public class SizeTest
{
	private static final float PRECISION = 0.001f;

	private final float width = 1.0f;
	private final float height = 2.0f;
	private Size size;

	@Before
	public void setUp() throws Exception
	{
		size = new Size(width, height);
	}

	@Test
	public void testAttributes()
	{
		assertEquals(width, size.width, PRECISION);
		assertEquals(height, size.height, PRECISION);
	}
}
