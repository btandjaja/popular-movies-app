package com.btandjaja.www.popular_movies_app;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.content.Intent;
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
import com.btandjaja.www.popular_movies_app.utilities.MovieUtils;
import com.btandjaja.www.popular_movies_app.utilities.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;

@SuppressWarnings("ALL")
public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler,
        LoaderManager.LoaderCallbacks<String> {
    /* declarations */
    private TextView mError = null;
    private ProgressBar mProgressBar = null;
    private RecyclerView mRecyclerView = null;
    private MovieAdapter mMovieAdapter = null;
    private static URL mURL = null;
    private static String mMoviesToQuery = null;
    private static Boolean mAdapterCreated = false;
    private static String mMovieTrailer = null;
    private static ArrayList<Movie> mMovieList;
    private static String sortType;
    private static boolean singleMovie;
    private static String singleMovieJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /* initialize variables & recycler */
        initializedDisplayVariables();
        createAdapter();
        initializeRecyclerLayout();
        /* get movie data */
        loadMoviesData();
        /* initialize loader */
        getSupportLoaderManager().initLoader(movieQueryLoader(), null, this);
    }

    /**
     * This methods find appropriate views and set the variables.
     */
    private void initializedDisplayVariables() {
        mMovieList = new ArrayList<>();
        mError = findViewById(R.id.tv_error);
        mProgressBar = findViewById(R.id.pb_view);
        mRecyclerView = findViewById(R.id.recycler_view);
        sortType = null;
        singleMovie = false;
        if (mMoviesToQuery == null) mMoviesToQuery = getString(R.string.now_playing);
    }

    /**
     * This method creates the MovieAdapter.
     */
    private void createAdapter() {
        mMovieAdapter = new MovieAdapter(MainActivity.this);
    }

    /**
     * This method initialize RecyclerView with a layout
     * and movieAdapter.
     */
    private void initializeRecyclerLayout() {
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,
                Integer.parseInt(getString(R.string.split_column))));
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
        if (TextUtils.isEmpty(mMoviesToQuery) || TextUtils.isEmpty(BuildConfig.API_KEY)) {
            showErrorMessage();
            return;
        }
        restartLoader();
    }

    /**
     * This method check for AsyncTaskLoader.
     * Creates or restart Loader.
     */
    private void restartLoader() {
        mURL = NetworkUtils.buildUrl(this, mMoviesToQuery, singleMovie);
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

    /**
     * This method is to disable error message.
     */
    private void showMoviesDataView() {
        mError.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    /**
     * This method is to enable error message.
     */
    private void showErrorMessage() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mError.setText(getResources().getString(R.string.error));
        mError.setVisibility(View.VISIBLE);
    }

    /* asyncTaskLoader */
    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable final Bundle args) {
        return new AsyncTaskLoader<String>(this) {

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                if (args == null) return;
                mProgressBar.setVisibility(View.VISIBLE);
                forceLoad();
            }

            @Override
            public String loadInBackground() {
                return MovieUtils.getMovieListJsonString(args.getString(movieQueryString()));
            }
        };
    }

    /**
     * This method provides the string after loader finished loading
     * @param loader is the current loader used
     * @param jsonString is the output from network
     */
    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String jsonString) {
        mProgressBar.setVisibility(View.INVISIBLE);
        if (jsonString == null || TextUtils.isEmpty(jsonString)) {
            showErrorMessage();
            return;
        }
        showMoviesDataView();
        fillData(jsonString);
        setAdapter();
    }

    /**
     * Has to be created, but we are not using it
     *
     * @param loader
     */
    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(movieQueryString(), mURL.toString());
    }

    /**
     * This method takes jsonString and extracts the data with MovieUtils.getMovieList method
     *
     * @param jsonString
     */
    private void fillData(String jsonString) {
        mMovieList.clear();
        MovieUtils.getMovieList(this, jsonString, mMovieList);
    }

    /**
     * This method set up the MovieAdapter
     */
    private void setAdapter() {
        if (sortType != null) MovieUtils.sort(this, mMovieList, sortType);
        mMovieAdapter.setMovieList(this, mMovieList);
        mRecyclerView.setAdapter(mMovieAdapter);
    }

    /* Menu */
    /**
     * This method is for creating menu
     *
     * @param menu menu to be inflated
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    /**
     * This method will call the MovieUtils.sort method
     *
     * @param item options selected from the menu
     * @return true if option is selected, else return super
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.current_playing:
                mMoviesToQuery = getString(R.string.now_playing);
                sortType = null;
                break;
            case R.id.sort_by_popularity:
                mMoviesToQuery = getString(R.string.popular);
                sortType = getString(R.string.popular);
                break;
            case R.id.sort_by_rating:
                mMoviesToQuery = getString(R.string.top_rated);
                sortType = getString(R.string.vote_average);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        restartLoader();
        return true;
    }

    /**
     * This method takes in movie based on the position that is selected,
     * store the data and start detail activity with the data.
     *
     * @param movie
     */
    @Override
    public void onClick(Movie movie) {
        Intent detailIntent = new Intent(this, DetailActivity.class);
        putMovieExtra(detailIntent, movie);
        startActivity(detailIntent);
    }

    /**
     * This is a helper method to putExtra strings prior intent
     * @param detailIntent
     * @param movie
     */
    private void putMovieExtra(Intent detailIntent, Movie movie) {
        detailIntent.putExtra(getString(R.string.poster_path), movie.getPosterPath());
        detailIntent.putExtra(getString(R.string.vote_average), movie.getVoteAvg());
        detailIntent.putExtra(getString(R.string.movie_id), movie.getMovieId());
    }

    /**
     * This method provides a loader id
     * @return a loader id from string resource file
     */
    private int movieQueryLoader() {
        return Integer.parseInt(getString(R.string.movie_query_loader));
    }

    /**
     * This method get string stored in resource file
     * @return query string
     */
    private String movieQueryString() {
        return getString(R.string.movie_query_string);
    }
}