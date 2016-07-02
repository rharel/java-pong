package com.rharel.pong.input;


import com.rharel.pong.core.HumanPlayer;


public class KeyboardController
{
	public void pressLeft()
	{
		moveLeft = true;
		mostRecentCommand = HumanPlayer.Command.MOVE_LEFT;
	}
	public void releaseLeft()
	{
		moveLeft = false;
	}
	public void pressRight()
	{
		moveRight = true;
		mostRecentCommand = HumanPlayer.Command.MOVE_RIGHT;
	}
	public void releaseRight()
	{
		moveRight = false;
	}
	
	public HumanPlayer.Command getCommand()
	{
		if (!moveLeft && !moveRight)
		{
			return HumanPlayer.Command.MOVE_NONE;
		}
		else if (moveLeft && !moveRight)
		{
			return HumanPlayer.Command.MOVE_LEFT;
		}
		else if (!moveLeft && moveRight)
		{
			return HumanPlayer.Command.MOVE_RIGHT;
		}
		else
		{
			return mostRecentCommand;
		}
	}

	private boolean moveLeft = false;
	private boolean moveRight = false;
	private HumanPlayer.Command mostRecentCommand;
}
