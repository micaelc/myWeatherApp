package com.micaelcampos.myweatherapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class WeatherDbHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "weather.db";
	private static final int DATABASE_VERSION = 1;

	public WeatherDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}


	@Override
	public void onCreate(SQLiteDatabase db) {
		String SQL_CREATE_WEATHERDATA_TABLE = "CREATE TABLE " +
				WeatherContract.WeatherData.TABLE_NAME + " (" +
				WeatherContract.WeatherData._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
				WeatherContract.WeatherData.COLUMN_CITY + " TEXT NOT NULL," +
				WeatherContract.WeatherData.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
				");";
		db.execSQL(SQL_CREATE_WEATHERDATA_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String SQL_DROP_WEATHERDATA_TABLE = "DROP TABLE IF EXISTS " + WeatherContract.WeatherData.TABLE_NAME + ";";
		db.execSQL(SQL_DROP_WEATHERDATA_TABLE);
		onCreate(db);
	}
}
