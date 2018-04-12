package com.micaelcampos.myweatherapp.activities;

import android.app.DialogFragment;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.micaelcampos.myweatherapp.R;
import com.micaelcampos.myweatherapp.adapters.ForecastAdapter;
import com.micaelcampos.myweatherapp.data.WeatherContract;
import com.micaelcampos.myweatherapp.data.WeatherDbHelper;
import com.micaelcampos.myweatherapp.dialogs.AddCityDialog;
import com.micaelcampos.myweatherapp.models.Forecast;
import com.micaelcampos.myweatherapp.network.WeatherAPI;
import com.micaelcampos.myweatherapp.network.WeatherService;
import com.micaelcampos.myweatherapp.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements AddCityDialog.AddCityDialogListner{

	private Toolbar toolbar;
    private FloatingActionButton mFabAddCity;
    private RecyclerView mRecyclerView;
	private ForecastAdapter mAdapter;
	private DividerItemDecoration mDividerItemDecoration;
	private LinearLayoutManager mLayoutManager;
	private List<Forecast> mForecasts = new ArrayList();
	private Cursor mCursor;

	WeatherDbHelper mDbHelper;
	SQLiteDatabase mDatabase;

    private WeatherAPI mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

	    mFabAddCity = (FloatingActionButton) findViewById(R.id.fab);


	    mFabAddCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
	            AddCityDialog dialog = new AddCityDialog();
	            dialog.show(getFragmentManager(), getString(R.string.dialog_tag));

            }
        });

	    mRecyclerView = (RecyclerView) findViewById(R.id.rvWeatherList);
	    mRecyclerView.setHasFixedSize(true);
	    mLayoutManager = new LinearLayoutManager(this);
	    mRecyclerView.setLayoutManager(mLayoutManager);
	    mDividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(), mLayoutManager.getOrientation());
	    mRecyclerView.addItemDecoration(mDividerItemDecoration);
	    mAdapter = new ForecastAdapter(mForecasts, MainActivity.this);
	    mRecyclerView.setAdapter(mAdapter);

	    mService = WeatherService.createService();
	    getWeatherfromDatabase();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

	private void getWeatherfromDatabase() {
		// Read the data from the Database
		mDbHelper = new WeatherDbHelper(MainActivity.this);
		SQLiteDatabase db = mDbHelper.getReadableDatabase();
		if (db != null) {
			String[] projection = {
					WeatherContract.WeatherData._ID,
					WeatherContract.WeatherData.COLUMN_CITY
			};
			mCursor = db.query(
					WeatherContract.WeatherData.TABLE_NAME, // Table name
					projection, // selected columns
					null, null, null, null, null);

			if (mCursor != null) {
				try {
					while (mCursor.moveToNext()) {
						String cityName = mCursor.getString(mCursor.getColumnIndex(WeatherContract.WeatherData.COLUMN_CITY));
						getForecastFromAPI(cityName);
					}
				} finally {
					mCursor.close();
				}
			}
		}
		mAdapter.notifyDataSetChanged();
	}

    private void getForecastFromAPI(String cityName) {

        Call<Forecast> call = mService.getWeatherDataByCityName(
                cityName,
                Constants.OWM_API_KEY,
                Constants.APP_KEY_OWN_API_UNITS);

        call.enqueue(new Callback<Forecast>() {
            @Override
            public void onResponse(Call<Forecast> call, Response<Forecast> response) {
                if (response.isSuccessful()) {
                    Forecast data = response.body();
	                mForecasts.add(data);
	                mAdapter.notifyDataSetChanged();

                } else {
                    Log.d(Constants.TAG, "Response Not Successful: " + response.code());
	                Toast.makeText(MainActivity.this, response.message().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Forecast> call, Throwable t) {
                Toast.makeText(MainActivity.this, getString(R.string.something_wrong), Toast.LENGTH_SHORT).show();
            }
        });
    }

	private Long addCityName(String cityName) {
		mDbHelper = new WeatherDbHelper(MainActivity.this);
		mDatabase = mDbHelper.getWritableDatabase();

		// Create a new map of values, where column names are the keys
		ContentValues values = new ContentValues();
		values.put(WeatherContract.WeatherData.COLUMN_CITY, cityName);

		// Insert the new row, returning the primary key value of the new row
		long newRowId = mDatabase.insert(WeatherContract.WeatherData.TABLE_NAME, null, values);
		return newRowId;
	}

	@Override
	public void onDialogPositiveClick(DialogFragment dialog, String cityName) {
		Long rowId = addCityName(cityName);
		getForecastFromAPI(cityName);
		//Toast.makeText(this, String.valueOf(rowId) + " - " + cityName, Toast.LENGTH_LONG).show();
	}

	@Override
	public void onDialogNegativeClick(DialogFragment dialog) {
		dialog.dismiss();
	}
}
