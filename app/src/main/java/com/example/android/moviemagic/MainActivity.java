package com.example.android.moviemagic;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.moviemagic.utilities.MovieJsonUtils;
import com.example.android.moviemagic.utilities.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements DataAdapter.DataAdapterOnClickHandler{

    private RecyclerView mRecyclerView;

    private GridLayoutManager mLayoutManager;

    private DataAdapter mDataAdapter;

    private ProgressBar mLoadingIndicator;

    private TextView mErrorMessageDisplay;

    private static Boolean mTopRatedIsSelected = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_movie_posters);

        /* This TextView is used to display errors and will be hidden if there are no errors */
        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);

        mLayoutManager = new GridLayoutManager(MainActivity.this, 2);

        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setLayoutManager(mLayoutManager);

        mDataAdapter = new DataAdapter(this, this);

        mRecyclerView.setAdapter(mDataAdapter);

        /*
         * The ProgressBar that will indicate to the user that we are loading data. It will be
         * hidden when no data is loading.
         *
         * Please note: This so called "ProgressBar" isn't a bar by default. It is more of a
         * circle. We didn't make the rules (or the names of Views), we just follow them.
         */
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        /* Once all of our views are setup, we can load the movie data. */
        loadMovieData();
    }

    @Override
    public void onClick(Movie movie) {
        Context context = this;
        Class destinationClass = DetailActivity.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);

        Bundle bundle = new Bundle();
        bundle.putSerializable("movie", movie);

        intentToStartDetailActivity.putExtras(bundle);
        startActivity(intentToStartDetailActivity);
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
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        /* Then, make sure the weather data is visible */
        mRecyclerView.setVisibility(View.VISIBLE);
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
        mRecyclerView.setVisibility(View.INVISIBLE);
        /* Then, show the error */
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    public class FetchMovieDataTask extends AsyncTask<Void, Void, ArrayList<Movie>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<Movie> doInBackground(Void... params) {

            URL movieRequestUrl;

            if(mTopRatedIsSelected){
                movieRequestUrl = NetworkUtils.buildTopRatedUrl();
            } else{
                movieRequestUrl = NetworkUtils.buildPopularUrl();
            }

            try {
                String jsonMovieResponse = NetworkUtils.getResponseFromHttpUrl(movieRequestUrl);
                ArrayList<Movie> movies = MovieJsonUtils
                        .extractMovies(MainActivity.this, jsonMovieResponse);
                return movies;

            } catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> movies) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (movies != null) {
                showMoviePosterDataView();
                mDataAdapter.setMovies(movies);
            } else {
                showErrorMessage();
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* Use AppCompatActivity's method getMenuInflater to get a handle on the menu inflater */
        MenuInflater inflater = getMenuInflater();
        /* Use the inflater's inflate method to inflate our menu layout to this menu */
        inflater.inflate(R.menu.sort, menu);
        /* Return true so that the menu is displayed in the Toolbar */
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_popular) {
            mTopRatedIsSelected = false;
            mDataAdapter.setMovies(null);
            loadMovieData();
            return true;
        }

        if (id == R.id.action_top_rated) {
            mTopRatedIsSelected = true;
            mDataAdapter.setMovies(null);
            loadMovieData();
            return true;
        }

        if (id == R.id.action_favourites) {
            Intent intent = new Intent(this, FavouritesActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

}
