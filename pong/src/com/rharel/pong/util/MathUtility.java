package com.rharel.pong.util;


/**
 * Math utility methods.
 * 
 * @author Raoul Harel
 */
public class MathUtility
{
	/**
	 * Limits a value's range to [min, max].
	 * 
	 * @param value
	 * @param min
	 * @param max
	 * @return Clamped value in [min, max].
	 */
	public static float clamp(
		final float value,
		final float min,
		final float max)
	{
		return value < min ? min :
			   value > max ? max :
			   value;
	}
}
