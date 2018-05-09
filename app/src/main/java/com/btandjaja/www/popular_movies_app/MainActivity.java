package com.btandjaja.www.popular_movies_app;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler{

    private ArrayList<Movie> mMovieList = new ArrayList<>();
    private TextView mError;
    private ProgressBar mProgressBar;
    private RecyclerView mRecyclerView;

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
    }

    /* get movies data */
    private void loadMoviesData() {
        showMoviesDataView();
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
            //TODO remove
//            mJsonMovieData = movieJsonString;
            if(movieJsonString!=null && !movieJsonString.equals("")) {
                MovieUtils.getMovieList(movieJsonString, mMovieList);
//                mRecyclerView.setAdapter(new MovieAdapter(MainActivity.this, mMovieList));
                createAndSetAdapter();
            }
            else showErrorMessage();
        }
    }

    private void createAndSetAdapter(){
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL,
                false));
        mRecyclerView.setAdapter(new MovieAdapter(MainActivity.this, mMovieList));
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
            /* do something */
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
