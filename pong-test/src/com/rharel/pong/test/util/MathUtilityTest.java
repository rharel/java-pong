package com.rharel.pong.test.util;


import com.rharel.pong.util.MathUtility;

import static org.junit.Assert.*;

import org.junit.Test;


public class MathUtilityTest
{
	private static final float PRECISION = 0.001f;
	
	@Test
	public void testClamp()
	{
		assertEquals(1, MathUtility.clamp(0.0f, 1.0f, 3.0f), PRECISION);
		assertEquals(2, MathUtility.clamp(2.0f, 1.0f, 3.0f), PRECISION);
		assertEquals(3, MathUtility.clamp(4.0f, 1.0f, 3.0f), PRECISION);
	}
}
