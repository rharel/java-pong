package com.rharel.pong.core;


/**
 * A human player is manually controlled through explicit commands.
 * 
 * @author Raoul Harel
 */
public class HumanPlayer extends Player
{
	public enum Command
	{
		MOVE_LEFT,
		MOVE_RIGHT,
		MOVE_NONE
	}
	
	/**
	 * @see com.rharel.pong.ai.Player#Player(Position)
	 */
	public HumanPlayer(final Position position)
	{
		super(position);
	}
	
	public Command command = Command.MOVE_NONE;
	
	/**
	 * Moves paddle according to the currently issued command.
	 * 
	 * @see com.rharel.pong.core.Player#play(Paddle, Table)
	 */
	@Override
	public void play(
		final Paddle paddle,
		final Table tableState)
	{
		if (this.command == Command.MOVE_LEFT)
		{
			paddle.moveLeft();
		}
		else if (this.command == Command.MOVE_RIGHT)
		{
			paddle.moveRight();
		}
		else
		{
			paddle.dontMove();
		}
	}
}
