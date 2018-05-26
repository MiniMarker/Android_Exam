package no.woact.marchr16.menu;

import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import no.cmarker.pgr201exam2018.R;
import no.woact.marchr16.database.datasources.MatchStatsDataSource;
import no.woact.marchr16.database.entities.MatchStats;

public class HighScoreFragment extends Fragment {

	//WIDGETS
	private TextView txtHighScoreHeader, txtName, txtVictories, txtDefeats;

	//LIST VIEW
	private ListView lstViewHighScore;

	//DATABASE & ADAPTERS
	private MatchStatsDataSource mMatchStatsDataSource;
	private ArrayAdapter<MatchStats> mMatchStatsArrayAdapter;
	private ArrayList<MatchStats> mMatchStatsList = new ArrayList<>();

	public HighScoreFragment() {
		// Required empty public constructor
	}

	/**
	 * Overrides onCreate to handle:
	 * Opening the database
	 * Populating mMatchStatsList with data from database.
	 * Initializing the widgets and List view
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_high_score, container, false);

		mMatchStatsDataSource = new MatchStatsDataSource(getContext());
		mMatchStatsDataSource.open(false);

		mMatchStatsList = mMatchStatsDataSource.getAllStats();

		initWidgets(view);
		initListView();

		return view;
	}

	/**
	 * Initializing the widgets and setting Typeface for all widgets containing text
	 *
	 * @param view which view is the widgets initialized in
	 */
	private void initWidgets(View view) {
		//TEXT VIEW
		txtHighScoreHeader = view.findViewById(R.id.txtHighScoreHeader);
		txtName = view.findViewById(R.id.txtName);
		txtVictories = view.findViewById(R.id.txtVictories);
		txtDefeats = view.findViewById(R.id.txtDefeats);

		//LIST VIEW
		lstViewHighScore = view.findViewById(R.id.lstViewHighScore);

		//FONTS
		Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/outage_cut.ttf");

		txtHighScoreHeader.setTypeface(font);
		txtName.setTypeface(font);
		txtVictories.setTypeface(font);
		txtDefeats.setTypeface(font);
	}

	/**
	 * Setting adapter class MatchStatsAdapter to the ListView
	 */
	private void initListView() {

		mMatchStatsArrayAdapter = new MatchStatsAdapter(getContext(), mMatchStatsList);
		lstViewHighScore.setAdapter(mMatchStatsArrayAdapter);
	}

	/**
	 * Overrides the onDestroyView to close the database connection
	 */
	@Override
	public void onDestroyView() {
		super.onDestroyView();

		mMatchStatsDataSource.close();
	}
}
