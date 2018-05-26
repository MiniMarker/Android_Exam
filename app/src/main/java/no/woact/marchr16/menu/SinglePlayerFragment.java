package no.woact.marchr16.menu;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import no.cmarker.pgr201exam2018.R;
import no.woact.marchr16.game.GameActivity;

// Created by Christian on 11/03/2018.

public class SinglePlayerFragment extends Fragment {

	//WIDGETS
	private TextView txtSingleHeader, txtSinglePlayer1;
	private EditText edtPlayer1Single;
	private Button btnPlayGameSingle;

	//AUDIO
	private MediaPlayer mBtnPressTune = null;

	public SinglePlayerFragment() {
		// Required empty public constructor
	}

	/**
	 * Overrides onCreate to handle:
	 * Initializing the widgets and setting onClickListeners
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_single_player, container, false);

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
		txtSingleHeader = view.findViewById(R.id.txtSingleHeader);
		txtSinglePlayer1 = view.findViewById(R.id.txtSinglePlayer1);

		//EDIT TEXTS
		edtPlayer1Single = view.findViewById(R.id.edtSinglePlayer1);

		//BUTTONS
		btnPlayGameSingle = view.findViewById(R.id.btnSinglePlayGame);

		//AUDIO
		mBtnPressTune = MediaPlayer.create(getContext(), R.raw.menu_selection);

		//FONTS
		Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/outage_cut.ttf");

		txtSingleHeader.setTypeface(font);
		txtSinglePlayer1.setTypeface(font);
		edtPlayer1Single.setTypeface(font);
		btnPlayGameSingle.setTypeface(font);
	}

	/**
	 * Setting OnClickListeners for all buttons
	 */
	private void initListeners() {

		btnPlayGameSingle.setOnClickListener((View v) -> {

			//NAMING RULES
			if (((MainActivity) getActivity()).testNamingRules(edtPlayer1Single)) {

				//PLAY BUTTON PRESS SOUND
				mBtnPressTune.start();

				//SETTING INTENTS
				Intent startGameIntent = new Intent(getContext(), GameActivity.class);
				startGameIntent.putExtra("player1Name", edtPlayer1Single.getText().toString());
				startGameIntent.putExtra("player2Name", "TTTBot");
				startGameIntent.putExtra("gameType", "SinglePlayer");
				startGameIntent.putExtra("gameDifficulty", ((MainActivity) getActivity()).getChosenDifficulty().toString());
				startGameIntent.putExtra("musicPosistion", ((MainActivity) getActivity()).getmMediaPlayer().getCurrentPosition());

				System.out.println(((MainActivity) getActivity()).getChosenDifficulty().toString());

				startActivity(startGameIntent);
			}
		});
	}

	/**
	 * Overrides onDestroyView method to implement logic MediaPlayer
	 */
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		mBtnPressTune = null;
	}
}
