/*
 * Copyright (c) 2016. Libero Strategies, LLC - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and Confidential
 */

package com.example.android.sunshine.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
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
    public void onStart() {
        super.onStart();
        updateWeather();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        final List<String> weekForecast = new ArrayList<String>();

        // Initialize adapter.
        mForecastAdapter = new ArrayAdapter<String>(
                // context - Contains global info about app environment. Access system resources.
                getActivity(),
                // layout - xml layout file
                R.layout.list_item_forecast,
                // view - view id
                R.id.list_item_forecast_textview,
                // list of com.example.android.sunshine.app.data
                weekForecast);

        ListView listViewForecast = (ListView)rootView.findViewById(R.id.listview_forecast);
        listViewForecast.setAdapter(mForecastAdapter);

        listViewForecast.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Context context = getActivity();
//                CharSequence text = weekForecast.get(position);
//                int duration = Toast.LENGTH_SHORT;
//
//                Toast toast = Toast.makeText(context, text, duration);
//                toast.show();

                Intent detailsIntent = new Intent(getActivity(), DetailActivity.class)
                        .putExtra(Intent.EXTRA_TEXT, weekForecast.get(position));
                startActivity(detailsIntent);
            }
        });
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.forecastfragment, menu);
    }

    private void updateWeather() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Resources resources = getActivity().getResources();
        String locationPref = sharedPref.getString(resources.getString(R.string.pref_location_key),
                resources.getString(R.string.pref_location_default));
        new FetchWeatherTask(getActivity(), mForecastAdapter).execute(locationPref);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                // Call the FetchWeatherTask.
                updateWeather();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
