package no.woact.marchr16.game.gamelogic;

import no.woact.marchr16.game.BoardFragment;

import static no.woact.marchr16.game.gamelogic.Marks.EMPTY;

// Created by Christian on 26/04/2018.
public class AiHard {

	private boolean markPlaced;

	/**
	 * Iterates the given board to check if there is at threatening move from player1 on the given board
	 *
	 * @param boardFragment to get the methods in the GUI class
	 * @param mPlayer1 player 1
	 * @param mPlayer2 player 2
	 * @param mBoard the board which is used in the game
	 */
	public AiHard(BoardFragment boardFragment, Player mPlayer1, Player mPlayer2, Board mBoard) {

		markPlaced = false;

		markPlaced = checkRows(boardFragment, mPlayer1, mPlayer2, mBoard);

		if (!markPlaced)
			markPlaced = checkCols(boardFragment, mPlayer1, mPlayer2, mBoard);

		if (!markPlaced)
			markPlaced = checkDiag1(boardFragment, mPlayer1, mPlayer2, mBoard);

		if (!markPlaced)
			markPlaced = checkDiag2(boardFragment, mPlayer1, mPlayer2, mBoard);

		if (!markPlaced) {
			boardFragment.placeRandom();
		}
	}

	/**
	 * Iterates trough all the rows in on the board
	 *
	 * @return true if there was placed a mark on the board
	 */
	private boolean checkRows(BoardFragment boardFragment, Player mPlayer1, Player mPlayer2, Board mBoard) {

		for (int row = 0; row <= 2; row++) {

			//to avoid IndexOutOfBoundsException i only check the state of col 0 and 1
			for (int col = 0; col <= 1; col++) {

				// X |  |
				if (mBoard.getTile(row, col).equals(mPlayer1.getChosenMark())) {

					if (mBoard.getTile(row, (col + 1)).equals(mPlayer1.getChosenMark())) {

						// X | X | O
						if (col == 0 && mBoard.getTile(row, (col + 2)).equals(EMPTY)) {
							mBoard.placeMark(row, (col + 2), mPlayer2);
							boardFragment.placeCircleOnBoard(row, (col + 2));

							boardFragment.endPlayer2Turn();

							return true;

							// O | X | X
						} else if (!markPlaced && col > 0 && mBoard.getTile(row, (col - 1)).equals(EMPTY)) {
							mBoard.placeMark(row, (col - 1), mPlayer2);
							boardFragment.placeCircleOnBoard(row, (col - 1));

							boardFragment.endPlayer2Turn();

							return true;

						} else {
							return false;
						}

					}

					// X | O | X
					if (col == 0) {
						if (mBoard.getTile(row, (col + 2)).equals(mPlayer1.getChosenMark()) && mBoard.getTile(row, (col + 1)).equals(EMPTY)) {
							mBoard.placeMark(row, (col + 1), mPlayer2);
							boardFragment.placeCircleOnBoard(row, (col + 1));

							boardFragment.endPlayer2Turn();

							return true;
						}
					}
				}
			}
		}
		return false;
	}

	/**
	 * Iterates trough all the columns in on the board
	 *
	 * @return true if there was placed a mark on the board
	 */
	private boolean checkCols(BoardFragment boardFragment, Player mPlayer1, Player mPlayer2, Board mBoard) {

		for (int col = 0; col <= 2; col++) {

			//to avoid IndexOutOfBoundsException i only check the state of row 0 and 1
			for (int row = 0; row <= 1; row++) {

				// X
				// --
				//
				// --
				//
				if (mBoard.getTile(row, col).equals(mPlayer1.getChosenMark())) {

					if (mBoard.getTile((row + 1), col).equals(mPlayer1.getChosenMark())) {

						// X
						// --
						// X
						// --
						// O
						if (row == 0 && mBoard.getTile((row + 2), col).equals(EMPTY)) {
							mBoard.placeMark((row + 2), col, mPlayer2);
							boardFragment.placeCircleOnBoard((row + 2), col);

							boardFragment.endPlayer2Turn();

							return true;

							// O
							// --
							// X
							// --
							// X
						} else if (!markPlaced && row > 0 && mBoard.getTile((row - 1), col).equals(EMPTY)) {
							mBoard.placeMark((row - 1), col, mPlayer2);
							boardFragment.placeCircleOnBoard((row - 1), col);

							boardFragment.endPlayer2Turn();

							return true;

						} else {
							return false;
						}
					}

					// X
					// --
					// O
					// --
					// X
					if (row == 0) {
						if (mBoard.getTile((row + 2), col).equals(mPlayer1.getChosenMark()) && mBoard.getTile((row + 1), col).equals(EMPTY)) {

							mBoard.placeMark((row + 1), col, mPlayer2);
							boardFragment.placeCircleOnBoard((row + 1), col);

							boardFragment.endPlayer2Turn();

							return true;
						}
					}
				}
			}
		}
		return false;
	}

