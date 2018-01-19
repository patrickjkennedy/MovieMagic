package com.example.android.moviemagic;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by pkennedy on 1/18/18.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieHolder>{

    private Context context;

    private int images[];

    public MovieAdapter(Context context, int[] images) {
        this.context = context;
        this.images = images;
    }

    @Override
    public MovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_layout, null);
        MovieHolder movieHolder = new MovieHolder(layout);
        return movieHolder;
    }

    @Override
    public void onBindViewHolder(MovieHolder holder, int position) {
        holder.img.setImageResource(images[position]);
    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    public static class MovieHolder extends RecyclerView.ViewHolder{
        ImageView img;
        public MovieHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.iv_movie_image);

        }
    }
}
