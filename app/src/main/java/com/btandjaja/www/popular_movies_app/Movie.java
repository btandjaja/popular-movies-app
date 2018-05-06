package com.btandjaja.www.popular_movies_app;

public class Movie {
    /* variable declarations */
    private double mVoteAvg, mPopularity;
    private String mOriginalTitle, mPosterPath, mOverView, mReleaseDate;

    /* constructor */
    public Movie(double voteAvg, double popularity, String title, String posterPath,
                 String overView, String releaseDate) {
        mVoteAvg = voteAvg;  mPopularity = popularity;
        mOriginalTitle = title; mPosterPath = posterPath;
        mOverView = overView; mReleaseDate = releaseDate;
    }

    /* get data methods */
    public double getVoteAvg() {return mVoteAvg;}
    public double getPopularity() {return mPopularity;}
    public String getTitle() {return mOriginalTitle;}
    public String getPosterPath() {return mPosterPath;}
    public String getOverView() {return mOverView;}
    public String getReleaseDate() {return mReleaseDate;}
}
