/*
 * Copyright (c) 2016. Libero Strategies, LLC - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and Confidential
 */

package com.example.android.sunshine.app;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;

/**
 * Parses OpenWeatherMap weather data JSON.
 */

public class WeatherDataParser {
    private static final String LOG_TAG = WeatherDataParser.class.getSimpleName();

    /**
     * Given a string of the form returned by the api call:
     * http://api.openweathermap.org/data/2.5/forecast/daily?q=94043&mode=json&units=metric&cnt=7
     * retrieve the maximum temperature for the day indicated by dayIndex
     * (Note: 0-indexed, so 0 would refer to the first day).
     */
    public static double getMaxTemperatureForDay(String weatherJsonStr, int dayIndex)
            throws JSONException {
        JSONObject obj = new JSONObject(weatherJsonStr);
        JSONArray list = obj.getJSONArray("list");
        JSONObject dayForecast = list.getJSONObject(dayIndex);
        JSONObject dayTemp = dayForecast.getJSONObject("temp");
        return dayTemp.getDouble("max");
    }

    // Gists from Udacity.
    /* The date/time conversion code is going to be moved outside the asynctask later,
     * so for convenience we're breaking it out into its own method now.
     */
    public static String getReadableDateString(long time){
        // Because the API returns a unix timestamp (measured in seconds),
        // it must be converted to milliseconds in order to be converted to valid date.
        SimpleDateFormat shortenedDateFormat = new SimpleDateFormat("EEE MMM dd");
        return shortenedDateFormat.format(time);
    }


}
