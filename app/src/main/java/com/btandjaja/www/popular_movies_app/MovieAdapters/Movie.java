package com.btandjaja.www.popular_movies_app.MovieAdapters;

import android.database.Cursor;

import com.btandjaja.www.popular_movies_app.data.MovieContract.MovieEntry;

public class Movie {
    /* image url link front portion */
    private static final String IMAGE_URL = "http://image.tmdb.org/t/p/w185/";

    /* variable declarations */
    private double mVoteAvg, mPopularity;
    private String mOriginalTitle, mPosterPath, mOverView, mReleaseDate;

    /* constructor */
    public Movie(double voteAvg, double popularity, String title, String posterPath,
                 String overView, String releaseDate) {
        mVoteAvg = voteAvg;  mPopularity = popularity;
        mOriginalTitle = title; mPosterPath = IMAGE_URL + posterPath;
        mOverView = overView; mReleaseDate = releaseDate;
    }

    /* constructor for data from sqlite */
    public Movie(Cursor cursor) {
        mOriginalTitle = cursor.getString(cursor.getColumnIndex(MovieEntry.COLUMN_NAME_TITLE));
        mPosterPath = cursor.getString(cursor.getColumnIndex(MovieEntry.COLUMN_NAME_POSTER_PATH));
        mOverView = cursor.getString(cursor.getColumnIndex(MovieEntry.COLUMN_NAME_OVER_VIEW));
        mReleaseDate = cursor.getString(cursor.getColumnIndex(MovieEntry.COLUMN_NAME_RELEASE_DATE));
        mVoteAvg = cursor.getDouble(cursor.getColumnIndex(MovieEntry.COLUMN_NAME_VOTE_AVERAGE));
        mPopularity = cursor.getDouble(cursor.getColumnIndex(MovieEntry.COLUMN_NAME_POPULARITY));
    }

    /* get data methods */
    public double getVoteAvg() {return mVoteAvg;}
    public double getPopularity() {return mPopularity;}
    public String getTitle() {return mOriginalTitle;}
    public String getPosterPath() {return mPosterPath;}
    public String getOverView() {return mOverView;}
    public String getReleaseDate() {return mReleaseDate;}
}
