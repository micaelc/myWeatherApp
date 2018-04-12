package com.micaelcampos.myweatherapp.models;

public class Forecast {

    private String name;
    private String country;
    private String temp;
    private String description;

    public Forecast(String name, String country, String temp, String description) {
        this.name = name;
        this.country = country;
        this.temp = temp;
        this.description = description;
    }

    public String getTemp() {
        return temp;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }
}
