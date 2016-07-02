package com.rharel.pong.geometry;

import com.rharel.pong.util.MathUtility;


/**
 * Simple 2D vector class.
 * 
 * @author Raoul Harel
 */
public class Vector2
{
	public static final Vector2 X = new Vector2(1, 0);
	public static final Vector2 Y = new Vector2(0, 1);
	
	public Vector2(
		final float x,
		final float y)
	{
		this.x = x;
		this.y = y;
	}
	public Vector2(final Vector2 source)
	{
		this(source.x, source.y);
	}
	
	public float x;
	public float y;

	/**
	 * Performs component-wise negation.
	 * @return -this
	 */
	public Vector2 negate()
	{
		return new Vector2(-x, -y);
	}
	
	/**
	 * Performs component-wise addition.
	 * @param other
	 * @return this + other
	 */
	public Vector2 add(final Vector2 other)
	{
		return new Vector2(
			this.x + other.x,
			this.y + other.y);
	}
	/**
	 * Performs component-wise subtraction.
	 * @param other
	 * @return this - other
	 */
	public Vector2 subtract(final Vector2 other)
	{
		return add(other.negate());
	}
	
	/**
	 * Performs uniform scaling.
	 * @param scalar
	 * @return this * scalar
	 */
	public Vector2 multiply(final float scalar)
	{
		return new Vector2(scalar * x, scalar * y);
	}
	/**
	 * Performs uniform division.
	 * @param scalar
	 * @return this / scalar
	 */
	public Vector2 divide(final float scalar)
	{
		if (scalar == 0.0f)
		{
			throw new IllegalArgumentException("Division by 0");
		}
		return multiply(1.0f / scalar);
	}
	
	/**
	 * Performs dot product.
	 * @param other
	 * @return this . other
	 */
	public float dot(final Vector2 other)
	{
		return (this.x * other.x) + (this.y * other.y);
	}
	
	/**
	 * Computes length squared.
	 */
	public float length2()
	{
		return this.dot(this);
	}
	/**
	 * Computes length.
	 */
	public float length()
	{
		return (float) Math.sqrt(length2());
	}
	
	/**
	 * Performs normalization.
	 * @return Vector of unit length pointing in the same direction as this.
	 */
	public Vector2 normalize()
	{
		return this.divide(length());
	}
	
	/**
	 * Performs value clamping to given range.
	 * @param min
	 * @param max
	 * @return this clamped to then range [min, max]
	 */
	public Vector2 clamp(
		final Vector2 min,
		final Vector2 max)
	{
		return new Vector2(
			MathUtility.clamp(x, min.x, max.x),
			MathUtility.clamp(y, min.y, max.y));
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(x);
		result = prime * result + Float.floatToIntBits(y);
		return result;
	}
	@Override
	public boolean equals(
		final Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		final Vector2 other = (Vector2) obj;
		if (Float.floatToIntBits(x) != Float.floatToIntBits(other.x))
		{
			return false;
		}
		if (Float.floatToIntBits(y) != Float.floatToIntBits(other.y))
		{
			return false;
		}
		return true;
	}
	@Override
	public String toString()
	{
		return "Vector2 [x=" + x + ", y=" + y + "]";
	}
}
