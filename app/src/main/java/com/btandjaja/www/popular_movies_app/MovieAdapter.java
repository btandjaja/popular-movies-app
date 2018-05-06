package com.btandjaja.www.popular_movies_app;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieAdapter extends  BaseAdapter {
    private static final String IMAGE_URL = "http://image.tmdb.org/t/p/w185/";
    private final Context mContext;
    private final ArrayList<Movie> mMovieList;

    public MovieAdapter(Context context, ArrayList<Movie> movieList) {
        mContext = context;
        mMovieList = movieList;
    }

    @Override
    public int getCount() { return mMovieList.size(); }

    /* do we need this? */
    @Override
    public Object getItem(int position) { return mMovieList.get(position); }

    /* don't know what this is for */
    @Override
    public long getItemId(int position) { return 0; }

    /* check implementation */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = convertView == null ?
                new ImageView(mContext) : (ImageView) convertView;

        String imagePath = IMAGE_URL + mMovieList.get(position).getPosterPath();

        Picasso.with(mContext).load(imagePath).into(imageView);

        return imageView;
    }
}
