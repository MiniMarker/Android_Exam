package no.woact.marchr16.game;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import no.cmarker.pgr201exam2018.R;

// Created by Christian on 11/03/2018.

public class PlayersFragment extends Fragment {

	private TextView txtPlayer1Name, txtPlayer2Name, txtVs, txtGameDifficulty;
	private Intent mIntent;

	public PlayersFragment() {
		// Required empty public constructor
	}

	/**
	 * Overrides onCreate to initialize the Widgets and get player data from Intent
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_players, container, false);

		mIntent = getActivity().getIntent();
		initWidgets(view);

		return view;
	}

	/**
	 * Initializing the widgets and setting Typeface for all widgets containing text
	 *
	 * @param view which view is the widgets initialized in
	 */
	private void initWidgets(View view) {
		//GETTING DATA FROM INTENTS
		String player1Name = mIntent.getStringExtra("player1Name");
		String player2Name = mIntent.getStringExtra("player2Name");
		String gameDifficulty = mIntent.getStringExtra("gameDifficulty");

		//TEXT VIEW
		txtPlayer1Name = view.findViewById(R.id.txtMultiPlayer1);
		txtPlayer2Name = view.findViewById(R.id.txtMultiPlayer2);
		txtVs = view.findViewById(R.id.txtVs);
		txtGameDifficulty = view.findViewById(R.id.txtGameDifficulty);

		//SETTING VALUES
		txtPlayer1Name.setText(player1Name);
		txtPlayer2Name.setText(player2Name);
		txtGameDifficulty.setText(gameDifficulty);

		//FONTS
		Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/outage_cut.ttf");

		txtGameDifficulty.setTypeface(font);
		txtPlayer1Name.setTypeface(font);
		txtPlayer2Name.setTypeface(font);
		txtVs.setTypeface(font);

	}
}