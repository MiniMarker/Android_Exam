package no.woact.marchr16.game;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Random;

import no.cmarker.pgr201exam2018.R;
import no.woact.marchr16.database.datasources.MatchStatsDataSource;
import no.woact.marchr16.database.entities.MatchStats;
import no.woact.marchr16.game.gamelogic.AiHard;
import no.woact.marchr16.game.gamelogic.Board;
import no.woact.marchr16.game.gamelogic.Marks;
import no.woact.marchr16.game.gamelogic.Player;
import no.woact.marchr16.menu.MainActivity;

import static no.woact.marchr16.game.gamelogic.Marks.*;

// Created by Christian on 11/03/2018.

public class BoardFragment extends Fragment {

	private ImageButton imgBtn00, imgBtn01, imgBtn02, imgBtn10, imgBtn11, imgBtn12, imgBtn20, imgBtn21, imgBtn22;
	private ImageButton[][] imageButtons = new ImageButton[][]{
			{imgBtn00, imgBtn01, imgBtn02},
			{imgBtn10, imgBtn11, imgBtn12},
			{imgBtn20, imgBtn21, imgBtn22}};

	private Chronometer mChronoTimeElapsed;
	private boolean mStopWatchIsCounting;
	private long mTimeElapsed;

	private Board mBoard;
	private Player mPlayer1, mPlayer2, mCurrentPlayer;
	private Intent mIntent;

	private MatchStatsDataSource matchStatsDataSource;

	/**
	 * Constructor that creates instances of the backend code
	 */
	public BoardFragment() {

		mPlayer1 = new Player(Marks.X);
		mPlayer2 = new Player(Marks.O);
		mCurrentPlayer = mPlayer1;

		mBoard = new Board();
	}

	/**
	 * Overrides onCreate to handle:
	 * Opening the database
	 * Get data from Intent
	 * Creates a MatchStat if there is no existing MatchStat on player name
	 * Initializing the widgets and setting onClickListeners
	 * Starting the Chronometer
	 * Clearing the Board
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_board, container, false);

		//OPENING DATABASE
		matchStatsDataSource = new MatchStatsDataSource(view.getContext());
		matchStatsDataSource.open(false);

		//GETTING DATA FROM INTENTS
		mIntent = getActivity().getIntent();
		String player1Name = mIntent.getStringExtra("player1Name").toLowerCase();
		String player2Name = mIntent.getStringExtra("player2Name").toLowerCase();

		//CREATING PLAYER 1 IN DATABASE
		mPlayer1.setName(player1Name);
		matchStatsDataSource.createMatchStat(player1Name);

		mPlayer2.setName(player2Name);
		matchStatsDataSource.createMatchStat(player2Name);

		initWidgets(view);
		initListeners();

		startTime();
		clearBoard();

		return view;
	}

	/**
	 * Initializing the widgets and setting Typeface for all widgets containing text
	 *
	 * @param view which view is the widgets initialized in
	 */
	private void initWidgets(View view) {
		assert view != null;

		//CHRONOMETER
		mChronoTimeElapsed = view.findViewById(R.id.chronoTimeElapsed);

		//ROW 1
		imageButtons[0][0] = view.findViewById(R.id.imgBtn00);
		imageButtons[0][1] = view.findViewById(R.id.imgBtn01);
		imageButtons[0][2] = view.findViewById(R.id.imgBtn02);

		//ROW 2
		imageButtons[1][0] = view.findViewById(R.id.imgBtn10);
		imageButtons[1][1] = view.findViewById(R.id.imgBtn11);
		imageButtons[1][2] = view.findViewById(R.id.imgBtn12);

		//ROW 3
		imageButtons[2][0] = view.findViewById(R.id.imgBtn20);
		imageButtons[2][1] = view.findViewById(R.id.imgBtn21);
		imageButtons[2][2] = view.findViewById(R.id.imgBtn22);

		//FONTS
		Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/outage_cut.ttf");

		mChronoTimeElapsed.setTypeface(font);
	}

	/**
	 * Setting OnClickListeners for all buttons
	 */
	private void initListeners() {
		//ROW 1
		imageButtons[0][0].setOnClickListener((View v) -> drawMark(mCurrentPlayer, 0, 0));
		imageButtons[0][1].setOnClickListener((View v) -> drawMark(mCurrentPlayer, 0, 1));
		imageButtons[0][2].setOnClickListener((View v) -> drawMark(mCurrentPlayer, 0, 2));

		//ROW 2
		imageButtons[1][0].setOnClickListener((View v) -> drawMark(mCurrentPlayer, 1, 0));
		imageButtons[1][1].setOnClickListener((View v) -> drawMark(mCurrentPlayer, 1, 1));
		imageButtons[1][2].setOnClickListener((View v) -> drawMark(mCurrentPlayer, 1, 2));

		//ROW 3
		imageButtons[2][0].setOnClickListener((View v) -> drawMark(mCurrentPlayer, 2, 0));
		imageButtons[2][1].setOnClickListener((View v) -> drawMark(mCurrentPlayer, 2, 1));
		imageButtons[2][2].setOnClickListener((View v) -> drawMark(mCurrentPlayer, 2, 2));
	}

