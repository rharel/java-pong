package com.rharel.pong.geometry;


/**
 * 2D size descriptor.
 *
 * @author Raoul Harel
 */
public class Size
{
	public Size(
		final float width,
		final float height)
	{
		this.width = width;
		this.height = height;
	}
	public Size(final Size source)
	{
		this(source.width, source.height);
	}
	
	public final float width;
	public final float height;
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(height);
		result = prime * result + Float.floatToIntBits(width);
		return result;
	}
	@Override
	public boolean equals(final Object obj)
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
		final Size other = (Size) obj;
		if (Float.floatToIntBits(height) != Float.floatToIntBits(other.height))
		{
			return false;
		}
		if (Float.floatToIntBits(width) != Float.floatToIntBits(other.width))
		{
			return false;
		}
		return true;
	}
}
