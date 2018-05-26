package com.btandjaja.www.popular_movies_app.MovieAdapters;


import com.btandjaja.www.popular_movies_app.utilities.Constants;

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
        mOriginalTitle = title; mPosterPath = Constants.IMAGE_URL + posterPath;
        mOverView = overView; mReleaseDate = releaseDate;
        mMovieId = movieId;
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
