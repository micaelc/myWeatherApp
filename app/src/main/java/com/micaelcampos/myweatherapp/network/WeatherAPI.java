package com.micaelcampos.myweatherapp.network;

import android.provider.SyncStateContract;

import com.micaelcampos.myweatherapp.models.Forecast;
import com.micaelcampos.myweatherapp.utils.Constants;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherAPI {

    @GET("data/2.5/weather?")
    Call<Forecast> getWeatherDataByCityName(
            @Query(Constants.APP_KEY_OWM_API_QUERY_CITY_NAME) String cityName,
            @Query(Constants.APP_KEY_OWM_API_QUERY_API_KEY) String apiKey,
            @Query(Constants.APP_KEY_OWM_API_QUERY_UNITS) String units);
}
