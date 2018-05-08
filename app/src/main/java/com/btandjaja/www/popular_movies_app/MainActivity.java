package com.btandjaja.www.popular_movies_app;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Movie> mMovieList;
    private String mJsonMovieData;
    private TextView mTestText, mError;
    private ProgressBar mProgressBar;
    private GridView mGrid;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /* initialize variables */
        initializedDisplayVariables();
        /* get movie data */
        loadMoviesData();
        String retreive = (String) mTestText.getText();
        Log.v("***extracted String: ", retreive);
        /* create and set movie adapter */
//        mGrid.setAdapter(new MovieAdapter(this, mMovieList));
//        mRecyclerView.setAdapter(new MovieAdapter(this, mMovieList));
    }

    /* initialize variables */
    private void initializedDisplayVariables() {
        mTestText = findViewById(R.id.tv_display_movie);
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
        mTestText.setVisibility(View.VISIBLE);
    }

    /* show error message */
    private void showErrorMessage() {
        mTestText.setVisibility(View.INVISIBLE);
        mError.setVisibility(View.VISIBLE);
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
            mJsonMovieData = movieJsonString;
            ArrayList<Movie> temp = new ArrayList<>();
            if(movieJsonString!=null && !movieJsonString.equals("")) {
                mMovieList = new ArrayList<> (MovieUtils.getMovieList(movieJsonString));
//                mMovieList = temp;
//                mMovieList = (ArrayList<Movie>)
//                        (MovieUtils.getMovieList(movieJsonString).clone());
                mTestText.setText(mMovieList.toString());
            }
            else showErrorMessage();
            //TODO remove
            Log.v("**check equal", String.valueOf(temp==mMovieList));
            Log.v("****ADDED size:", String.valueOf(mMovieList.size()));
        }
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