	/**
	 * Clears the GUI-mBoard by setting the drawable in all ImageButtons to blank.png
	 * Sets all buttons to enabled
	 * Resets and starts the Chronometer
	 */
	private void clearBoard() {
		int blankButton = R.drawable.blank;

		for (ImageButton row[] : imageButtons) {
			for (ImageButton button : row) {

				button.setImageResource(blankButton);
				button.setEnabled(true);
			}
		}
		resetTime();
		startTime();
		setmCurrentPlayer(mPlayer1);
	}

	/**
	 * This method draws the players chosen mark on the UI- and backend mBoard.
	 *
	 * @param player instance of player class to use
	 * @param row    which row in imageButtons[][] to use
	 * @param col    which column in imageButtons[][] to use
	 */
	private void drawMark(Player player, int row, int col) {

		if (row > 2 || row < 0) throw new IllegalArgumentException();
		if (col > 2 || col < 0) throw new IllegalArgumentException();

		if (!mBoard.ismGameOver()) {

			if (player == mPlayer1) {

				mBoard.placeMark(row, col, player);
				imageButtons[row][col].setImageResource(R.drawable.cross3);
				imageButtons[row][col].setEnabled(false);
				setmCurrentPlayer(mPlayer2);

				if (mBoard.ismGameOver()) {
					endGameAndUpdateDb("Player1");
					return;
				}

				if (mIntent.getStringExtra("gameType").equals("SinglePlayer")) {

					//AI
					aiDrawMark();

				}
			} else {
				//MULTIPLAYER
				mBoard.placeMark(row, col, player);
				placeCircleOnBoard(row, col);

				setmCurrentPlayer(mPlayer1);

				if (mBoard.ismGameOver()) {
					endGameAndUpdateDb("Player2");
				}
			}
		}
	}

	private void aiDrawMark(){
		//AI = EASY
		if (mIntent.getStringExtra("gameDifficulty").equals("EASY")) {

			if (mBoard.getRemainingTilesLeft() != 0) {
				placeRandom();
			}

		} else {

			if (mBoard.getRemainingTilesLeft() != 0) {
				new AiHard(this, mPlayer1, mPlayer2, mBoard);
			}
		}
	}

	/**
	 * Draws a circle on the tile in given row and col
	 *
	 * @param row which row in imageButtons[][] to use
	 * @param col which column in imageButtons[][] to use
	 */
	public void placeCircleOnBoard(int row, int col) {

		imageButtons[row][col].setImageResource(R.drawable.circle3);
		imageButtons[row][col].setEnabled(false);
	}

	/**
	 * It works by random generating two numbers between 0 and 2 which is used for column and row
	 * and checks with the backend if the tile is empty, if not the method repeats until it hits
	 * an empty tile
	 */
	public void placeRandom() {
		while (true) {

			int randomRow = new Random().nextInt(3);
			int randomCol = new Random().nextInt(3);


			if (mBoard.getTile(randomRow, randomCol) == EMPTY) {

				mBoard.placeMark(randomRow, randomCol, getmCurrentPlayer());
				placeCircleOnBoard(randomRow, randomCol);

				endPlayer2Turn();

				break;
			}
		}
	}

	/**
	 * Sets currentPlayer to player 1
	 * Checks if game is over and if player 2 is the winner
	 */
	public void endPlayer2Turn() {
		setmCurrentPlayer(mPlayer1);

		if (mBoard.ismGameOver()) {
			endGameAndUpdateDb("Player2");
		}
	}


	/**
	 * This method displays a AlertDialog if the round has ended and stops the time
	 *
	 * @param winner which player won the round
	 */
	public void endGameAndUpdateDb(String winner) {
		pauseTime();

		if (mBoard.getRemainingTilesLeft() == 0 && mBoard.getmWinningPlayer() == null) {
			showDialog(false);
		} else {
			showDialog(true);
		}

		if (mBoard.getmWinningPlayer() != null) {
			updateDatabase(winner);
		}
	}

