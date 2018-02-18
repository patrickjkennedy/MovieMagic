package com.example.android.moviemagic;

/**
 * Created by pkennedy on 2/18/18.
 */

public class Review {

    private String mAuthor, mContent;

    public Review(String mAuthor, String mContent) {
        this.mAuthor = mAuthor;
        this.mContent = mContent;
    }

    public String getmAuthor() { return mAuthor; }

    public String getmContent() { return mContent; }

}
