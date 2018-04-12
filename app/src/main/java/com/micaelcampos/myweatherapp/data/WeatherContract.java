package com.micaelcampos.myweatherapp.data;

import android.provider.BaseColumns;

public class WeatherContract {

	public static final class WeatherData implements BaseColumns {
		public static final String TABLE_NAME = "weatherdata";
		public static final String COLUMN_CITY = "city";
		public static final String COLUMN_TIMESTAMP = "timestamp";
	}
}
