package com.btandjaja.www.popular_movies_app.MovieAdapters;

import android.database.Cursor;

public class Movie {
    /* constants for query from table */
    public static final int TITLE = 1;
    public static final int OVERVIEW = 2;
    public static final int POPULARITY = 3;
    public static final int VOTE_AVG = 4;
    public static final int RELEASE_DATE = 5;
    public static final int POSTER_PATH = 6;

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
        mOriginalTitle = cursor.getString(TITLE);
        mPosterPath = cursor.getString(POSTER_PATH);
        mOverView = cursor.getString(OVERVIEW);
        mReleaseDate = cursor.getString(RELEASE_DATE);
        mVoteAvg = cursor.getDouble(VOTE_AVG);
        mPopularity = cursor.getDouble(POPULARITY);
    }

    /* get data methods */
    public double getVoteAvg() {return mVoteAvg;}
    public double getPopularity() {return mPopularity;}
    public String getTitle() {return mOriginalTitle;}
    public String getPosterPath() {return mPosterPath;}
    public String getOverView() {return mOverView;}
    public String getReleaseDate() {return mReleaseDate;}
}
