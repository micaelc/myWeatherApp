package com.micaelcampos.myweatherapp.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.micaelcampos.myweatherapp.models.Forecast;
import com.micaelcampos.myweatherapp.utils.Constants;
import com.micaelcampos.myweatherapp.utils.ForecastDeserialiser;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherService {

    public static WeatherAPI createService() {

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Forecast.class, new ForecastDeserialiser())
                .create();

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .build();

        // Retrofit
        Retrofit retro = new Retrofit.Builder()
                .baseUrl(Constants.APP_KEY_OWM_API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient)
                .build();

        // Create
        return retro.create(WeatherAPI.class);
    }

}
