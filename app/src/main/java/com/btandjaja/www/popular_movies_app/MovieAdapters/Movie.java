package com.btandjaja.www.popular_movies_app.MovieAdapters;

import android.database.Cursor;

import com.btandjaja.www.popular_movies_app.data.MovieContract.MovieEntry;

public class Movie {
    /* image url link front portion */
    private static final String IMAGE_URL = "http://image.tmdb.org/t/p/w185/";

    /* variable declarations */
    private final double mVoteAvg, mPopularity;
    private final String mOriginalTitle, mPosterPath, mOverView, mReleaseDate;
    private final int mMovieId;

    /* constructor */
    public Movie(double voteAvg, double popularity, String title, String posterPath,
                 String overView, String releaseDate, int movieId) {
        mVoteAvg = voteAvg;  mPopularity = popularity;
        mOriginalTitle = title; mPosterPath = IMAGE_URL + posterPath;
        mOverView = overView; mReleaseDate = releaseDate;
        mMovieId = movieId;
    }

    /* constructor for data from SQLite */
    public Movie(Cursor cursor) {
        mOriginalTitle = cursor.getString(cursor.getColumnIndex(MovieEntry.COLUMN_NAME_TITLE));
        mPosterPath = cursor.getString(cursor.getColumnIndex(MovieEntry.COLUMN_NAME_POSTER_PATH));
        mOverView = cursor.getString(cursor.getColumnIndex(MovieEntry.COLUMN_NAME_OVER_VIEW));
        mReleaseDate = cursor.getString(cursor.getColumnIndex(MovieEntry.COLUMN_NAME_RELEASE_DATE));
        mVoteAvg = cursor.getDouble(cursor.getColumnIndex(MovieEntry.COLUMN_NAME_VOTE_AVERAGE));
        mPopularity = cursor.getDouble(cursor.getColumnIndex(MovieEntry.COLUMN_NAME_POPULARITY));
        mMovieId = cursor.getInt(cursor.getColumnIndex(MovieEntry.COLUMN_NAME_MOVIE_ID));
    }

    /* get data methods */
    public double getVoteAvg() {return mVoteAvg;}
    public double getPopularity() {return mPopularity;}
    public String getTitle() {return mOriginalTitle;}
    public String getPosterPath() {return mPosterPath;}
    public String getOverView() {return mOverView;}
    public String getReleaseDate() {return mReleaseDate;}
    public int getMovieId() {return mMovieId;}
}
