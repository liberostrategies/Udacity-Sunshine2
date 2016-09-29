/*
 * Copyright (c) 2016. Libero Strategies, LLC - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and Confidential
 */

package com.example.android.sunshine.app;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */

public class ForecastFragment extends Fragment {
    private final String LOG_TAG = ForecastFragment.class.getSimpleName();

    /**
     * Weather forecast adapter.
     */
    private ArrayAdapter<String> mForecastAdapter;

    public ForecastFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Handle menu events to indicate there are callbacks for the menu methods.
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        // Dummy data to display in listview_forecast.
        String[] forecastArray = {
                "Today - Sunny - 88/63",
                "Tomorrow - Foggy - 70/40",
                "Weds - Cloudy - 72/63",
                "Thurs - Asteroids - 75/65",
                "Fri - Heavy Rain - 65/56",
                "Sat - HELP TRAPPED IN WEATHERSTATION - 60/51",
                "Sun - Sunny - 80/68"
        };
        List<String> weekForecast = new ArrayList<String>(Arrays.asList(forecastArray));

        // Initialize adapter.
        mForecastAdapter = new ArrayAdapter<String>(
                // context - Contains global info about app environment. Access system resources.
                getActivity(),
                // layout - xml layout file
                R.layout.list_item_forecast,
                // view - view id
                R.id.list_item_forecast_textview,
                // list of data
                weekForecast);

        ListView listViewForecast = (ListView)rootView.findViewById(R.id.listview_forecast);
        listViewForecast.setAdapter(mForecastAdapter);

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.forecastfragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                // Call the FetchWeatherTask.
                new FetchWeatherTask().execute("94043");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public class FetchWeatherTask extends AsyncTask<String, Void, String[]> {

        @Override
        protected String[] doInBackground(String... params) {
            String[] weatherData = null;

            // Verify params size. If no postal code, stop.
            if (params.length == 0) {
                return null;
            }

            // HTTP Request for Weather
            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String forecastJsonStr = null;

            // Query param values.
            // My Dev API key.
            String apiKey = "935225c273817e0369d2aee6b8773c3d";
            String format = "json";
            String units = "metric";
            int numDays = 7;

            try {
                // Construct the URL for the OpenWeatherMap query
                // Possible parameters are avaiable at OWM's forecast API page, at
                // http://openweathermap.org/API#forecast

                final String BASE_URL_FORECAST = "http://api.openweathermap.org/data/2.5/forecast/daily";
                final String PARAM_APPID = "APPID";
                final String PARAM_QUERY = "q";
                final String PARAM_FORMAT = "mode";
                final String PARAM_UNITS = "units";
                final String PARAM_DAYS = "cnt";

                Uri uriBuilder = Uri.parse(BASE_URL_FORECAST).buildUpon()
                    .appendQueryParameter(PARAM_APPID, apiKey)
                    .appendQueryParameter(PARAM_QUERY, params[0])
                    .appendQueryParameter(PARAM_FORMAT, format)
                    .appendQueryParameter(PARAM_UNITS, units)
                    .appendQueryParameter(PARAM_DAYS, Integer.toString(numDays))
                    .build();
                URL url = new URL(uriBuilder.toString());
                Log.v(LOG_TAG, "Built URI = " + url);

                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                forecastJsonStr = buffer.toString();
                Log.v(LOG_TAG, "JSON Reply = " + forecastJsonStr);

                try {
                    weatherData = WeatherDataParser.getWeatherDataFromJson(forecastJsonStr, numDays);
                } catch (JSONException jse) {
                    Log.e(LOG_TAG, "Error parsing weather data JSON", jse);
                    return null;
                }
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                return null;
            } finally{
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }
            return weatherData;
        }

        @Override
        protected void onPostExecute(String[] strings) {
            if (strings != null) {
                mForecastAdapter.clear();
                mForecastAdapter.addAll(Arrays.asList(strings));
            }
        }
    }


}
