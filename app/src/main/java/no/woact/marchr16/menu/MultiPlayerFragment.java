package no.woact.marchr16.menu;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
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

public class MultiPlayerFragment extends Fragment {

	//WIDGETS
	private TextView txtMultiPlayer1, txtMultiPlayer2, txtMultiHeader;
	private EditText edtMultiPlayer1, edtMultiPlayer2;
	private Button btnMultiPlayGame;

	//AUDIO
	private MediaPlayer mBtnPressTune = null;

	public MultiPlayerFragment() {
		// Required empty public constructor
	}

	/**
	 * Overrides onCreate to handle:
	 * Initializing the widgets and setting onClickListeners
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_multi_player, container, false);

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
		txtMultiHeader = view.findViewById(R.id.txtMultiHeader);
		txtMultiPlayer1 = view.findViewById(R.id.txtMultiPlayer1);
		txtMultiPlayer2 = view.findViewById(R.id.txtMultiPlayer2);

		//EDIT TEXT
		edtMultiPlayer1 = view.findViewById(R.id.edtMultiPlayer1);
		edtMultiPlayer2 = view.findViewById(R.id.edtMultiPlayer2);

		//BUTTONS
		btnMultiPlayGame = view.findViewById(R.id.btnMultiPlayGame);

		//AUDIO
		mBtnPressTune = MediaPlayer.create(getContext(), R.raw.menu_selection);

		//FONTS
		Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/outage_cut.ttf");

		txtMultiHeader.setTypeface(font);
		txtMultiPlayer1.setTypeface(font);
		txtMultiPlayer2.setTypeface(font);
		edtMultiPlayer1.setTypeface(font);
		edtMultiPlayer2.setTypeface(font);
		btnMultiPlayGame.setTypeface(font);
	}

	/**
	 * Setting OnClickListeners for all buttons
	 */
	private void initListeners() {

		btnMultiPlayGame.setOnClickListener((View v) -> {

			//NAMING RULES
			if (((MainActivity) getActivity()).testNamingRules(edtMultiPlayer1)
					&& ((MainActivity) getActivity()).testNamingRules(edtMultiPlayer2)
					&& !edtMultiPlayer1.getText().toString().equals(edtMultiPlayer2.getText().toString())
					&& !edtMultiPlayer2.getText().toString().equals(edtMultiPlayer1.getText().toString())) {

				//PLAY BUTTON PRESS SOUND
				mBtnPressTune.start();

				//SETTING INTENTS
				Intent startGameIntent = new Intent(getContext(), GameActivity.class);
				startGameIntent.putExtra("player1Name", edtMultiPlayer1.getText().toString());
				startGameIntent.putExtra("player2Name", edtMultiPlayer2.getText().toString());
				startGameIntent.putExtra("gameType", "MultiPlayer");
				startGameIntent.putExtra("musicPosistion", ((MainActivity) getActivity()).getmMediaPlayer().getCurrentPosition());

				//Stating intent
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