package com.btandjaja.www.popular_movies_app.MovieAdapters;


import com.btandjaja.www.popular_movies_app.utilities.Constants;

public class Movie {
    /* variable declarations */
    private double mVoteAvg, mPopularity;
    private String mOriginalTitle, mPosterPath, mOverView, mReleaseDate;
    private int mMovieId;

    /* constructor */
    public Movie(double voteAvg, double popularity, String title, String posterPath,
                 String overView, String releaseDate, int movieId) {
        mVoteAvg = voteAvg;  mPopularity = popularity;
        mOriginalTitle = title; mPosterPath = posterPath;
        mOverView = overView; mReleaseDate = releaseDate;
        mMovieId = movieId;
    }

    /* copy */
    public static Movie copyMovie(Movie movie) {
        return new Movie(movie.getVoteAvg(), movie.getPopularity(), movie.getTitle(),
                movie.getPosterPath(), movie.getOverView(), movie.getReleaseDate(), movie.getMovieId());
    }

    /* get data methods */
    public double getVoteAvg() {return mVoteAvg;}
    public double getPopularity() {return mPopularity;}
    public String getTitle() {return mOriginalTitle;}
    public String getPosterPath() {return mPosterPath;}
    public String getOverView() {return mOverView;}
    public String getReleaseDate() {return mReleaseDate;}
    public int getMovieId() {return mMovieId;}

    /* set data methods */
    private void setVoteAve(double voteAvg) { mVoteAvg = voteAvg; }
    private void setPopularity(double popularity) { mPopularity = popularity; }
    private void setTitle(String title) { mOriginalTitle = title; }
    private void setPosterPath(String posterPath) { mPosterPath = posterPath; }
    private void setOverView(String overView) { mOverView = overView; }
    private void setReleaseDate(String releaseDate) { mReleaseDate = releaseDate; }
    private void setMovieId(int movieId) { mMovieId = movieId; }

}
