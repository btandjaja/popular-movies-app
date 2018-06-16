package com.btandjaja.www.popular_movies_app.MovieAdapters;

import java.util.ArrayList;

public class Movie {
    /* variable declarations */
    private double mVoteAvg, mPopularity;
    private String mOriginalTitle, mPosterPath, mOverView, mReleaseDate, mRunTime, mMovieId;
    private ArrayList<String> mTrailerKeys, mReviews;
    private boolean mFavorite;

    /* constructor */
    public Movie(double voteAvg, String posterPath, String movieId) {
        mVoteAvg = voteAvg; mPopularity = 0;
        mPosterPath = posterPath; mMovieId = movieId;
        initializedNull();
    }

    /* constructor */
    public Movie(double voteAvg, double popularity, String posterPath, String movieId) {
        mVoteAvg = voteAvg; mPopularity = popularity;
        mPosterPath = posterPath; mMovieId = movieId;
        initializedNull();
    }

    private void initializedNull() {
        mFavorite = false;
        mOriginalTitle = null; mOverView = null;
        mReleaseDate = null; mRunTime = "0";
        mTrailerKeys = new ArrayList<>();
        mReviews = new ArrayList<>();
    }

    /* copy */
    public static Movie copyMovie(Movie movie) {
        return new Movie(movie.getVoteAvg(), movie.getPosterPath(), movie.mMovieId);
    }

    /* get data methods */
    public double getVoteAvg() { return mVoteAvg; }
    public double getPopularity() { return mPopularity; }
    public String getTitle() { return mOriginalTitle; }
    public String getPosterPath() { return mPosterPath; }
    public String getOverView() { return mOverView; }
    public String getReleaseYear() { return mReleaseDate; }
    public String getMovieId() { return mMovieId; }
    public ArrayList<String> getTrailerKeys() { return mTrailerKeys; }
    public ArrayList<String> getReviews() { return mReviews; }
    public String getRunTime() { return mRunTime; }
    public boolean getFavorite() { return mFavorite; }

    /* setter methods */
    public void setTitle(String title) { mOriginalTitle = title; }
    public void setOverView(String overView) { mOverView = overView; }
    public void setReleaseDate(String releaseDate) { mReleaseDate = releaseDate; }
    public void setTrailerKey(String trailerKey) { mTrailerKeys.add(trailerKey); }
    public void setReview(String review) { mReviews.add(review); }
    public void setRunTime(int runTime) { mRunTime = String.valueOf(runTime); }
    public void setFavorit(boolean favorite) { mFavorite = favorite; }
}
