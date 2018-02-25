package com.example.android.moviemagic;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.moviemagic.data.FavouriteContract;

/**
 * Created by pkennedy on 2/25/18.
 */

public class FavouriteCursorAdapter extends RecyclerView.Adapter<FavouriteCursorAdapter.FavouriteViewHolder>{

    private Cursor mCursor;
    private Context mContext;

    /**
     * Constructor for the FavouriteCursorAdapter that initializes the Context.
     *
     * @param mContext the current Context
     */
    public FavouriteCursorAdapter(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * Called when ViewHolders are created to fill a RecyclerView.
     *
     * @return A new TaskViewHolder that holds the view for each task
     */
    @Override
    public FavouriteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // Inflate the task_layout to a view
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.favourite_layout, parent, false);

        return new FavouriteViewHolder(view);
    }

    /**
     * Called by the RecyclerView to display data at a specified position in the Cursor.
     *
     * @param holder The ViewHolder to bind Cursor data to
     * @param position The position of the data in the Cursor
     */
    @Override
    public void onBindViewHolder(FavouriteViewHolder holder, int position) {

        // Indices for the _id, and title columns
        int idIndex = mCursor.getColumnIndex(FavouriteContract.FavouriteEntry._ID);
        int titleIndex = mCursor.getColumnIndex(FavouriteContract.FavouriteEntry.COLUMN_MOVIE_TITLE);

        mCursor.moveToPosition(position); // get to the right location in the cursor

        // Determine the values of the wanted data
        final int id = mCursor.getInt(idIndex);
        String title = mCursor.getString(titleIndex);

        Log.v("FavouritesActivity", "Title: " + title);

        // Set values
        holder.itemView.setTag(id);
        holder.favouriteTitleView.setText(title);

    }

    /**
     * Returns the number of items to display.
     */
    @Override
    public int getItemCount() {
        if (mCursor == null) {
            return 0;
        }
        return mCursor.getCount();
    }


    /**
     * When data changes and a re-query occurs, this function swaps the old Cursor
     * with a newly updated Cursor (Cursor c) that is passed in.
     */
    public Cursor swapCursor(Cursor c) {
        // check if this cursor is the same as the previous cursor (mCursor)
        if (mCursor == c) {
            return null; // bc nothing has changed
        }
        Cursor temp = mCursor;
        this.mCursor = c; // new cursor value assigned

        //check if this is a valid cursor, then update the cursor
        if (c != null) {
            this.notifyDataSetChanged();
        }
        return temp;
    }

    class FavouriteViewHolder extends RecyclerView.ViewHolder {

        TextView favouriteTitleView;

        /**
         * Constructor for the FavouriteViewHolders.
         *
         * @param itemView The view inflated in onCreateViewHolder
         */
        public FavouriteViewHolder(View itemView) {
            super(itemView);
            favouriteTitleView = (TextView) itemView.findViewById(R.id.tv_favourite_title);
        }

    }

}
