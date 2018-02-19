package com.example.android.moviemagic;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.moviemagic.utilities.MovieJsonUtils;
import com.example.android.moviemagic.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity implements TrailerAdapter.TrailerAdapterOnClickHandler{

    private String mId;

    private RecyclerView mTrailersRecyclerView;

    private LinearLayoutManager mTrailersLayoutManager;

    private TrailerAdapter mTrailerAdapter;

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

        /* Bind the data to the views above */

        Picasso.with(this)
                .load(movie.getmPosterPath())
                .into(mPoster);

        mId = movie.getmId();

        mTitle.setText(movie.getmTitle());
        mUserRating.setText(movie.getmRating());
        mReleaseDate.setText(movie.getmReleaseDate());
        mOverview.setText(movie.getmSynopsis());

        /* Setup the Trailers Recyclerview */
        mTrailersRecyclerView = (RecyclerView) findViewById(R.id.rv_trailers);

        mTrailersLayoutManager = new LinearLayoutManager(DetailActivity.this);

        mTrailersRecyclerView.setHasFixedSize(true);

        mTrailersRecyclerView.setLayoutManager(mTrailersLayoutManager);

        mTrailerAdapter = new TrailerAdapter(this, this);

        mTrailersRecyclerView.setAdapter(mTrailerAdapter);

        /* Fetch the trailer data from the API */
        loadTrailerData();

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

    private void loadTrailerData(){
        new FetchTrailerDataTask().execute();
    }

    public class FetchTrailerDataTask extends AsyncTask<Void, Void, ArrayList<Trailer>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
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
            if (trailers != null) {
                mTrailerAdapter.setTrailers(trailers);
            } else {
                System.out.println("Didn't work");
            }
        }

    }

}
