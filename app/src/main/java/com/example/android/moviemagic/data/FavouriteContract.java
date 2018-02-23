package com.example.android.moviemagic.data;

import android.provider.BaseColumns;

/**
 * Created by pkennedy on 2/23/18.
 */

public class FavouriteContract {

    public static final class FavouriteEntry implements BaseColumns {

        // Table name
        public static final String TABLE_NAME = "favourites";

        // Column names
        public static final String COLUMN_MOVIE_TITLE = "title";
        public static final String COLUMN_MOVIE_ID = "movie_api_id";

    }
}
