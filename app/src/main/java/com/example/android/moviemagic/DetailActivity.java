package com.example.android.moviemagic;

import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.android.moviemagic.data.FavouriteContract;
import com.example.android.moviemagic.utilities.MovieJsonUtils;
import com.example.android.moviemagic.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;
import java.net.URL;
import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity implements
        TrailerAdapter.TrailerAdapterOnClickHandler{

    private String mId;

    private RecyclerView mTrailersRecyclerView;

    private View mTrailerReviewDivider;

    private TextView mTrailerHeading;

    private LinearLayoutManager mTrailersLayoutManager;

    private TrailerAdapter mTrailerAdapter;

    private ProgressBar mTrailerLoadingIndicator;

    private RecyclerView mReviewsRecyclerView;

    private TextView mReviewErrorMessageDisplay;

    private TextView mReviewHeading;

    private LinearLayoutManager mReviewsLayoutManager;

    private ReviewAdapter mReviewAdapter;

    private TextView mTrailerErrorMessageDisplay;

    private CheckBox mFavouriteCheckbox;

    private String mMovieTitle;

    // Constants for logging
    private static final String TAG = DetailActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ImageView mPoster;
        TextView mTitle, mUserRating, mReleaseDate, mOverview;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intentThatStartedThisActivity = getIntent();
        Movie movie = (Movie) intentThatStartedThisActivity.getSerializableExtra("movie");

        /* Find the image and text views in the Detail Activity*/
        mPoster = (ImageView) findViewById(R.id.iv_poster);
        mTitle = (TextView) findViewById(R.id.tv_title);
        mUserRating = (TextView) findViewById(R.id.tv_rating);
        mReleaseDate = (TextView) findViewById(R.id.tv_release_date);
        mOverview = (TextView) findViewById(R.id.tv_overview);

        /* Get the checkbox */
        mFavouriteCheckbox = (CheckBox) findViewById(R.id.cb_favorite);

        /* Bind the data to the views above */

        Picasso.with(this)
                .load(movie.getmPosterPath())
                .into(mPoster);

        /* Set the global variables we will need for adding the movie to Favourites */
        mId = movie.getmId();
        mMovieTitle = movie.getmTitle();

        mTitle.setText(movie.getmTitle());
        mUserRating.setText(movie.getmRating());
        mReleaseDate.setText(movie.getmReleaseDate());
        mOverview.setText(movie.getmSynopsis());

        /* These TextViews are used to display errors and will be hidden if there are no errors */
        mTrailerErrorMessageDisplay = (TextView) findViewById(R.id.tv_trailer_error_message_display);
        mReviewErrorMessageDisplay = (TextView) findViewById(R.id.tv_review_error_message_display);


        /* Setup the Trailers Recyclerview */
        mTrailersRecyclerView = (RecyclerView) findViewById(R.id.rv_trailers);

        /* Get the divider and heading for the trailer section */
        mTrailerReviewDivider = (View) findViewById(R.id.v_hbar2);
        mTrailerHeading = (TextView) findViewById(R.id.tv_detail_trailers);
        mReviewHeading = (TextView) findViewById(R.id.tv_detail_reviews);

        mTrailersLayoutManager = new LinearLayoutManager(DetailActivity.this);

        mTrailersRecyclerView.setHasFixedSize(true);

        mTrailersRecyclerView.setLayoutManager(mTrailersLayoutManager);

        mTrailerAdapter = new TrailerAdapter(this, this);

        mTrailersRecyclerView.setAdapter(mTrailerAdapter);

        /* Loading indicator for trailers */
        mTrailerLoadingIndicator = (ProgressBar) findViewById(R.id.pb_trailer_loading_indicator);

        /* Setup the Reviews Recyclerview */
        mReviewsRecyclerView = (RecyclerView) findViewById(R.id.rv_reviews);

        mReviewsLayoutManager = new LinearLayoutManager(DetailActivity.this);

        mReviewsRecyclerView.setHasFixedSize(true);

        mReviewAdapter = new ReviewAdapter(this);

        mReviewsRecyclerView.setLayoutManager(mReviewsLayoutManager);

        mReviewsRecyclerView.setAdapter(mReviewAdapter);

        //TODO: Check to see if film is a Favourite, if yes, set checkbox to checked


        /* Fetch the trailer data from the API */
        loadTrailerData();

        /* Fetch the review data from the API */
        loadReviewData();

    }



    @Override
    public void onClick(Trailer trailer) {
        Context context = this;
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + trailer.getmKey()));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + trailer.getmKey()));
        try {
            context.startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            context.startActivity(webIntent);
        }
    }

    public void onClickToggleFavourite(View view){
        // If Favourites Checkbox is checked
        if (mFavouriteCheckbox.isChecked()){
            // Store the Movie API ID and Movie name in a content values object
            String mTitleAsString = mMovieTitle;
            int mIdAsInt = Integer.parseInt(mId);

            ContentValues contentValues = new ContentValues();

            contentValues.put(FavouriteContract.FavouriteEntry.COLUMN_MOVIE_TITLE, mTitleAsString);
            contentValues.put(FavouriteContract.FavouriteEntry.COLUMN_MOVIE_ID, mIdAsInt);

            // Insert new favourite via a ContentResolver
            Uri uri = getContentResolver().insert(FavouriteContract.FavouriteEntry.CONTENT_URI, contentValues);

            // Display 'Added to Favourites' in a Toast
            if(uri != null) {
                Toast.makeText(getBaseContext(), "Added to Favourites: " + uri.toString(), Toast.LENGTH_LONG).show();
            }
        }

    }

    private void loadTrailerData(){
        new FetchTrailerDataTask().execute();
    }

    private void loadReviewData(){
        new FetchReviewDataTask().execute();
    }


    /**
     * This method will make the View for the movie poster images visible and
     * hide the error message.
     * <p>
     * Since it is okay to redundantly set the visibility of a View, we don't
     * need to check whether each view is currently visible or invisible.
     */
    private void showTrailerRecyclerView() {
        /* First, make sure the error is invisible */
        mTrailerErrorMessageDisplay.setVisibility(View.INVISIBLE);
        /* Then, make sure the trailer data is visible */
        mTrailersRecyclerView.setVisibility(View.VISIBLE);
    }

    /**
     * This method will make the error message visible and hide the movie poster
     * View.
     * <p>
     * Since it is okay to redundantly set the visibility of a View, we don't
     * need to check whether each view is currently visible or invisible.
     */
    private void showTrailerErrorMessage() {
        /* First, hide the trailer section */
        mTrailersRecyclerView.setVisibility(View.INVISIBLE);
        mTrailerHeading.setVisibility(View.INVISIBLE);
        mTrailerReviewDivider.setVisibility(View.INVISIBLE);

        /* Then, show the error */
        mTrailerErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    /**
     * This method will make the View for the movie poster images visible and
     * hide the error message.
     * <p>
     * Since it is okay to redundantly set the visibility of a View, we don't
     * need to check whether each view is currently visible or invisible.
     */
    private void showReviewRecyclerView() {
        /* First, make sure the error is invisible */
        mReviewErrorMessageDisplay.setVisibility(View.INVISIBLE);
        /* Then, make sure the trailer data is visible */
        mReviewsRecyclerView.setVisibility(View.VISIBLE);
    }

    /**
     * This method will make the error message visible and hide the movie poster
     * View.
     * <p>
     * Since it is okay to redundantly set the visibility of a View, we don't
     * need to check whether each view is currently visible or invisible.
     */
    private void showReviewErrorMessage() {
        /* First, hide the trailer section */
        mReviewsRecyclerView.setVisibility(View.INVISIBLE);
        mReviewHeading.setVisibility(View.INVISIBLE);
        mTrailerReviewDivider.setVisibility(View.INVISIBLE);

        /* Then, show the error */
        mReviewErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    public class FetchTrailerDataTask extends AsyncTask<Void, Void, ArrayList<Trailer>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mTrailerLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<Trailer> doInBackground(Void... params) {

            URL videoRequestUrl = NetworkUtils.buildVideosUrl(mId);

            try {
                String jsonVideoResponse = NetworkUtils.getResponseFromHttpUrl(videoRequestUrl);
                ArrayList<Trailer> trailers = MovieJsonUtils
                        .extractTrailers(DetailActivity.this, jsonVideoResponse);
                return trailers;

            } catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<Trailer> trailers) {
            mTrailerLoadingIndicator.setVisibility(View.INVISIBLE);
            if (trailers.size() != 0) {
                showTrailerRecyclerView();
                mTrailerAdapter.setTrailers(trailers);
            } else {
                showTrailerErrorMessage();
            }
        }

    }

    public class FetchReviewDataTask extends AsyncTask<Void, Void, ArrayList<Review>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<Review> doInBackground(Void... params) {

            URL reviewRequestUrl = NetworkUtils.buildReviewsUrl(mId);

            try {
                String jsonReviewResponse = NetworkUtils.getResponseFromHttpUrl(reviewRequestUrl);
                ArrayList<Review> reviews = MovieJsonUtils
                        .extractReviews(DetailActivity.this, jsonReviewResponse);
                return reviews;

            } catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<Review> reviews) {
            if (reviews.size() != 0) {
                showReviewRecyclerView();
                mReviewAdapter.setReviews(reviews);
            } else {
                showReviewErrorMessage();
            }
        }

    }
}
