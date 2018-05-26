package no.woact.marchr16.menu;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import no.cmarker.pgr201exam2018.R;
import no.woact.marchr16.database.datasources.MatchStatsDataSource;

import static android.media.AudioManager.STREAM_MUSIC;
import static no.woact.marchr16.game.gamelogic.GameDifficulty.*;

// Created by Christian on 11/03/2018.

public class SettingsFragment extends Fragment {

	//WIDGETS
	private SeekBar sldrVolumeLevel;
	private Button btnResetHighScore, btnDifficultyEasy, btnDifficultyHard;
	private TextView txtVolumeSettings, txtDifficulty, txtResetSettings, txtSettingsHeader;

	//AUDIO
	private AudioManager mAudioManager;
	private MediaPlayer mBtnPressTune = null;

	//DATABASE
	private MatchStatsDataSource mMatchStatsDataSource;

	public SettingsFragment() {
		//Required empty public constructor
	}

	/**
	 * Overrides onCreate to handle:
	 * opening the connection to the database
	 * Initializing the widgets and setting onClickListeners
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_settings, container, false);

		mMatchStatsDataSource = new MatchStatsDataSource(getContext());
		mMatchStatsDataSource.open(true);

		initWidgets(view);
		initListeners();

		return view;
	}

	/**
	 * Initializing the widgets and setting Typeface for all widgets containing text
	 *
	 * @param view which view is the widgets initialized in
	 */
	private void initWidgets(View view) {
		assert view != null;

		//TEXT VIEWS
		txtSettingsHeader = view.findViewById(R.id.txtSettingsHeader);
		txtVolumeSettings = view.findViewById(R.id.txtVolumeSettings);
		txtResetSettings = view.findViewById(R.id.txtResetSettings);
		txtDifficulty = view.findViewById(R.id.txtDifficulty);

		//BUTTONS
		btnResetHighScore = view.findViewById(R.id.btnResetHighScore);
		btnDifficultyEasy = view.findViewById(R.id.btnDifficultyEasy);
		btnDifficultyHard = view.findViewById(R.id.btnDifficultyHard);

		//AUDIO
		mBtnPressTune = MediaPlayer.create(getContext(), R.raw.menu_selection);

		//FONTS
		Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/outage_cut.ttf");

		txtDifficulty.setTypeface(font);
		txtSettingsHeader.setTypeface(font);
		txtVolumeSettings.setTypeface(font);
		txtResetSettings.setTypeface(font);
		btnResetHighScore.setTypeface(font);
		btnDifficultyEasy.setTypeface(font);
		btnDifficultyHard.setTypeface(font);

		//SLIDERS
		sldrVolumeLevel = view.findViewById(R.id.sldrVolumeLevel);
		mAudioManager = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);

		assert mAudioManager != null;
		sldrVolumeLevel.setMax(mAudioManager.getStreamMaxVolume(STREAM_MUSIC));
		sldrVolumeLevel.setProgress(mAudioManager.getStreamVolume(STREAM_MUSIC));
	}

	/**
	 * Setting OnClickListeners for all buttons and SeekBar
	 */
	private void initListeners() {

		btnResetHighScore.setOnLongClickListener((View v) -> {
			mBtnPressTune.start(); //PLAY BUTTON PRESS SOUND

			mMatchStatsDataSource.deleteAllStats();

			Toast.makeText(getContext(), "HighScore deleted", Toast.LENGTH_SHORT).show();

			return true;

		});

		btnDifficultyEasy.setOnClickListener((View v) -> {

			mBtnPressTune.start(); //PLAY BUTTON PRESS SOUND

			Toast.makeText(getContext(), "Difficulty set to EASY", Toast.LENGTH_SHORT).show();
			((MainActivity)getActivity()).setChosenDifficulty(EASY);
		});

		btnDifficultyHard.setOnClickListener((View v) -> {

			mBtnPressTune.start(); //PLAY BUTTON PRESS SOUND

			Toast.makeText(getContext(), "Difficulty set to HARD", Toast.LENGTH_SHORT).show();
			((MainActivity)getActivity()).setChosenDifficulty(HARD);
		});

		sldrVolumeLevel.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

				try {
					mAudioManager.setStreamVolume(STREAM_MUSIC, progress, 0);

				} catch (SecurityException secEx) {
					System.out.println(secEx.getMessage());
				}
			}
		});
	}

	/**
	 * Overrides onDestroyView method to handle the MediaPlayer and the database
	 */
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		mMatchStatsDataSource.close();
		mBtnPressTune = null;
	}

}
