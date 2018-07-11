package com.btandjaja.www.popular_movies_app.MovieAdapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.btandjaja.www.popular_movies_app.R;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {
    // declaration
    private static Context mContext;
    private static ArrayList<String> mReview;

    /**
     * Constructor for ReviewAdapter that initilizes the Context
     *
     * @param mContext is current context
     */
    public ReviewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setReview(ArrayList<String> reviewList) {
        mReview = reviewList;
        notifyDataSetChanged();
    }

    /**
     * Called when ViewHolders are created to fill a RecyclerView.
     *
     * @return A new ReviewViewHOlder that holds the view for each review
     */
    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflate review_layout to a view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item, parent, false);
        return new ReviewViewHolder(view);
    }

    /**
     * Called by the RecyclerView to display review of current position
     *
     * @param holder   to bind the textview with
     * @param position of the reviews
     */
    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        if (mReview.get(position) == null) return;
        holder.mTextView.setText(mReview.get(position));
    }

    /**
     * Returns the number of items to display.
     */
    @Override
    public int getItemCount() {
        return mReview == null ? 0 : mReview.size();
    }

    // Inner class for creating ViewHolders
    class ReviewViewHolder extends RecyclerView.ViewHolder {
        private final TextView mTextView;

        /**
         * Constructor for the ReviewViewHolder.
         *
         * @param itemView The view inflated in onCreateViewHolder
         */
        public ReviewViewHolder(View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.tv_single_review);
        }
    }
}
