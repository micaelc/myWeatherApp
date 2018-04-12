package com.micaelcampos.myweatherapp.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.micaelcampos.myweatherapp.R;
import com.micaelcampos.myweatherapp.models.Forecast;
import com.micaelcampos.myweatherapp.network.WeatherAPI;
import com.micaelcampos.myweatherapp.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText mCity;
    private Button mAction;
    private TextView mTemperature;
    private TextView mWeatherString;
    private Toolbar toolbar;
    private FloatingActionButton fab;

    private WeatherAPI mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCity = (EditText) findViewById(R.id.etCity);
        mAction = (Button) findViewById(R.id.btnGo);
        mTemperature = (TextView) findViewById(R.id.tvTemperature);
        mWeatherString = (TextView) findViewById(R.id.tvWeatherString);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        setSupportActionBar(toolbar);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String city = mCity.getText().toString();
                getForecastFromAPI(city);
            }
        });
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
                    mTemperature.setText(data.getTemp());
                    mWeatherString.setText(data.getDescription());

                } else {
                    Log.d(Constants.TAG, "Response Not Successful: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Forecast> call, Throwable t) {
                Toast.makeText(MainActivity.this, getString(R.string.something_wrong), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
