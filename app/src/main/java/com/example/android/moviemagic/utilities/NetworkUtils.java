package com.example.android.moviemagic.utilities;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by pkennedy on 1/20/18.
 */

public final class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    private static final String MOVIEDB_BASE_URL =
            "https://api.themoviedb.org/3/movie";

    private static final String MOST_POPULAR_PATH =
            "popular";

    private static final String TOP_RATED_PATH =
            "top_rated";

    private static final String VIDEOS =
            "videos";

    private static final String REVIEWS =
            "reviews";

    // Parameters for the API

    //TODO: Insert your API key into the empty string provided below.
    private static final String apiKey = "";

    private static final String APIKEY_PARAM = "api_key";

    /**
     * Builds the URL to fetch the most popular movies from themoviedb.org API.
     *
     * @return The URL to query the server
     */

    public static URL buildPopularUrl() {
        Uri builtUri = Uri.parse(MOVIEDB_BASE_URL).buildUpon()
                .appendPath(MOST_POPULAR_PATH)
                .appendQueryParameter(APIKEY_PARAM, apiKey)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    /**
     * Builds the URL to fetch the top rated movies from themoviedb.org API.
     *
     * @return The URL to query the server
     */

    public static URL buildTopRatedUrl() {
        Uri builtUri = Uri.parse(MOVIEDB_BASE_URL).buildUpon()
                .appendPath(TOP_RATED_PATH)
                .appendQueryParameter(APIKEY_PARAM, apiKey)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    /**
     * Builds the URL to fetch the videos from themoviedb.org API for the given ID
     *
     * @param id the ID for a film (found in movie object)
     *
     * @return The URL to query the server
     */

    public static URL buildVideosUrl(String id) {
        Uri builtUri = Uri.parse(MOVIEDB_BASE_URL).buildUpon()
                .appendPath(id)
                .appendPath(VIDEOS)
                .appendQueryParameter(APIKEY_PARAM, apiKey)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    /**
     * Builds the URL to fetch the reviews from themoviedb.org API for the given ID
     *
     * @param id the ID for a film (found in movie object)
     *
     * @return The URL to query the server
     */

    public static URL buildReviewsUrl(String id) {
        Uri builtUri = Uri.parse(MOVIEDB_BASE_URL).buildUpon()
                .appendPath(id)
                .appendPath(REVIEWS)
                .appendQueryParameter(APIKEY_PARAM, apiKey)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }


}
