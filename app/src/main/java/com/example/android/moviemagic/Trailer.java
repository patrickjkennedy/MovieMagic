package com.example.android.moviemagic;

/**
 * Created by pkennedy on 2/18/18.
 */

public class Trailer {

    private String mName, mKey;

    public Trailer(String mName, String mKey){
        this.mName = mName;
        this.mKey = mKey;
    }

    public String getmName() { return mName; }

    public String getmKey() { return mKey; }
}
