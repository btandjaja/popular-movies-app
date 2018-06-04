package com.btandjaja.www.popular_movies_app.MovieAdapters;

import java.util.ArrayList;

public class Movie {
    /* variable declarations */
    private double mVoteAvg, mPopularity;
    private String mOriginalTitle, mPosterPath, mOverView, mReleaseDate, mRunTime;
    private int mMovieId;
    private ArrayList<String> mTrailerId;

    /* constructor */
    public Movie(double voteAvg, String posterPath, int movieId) {
        mVoteAvg = voteAvg; mPopularity = 0;
        mPosterPath = posterPath; mMovieId = movieId;
        initializedNull();
    }
    /* constructor */
    public Movie(double voteAvg, double popularity, String posterPath, int movieId) {
        mVoteAvg = voteAvg; mPopularity = popularity;
        mPosterPath = posterPath; mMovieId = movieId;
        initializedNull();
    }
    /* constructor */
    public Movie(double voteAvg, double popularity, String title, String posterPath,
                 String overView, String releaseDate, int movieId) {
        mVoteAvg = voteAvg;  mPopularity = popularity;
        mOriginalTitle = title; mPosterPath = posterPath;
        mOverView = overView; mReleaseDate = releaseDate;
        mMovieId = movieId;
    }

    private void initializedNull() {
        mOriginalTitle = null; mOverView = null;
        mReleaseDate = null; mRunTime = "0";
        mTrailerId = new ArrayList<>();
    }

    /* copy */
    public static Movie copyMovie(Movie movie) {
//        return new Movie(movie.getVoteAvg(), movie.getPopularity(), movie.getTitle(),
//                movie.getPosterPath(), movie.getOverView(), movie.getReleaseDate(), movie.getMovieId());
        return new Movie(movie.getVoteAvg(), movie.getPosterPath(), movie.mMovieId);
    }

    /* get data methods */
    public double getVoteAvg() { return mVoteAvg; }
    public double getPopularity() { return mPopularity; }
    public String getTitle() { return mOriginalTitle; }
    public String getPosterPath() { return mPosterPath; }
    public String getOverView() { return mOverView; }
    public String getReleaseDate() { return mReleaseDate; }
    public int getMovieId() { return mMovieId; }
    public ArrayList<String> getTrailerId() { return mTrailerId; }
    public String getRunTime() { return mRunTime; }

    /* setter methods */
    public void setTitle(String title) { mOriginalTitle = title; }
    public void setOverView(String overView) { mOverView = overView; }
    public void setReleaseDate(String releaseDate) { mReleaseDate = releaseDate; }
    public void setTrailerId(ArrayList<String> trailerIds) {
        for(String trailerId : trailerIds) {
            mTrailerId.add(new String(trailerId));
        }
    }
    public void setRunTime(int runTime) { mRunTime = String.valueOf(runTime); }
}
