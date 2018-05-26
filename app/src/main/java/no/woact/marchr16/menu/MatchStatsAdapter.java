package no.woact.marchr16.menu;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import no.cmarker.pgr201exam2018.R;
import no.woact.marchr16.database.entities.MatchStats;

// Created by Christian on 12/04/2018.
public class MatchStatsAdapter extends ArrayAdapter<MatchStats> {

	private Context mContext;
	private List<MatchStats> mMatchStatList;

	/**
	 * Required constructor that sets the fields
	 *
	 * @param context context of the list to be initializes within
	 * @param list    list of MatchStats to be processed
	 */
	public MatchStatsAdapter(@NonNull Context context, ArrayList<MatchStats> list) {
		super(context, 0, list);
		mContext = context;
		mMatchStatList = list;
	}

	/**
	 * Setting values from database to the TextViews associated with the values in the database
	 */
	@NonNull
	@Override
	public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
		View listItem = convertView;

		if (listItem == null) {
			listItem = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);
		}

		MatchStats currentMatchStat = mMatchStatList.get(position);

		TextView list_item_name = listItem.findViewById(R.id.list_item_name);
		TextView list_item_victories = listItem.findViewById(R.id.list_item_victories);
		TextView list_item_defeats = listItem.findViewById(R.id.list_item_defeats);

		list_item_name.setText(currentMatchStat.getPlayer_name());
		list_item_victories.setText(String.format("%d", currentMatchStat.getVictories()));
		list_item_defeats.setText(String.format("%d", currentMatchStat.getDefeats()));

		return listItem;
	}

	/**
	 * Overrides the clickListeners on all rows in ListView to disable click events
	 */
	@Override
	public boolean isEnabled(int position) {
		return false;
	}

}
