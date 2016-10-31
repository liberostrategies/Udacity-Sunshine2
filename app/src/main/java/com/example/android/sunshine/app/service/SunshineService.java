/*
 * Copyright (c) 2016. Libero Strategies, LLC - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and Confidential
 */

package com.example.android.sunshine.app.service;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by pink on 10/26/2016.
 */

public class SunshineService extends IntentService {
    public static final String LOCATION_QUERY_EXTRA = "lqe";
    private final String LOG_TAG = SunshineService.class.getSimpleName();

    public SunshineService() {
        super("Sunshine");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String locationQuery = intent.getStringExtra(LOCATION_QUERY_EXTRA);

//        // These two need to be declared outside the try/catch
//        // so that they can be closed in the finally block.
//        HttpURLConnection urlConnection = null;
//        BufferedReader reader = null;
//
//        // Will contain the raw JSON response as a string.
//        String forecastJsonStr = null;
//
//        // My Dev API key.
//        String apiKey = "935225c273817e0369d2aee6b8773c3d";
//        String format = "json";
//        String units = "metric";
//        int numDays = 14;
//
//        try {
//            // Construct the URL for the OpenWeatherMap query
//            // Possible parameters are avaiable at OWM's forecast API page, at
//            // http://openweathermap.org/API#forecast
//            final String FORECAST_BASE_URL =
//                    "http://api.openweathermap.org/data/2.5/forecast/daily?";
//            final String PARAM_APPID = "APPID";
//            final String QUERY_PARAM = "q";
//            final String FORMAT_PARAM = "mode";
//            final String UNITS_PARAM = "units";
//            final String DAYS_PARAM = "cnt";
//
//            Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
//                    .appendQueryParameter(PARAM_APPID, apiKey)
//                    .appendQueryParameter(QUERY_PARAM, LOCATION_QUERY_EXTRA)
//                    .appendQueryParameter(FORMAT_PARAM, format)
//                    .appendQueryParameter(UNITS_PARAM, units)
//                    .appendQueryParameter(DAYS_PARAM, Integer.toString(numDays))
//                    .build();
//
//            URL url = new URL(builtUri.toString());
//
//            // Create the request to OpenWeatherMap, and open the connection
//            urlConnection = (HttpURLConnection) url.openConnection();
//            urlConnection.setRequestMethod("GET");
//            urlConnection.connect();
//
//            // Read the input stream into a String
//            InputStream inputStream = urlConnection.getInputStream();
//            StringBuffer buffer = new StringBuffer();
//            if (inputStream == null) {
//                // Nothing to do.
//                return;
//            }
//            reader = new BufferedReader(new InputStreamReader(inputStream));
//
//            String line;
//            while ((line = reader.readLine()) != null) {
//                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
//                // But it does make debugging a *lot* easier if you print out the completed
//                // buffer for debugging.
//                buffer.append(line + "\n");
//            }
//
//            if (buffer.length() == 0) {
//                // Stream was empty.  No point in parsing.
//                return;
//            }
//            forecastJsonStr = buffer.toString();
//            getWeatherDataFromJson(forecastJsonStr, locationQuery);
//        } catch (IOException e) {
//            Log.e(LOG_TAG, "Error ", e);
//            // If the code didn't successfully get the weather data, there's no point in attemping
//            // to parse it.
//            return;
//        } catch (JSONException e) {
//            Log.e(LOG_TAG, e.getMessage(), e);
//            e.printStackTrace();
//        } finally {
//            if (urlConnection != null) {
//                urlConnection.disconnect();
//            }
//            if (reader != null) {
//                try {
//                    reader.close();
//                } catch (final IOException e) {
//                    Log.e(LOG_TAG, "Error closing stream", e);
//                }
//            }
//        }
        // This will only happen if there was an error getting or parsing the forecast.
        return;
    }

    static public class AlarmReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Intent sendIntent = new Intent(context, SunshineService.class);
            sendIntent.putExtra(LOCATION_QUERY_EXTRA, intent.getStringExtra(LOCATION_QUERY_EXTRA));
            context.startService(sendIntent);
        }
    }
}