	private boolean checkDiag1(BoardFragment boardFragment, Player mPlayer1, Player mPlayer2, Board mBoard) {

		//to avoid IndexOutOfBoundsException i only check the state of row 0 and 1
		for (int row = 0; row <= 1; row++) {

			//to avoid IndexOutOfBoundsException i only check the state of col 0 and 1
			for (int col = 0; col <= 1; col++) {

				// X |  |
				// --------
				//   |  |
				// --------
				//   |  |
				if (mBoard.getTile(row, col).equals(mPlayer1.getChosenMark())) {


					if (mBoard.getTile((row + 1), (col + 1)).equals(mPlayer1.getChosenMark())) {

						// X |   |
						// --------
						//   | X |
						// --------
						//   |   | O
						if (row == 0 && col == 0 && mBoard.getTile((row + 2), (col + 2)).equals(EMPTY)) {
							mBoard.placeMark((row + 2), (col + 2), mPlayer2);
							boardFragment.placeCircleOnBoard((row + 2), (col + 2));

							boardFragment.endPlayer2Turn();

							return true;

							// O |   |
							// --------
							//   | X |
							// --------
							//   |   | X
						} else if (row == 1 && col == 1 && mBoard.getTile((row - 1), (col - 1)).equals(EMPTY)){
							mBoard.placeMark((row - 1), (col - 1), mPlayer2);
							boardFragment.placeCircleOnBoard((row - 1), (col - 1));

							boardFragment.endPlayer2Turn();

							return true;
						} else {
							return false;
						}
					}

					// X |   |
					// --------
					//   | O |
					// --------
					//   |   | X
					if (row == 0 && col == 0){

						if (mBoard.getTile((row + 2), (col + 2)).equals(mPlayer1.getChosenMark()) && mBoard.getTile((row + 1), (col + 1)).equals(EMPTY)){

							mBoard.placeMark((row + 1), (col + 1), mPlayer2);
							boardFragment.placeCircleOnBoard((row + 1), (col + 1));

							boardFragment.endPlayer2Turn();

							return true;
						}
					}
				}
			}
		}
		return false;
	}

	private boolean checkDiag2(BoardFragment boardFragment, Player mPlayer1, Player mPlayer2, Board mBoard) {

		//to avoid IndexOutOfBoundsException i only check the state of row 0 and 1
		for (int row = 0; row <= 1; row++) {

			//to avoid IndexOutOfBoundsException i only check the state of col 1 and 2
			for (int col = 1; col <= 2 ; col++) {

				//   |  | X
				// --------
				//   |  |
				// --------
				//   |  |
				if (mBoard.getTile(row, col).equals(mPlayer1.getChosenMark())) {


					if (mBoard.getTile((row + 1), (col - 1)).equals(mPlayer1.getChosenMark())) {

						//   |   | X
						// --------
						//   | X |
						// --------
						// O |   |
						if (row == 0 && col == 2 && mBoard.getTile((row + 2), (col - 2)).equals(EMPTY)) {
							mBoard.placeMark((row + 2), (col - 2), mPlayer2);
							boardFragment.placeCircleOnBoard((row + 2), (col - 2));

							boardFragment.endPlayer2Turn();

							return true;

							//   |   | O
							// --------
							//   | X |
							// --------
							// X |   |
						} else if (row == 1 && col == 1 && mBoard.getTile((row - 1), (col + 1)).equals(EMPTY)) {

							mBoard.placeMark((row - 1), (col + 1), mPlayer2);
							boardFragment.placeCircleOnBoard((row - 1), (col + 1));

							boardFragment.endPlayer2Turn();

							return true;
						} else {
							return false;
						}
					}

					//   |   | X
					// --------
					//   | O |
					// --------
					// X |   |
					if (row == 0 && col == 2){

						if (mBoard.getTile((row + 2), (col - 2)).equals(mPlayer1.getChosenMark()) && mBoard.getTile((row + 1), (col - 1)).equals(EMPTY)){
							mBoard.placeMark((row + 1), (col - 1), mPlayer2);
							boardFragment.placeCircleOnBoard((row + 1), (col - 1));

							boardFragment.endPlayer2Turn();

							return true;
						}
					}
				}
			}
		}
		return false;
	}
}
