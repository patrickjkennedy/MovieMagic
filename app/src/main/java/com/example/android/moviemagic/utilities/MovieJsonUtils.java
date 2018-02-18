package com.example.android.moviemagic.utilities;

import android.content.Context;
import android.util.Log;

import com.example.android.moviemagic.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by pkennedy on 1/20/18.
 */

public final class MovieJsonUtils {

    public static String[] getPosterPathsFromJson(Context context, String movieJsonStr)
            throws JSONException {

        final String BASE_POSTER_PATH = "https://image.tmdb.org/t/p/w500/";

        /* A string array to hold each movie's poster path */
        String[] posterPaths = null;

        try{
            JSONObject movieJson = new JSONObject(movieJsonStr);

            /* Get the results object */
            JSONArray results = movieJson.getJSONArray("results");

            posterPaths = new String[results.length()];

            /* Loop through each result in the array */
            for(int i = 0; i < results.length(); i++){
                JSONObject r = results.getJSONObject(i);
                String posterPath = r.getString("poster_path");
                posterPaths[i] = BASE_POSTER_PATH + posterPath;
            }

        } catch (JSONException e){
            Log.e("MovieJsonUtils", "Problem parsing the Movie DB API results", e);
        }

        return posterPaths;
    }

    public static ArrayList<Movie> extractMovies(Context context, String movieJsonStr)
            throws JSONException {

        // Create an empty ArrayList that we can start adding movies to
        ArrayList<Movie> movies = new ArrayList<>();

        final String BASE_POSTER_PATH = "https://image.tmdb.org/t/p/w500/";

        // Try to parse the jsonResponse. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            JSONObject movieJson = new JSONObject(movieJsonStr);

            /* Get the results object */
            JSONArray results = movieJson.getJSONArray("results");

            /* Loop through each result in the array */
            for(int i = 0; i < results.length(); i++){
                JSONObject r = results.getJSONObject(i);
                String id = r.getString("id");
                String title = r.getString("title");
                String releaseDate = r.getString("release_date");
                String posterPath = r.getString("poster_path");
                String rating = r.getString("vote_average");
                String summary = r.getString("overview");

                /* Create a Movie object using the data above */
                movies.add(new Movie(id, title, releaseDate, BASE_POSTER_PATH + posterPath, rating, summary));
            }
        }  catch (JSONException e){
            Log.e("MovieJsonUtils", "Problem parsing the Movie DB API results", e);
        }
        /* Return the arraylist of movies */
        return movies;
    }

    public static ArrayList<String> getTrailerKeysFromJson(Context context, String videoJsonStr)
            throws JSONException{

        /* A string array to hold each trailer's key */
        ArrayList<String> keys = null;

        try{
            JSONObject videoJson = new JSONObject(videoJsonStr);

            /* Get the results object */
            JSONArray results = videoJson.getJSONArray("results");

            keys = new ArrayList<String>();

            /* Loop through each result in the array */
            for(int i = 0; i < results.length(); i++){
                JSONObject r = results.getJSONObject(i);
                if(r.getString("type").equals("Trailer")){
                    String key = r.getString("key");
                    keys.add(key);
                }
            }

        } catch (JSONException e){
            Log.e("MovieJsonUtils", "Problem parsing the Movie DB API Video results", e);
        }
        return keys;
    }
}
