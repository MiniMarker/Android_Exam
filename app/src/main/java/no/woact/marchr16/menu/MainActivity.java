package no.woact.marchr16.menu;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageView;

import no.cmarker.pgr201exam2018.R;
import no.woact.marchr16.game.gamelogic.GameDifficulty;

// Created by Christian on 11/03/2018.

public class MainActivity extends AppCompatActivity {

	private ImageView imgLogo;

	private GameDifficulty chosenDifficulty;
	private MediaPlayer mMediaPlayer = null;

	private Fragment mMainMenuFragment;
	private FragmentManager mFragmentManager = getFragmentManager();
	private FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();

	/**
	 * Overrides onCreate to handle:
	 * Initializing of all widgets
	 * Starts the playback of soundtrack
	 * Displays the MainMenuFragment
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(android.R.style.Theme_Material_Wallpaper_NoTitleBar);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initWidgets();
		startPlayback();

		chosenDifficulty = GameDifficulty.EASY;

		mMainMenuFragment = new MainMenuFragment();
		mFragmentTransaction.add(R.id.fragmentHolder, mMainMenuFragment);
		mFragmentTransaction.addToBackStack(null);
		mFragmentTransaction.commit();
	}

	/**
	 * Sending a request to DownloadImageTask class to get the picture
	 * given as a tag to the ImageView.
	 * This is an Async task!
	 */
	private void initWidgets() {

		imgLogo = findViewById(R.id.imgLogo);
		imgLogo.setTag("https://i.imgur.com/qpWo53X.png");

		new DownloadImageTask().execute(imgLogo);
	}

	/**
	 * Checks certain naming rules i don't want the player to be named
	 *
	 * @param editText EditText field to be checked
	 * @return true if name is not in the list of illegalNames
	 */
	public boolean testNamingRules(EditText editText) {
		String[] illegalNames = new String[]{"", " ", ".", ",", "0", "123", "12345", "tttbots"};

		for (String name : illegalNames) {

			if (name.equals(editText.getText().toString())) {
				return false;
			}
		}

		return true;
	}

	public MediaPlayer getmMediaPlayer() {
		return mMediaPlayer;
	}

	public GameDifficulty getChosenDifficulty() {

		return chosenDifficulty;
	}

	public void setChosenDifficulty(GameDifficulty chosenDifficulty) {
		this.chosenDifficulty = chosenDifficulty;
	}


	/**
	 * Starts the playback of given track
	 */
	private void startPlayback() {
		if (mMediaPlayer == null) {
			mMediaPlayer = MediaPlayer.create(this, R.raw.hypnotic_puzzle);
			mMediaPlayer.setLooping(true);
			mMediaPlayer.start();
		}
	}

	/**
	 * Stops the currently playing music and resets the track
	 */
	private void resetPlayback() {
		if (mMediaPlayer != null) {
			mMediaPlayer.pause();
			mMediaPlayer.seekTo(0);
		}
	}

	/**
	 * Overrides onStart to handle the MediaPlayer
	 * Starts the given track
	 */
	@Override
	protected void onStart() {
		super.onStart();
		mMediaPlayer.start();
	}

	/**
	 * Overrides onResume to handle the MediaPlayer
	 * Starts the given track
	 */
	@Override
	protected void onResume() {
		super.onResume();
		mMediaPlayer.start();
	}

	/**
	 * Overrides onPause to handle the MediaPlayer
	 * Resets the currently playing track
	 */
	@Override
	protected void onPause() {
		super.onPause();
		resetPlayback();
	}

	/**
	 * Overrides onStop to handle the MediaPlayer
	 * Resets the currently playing track
	 */
	@Override
	protected void onStop() {
		super.onStop();
		resetPlayback();
	}

	/**
	 * Overrides onDestroy to handle the MediaPlayer
	 * Sets MediaPlayer instance to null to free the used memory
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();

		if (mMediaPlayer != null) {
			mMediaPlayer.release();
			mMediaPlayer = null;
		}
	}


}
