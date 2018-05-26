package no.woact.marchr16.database.datasources;
// Created by Christian on 22/03/2018.

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import no.woact.marchr16.database.SQLiteHelper;
import no.woact.marchr16.database.entities.MatchStats;

import static android.content.ContentValues.TAG;

public class MatchStatsDataSource {

	private SQLiteDatabase mDatabase;
	private SQLiteHelper mDbHelper;

	private String[] allColumnsInDb = {
			SQLiteHelper.MATCHSTATS_COLUMN_ID,
			SQLiteHelper.MATCHSTATS_COLUMN_PLAYER_NAME,
			SQLiteHelper.MATCHSTATS_COLUMN_VICTORIES,
			SQLiteHelper.MATCHSTATS_COLUMN_DEFEATS};

	public MatchStatsDataSource(Context context) {
		mDbHelper = new SQLiteHelper(context);
	}

	/**
	 * Opens a database connection
	 *
	 * @param readOnly to decide if the instance should be readable only or not
	 * @return database connection with given read/write permissions
	 */
	public MatchStatsDataSource open(boolean readOnly) {

		if (mDatabase == null || !mDatabase.isOpen()) {
			if (readOnly) {
				mDatabase = mDbHelper.getReadableDatabase();
			} else {
				mDatabase = mDbHelper.getWritableDatabase();
			}
		}
		return this;
	}

	/**
	 * Close the database connection
	 */
	public void close() {
		mDbHelper.close();
	}

	/**
	 * This method updates the attributes "victory" or "defeat" for the given MatchStat
	 *
	 * @param matchStats the instance of MatchStat to be updated
	 * @param isVictory  true if the player won the game,
	 * @return true if affectedRows > 0
	 */
	public boolean reportStat(MatchStats matchStats, boolean isVictory) {

		ContentValues values = new ContentValues();

		if (isVictory) {
			int updatedVictories = matchStats.getVictories() + 1;
			values.put("victories", updatedVictories);

		} else {
			int updatedDefeats = matchStats.getDefeats() + 1;
			values.put("defeats", updatedDefeats);
		}

		int affectedRows = mDatabase.update(SQLiteHelper.MATCHSTATS_TABLE_NAME, values, "player_name = '" + matchStats.getPlayer_name() + "'", null);

		return affectedRows > 0;
	}


	/*
		CRUD Operations
	 */

	/**
	 * Creates a new row in the database with default values and a given name
	 *
	 * @param playerName name of the player
	 * @return id of the new row in the database
	 */
	public Long createMatchStat(String playerName) {

		if (getMatchStatFromPlayerName(playerName) != null) {
			return null;
		}

		ContentValues values = new ContentValues();
		values.put("defeats", 0);
		values.put("victories", 0);
		values.put("player_name", playerName);

		long matchStatId = mDatabase.insert(SQLiteHelper.MATCHSTATS_TABLE_NAME, null, values);

		Log.d(TAG, "createMatchStat: MatchStat with id: " + matchStatId);

		return matchStatId;
	}

	/**
	 * Searches for a row in th database with given name
	 *
	 * @param playerName name of player to search for
	 * @return a MatchStat-object of the the found row. null if none is found
	 */
	public MatchStats getMatchStatFromPlayerName(String playerName) {

		Cursor cursor = mDatabase.query(SQLiteHelper.MATCHSTATS_TABLE_NAME,
				allColumnsInDb,
				SQLiteHelper.MATCHSTATS_COLUMN_PLAYER_NAME + " = '" + playerName + "'",
				null, null, null, null);

		cursor.moveToFirst();

		if (cursor.getCount() == 0) {
			return null;
		}

		MatchStats matchStat = new MatchStats();

		matchStat.setId(cursor.getInt(0));
		matchStat.setPlayer_name(cursor.getString(1));
		matchStat.setVictories(cursor.getInt(2));
		matchStat.setDefeats(cursor.getInt(3));

		cursor.close();

		return matchStat;
	}

	/**
	 * Iterates through the entire database table
	 *
	 * @return ArrayList that contains every row in the table
	 */
	public ArrayList<MatchStats> getAllStats() {

		ArrayList<MatchStats> matchStatsList = new ArrayList<>();

		Cursor cursor = mDatabase.query(SQLiteHelper.MATCHSTATS_TABLE_NAME, allColumnsInDb,
				null, null, null, null, SQLiteHelper.MATCHSTATS_COLUMN_VICTORIES + " DESC");

		cursor.moveToFirst();

		while (!cursor.isAfterLast()) {

			MatchStats matchStats = new MatchStats();

			matchStats.setId(cursor.getInt(0));
			matchStats.setPlayer_name(cursor.getString(1));
			matchStats.setVictories(cursor.getInt(2));
			matchStats.setDefeats(cursor.getInt(3));

			matchStatsList.add(matchStats);
			cursor.moveToNext();
		}

		cursor.close();

		return matchStatsList;
	}

	/**
	 * Deletes all rows in the table
	 *
	 * @return true if success
	 */
	public boolean deleteAllStats() {
		try {
			mDatabase.execSQL("DELETE FROM " + SQLiteHelper.MATCHSTATS_TABLE_NAME);
			return true;

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
}
