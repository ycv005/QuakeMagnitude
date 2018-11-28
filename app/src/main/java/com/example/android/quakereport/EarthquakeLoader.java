package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;
import android.util.Log;

import java.util.List;

public class EarthquakeLoader extends AsyncTaskLoader<List<ArrayElements>> {
    /*We define the generic type because we are telling this class that <D> is the type that it will accept as the input data type*/

    /** Tag for log messages */
    private static final String LOG_TAG = EarthquakeLoader.class.getName();

    /** Query URL */
    private String mUrl;
    /**
     * Constructs a new {@link EarthquakeLoader}.
     *
     * @param context of the activity
     * @param url to load data from
     */
    public EarthquakeLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    /*We need to override the onStartLoading to call forceLoad method in order to kick the loadingBackground*/
    @Override
    protected void onStartLoading() {
        Log.i(LOG_TAG,"OnStartLoading");
        forceLoad();
    }

    /**
     * This is on a background thread.
     */
    @Override
    public List<ArrayElements> loadInBackground() {
        Log.i(LOG_TAG,"loadInBackground");
        if (mUrl == null) {
            return null;
        }
        // Perform the network request, parse the response, and extract a list of earthquakes.
        List<ArrayElements> earthquakes = QueryUtils.fetchEarthquakeData(mUrl);
        return earthquakes;
    }
}

