package com.example.android.moviemagic;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.moviemagic.utilities.MovieJsonUtils;
import com.example.android.moviemagic.utilities.NetworkUtils;

import org.w3c.dom.Text;

import java.net.URL;

public class MainActivity extends AppCompatActivity{

    private RecyclerView recyclerView;

    private GridLayoutManager layoutManager;

    private DataAdapter dataAdapter;

    private ProgressBar loadingIndicator;

    private TextView errorMessageDisplay;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_movie_posters);

        /* This TextView is used to display errors and will be hidden if there are no errors */
        errorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);

        layoutManager = new GridLayoutManager(MainActivity.this, 2);

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(layoutManager);

        dataAdapter = new DataAdapter(this);

        recyclerView.setAdapter(dataAdapter);

        /*
         * The ProgressBar that will indicate to the user that we are loading data. It will be
         * hidden when no data is loading.
         *
         * Please note: This so called "ProgressBar" isn't a bar by default. It is more of a
         * circle. We didn't make the rules (or the names of Views), we just follow them.
         */
        loadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        /* Once all of our views are setup, we can load the movie data. */
        loadMovieData();
    }

    private void loadMovieData(){
        showMoviePosterDataView();

        new FetchMovieDataTask().execute();
    }

    /**
     * This method will make the View for the movie poster images visible and
     * hide the error message.
     * <p>
     * Since it is okay to redundantly set the visibility of a View, we don't
     * need to check whether each view is currently visible or invisible.
     */
    private void showMoviePosterDataView() {
        /* First, make sure the error is invisible */
        errorMessageDisplay.setVisibility(View.INVISIBLE);
        /* Then, make sure the weather data is visible */
        recyclerView.setVisibility(View.VISIBLE);
    }

    /**
     * This method will make the error message visible and hide the movie poster
     * View.
     * <p>
     * Since it is okay to redundantly set the visibility of a View, we don't
     * need to check whether each view is currently visible or invisible.
     */
    private void showErrorMessage() {
        /* First, hide the currently visible data */
        recyclerView.setVisibility(View.INVISIBLE);
        /* Then, show the error */
        errorMessageDisplay.setVisibility(View.VISIBLE);
    }

    public class FetchMovieDataTask extends AsyncTask<Void, Void, String[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected String[] doInBackground(Void... params) {

            URL movieRequestUrl = NetworkUtils.buildPopularUrl();

            try {
                String jsonMovieResponse = NetworkUtils.getResponseFromHttpUrl(movieRequestUrl);
                String[] posterPaths = MovieJsonUtils
                        .getPosterPathsFromJson(MainActivity.this, jsonMovieResponse);

                Log.v("MainActivity", "JSON: " + jsonMovieResponse);
                return posterPaths;

            } catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String[] posterPaths) {
            loadingIndicator.setVisibility(View.INVISIBLE);
            if (posterPaths != null) {
                showMoviePosterDataView();
                dataAdapter.setPosterPaths(posterPaths);
            } else {
                showErrorMessage();
            }
        }

    }

}
