package com.example.android.moviemagic.utilities;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
}
