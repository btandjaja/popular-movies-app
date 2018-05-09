package com.btandjaja.www.popular_movies_app;

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

    /* get data methods */
    public double getVoteAvg() {return mVoteAvg;}
    public double getPopularity() {return mPopularity;}
    public String getTitle() {return mOriginalTitle;}
    public String getPosterPath() {return mPosterPath;}
    public String getOverView() {return mOverView;}
    public String getReleaseDate() {return mReleaseDate;}
}
