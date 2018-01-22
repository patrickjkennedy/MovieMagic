package com.example.android.moviemagic;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class DetailActivity extends AppCompatActivity {

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

        mTitle.setText(movie.getmTitle());
        mUserRating.setText(movie.getmRating());
        mReleaseDate.setText(movie.getmReleaseDate());
        mOverview.setText(movie.getmSynopsis());
    }
}
