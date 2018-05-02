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
    TextView mTestText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTestText = findViewById(R.id.tv_display_movie);
        URL movieSearchUrl = NetworkUtils.buildUrl("Avenger");
        new MovieQueryTask().execute(movieSearchUrl);

    }

    public class MovieQueryTask extends AsyncTask<URL, Void, String> {

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
