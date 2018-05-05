package com.btandjaja.www.popular_movies_app;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class MovieAdapter extends  RecyclerView.Adapter<MovieAdapter.MovieViewHolder>{
    private int mNumbersOfMovies;
    /* constructor */
    public MovieAdapter (int numOfMovies) { mNumbersOfMovies = numOfMovies; }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        /* constants */
        private final String LINK_ADDRESS = "http://image.tmdb.org/t/p/w185/";
        /* variable declarations */
        private ImageView mImageView;

        public MovieViewHolder(View itemView) {
            super(itemView);
            /* get the view from image layout */
            mImageView = (ImageView) itemView.findViewById(R.id.single_movie);
        }
        /* bind the image layout with the provided image */
        void bind(String image) {
            Picasso.with(this).load(LINK_ADDRESS + image).into(mImageView);}
    }
}
