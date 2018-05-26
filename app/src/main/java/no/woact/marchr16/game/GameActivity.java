package no.woact.marchr16.game;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import no.cmarker.pgr201exam2018.R;

// Created by Christian on 11/03/2018.

public class GameActivity extends AppCompatActivity {
	private MediaPlayer mMediaPlayer = null;
	private Intent mIntent;

	/**
	 * Overrides onCreate to get intent and start the playback of music
	 *
	 * @param savedInstanceState
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(android.R.style.Theme_Material_Wallpaper_NoTitleBar);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);

		mIntent = getIntent();
		startPlayback();
	}

	/**
	 * Starts the playback of given track
	 * Sets the start to match the point of time elapsed when leaving the mainActivity
	 * This information is passed through an Intent
	 */
	private void startPlayback() {
		if (mMediaPlayer == null) {

			int position = mIntent.getIntExtra("musicPosistion", 0);

			mMediaPlayer = MediaPlayer.create(this, R.raw.hypnotic_puzzle);
			mMediaPlayer.setLooping(true);
			mMediaPlayer.seekTo(position);
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
