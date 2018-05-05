package com.btandjaja.www.popular_movies_app;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

public class MovieAdapter extends  RecyclerView.Adapter<MovieAdapter.NumberViewHolder>{
    private int mNumbersOfMovies;
    /* constructor */
    public MovieAdapter (int numOfMovies) { mNumbersOfMovies = numOfMovies; }

    @NonNull
    @Override
    public NumberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull NumberViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class NumberViewHolder extends RecyclerView.ViewHolder {

    }
}
