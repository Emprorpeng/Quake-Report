package com.example.empro.quake_report;

import android.util.Log;

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
import java.util.ArrayList;
public class QueryUtils {

    /** Sample JSON response for a USGS query */

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Return a list of {@link Earthquake} objects that has been built up from
     * parsing a JSON response.
     */
    private static URL CreateUrl(String input){
        URL url = null;
        try {
            url = new URL(input);
        } catch (MalformedURLException e) {
            Log.e("CreateUrl","Malformed URL");
        }
        return url;
    }
    private static String MakeHTTPRequest(URL url) throws IOException {
        String JSONResponse = "";
        HttpURLConnection URLconnection = null;
        InputStream inputStream = null;
        try
        {
            URLconnection = (HttpURLConnection) url.openConnection();
            URLconnection.setConnectTimeout(15000);
            URLconnection.setReadTimeout(10000);
            URLconnection.setRequestMethod("GET");
            URLconnection.connect();
            if (URLconnection.getResponseCode() == 200) {
                inputStream = URLconnection.getInputStream();
                JSONResponse = getJSONString(inputStream);
            } else {
                Log.e("Error Response code :", "" + URLconnection.getResponseCode());
            }
        }
        catch (IOException e) {
            Log.e("Make HTTP request", "Error while making HTTP request");
        }
        finally {
            if(URLconnection!= null){
                URLconnection.disconnect();
            }
            if ((inputStream!=null)){
                inputStream.close();
            }
            return JSONResponse;
        }
    }
    public static ArrayList<Earthquake> extractEarthquakes(String JSON_string) throws IOException {

        // Create an empty ArrayList that we can start adding earthquakes to
        URL url = CreateUrl(JSON_string);
        String JSONresponse = MakeHTTPRequest(url);
        ArrayList<Earthquake> earthquakes = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            JSONObject JSONroot = new JSONObject(JSONresponse);
            JSONArray Features = JSONroot.getJSONArray("features");
            for (int i=0;i<Features.length();i++) {
                JSONObject earthquake = Features.getJSONObject(i);
                JSONObject properties = earthquake.getJSONObject("properties");
                int EarthquakeMagnitude = properties.getInt("mag");
                String EarthquakePlace = properties.getString("place");
                long EarthquakeDate = properties.getLong("time");
                String EarthquakeURL = properties.getString("url");
                Earthquake e = new Earthquake(EarthquakeMagnitude,EarthquakePlace,EarthquakeDate,EarthquakeURL);
                earthquakes.add(e);
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

    private static String getJSONString(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

}

