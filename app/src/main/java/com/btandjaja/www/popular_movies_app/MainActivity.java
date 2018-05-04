package com.btandjaja.www.popular_movies_app;

import android.net.Network;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
//
//    final static String PARAM_SORT = "sort";
//    final static String PARAM_QUERY = "q";
//    final static String sortByRating = "vote_average";
//    final static String sortByPopularity = "popularity";
    private TextView mTestText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTestText = findViewById(R.id.tv_display_movie);
        /* get movie data */
        queryMovies();
    }

    private class Movies extends AsyncTask<URL, Void, String> {

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
        protected void onPostExecute(String s) {
            if(s!=null && !s.equals("")) mTestText.setText(s);
        }
    }

    /* get movies data */
    private void queryMovies() {
        URL movieSearchUrl = NetworkUtils.buildUrl();
        new Movies().execute(movieSearchUrl);
    }

    /* show movie data */
    private void showMoviesDataView() {
        /* show the movie data */
        /* hide the error message */
    }

    /* show error message */
    private void showErrorMessage() {
        /* hide movie data */
        /* show error message */
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
