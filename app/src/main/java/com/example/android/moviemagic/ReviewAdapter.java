package com.example.android.moviemagic;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by pkennedy on 1/18/18.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.reviewDataHolder>{

    private Context mContext;

    private ArrayList<Review> mReviews = new ArrayList<>();

    public ReviewAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public reviewDataHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item, null);
        reviewDataHolder reviewHolder = new reviewDataHolder(layout);
        return reviewHolder;
    }

    @Override
    public void onBindViewHolder(reviewDataHolder holder, int position) {
        holder.reviewContent.setText(mReviews.get(position).getmContent());
        holder.reviewAuthor.setText(mReviews.get(position).getmAuthor());
    }

    @Override
    public int getItemCount() {
        if (null == mReviews) return 0;
        return mReviews.size();
    }

    public class reviewDataHolder extends RecyclerView.ViewHolder {

        TextView reviewContent;
        TextView reviewAuthor;

        public reviewDataHolder(View itemView) {
            super(itemView);
            reviewContent = (TextView) itemView.findViewById(R.id.tv_review_content);
            reviewAuthor = (TextView) itemView.findViewById(R.id.tv_review_author);
        }
    }


    public void setReviews(ArrayList<Review> reviews) {
        mReviews = reviews;
        notifyDataSetChanged();
    }
}
