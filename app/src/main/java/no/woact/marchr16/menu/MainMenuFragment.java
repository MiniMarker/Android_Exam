package no.woact.marchr16.menu;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import no.cmarker.pgr201exam2018.R;

// Created by Christian on 11/03/2018.

public class MainMenuFragment extends Fragment {

	//WIDGETS
	private Button btnSinglePlayer, btnMultiPlayer, btnHighScore, btnSettings;

	//AUDIO
	private MediaPlayer mBtnPressTune = null;


	public MainMenuFragment() {
		// Required empty public constructor
	}

	/**
	 * Overrides onCreateView to handle:
	 * Initializing the widgets and setting onClickListeners
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		mBtnPressTune = MediaPlayer.create(getContext(), R.raw.menu_selection);

		View view = inflater.inflate(R.layout.fragment_main_menu, container, false);

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

		//BUTTONS
		btnSinglePlayer = view.findViewById(R.id.btnSinglePlayer);
		btnMultiPlayer = view.findViewById(R.id.btnMultiPlayer);
		btnHighScore = view.findViewById(R.id.btnHighScore);
		btnSettings = view.findViewById(R.id.btnSettings);

		//FONTS
		Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/outage_cut.ttf");

		btnSinglePlayer.setTypeface(font);
		btnMultiPlayer.setTypeface(font);
		btnHighScore.setTypeface(font);
		btnSettings.setTypeface(font);
	}

	/**
	 * Setting OnClickListeners for all buttons
	 * Plays the mBtnPressTune
	 * Shows the fragment associated with button
	 */
	private void initListeners() {
		btnSinglePlayer.setOnClickListener((View v) -> {
			SinglePlayerFragment singlePlayerFragment = new SinglePlayerFragment();
			mBtnPressTune.start();

			FragmentManager fm = getFragmentManager();
			FragmentTransaction transaction = fm.beginTransaction();
			transaction.replace(R.id.fragmentHolder, singlePlayerFragment);
			transaction.addToBackStack(null);

			transaction.commit();
		});

		btnMultiPlayer.setOnClickListener((View v) -> {

			mBtnPressTune.start();

			MultiPlayerFragment multiPlayerFragment = new MultiPlayerFragment();

			FragmentManager fm = getFragmentManager();
			FragmentTransaction transaction = fm.beginTransaction();
			transaction.replace(R.id.fragmentHolder, multiPlayerFragment);
			transaction.addToBackStack(null);

			transaction.commit();
		});

		btnHighScore.setOnClickListener((View v) -> {
			HighScoreFragment highScoreFragment = new HighScoreFragment();
			mBtnPressTune.start();

			FragmentManager fm = getFragmentManager();
			FragmentTransaction transaction = fm.beginTransaction();
			transaction.replace(R.id.fragmentHolder, highScoreFragment);
			transaction.addToBackStack(null);

			transaction.commit();
		});

		btnSettings.setOnClickListener((View v) -> {
			SettingsFragment settingsFragment = new SettingsFragment();
			mBtnPressTune.start();

			FragmentManager fm = getFragmentManager();
			FragmentTransaction transaction = fm.beginTransaction();
			transaction.replace(R.id.fragmentHolder, settingsFragment);
			transaction.addToBackStack(null);

			transaction.commit();
		});
	}
}
