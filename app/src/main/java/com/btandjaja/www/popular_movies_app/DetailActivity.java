package com.btandjaja.www.popular_movies_app;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.btandjaja.www.popular_movies_app.MovieAdapters.TrailerAdapter;
import com.btandjaja.www.popular_movies_app.data.MovieContract.MovieEntry;
import com.btandjaja.www.popular_movies_app.utilities.MovieUtils;
import com.btandjaja.www.popular_movies_app.utilities.NetworkUtils;
import com.btandjaja.www.popular_movies_app.MovieAdapters.Movie;
import com.squareup.picasso.Picasso;

import java.net.URL;

public class DetailActivity extends AppCompatActivity implements TrailerAdapter.TrailerOnClickHandler,
        LoaderManager.LoaderCallbacks<String> {
    /* views from detail activity */
    private ImageView mThumbnail;
    private TextView mTitle, mRating, mOverView, mReleaseDate, mRunTime, mError;
    private ProgressBar mLoadingInidicator;
    private ScrollView mScrollView;
    private ImageButton mButton;
    private ContentValues mCurrentValues;
    private RecyclerView mRecyclerView;
    private TrailerAdapter mTrailerAdapter;

    /* extract data variables */
    private static URL mURL;
    private static Movie mMovie;
    private static boolean singleMovie;
    private static boolean isFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        /* get intent from different activity */
        final Intent movieDetailIntent = getIntent();
        /* check string existence */
        if (movieDetailIntent.hasExtra(getString(R.string.movie_id))) {
            prefillData(movieDetailIntent);
            getDetailLayoutId();
            getDataFromNetwork();
            mButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO test uri onclick
                    isFavorite = !isFavorite;
                    Uri uri = MovieEntry.CONTENT_URI;
                    uri = uri.buildUpon().appendPath(mMovie.getMovieId()).build();
                    if(isFavorite) {
                        getContentResolver().insert(uri, mCurrentValues);
                    } else {
                        getContentResolver().delete(uri, null, null);
                    }
                    markFavorite();
                }
            });
        }
        getSupportLoaderManager().initLoader(movieQueryLoader(), null, this);
    }

    /**
     * This method extracts data provided from previous activity
     *
     * @param movieDetailIntent has data to be extracted from previous activity
     */
    private void prefillData(Intent movieDetailIntent) {
        mCurrentValues = new ContentValues();
        singleMovie = true;
        String thumbnail = movieDetailIntent.getStringExtra(getString(R.string.poster_path));
        double rating = movieDetailIntent.getDoubleExtra(getString(R.string.vote_average),
                Integer.parseInt(getString(R.string.begin_year)));
        String movieId = movieDetailIntent.getStringExtra(getString(R.string.movie_id));
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
        mButton = findViewById(R.id.favorite_button);
        mRecyclerView = findViewById(R.id.rv_trailer);
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
     * This method set like button
     */
    private void markFavorite() {
        if (isFavorite) {
            mButton.setImageDrawable(getResources().getDrawable(R.drawable.favorite_pressed));
        } else {
            mButton.setImageDrawable(getResources().getDrawable(R.drawable.favorite_unpressed));
        }
    }

    /**
     * This method takes the first four character from release date
     *
     * @return year of release date
     */
    private String parsedDate() {
        return mMovie.getReleaseYear().substring(Integer.parseInt(getString(R.string.begin_year)),
                Integer.parseInt(getString(R.string.end_year)));
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
        movieBundle.putString(movieQueryString(), mURL.toString());
        LoaderManager loaderManager = getSupportLoaderManager();
        if (loaderManager.getLoader(movieQueryLoader()) == null
                ) {
            loaderManager.initLoader(movieQueryLoader(), movieBundle, this);
        } else {
            loaderManager.restartLoader(movieQueryLoader(), movieBundle, this);
        }
    }

    /* AsyncTaskLoader start & load in background */
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
                return MovieUtils.getMovieJsonString(args.getString(movieQueryString()));
            }
        };
    }

    /* asyncTask loadFinished */
    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String jsonString) {
        mLoadingInidicator.setVisibility(View.INVISIBLE);
        if (jsonString == null || TextUtils.isEmpty(jsonString)) {
            showErrorMessage();
            return;
        }
        showMovieDetail();
        setFavorite();
        markFavorite();
        MovieUtils.getSingleMovie(this, jsonString, mMovie);
        prefillContentValues();
        fillData();
    }

    /**
     * Not in used.
     * @param loader
     */
    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }

    /**
     * This method check if movie is mark as favorite
     */
    private void setFavorite() {
        isFavorite = checkDb();
    }

    /**
     * This method fills in data to detail activity layout
     * after the data has been extracted.
     */
    private void fillData() {
        mTitle.setText(mMovie.getTitle());
        mRating.setText(parseRating());
        mOverView.setText(mMovie.getOverView());
        mRunTime.setText(mMovie.getRunTime());
        mReleaseDate.setText(parsedDate());
        Picasso.with(this).load(mMovie.getPosterPath()).into(mThumbnail);
    }

    private boolean checkDb() {
        String selection = MovieEntry.COLUMN_MOVIE_ID + "=?";
        String[] selectionArgs = new String[] {mMovie.getMovieId()};
        Cursor cursor = getContentResolver().query(MovieEntry.CONTENT_URI,
                null,
                selection,
                selectionArgs,
                null);
        return cursor.getCount() > 0;
    }

    /**
     * This method prefill contenvalues
     */
    private void prefillContentValues() {
        mCurrentValues.put(MovieEntry.COLUMN_MOVIE_POSTER, mMovie.getPosterPath());
        mCurrentValues.put(MovieEntry.COLUMN_MOVIE_ID, mMovie.getMovieId());
        mCurrentValues.put(MovieEntry.COLUMN_MOVIE_TITLES, mMovie.getTitle());
    }

    /**
     * This method returns query string
     * @return is stored in string resource file
     */
    private String movieQueryString() {
        return getString(R.string.movie_query_string);
    }

    /**
     * This method provides a loader id
     * @return a loader id from string resource file
     */
    private int movieQueryLoader() {
        return Integer.parseInt(getString(R.string.movie_query_loader));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(movieQueryString(), mURL.toString());
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

    /**
     * This method set RecyclerView
     */
    private void trailerRecyclerView() {
        mTrailerAdapter = new TrailerAdapter(this);
        mTrailerAdapter.setTrailer(this, mMovie.getTrailerKeys());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    //TODO
    @Override
    public void onClick() {

    }
}
