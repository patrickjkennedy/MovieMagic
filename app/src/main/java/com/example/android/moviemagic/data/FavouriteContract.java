package com.example.android.moviemagic.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by pkennedy on 2/23/18.
 */

public class FavouriteContract {

    // Define the authority
    public static final String AUTHORITY = "com.example.android.moviemagic";

    // The base content URI
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    // This is the path for the "favourites" directory
    public static final String PATH_FAVOURITES = "favourites";

    public static final class FavouriteEntry implements BaseColumns {

        // TaskEntry content URI = base content URI + path
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVOURITES).build();

        // Table name
        public static final String TABLE_NAME = "favourites";

        // Column names
        public static final String COLUMN_MOVIE_TITLE = "title";
        public static final String COLUMN_MOVIE_ID = "movie_api_id";

    }
}
