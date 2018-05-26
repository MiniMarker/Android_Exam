package no.woact.marchr16.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

// Created by Christian on 22/03/2018.

public class SQLiteHelper extends SQLiteOpenHelper {

	private static final int DB_VERSION = 1;
	public static final String DB_NAME = "TicTacToeDB";

	public static final String MATCHSTATS_TABLE_NAME = "MatchStats";
	public static final String MATCHSTATS_COLUMN_ID = "id";
	public static final String MATCHSTATS_COLUMN_PLAYER_NAME = "player_name";
	public static final String MATCHSTATS_COLUMN_VICTORIES = "victories";
	public static final String MATCHSTATS_COLUMN_DEFEATS = "defeats";

	/**
	 * Required constructor
	 *
	 * @param context
	 */
	public SQLiteHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	/**
	 * Overrides onCreate to run a query that creates the MatchStats table
	 *
	 * @param db
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {

		final String MATCHSTATS_TABLE_CREATE = "CREATE TABLE " + MATCHSTATS_TABLE_NAME + "(" +
				MATCHSTATS_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
				MATCHSTATS_COLUMN_PLAYER_NAME + " TEXT NOT NULL, " +
				MATCHSTATS_COLUMN_VICTORIES + " INTEGER, " +
				MATCHSTATS_COLUMN_DEFEATS + " INTEGER);";

		db.execSQL(MATCHSTATS_TABLE_CREATE);
	}

	/**
	 * Functionality to upgrade the database
	 *
	 * @param db
	 * @param oldVersion
	 * @param newVersion
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		final String TABLE_MATCHSTATS_DROP = " DROP TABLE If EXISTS " + MATCHSTATS_TABLE_NAME;

		db.execSQL(TABLE_MATCHSTATS_DROP);

		onCreate(db);
	}
}
