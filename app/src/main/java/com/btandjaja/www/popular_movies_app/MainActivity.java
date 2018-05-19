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
import android.widget.Toast;

import com.btandjaja.www.popular_movies_app.MovieAdapters.Movie;
import com.btandjaja.www.popular_movies_app.MovieAdapters.MovieAdapter;
import com.btandjaja.www.popular_movies_app.data.MovieContract.MovieEntry;
import com.btandjaja.www.popular_movies_app.data.MovieDbHelper;
import com.btandjaja.www.popular_movies_app.utilities.MovieUtils;
import com.btandjaja.www.popular_movies_app.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler,
        LoaderManager.LoaderCallbacks<String>{
    /* movie link + need key */
    private final static String POPULAR_MOVIES_BASE_URL = "http://api.themoviedb.org/3/movie/popular?api_key=";
    private final static String TOP_RATED_MOVIES_BASE_URL = "https://api.themoviedb.org/3/movie/top_rated?api_key=";
    private final static String CURRENT_PLAYING_MOVIES_BASE_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=";
    //TODO Please provide API key
    private final static String API_KEY = "";
    private final static String POPULAR_MOVIES = POPULAR_MOVIES_BASE_URL + API_KEY;
    private final static String TOP_RATED_MOVIES = TOP_RATED_MOVIES_BASE_URL + API_KEY;
    private final static String CURRENT_PLAYING_MOVIES = CURRENT_PLAYING_MOVIES_BASE_URL + API_KEY;
    /* constants */
    private static final int SPLIT_COLUMN = 2;
    private static final int MOVIE_QUERY_LOADER = 2001;
    private static final String MOVIE_QUERY = "query";

    /* declarations */
    private static TextView mError;
    private static ProgressBar mProgressBar;
    private static RecyclerView mRecyclerView;
    private static SQLiteDatabase mDb;
    private static Cursor mCursor;
    private static MovieAdapter mMovieAdapter;
    private static URL mURL;
    private static String MOVIES_TO_QUERY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /* initialize variables */
        initializedDisplayVariables();
        /* get movie data */
        loadMoviesData();
        /* initialize loader */
        getSupportLoaderManager().initLoader(MOVIE_QUERY_LOADER, null, this);
    }

    /* initialize variables */
    private void initializedDisplayVariables() {
        mError = findViewById(R.id.tv_error);
        mProgressBar = findViewById(R.id.pb_view);
        mRecyclerView = findViewById(R.id.recycler_view);
        mDb = (new MovieDbHelper(this)).getWritableDatabase();
        MOVIES_TO_QUERY = CURRENT_PLAYING_MOVIES;
    }

    /* get movies data */
    private void loadMoviesData() {
        showMoviesDataView();
        getDataFromNetwork();
    }

    /* connect to network retrieve data */
    private void getDataFromNetwork() {
        if(TextUtils.isEmpty(POPULAR_MOVIES_BASE_URL) || TextUtils.isEmpty(API_KEY)) {
            showErrorMessage();
            return;
        }
        restartLoader();
    }

    /* restarts the asyncTaskLoader if it's not created */
    private void restartLoader() {
        mURL = NetworkUtils.buildUrl(MOVIES_TO_QUERY);
        Bundle movieBundle = new Bundle();
        movieBundle.putString(MOVIE_QUERY, mURL.toString());
        LoaderManager loaderManager = getSupportLoaderManager();
        if(loaderManager.getLoader(MOVIE_QUERY_LOADER) == null) {
            loaderManager.initLoader(MOVIE_QUERY_LOADER, movieBundle, this);
        } else {
            loaderManager.restartLoader(MOVIE_QUERY_LOADER, movieBundle, this);
        }
    }

    /* show movie data */
    private void showMoviesDataView() {
        mError.setVisibility(View.INVISIBLE);
    }

    /* show error message */
    private void showErrorMessage() {
        mError.setText("Failed to load!");
        mError.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(Movie movie) {
        Intent detailIntent = new Intent(this, Detail.class);
        detailIntent.putExtra(MovieEntry.COLUMN_NAME_TITLE, movie.getTitle());
        detailIntent.putExtra(MovieEntry.COLUMN_NAME_POSTER_PATH, movie.getPosterPath());
        detailIntent.putExtra(MovieEntry.COLUMN_NAME_OVER_VIEW, movie.getOverView());
        detailIntent.putExtra(MovieEntry.COLUMN_NAME_VOTE_AVERAGE, movie.getVoteAvg());
        detailIntent.putExtra(MovieEntry.COLUMN_NAME_RELEASE_DATE, movie.getReleaseDate());
        startActivity(detailIntent);
    }

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
                try {
                    String movieUrl = args.getString(MOVIE_QUERY);
                    /* check for valid url */
                    if(movieUrl == null || TextUtils.isEmpty(movieUrl)) return null;
                    mURL = new URL(movieUrl);
                    return NetworkUtils.getResponseFromHttpUrl(mURL);
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
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
        createAdapter();
        setAdapter();
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(MOVIE_QUERY, mURL.toString());
    }

    /* fill in database */
    private void fillDatabase(String jsonString) {
        ArrayList<Movie> movieList = new ArrayList<Movie>();
        MovieUtils.getMovieList(jsonString, movieList);
        MovieUtils.initializedDb(mDb, movieList);
        mCursor = MovieUtils.getAllMovies(mDb);
    }

    /* used in AsyncTask */
    private void createAdapter() {
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, SPLIT_COLUMN));
        mMovieAdapter = new MovieAdapter(MainActivity.this, mCursor);
    }

    /* Menu */
    /** This method is for creating menu
     *
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
            case R.id.sort_by_popularity:
                MOVIES_TO_QUERY = POPULAR_MOVIES;
                restartLoader();
                mCursor = MovieUtils.sort(mCursor, mDb, mCursor.getColumnIndex(MovieEntry.COLUMN_NAME_POPULARITY));
                break;
            case R.id.sort_by_rating:
                MOVIES_TO_QUERY = TOP_RATED_MOVIES;
                restartLoader();
                mCursor = MovieUtils.sort(mCursor, mDb, mCursor.getColumnIndex(MovieEntry.COLUMN_NAME_VOTE_AVERAGE));
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void setAdapter() {
        mMovieAdapter.setMovieList(this, mCursor);
        mRecyclerView.setAdapter(mMovieAdapter);
    }
}