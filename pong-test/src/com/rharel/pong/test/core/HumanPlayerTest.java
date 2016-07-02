package com.rharel.pong.test.core;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import com.rharel.pong.core.HumanPlayer;
import com.rharel.pong.core.HumanPlayer.Command;
import com.rharel.pong.core.Paddle;
import com.rharel.pong.core.Player.Position;
import com.rharel.pong.core.Table;


public class HumanPlayerTest
{
	private final Table tableState = mock(Table.class);
	private Paddle paddle;
	private HumanPlayer player;
	
	@Before
	public void setUp() throws Exception
	{
		paddle = mock(Paddle.class);
		player = new HumanPlayer(Position.FIRST);
	}

	@Test
	public void testConstructor()
	{
		assertEquals(player.position, Position.FIRST);
		assertEquals(player.command, Command.MOVE_NONE);
	}
	
	@Test
	public void testMoveLeftCommand()
	{
		player.command = Command.MOVE_LEFT;
		player.play(paddle, tableState);
		
		verify(paddle, times(1)).moveLeft();
		verify(paddle, never()).moveRight();
	}
	@Test
	public void testMoveRightCommand()
	{
		player.command = Command.MOVE_RIGHT;
		player.play(paddle, tableState);
		
		verify(paddle, times(1)).moveRight();
		verify(paddle, never()).moveLeft();
	}
	@Test
	public void testMoveNowhereCommand()
	{
		player.command = Command.MOVE_NONE;
		player.play(paddle, tableState);
		
		verify(paddle, never()).moveLeft();
		verify(paddle, never()).moveRight();
	}
}
