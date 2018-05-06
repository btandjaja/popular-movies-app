package com.btandjaja.www.popular_movies_app;

public class Movie {
    /* variable declarations */
    private double mVoteAvg, mPopularity;
    private String mTitle, mDescription, mPosterPath, mOverView;

    /* constructor */
    public Movie(double voteAvg, double popularity, String title, String description,
                 String posterPath, String overView) {
        mVoteAvg = voteAvg;  mPopularity = popularity;
        mTitle = title; mDescription = description; mPosterPath = posterPath;
        mOverView = overView;
    }

    /* get data methods */
    public double getVoteAvg() {return mVoteAvg;}
    public double getPopularity() {return mPopularity;}
    public String getTitle() {return mTitle;}
    public String getDescription() {return mDescription;}
    public String getPosterPath() {return mPosterPath;}
    public String getOverView() {return mOverView;}
}
