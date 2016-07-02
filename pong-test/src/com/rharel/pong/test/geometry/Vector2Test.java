package com.rharel.pong.test.geometry;


import com.rharel.pong.geometry.Vector2;
import com.rharel.pong.util.MathUtility;

import static org.junit.Assert.*;

import org.junit.Test;


public class Vector2Test
{
	private static final float PRECISION = 0.001f;
	
	private final Vector2 a = new Vector2(2, 3);
	private final Vector2 b = new Vector2(4, 5);
	private final float scalar = 2.0f;
	
	@Test
	public void testAttributes()
	{
		assertEquals(2, a.x, PRECISION);
		assertEquals(3, a.y, PRECISION);
	}
	
	@Test
	public void testNegation()
	{
		Vector2 c = a.negate();
		
		assertEquals(c.x, -a.x, PRECISION);
		assertEquals(c.y, -a.y, PRECISION);
	}
	
	@Test
	public void testAddition()
	{
		Vector2 c = a.add(b);
		
		assertEquals(c.x, a.x + b.x, PRECISION);
		assertEquals(c.y, a.y + b.y, PRECISION);
	}
	@Test
	public void testSubtraction()
	{
		Vector2 c = a.subtract(b);
		
		assertEquals(c.x, a.x - b.x, PRECISION);
		assertEquals(c.y, a.y - b.y, PRECISION);
	}
	
	@Test
	public void testScalarMultiplication()
	{
		Vector2 c = a.multiply(scalar);
		
		assertEquals(c.x, a.x * scalar, PRECISION);
		assertEquals(c.y, a.y * scalar, PRECISION);
	}
	@Test
	public void testScalarDivision()
	{
		Vector2 c = a.divide(scalar);
		
		assertEquals(c.x, a.x / scalar, PRECISION);
		assertEquals(c.y, a.y / scalar, PRECISION);
	}
	
	@Test
	public void testDotProduct()
	{
		assertEquals(a.dot(b), a.x * b.x + a.y * b.y, PRECISION);
	}
	
	@Test
	public void testLengthSquared()
	{
		assertEquals(a.length2(), a.x * a.x + a.y * a.y, PRECISION);
	}
	
	@Test
	public void testNormalization()
	{
		Vector2 c = a.normalize();
		
		assertEquals(c.length2(), 1.0f, PRECISION);
	}
	
	@Test
	public void testValueClamping()
	{
		Vector2 c = a.clamp(
			new Vector2(0, 3),
			new Vector2(2, 4));
		
		assertEquals(c.x, MathUtility.clamp(a.x, 0, 2), PRECISION);
		assertEquals(c.y, MathUtility.clamp(a.y, 3, 4), PRECISION);
	}
}
