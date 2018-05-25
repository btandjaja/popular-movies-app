package com.btandjaja.www.popular_movies_app;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.btandjaja.www.popular_movies_app.MovieAdapters.Movie;
import com.btandjaja.www.popular_movies_app.MovieAdapters.MovieAdapter;
import com.btandjaja.www.popular_movies_app.data.MovieContract.MovieEntry;
import com.btandjaja.www.popular_movies_app.data.MovieDbHelper;
import com.btandjaja.www.popular_movies_app.utilities.Constants;
import com.btandjaja.www.popular_movies_app.utilities.MovieUtils;
import com.btandjaja.www.popular_movies_app.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

@SuppressWarnings("ALL")
public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler,
        LoaderManager.LoaderCallbacks<String>{
    /* declarations */
    private TextView mError = null;
    private ProgressBar mProgressBar = null;
    private RecyclerView mRecyclerView = null;
    private static SQLiteDatabase mDb = null;
    private static Cursor mCursor = null;
    private MovieAdapter mMovieAdapter = null;
    private static URL mURL = null;
    private static String mMoviesToQuery = null;
    private static Boolean mAdapterCreated = false;
    private static String mMovieTrailer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /* initialize variables & recycler */
        initializedDisplayVariables();
        mMovieAdapter = new MovieAdapter();
        initializeRecyclerLayout();
        /* get movie data */
        loadMoviesData();
        /* initialize loader */
        getSupportLoaderManager().initLoader(Constants.MOVIE_QUERY_LOADER, null, this);

    }

    /**
     * This methods find appropriate views and set the variables.
     */
    private void initializedDisplayVariables() {
        mError = findViewById(R.id.tv_error);
        mProgressBar = findViewById(R.id.pb_view);
        mRecyclerView = findViewById(R.id.recycler_view);
        mDb = (new MovieDbHelper(this)).getWritableDatabase();
        if(mMoviesToQuery == null) mMoviesToQuery = Constants.CURRENT_PLAYING_MOVIES;
    }

    /**
     * This method creates the MovieAdapter.
     */
    private void createAdapter() {
        mMovieAdapter = new MovieAdapter();
    }

    /**
     * This method initialize RecyclerView with a layout
     * and movieAdapter.
     */
    private void initializeRecyclerLayout() {
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, Constants.SPLIT_COLUMN));
        mRecyclerView.setAdapter(mMovieAdapter);
    }

    /**
     * This method calls showMovieDataView
     * then getDataFromNetwork methods.
     */
    private void loadMoviesData() {
        showMoviesDataView();
        getDataFromNetwork();
    }

    /**
     * This method shows the error message if mMoviesToQuery is null
     * or if API_KEY is not provided.
     */
    private void getDataFromNetwork() {
        if(TextUtils.isEmpty(mMoviesToQuery) || TextUtils.isEmpty(Constants.API_KEY)) {
            showErrorMessage();
            return;
        }
        restartLoader();
    }

    /**
     * This method check for AsyncTaskLoader.
     * Creates or restart Loader if it already exist.
     */
    private void restartLoader() {
        mURL = NetworkUtils.buildUrl(mMoviesToQuery);
        Bundle movieBundle = new Bundle();
        movieBundle.putString(Constants.MOVIE_QUERY_STRING, mURL.toString());
        LoaderManager loaderManager = getSupportLoaderManager();
        if(loaderManager.getLoader(Constants.MOVIE_QUERY_LOADER) == null) {
            loaderManager.initLoader(Constants.MOVIE_QUERY_LOADER, movieBundle, this);
        } else {
            loaderManager.restartLoader(Constants.MOVIE_QUERY_LOADER, movieBundle, this);
        }
    }

    /**
     * This method is to disable error message.
     */
    private void showMoviesDataView() {
        mError.setVisibility(View.INVISIBLE);
    }

    /**
     * This method is to enable error message.
     */
    private void showErrorMessage() {
        mError.setText(getResources().getString(R.string.error));
        mError.setVisibility(View.VISIBLE);
    }

    /**
     * This method takes in movie based on the position that is selected,
     * store the data and start detail activity with the data.
     * @param movie
     */
    @Override
    public void onClick(Movie movie) {
        Intent detailIntent = new Intent(this, Detail.class);
        getMovieExtra(detailIntent, movie);
        startActivity(detailIntent);
    }

    private void getMovieExtra(Intent detailIntent, Movie movie) {
        detailIntent.putExtra(MovieEntry.COLUMN_NAME_TITLE, movie.getTitle());
        detailIntent.putExtra(MovieEntry.COLUMN_NAME_POSTER_PATH, movie.getPosterPath());
        detailIntent.putExtra(MovieEntry.COLUMN_NAME_OVER_VIEW, movie.getOverView());
        detailIntent.putExtra(MovieEntry.COLUMN_NAME_VOTE_AVERAGE, movie.getVoteAvg());
        detailIntent.putExtra(MovieEntry.COLUMN_NAME_RELEASE_DATE, movie.getReleaseDate());
        detailIntent.putExtra(Constants.TRAILER, getTrailerLink(detailIntent, movie));
    }

    private String getTrailerLink(Intent detailIntent, Movie movie) {
        return Constants.MOVIE_BASE_URL + movie.getMovieId() + Constants.TRAILER + Constants.API_KEY;
    }

    /* asyncTaskLoader */
    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable final Bundle args) {
        return new AsyncTaskLoader<String>(this) {

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                if(args == null) return;
                mProgressBar.setVisibility(View.VISIBLE);
                forceLoad();
            }

            @Override
            public String loadInBackground() {
                return MovieUtils.getMovieListJsonString(args.getString(Constants.MOVIE_QUERY_STRING), mURL);
//                try {
//                    String movieUrl = args.getString(Constants.MOVIE_QUERY_STRING);
//                    /* check for valid url */
//                    if(movieUrl == null || TextUtils.isEmpty(movieUrl)) return null;
//                    mURL = new URL(movieUrl);
//                    return NetworkUtils.getResponseFromHttpUrl(mURL);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    return null;
//                }
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String jsonString) {
        mProgressBar.setVisibility(View.INVISIBLE);
        if (jsonString == null || TextUtils.isEmpty(jsonString))  {
            showErrorMessage();
            return;
        }
        showMoviesDataView();
        fillDatabase(jsonString);
        setAdapter();
    }

    /**
     * Has to be created, but we are not using it
     * @param loader
     */
    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Constants.MOVIE_QUERY_STRING, mURL.toString());
    }

    /**
     * This method takes jsonString and extracts the data with MovieUtils.getMovieList method
     * @param jsonString
     */
    private void fillDatabase(String jsonString) {
        ArrayList<Movie> movieList = new ArrayList<Movie>();
        MovieUtils.getMovieList(jsonString, movieList);
        MovieUtils.initializedDb(mDb, movieList);
        mCursor = MovieUtils.getAllMovies(mDb);
    }

    /**
     * This method set up the MovieAdapter
     */
    private void setAdapter() {
        mMovieAdapter.setMovieList(this, mCursor);
        mRecyclerView.setAdapter(mMovieAdapter);
    }

    /* Menu */
    /**
     * This method is for creating menu
     * @param menu  menu to be inflated
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    /** This method will call the MovieUtils.sort method
     *
     * @param item  options selected from the menu
     * @return true if option is selected, else return super
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.current_playing:
                mMoviesToQuery = Constants.CURRENT_PLAYING_MOVIES;
                break;
            case R.id.sort_by_popularity:
                mMoviesToQuery = Constants.POPULAR_MOVIES;
                mCursor = MovieUtils.sort(mCursor, mDb, mCursor.getColumnIndex(MovieEntry.COLUMN_NAME_POPULARITY));
                break;
            case R.id.sort_by_rating:
                mMoviesToQuery = Constants.TOP_RATED_MOVIES;
                mCursor = MovieUtils.sort(mCursor, mDb, mCursor.getColumnIndex(MovieEntry.COLUMN_NAME_VOTE_AVERAGE));
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        restartLoader();
        return true;
    }
}