package no.woact.marchr16.game.gamelogic;

// Created by Christian on 11/03/2018.

import java.util.Arrays;

import static no.woact.marchr16.game.gamelogic.Marks.*;

public class Board {

	private boolean mGameOver;
	private Player mWinningPlayer = null;
	private Marks[][] mTiles = new Marks[3][3];

	public Board() {
		/* DEFAULT STATE

		EMPTY | EMPTY | EMPTY
		---------------------
		EMPTY | EMPTY | EMPTY
		---------------------
		EMPTY | EMPTY | EMPTY

		*/

		resetGame();
	}

	public boolean isTileEmpty(int row, int col){

		if (getTile(row,col).equals(EMPTY)){
			return true;
		}

		return false;

	}

	public Marks getTile(int row, int col) {
		return mTiles[row][col];
	}

	public void setTile(int row, int col, Marks value) {
		this.mTiles[row][col] = value;
	}

	public Player getmWinningPlayer() {
		return mWinningPlayer;
	}

	public void setmWinningPlayer(Player mWinningPlayer) {
		this.mWinningPlayer = mWinningPlayer;
	}

	public boolean ismGameOver() {
		return mGameOver;
	}

	/**
	 * Resets all settings to default state and clears the board
	 */
	public void resetGame() {

		mGameOver = false;
		mWinningPlayer = null;

		for (Marks[] row : mTiles) {
			Arrays.fill(row, EMPTY);
		}
	}

	/**
	 * Places a players chosen Mark on a given tile on the board
	 *
	 * @param row    which row in mTiles[][] to use
	 * @param col    which column in mTiles[][] to use
	 * @param player instance of player class to use
	 * @return returns true if mark has been placed
	 */
	public boolean placeMark(int row, int col, Player player) {

		if (row > 2 || row < 0) throw new IllegalArgumentException();
		if (col > 2 || col < 0) throw new IllegalArgumentException();

		if (!mGameOver) {

			if (getTile(row, col) == (EMPTY)) {

				setTile(row, col, player.getChosenMark());

				evaluateGame(player);
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	/**
	 * Counts all the tiles in the board that is EMPTY
	 *
	 * @return number of EMPTY tiles
	 */
	public int getRemainingTilesLeft() {
		int counter = 0;

		for (Marks row[] : mTiles) {
			for (Marks tile : row) {
				if (tile.equals(EMPTY)) {
					counter++;
				}
			}
		}

		return counter;
	}

	/**
	 * Evaluates the game and check if the given player has a winning row of marks
	 * This method also checks if there is a DRAW (no EMPTY tiles left in mTiles)
	 *
	 * @param player instance of Player to use
	 */
	private boolean evaluateGame(Player player) {

		Marks mark = player.getChosenMark();

		// I know this is kind of ugly code, but it does the job ;)
		if ((mTiles[0][0].equals(mark) && mTiles[0][1].equals(mark) && mTiles[0][2].equals(mark)) ||
				(mTiles[1][0].equals(mark) && mTiles[1][1].equals(mark) && mTiles[1][2].equals(mark)) ||
				(mTiles[2][0].equals(mark) && mTiles[2][1].equals(mark) && mTiles[2][2].equals(mark)) ||
				(mTiles[0][0].equals(mark) && mTiles[1][0].equals(mark) && mTiles[2][0].equals(mark)) ||
				(mTiles[0][1].equals(mark) && mTiles[1][1].equals(mark) && mTiles[2][1].equals(mark)) ||
				(mTiles[0][2].equals(mark) && mTiles[1][2].equals(mark) && mTiles[2][2].equals(mark)) ||
				(mTiles[0][0].equals(mark) && mTiles[1][1].equals(mark) && mTiles[2][2].equals(mark)) ||
				(mTiles[0][2].equals(mark) && mTiles[1][1].equals(mark) && mTiles[2][0].equals(mark))) {

			mGameOver = true;
			setmWinningPlayer(player);
			return true;
		}

		if (getRemainingTilesLeft() == 0) {
			mGameOver = true;
			return true;
		}

		return false;
	}
}
