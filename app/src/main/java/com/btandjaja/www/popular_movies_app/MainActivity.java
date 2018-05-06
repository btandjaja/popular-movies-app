package com.btandjaja.www.popular_movies_app;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
//
//    final static String PARAM_SORT = "sort";
//    final static String PARAM_QUERY = "q";
//    final static String sortByRating = "vote_average";
//    final static String sortByPopularity = "popularity";
    private String mJsonMovieData;
    private TextView mTestText, mError;
    private ProgressBar mProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /* initialize variables */
        initializedDisplayVariables();


        /* get movie data */
        loadMoviesData();
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
                mJsonMovieData = movieJsonString;
                mTestText.setText(movieJsonString);
            }
            else showErrorMessage();
        }
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

    private void initializedDisplayVariables() {
        mTestText = findViewById(R.id.tv_display_movie);
        mError = findViewById(R.id.tv_error);
        mProgressBar = findViewById(R.id.pb_view);

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
