package com.btandjaja.www.popular_movies_app;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
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
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.setAdapter(new MovieAdapter(MainActivity.this, mCursor));
    }

    /* Menu */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_settings){
//            mRecyclerView.
            /* do something */
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
