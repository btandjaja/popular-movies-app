package com.btandjaja.www.popular_movies_app;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler{
    /* declarations */
    private TextView mError;
    private ProgressBar mProgressBar;
    private RecyclerView mRecyclerView;
    private SQLiteDatabase mDb;
    private Cursor mCursor;
    private static MovieAdapter mMovieAdapter;
    private static final int SPLIT_COLUMN = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /* initialize variables */
        initializedDisplayVariables();
        /* get movie data */
        loadMoviesData();
    }

    /* initialize variables */
    private void initializedDisplayVariables() {
        mError = findViewById(R.id.tv_error);
        mProgressBar = findViewById(R.id.pb_view);
        mRecyclerView = findViewById(R.id.recycler_view);
        mDb = (new MovieDbHelper(this)).getWritableDatabase();
    }

    /* get movies data */
    private void loadMoviesData() {
        showMoviesDataView();
        getDataFromNetwork();
    }

    /* connect to network retrieve data */
    private void getDataFromNetwork() {
        URL movieSearchUrl = NetworkUtils.buildUrl();
        new Movies().execute(movieSearchUrl);
    }

    /* show movie data */
    private void showMoviesDataView() {
        mError.setVisibility(View.INVISIBLE);
    }

    /* show error message */
    private void showErrorMessage() {
        mError.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(Movie movie) {
        Toast.makeText(MainActivity.this, movie.getTitle(), Toast.LENGTH_LONG).show();
        Intent detailIntent = new Intent(this, Detail.class);
        detailIntent.putExtra(MovieEntry.COLUMN_NAME_TITLE, movie.getTitle());
        detailIntent.putExtra(MovieEntry.COLUMN_NAME_POSTER_PATH, movie.getPosterPath());
        detailIntent.putExtra(MovieEntry.COLUMN_NAME_OVER_VIEW, movie.getOverView());
        detailIntent.putExtra(MovieEntry.COLUMN_NAME_VOTE_AVERAGE, movie.getVoteAvg());
        detailIntent.putExtra(MovieEntry.COLUMN_NAME_RELEASE_DATE, movie.getReleaseDate());
        startActivity(detailIntent);
    }

    private class Movies extends AsyncTask<URL, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(URL... urls) {
            String movieSearchResult = null;
            try {
                movieSearchResult = NetworkUtils.getResponseFromHttpUrl(urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return movieSearchResult;
        }

        @Override
        protected void onPostExecute(String movieJsonString) {
            mProgressBar.setVisibility(View.INVISIBLE);
            if(movieJsonString!=null && !movieJsonString.equals("")) {
                ArrayList<Movie> movieList = new ArrayList<>();
                MovieUtils.getMovieList(movieJsonString, movieList);
                MovieUtils.initializedDb(mDb, movieList);
                mCursor = MovieUtils.getAllMovies(mDb);
                createAndSetAdapter();
            }
            else showErrorMessage();
        }
    }

    /* used in AsyncTask */
    private void createAndSetAdapter() {
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, SPLIT_COLUMN));
        mMovieAdapter = new MovieAdapter(MainActivity.this, mCursor);
        mRecyclerView.setAdapter( mMovieAdapter);
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
                mCursor = MovieUtils.sort(mCursor, mDb, mCursor.getColumnIndex(MovieEntry.COLUMN_NAME_POPULARITY));
                break;
            case R.id.sort_by_rating:
                mCursor = MovieUtils.sort(mCursor, mDb, mCursor.getColumnIndex(MovieEntry.COLUMN_NAME_VOTE_AVERAGE));
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        resetAdapter();
        return true;
    }

    private void resetAdapter() {
        mMovieAdapter.setMovieList(this, mCursor);
        mRecyclerView.setAdapter(mMovieAdapter);
    }
}
