package com.example.android.moviemagic;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by pkennedy on 1/18/18.
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.trailerDataHolder>{

    private Context mContext;

    private ArrayList<Trailer> mTrailers = new ArrayList<>();

    /*
    * An on-click handler that we've defined to make it easy for an Activity to interface with
    * our RecyclerView
    */

    private final TrailerAdapterOnClickHandler mClickHandler;

    /**
     * The interface that receives onClick messages.
     */
    public interface TrailerAdapterOnClickHandler {
        void onClick(Trailer trailer);
    }

    public TrailerAdapter(TrailerAdapterOnClickHandler clickHandler, Context context) {
        this.mClickHandler = clickHandler;
        this.mContext = context;
    }

    @Override
    public trailerDataHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_item, null);
        trailerDataHolder trailerHolder = new trailerDataHolder(layout);
        return trailerHolder;
    }

    @Override
    public void onBindViewHolder(trailerDataHolder holder, int position) {
        holder.trailerName.setText(mTrailers.get(position).getmName());
    }

    @Override
    public int getItemCount() {
        if (null == mTrailers) return 0;
        return mTrailers.size();
    }

    public class trailerDataHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView trailerName;
        public trailerDataHolder(View itemView) {
            super(itemView);
            trailerName = (TextView) itemView.findViewById(R.id.tv_trailer_name);
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
            Trailer trailer = mTrailers.get(getAdapterPosition());
            mClickHandler.onClick(trailer);
        }
    }

    public void setTrailers(ArrayList<Trailer> trailers) {
        mTrailers = trailers;
        notifyDataSetChanged();
    }
}
