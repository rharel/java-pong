package com.rharel.pong.test.util;


import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.rharel.pong.util.Pair;


public class PairTest
{
	private final Integer first = 1;
	private final Integer second = 2;

	private Pair<Integer> pair;

	@Before
	public void setUp() throws Exception
	{
		pair = new Pair<Integer>(first, second);
	}

	@Test
	public void testAttributes()
	{
		assertEquals(first, pair.first);
		assertEquals(second, pair.second);
	}
}
