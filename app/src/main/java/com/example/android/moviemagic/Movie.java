package com.example.android.moviemagic;

import java.io.Serializable;

/**
 * Created by pkennedy on 1/21/18.
 */

public class Movie implements Serializable{

    private String mId, mTitle, mReleaseDate, mPosterPath, mRating, mSynopsis;

    public Movie(String mId, String mTitle, String mReleaseDate, String mPosterPath, String mRating, String mSynopsis) {
        this.mId = mId;
        this.mTitle = mTitle;
        this.mReleaseDate = mReleaseDate;
        this.mPosterPath = mPosterPath;
        this.mRating = mRating;
        this.mSynopsis = mSynopsis;
    }

    public String getmId() {
        return mId;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmReleaseDate() {
        return mReleaseDate;
    }

    public String getmPosterPath() {
        return mPosterPath;
    }

    public String getmRating() {
        return mRating;
    }

    public String getmSynopsis() {
        return mSynopsis;
    }
}
