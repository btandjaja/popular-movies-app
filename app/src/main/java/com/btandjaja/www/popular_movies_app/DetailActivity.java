package com.btandjaja.www.popular_movies_app;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.btandjaja.www.popular_movies_app.utilities.Constants;
import com.btandjaja.www.popular_movies_app.utilities.MovieUtils;
import com.btandjaja.www.popular_movies_app.utilities.NetworkUtils;
import com.btandjaja.www.popular_movies_app.MovieAdapters.Movie;
import com.squareup.picasso.Picasso;

import java.net.URL;

public class DetailActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<String> {
    //public class DetailActivity extends AppCompatActivity {
    /* constant */
    private static final int BEGIN = 0;
    private static final int END = 4;

    /* views from detail activity */
    private ImageView mThumbnail;
    private TextView mTitle, mRating, mOverView, mReleaseDate, mRunTime, mError;
    private ProgressBar mLoadingInidicator;
    private ScrollView mScrollView;

    /* extract data variables */
    private static int runTime;
    private static URL mURL;
    private static boolean favorite;
    private static String jsonMovieData;
    private static Movie mMovie;
    private static boolean singleMovie;
    private static boolean isFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        /* get intent from different activity */
        Intent movieDetailIntent = getIntent();
        /* check if string exist */
        if (movieDetailIntent.hasExtra(Constants.MOVIE_ID)) {
            prefillData(movieDetailIntent);
            getDetailLayoutId();
            getDataFromNetwork();
        }
        getSupportLoaderManager().initLoader(Constants.MOVIE_QUERY_LOADER, null, this);
    }

    /**
     * This method extracts data provided from previous activity
     *
     * @param movieDetailIntent has data to be extracted from previous activity
     */
    private void prefillData(Intent movieDetailIntent) {
        singleMovie = true;
        String thumbnail = movieDetailIntent.getStringExtra(Constants.POSTER_PATH);
        double rating = movieDetailIntent.getDoubleExtra(Constants.VOTE_AVERAGE, Constants.DEFAULT_INTEGER);
        String movieId = movieDetailIntent.getStringExtra(Constants.MOVIE_ID);
        mMovie = new Movie(rating, thumbnail, movieId);
    }

    /**
     * This method finds the views from detail Activiy xml file.
     */
    private void getDetailLayoutId() {
        mThumbnail = findViewById(R.id.thumbnail);
        mTitle = findViewById(R.id.tv_title);
        mRating = findViewById(R.id.tv_rating);
        mOverView = findViewById(R.id.tv_over_view);
        mReleaseDate = findViewById(R.id.tv_release_date);
        mRunTime = findViewById(R.id.tv_run_time);
        mLoadingInidicator = findViewById(R.id.pb_detail_view);
        mError = findViewById(R.id.tv_detail_error);
        mScrollView = findViewById(R.id.sv_movie_detail);
    }

    /**
     * This method check if there is a valid movie ID to query
     */
    private void getDataFromNetwork() {
        if (mMovie.getMovieId() == null || TextUtils.isEmpty(mMovie.getMovieId())) {
            showErrorMessage();
            return;
        }
        restartLoader();
    }

    /**
     * This method fills in data to detail activity layout
     * after the data has been extracted.
     */
    private void fillData() {
        //TODO find favorite from data base
        isFavorite = false;
        mTitle.setText(mMovie.getTitle());
        mRating.setText(parseRating());
        mOverView.setText(mMovie.getOverView());
        mRunTime.setText(mMovie.getRunTime());
        mReleaseDate.setText(parsedDate());
        Picasso.with(this).load(mMovie.getPosterPath()).into(mThumbnail);
    }

    /**
     * This method takes the first four character from release date
     *
     * @return year of release date
     */
    private String parsedDate() {
        return mMovie.getReleaseYear().substring(BEGIN, END);
    }

    /**
     * This method set a ratio to current rating
     *
     * @return percentage rating
     */
    private String parseRating() {
        return String.valueOf(mMovie.getVoteAvg()) + getString(R.string.out_of);
    }

    /**
     * This method check for AsyncTaskLoader.
     * Creates or restart Loader.
     */
    private void restartLoader() {
        mURL = NetworkUtils.buildUrl(this, mMovie.getMovieId(), singleMovie);
        Bundle movieBundle = new Bundle();
        movieBundle.putString(Constants.MOVIE_QUERY_STRING, mURL.toString());
        LoaderManager loaderManager = getSupportLoaderManager();
        if (loaderManager.getLoader(Constants.MOVIE_QUERY_LOADER) == null
                ) {
            loaderManager.initLoader(Constants.MOVIE_QUERY_LOADER, movieBundle, this);
        } else {
            loaderManager.restartLoader(Constants.MOVIE_QUERY_LOADER, movieBundle, this);
        }
    }

    /* AsyncTaskLoader */
    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable final Bundle args) {
        return new AsyncTaskLoader<String>(this) {
            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                if (args == null) return;
                mLoadingInidicator.setVisibility(View.VISIBLE);
                forceLoad();
            }

            @Nullable
            @Override
            public String loadInBackground() {
                return MovieUtils.getMovieJsonString(args.getString(Constants.MOVIE_QUERY_STRING));
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String jsonString) {
        mLoadingInidicator.setVisibility(View.INVISIBLE);
        if (jsonString == null || TextUtils.isEmpty(jsonString)) {
            showErrorMessage();
            return;
        }
        showMovieDetail();
        MovieUtils.getSingleMovie(this, jsonString, mMovie);
        fillData();
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Constants.MOVIE_QUERY_STRING, mURL.toString());
    }

    /**
     * This method is to disable error message.
     */
    private void showMovieDetail() {
        mError.setVisibility(View.INVISIBLE);
        mScrollView.setVisibility(View.VISIBLE);
    }

    /**
     * This method is to enable error message.
     */
    private void showErrorMessage() {
        mScrollView.setVisibility(View.INVISIBLE);
        mError.setText(getResources().getString(R.string.error));
        mError.setVisibility(View.VISIBLE);
    }
}
