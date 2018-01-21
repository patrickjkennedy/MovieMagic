package com.example.android.moviemagic;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by pkennedy on 1/18/18.
 */

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.dataHolder>{

    private Context mContext;

    private ArrayList<Movie> mMovies = new ArrayList<>();

    /*
    * An on-click handler that we've defined to make it easy for an Activity to interface with
    * our RecyclerView
    */

    private final DataAdapterOnClickHandler mClickHandler;

    /**
     * The interface that receives onClick messages.
     */
    public interface DataAdapterOnClickHandler {
        void onClick(Movie movie);
    }

    public DataAdapter(DataAdapterOnClickHandler clickHandler, Context context) {
        this.mClickHandler = clickHandler;
        this.mContext = context;
    }

    @Override
    public dataHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_layout, null);
        dataHolder movieHolder = new dataHolder(layout);
        return movieHolder;
    }

    @Override
    public void onBindViewHolder(dataHolder holder, int position) {
        Picasso.with(mContext)
                .load(mMovies.get(position).getmPosterPath())
                .into(holder.img);
    }

    @Override
    public int getItemCount() {
        if (null == mMovies) return 0;
        return mMovies.size();
    }

    public class dataHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView img;
        public dataHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.iv_movie_image);
            itemView.setOnClickListener(this);
        }

        /**
         * This gets called by the child views during a click.
         *
         * @param view - the view that was clicked
         */
        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Movie movie = mMovies.get(getAdapterPosition());
            mClickHandler.onClick(movie);
        }
    }

    public void setMovies(ArrayList<Movie> movies) {
        mMovies = movies;
        notifyDataSetChanged();
    }
}