	/**
	 * This method is used to update the victory or defeat attribute in the database
	 *
	 * @param winner which player won the round, this is passed by the method endGameAndUpdateDb(String winner)
	 */
	private void updateDatabase(String winner) {

		MatchStats dbMatchStatPlayer1 = matchStatsDataSource.getMatchStatFromPlayerName(mPlayer1.getName().toLowerCase());
		MatchStats dbMatchStatPlayer2 = matchStatsDataSource.getMatchStatFromPlayerName(mPlayer2.getName().toLowerCase());

		switch (winner) {
			case "Player1":
				matchStatsDataSource.reportStat(dbMatchStatPlayer1, true);
				matchStatsDataSource.reportStat(dbMatchStatPlayer2, false);
				break;
			case "Player2":
				matchStatsDataSource.reportStat(dbMatchStatPlayer2, true);
				matchStatsDataSource.reportStat(dbMatchStatPlayer1, false);
				break;
		}
	}

	/**
	 * This method displays the dialog that pops up at the end of the game that contains
	 * information about the games duration, its winner status (who won/draw) and asks the user
	 * if he/she would like to play again
	 *
	 * @param isWinner used to deside what the title of the dialog should be.
	 *                 true if there is a winner, false if draw
	 */
	private void showDialog(boolean isWinner) {

		String titleText;

		if (isWinner) {
			titleText = mBoard.getmWinningPlayer().getName() + " won!";
		} else {
			titleText = "It's a draw...";
		}

		AlertDialog builder = new AlertDialog.Builder(getContext())
				.setTitle(titleText)
				.setMessage("The game lasted for: " + mChronoTimeElapsed.getText() +
						"\nDo you want to play again?")
				.setPositiveButton("Yes", (dialog, which) -> {
					clearBoard();
					mBoard.resetGame();
				})
				.setNegativeButton("No", (dialog, which) -> {
					Intent mainMenuIntent = new Intent(getContext(), MainActivity.class);
					startActivity(mainMenuIntent);
				})
				.show();

		builder.setCancelable(false);
		builder.setCanceledOnTouchOutside(false);


		Typeface font = Typeface.createFromAsset(
				getActivity().getAssets(), "fonts/outage_cut.ttf");

		// I had some trouble with accessing the title with standard android.R.id.title,
		// so i found a hack online, source in documentation
		int titleTexViewId = builder.getContext().getResources().getIdentifier(
				"android:id/alertTitle", null, null);

		TextView title = builder.findViewById(titleTexViewId);
		TextView message = builder.findViewById(android.R.id.message);
		TextView positiveBtn = builder.findViewById(android.R.id.button1);
		TextView negativeBtn = builder.findViewById(android.R.id.button2);

		title.setTypeface(font);
		message.setTypeface(font);
		positiveBtn.setTypeface(font);
		negativeBtn.setTypeface(font);
	}

	public Player getmCurrentPlayer() {
		return mCurrentPlayer;
	}

	public void setmCurrentPlayer(Player mCurrentPlayer) {
		this.mCurrentPlayer = mCurrentPlayer;
	}

	/*
		TIME
	 */

	/**
	 * This methods setup the Chromometer and starts the timer
	 * the method sets the start time to 00:00 + elapsed time
	 */
	private void startTime() {
		mChronoTimeElapsed.setBase(SystemClock.elapsedRealtime() + mTimeElapsed);
		mChronoTimeElapsed.start();
		mStopWatchIsCounting = true;
	}

	/**
	 * This method saves the elapsed time in a variable named "mTimeElapsed" and stops the countdown
	 */
	private void pauseTime() {
		if (mStopWatchIsCounting) {
			mTimeElapsed = mChronoTimeElapsed.getBase() - SystemClock.elapsedRealtime();
			mChronoTimeElapsed.stop();
		}
	}

	/**
	 * This method resets the Chronometer and sets the value of "mTimeElapsed" to 0
	 */
	private void resetTime() {
		if (mStopWatchIsCounting) {
			mChronoTimeElapsed.stop();
			mTimeElapsed = 0;
			mStopWatchIsCounting = false;
		}
	}

	/*
		LIFECYCLE
	 */

	/**
	 * Overrides onResume method to implement logic for starting the Chronometer
	 */
	@Override
	public void onResume() {
		super.onResume();
		startTime();
	}

	/**
	 * Overrides onPause method to implement logic for pausing the Chronometer
	 */
	@Override
	public void onPause() {
		super.onPause();
		pauseTime();
	}

	/**
	 * Overrides onDestroyView method to implement logic for restarting the Chronometer
	 */
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		resetTime();
	}



}