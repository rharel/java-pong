package com.rharel.pong.core;


/**
 * Players survey the table state and control one of the two paddles.
 * 
 * @author Raoul Harel
 */
public abstract class Player
{
	public enum Position { FIRST, SECOND }
	
	/**
	 * Creates a new player for given position.
	 * 
	 * @param position
	 */
	public Player(final Position position)
	{
		this.position = position;
	}
	
	public Position position = Position.FIRST;
	
	/**
	 * Controls the paddle based on current table state. Override in child
	 * class.
	 * 
	 * @param paddle Paddle to control.
	 * @param table Current table state.
	 */
	public abstract void play(
		final Paddle paddle,
		final Table table);
}
