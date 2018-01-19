package com.example.android.moviemagic;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by pkennedy on 1/18/18.
 */

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.dataHolder>{

    private Context context;

    private int images[];

    public DataAdapter(Context context, int[] images) {
        this.context = context;
        this.images = images;
    }

    @Override
    public dataHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_layout, null);
        dataHolder movieHolder = new dataHolder(layout);
        return movieHolder;
    }

    @Override
    public void onBindViewHolder(dataHolder holder, int position) {
        Picasso.with(context)
                .load(images[position])
                .into(holder.img);
    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    public static class dataHolder extends RecyclerView.ViewHolder{
        ImageView img;
        public dataHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.iv_movie_image);

        }
    }
}
