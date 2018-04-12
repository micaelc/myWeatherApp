package com.micaelcampos.myweatherapp.utils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.micaelcampos.myweatherapp.models.Forecast;

import java.lang.reflect.Type;

public class ForecastDeserialiser implements JsonDeserializer<Forecast>  {

    @Override
    public Forecast deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jObject = json.getAsJsonObject();

        String name = jObject.getAsJsonPrimitive("name").getAsString();
        String temp = jObject.getAsJsonObject("main").getAsJsonPrimitive("temp").getAsString();
        String country = jObject.getAsJsonObject("sys").getAsJsonPrimitive("country").getAsString();
        String description = jObject.getAsJsonArray("weather").get(0).getAsJsonObject().getAsJsonPrimitive("description").getAsString();

        return new Forecast(name, country,temp,description);
    }
}
