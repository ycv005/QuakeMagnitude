package com.example.android.quakereport;

import android.util.Log;
import android.webkit.JsPromptResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class QueryUtils {

    public static final String LOG_TAG = QueryUtils.class.getSimpleName();

    /** Sample JSON response for a USGS query */
    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */

    //Creating a inner class that will request the
    private QueryUtils() {
    }

    /*Method for Fetching Earthquake Data*/
    public static List<ArrayElements> fetchEarthquakeData(String urls){
        Log.i(LOG_TAG,"FetchingEarthquakeData");
        /*Step - 1- Creating URL as the URL object*/
        URL url = createURL(urls);
        /*step -2- Perform the HTTP request and get back the JSON Response*/
        String JsonResponse = "";
        try{
            JsonResponse = makeHttpRequest(url);
        }
        catch (IOException e){
            Log.e(LOG_TAG,"Error Occur while fetching Earthquake Data");
        }
        List<ArrayElements> currentArrayList = extractFeaturefromJSON(JsonResponse);
        return currentArrayList;
    }

    private static  String getDateString(long timeInMilliseconds) {
        SimpleDateFormat formatter = new SimpleDateFormat("EEE, d MMM yyyy 'at' HH:mm:ss z");
        return formatter.format(timeInMilliseconds);
    }

    private static URL createURL(String urls){
        /*Avoiding the url mistake, and handling the URL exception*/
        URL url = null;
        try{
            url = new URL(urls);
        }
        catch (MalformedURLException e){
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL urls) throws IOException{
        String JsonResponse = "";
    HttpURLConnection urlConnection=null;
    InputStream inputStream=null;
    if (urls == null){
        return JsonResponse;
    }
    try{
        urlConnection = (HttpURLConnection)urls.openConnection();
        int response_code = urlConnection.getResponseCode();
        if(response_code == 200){
            urlConnection.connect();
            inputStream = urlConnection.getInputStream();
            JsonResponse = readFromStream(inputStream);
        }
    }
    catch (IOException e){
        Log.e(LOG_TAG,"Errow while makeing HTTP request");
    }finally {
        if (urlConnection != null) {
            urlConnection.disconnect();
        }
        if (inputStream != null) {
            // function must handle java.io.IOException here
            inputStream.close();
        }
    }
    return  JsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException{
        /*StringBuilder is like the String but more powerful as it is mutable*/
        StringBuilder output = new StringBuilder();
        if(inputStream!=null){
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
    /**
     * Return a list of {@link ArrayElements} objects that has been built up from
     * parsing a JSON response.
     */
    public static ArrayList<ArrayElements> extractFeaturefromJSON(String earthquakeJSON) {

        if(earthquakeJSON == null){
            return null; //*Returning null, as we have to return the object/class instance type which can be replace with the null*//*
        }

        // Create an empty ArrayList that we can start adding earthquakes to
        ArrayList<ArrayElements> earthquakes = new ArrayList<ArrayElements>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            // TODO: Parse the response given by the SAMPLE_JSON_RESPONSE string and
            // build up a list of Earthquake objects with the corresponding data.
            //Converting JSON response that was in string into root JSONObject by passing JSON response
            JSONObject mainJsonObject = new JSONObject(earthquakeJSON);
            JSONArray featureJSONArray = mainJsonObject.getJSONArray("features");
            for(int i=0;i<featureJSONArray.length();i++){
                JSONObject JsonObject_one = featureJSONArray.getJSONObject(i);
                JSONObject JsonObject_two = JsonObject_one.getJSONObject("properties");
                long mag = JsonObject_two.getLong("mag");
                String location = JsonObject_two.getString("place");
                long time = JsonObject_two.getLong("time");
                String url = JsonObject_two.getString("url");
                String dateToDisplay = getDateString(time);
                //creating new earthquake object from these 3 and will return it.
                ArrayElements earthq = new ArrayElements(mag,location,dateToDisplay,url);
                earthquakes.add(earthq);
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return earthquakes;
    }

}