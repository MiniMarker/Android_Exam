package no.woact.marchr16;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import no.woact.marchr16.game.gamelogic.Board;
import no.woact.marchr16.game.gamelogic.Marks;
import no.woact.marchr16.game.gamelogic.Player;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

// Created by Christian on 11/03/2018.
public class BoardTest {

	private Player player1;
	private Player player2;

	private Board board;

	@Rule public final ExpectedException exception = ExpectedException.none();

	@Before
	public void setUp(){
		player1 = new Player(Marks.X);
		player2 = new Player(Marks.O);

		board = new Board();
	}

	@After
	public void tearDown(){
		board.resetGame();
	}

	@Test
	public void testPlaceMarkOnBoard(){
		assertTrue(board.placeMark(0,0, player1));
	}

	@Test
	public void testPlaceMarkOnExistingMark(){
		board.placeMark(0,0,player1);

		assertFalse(board.placeMark(0,0,player2));
	}

	@Test
	public void testCalculateRemainingTiles(){
		board.placeMark(0,0, player1);
		board.placeMark(0,1, player1);
		board.placeMark(0,2, player1);

		assertTrue(board.getRemainingTilesLeft() == 6);
	}

	@Test
	public void testPlayerWinningGame(){
		board.placeMark(0,0, player1);
		board.placeMark(0,1, player1);
		board.placeMark(0,2, player1);

		assertTrue(board.ismGameOver());
	}

	@Test
	public void testPlayerTryToPlaceMarkAfterGameOver(){
		board.placeMark(0,0, player1);
		board.placeMark(0,1, player1);
		board.placeMark(0,2, player1);

		assertTrue(board.ismGameOver());

		assertFalse(board.placeMark(1,1, player1));
	}

	@Test
	public void testResettingBoard(){
		board.placeMark(0,0, player1);
		board.placeMark(0,1, player1);
		board.placeMark(0,2, player1);

		assertTrue(board.getTile(0,0).equals(player1.getChosenMark()));
		assertTrue(board.ismGameOver());

		board.resetGame();

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				assertTrue(board.getTile(i, j).equals(Marks.EMPTY));
			}
		}

		assertFalse(board.ismGameOver());
	}

	@Test
	public void testPlaceMarkOutsideBoard(){
		exception.expect(IllegalArgumentException.class);
		board.placeMark(5,1, player1);
	}

}
