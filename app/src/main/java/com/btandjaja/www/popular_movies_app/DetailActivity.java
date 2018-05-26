package com.btandjaja.www.popular_movies_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.btandjaja.www.popular_movies_app.utilities.Constants;
import com.btandjaja.www.popular_movies_app.utilities.MovieUtils;
import com.btandjaja.www.popular_movies_app.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.net.URL;

public class DetailActivity extends AppCompatActivity {
    /* constant */
    private static final int BEGIN = 0;
    private static final int END = 4;
    private static final String OUT_OF = "/10";

    /* views from detail activity */
    private ImageView mThumbnail;
    private TextView mTitle, mRating, mOverView, mReleaseDate, mRunTime;

    /* extract data variables */
    private static String title;
    private static String thumbnail;
    private static String overView;
    private static Double rating;
    private static String release_date;
    private static String trailerStringUrl;
    private static int runTime;
    private static URL mURL;
    private static String mMovieJsonString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        /* get intent from different activity */
        Intent movieDetailIntent = getIntent();
        /* check if string exist */
        if (movieDetailIntent.hasExtra("original_title")) {
            extractData(movieDetailIntent);
            getDetailLayoutId();
            fillData();
            //TODO for part 2
//            getRunTime();
        }
    }

    /**
     * This method extracts data provided from previous activity
     *
     * @param movieDetailIntent has data to be extracted from previous activity
     */
    private void extractData(Intent movieDetailIntent) {
        title = movieDetailIntent.getStringExtra(Constants.ORIGINAL_TITLE);
        thumbnail = movieDetailIntent.getStringExtra(Constants.POSTER_PATH);
        overView = movieDetailIntent.getStringExtra(Constants.OVERVIEW);
        rating = movieDetailIntent.getDoubleExtra(Constants.VOTE_AVERAGE, 0);
        release_date = movieDetailIntent.getStringExtra(Constants.RELEASE_DATE);
        trailerStringUrl = movieDetailIntent.getStringExtra(Constants.TRAILER);
    }

    /**
     * This method finds the views from detail Activiy xml file.
     */
    private void getDetailLayoutId() {
        mThumbnail = findViewById(R.id.thumbnail);
        mTitle = findViewById(R.id.tv_title);
        mRating = findViewById(R.id.tv_rating);
        mOverView = findViewById(R.id.tv_over_view);
        mReleaseDate = findViewById(R.id.tv_release_date);
//        mRunTime = findViewById(R.id.tv_run_time);
    }

    /**
     * This method fills in data to detail activity layout
     * after the data has been extracted.
     */
    private void fillData() {
        mTitle.setText(title);
        mRating.setText(parseRating());
        mOverView.setText(overView);
        mReleaseDate.setText(parsedDate());
        Picasso.with(this).load(thumbnail).into(mThumbnail);
    }

    /**
     * This method takes the first four character from release date
     * @return year of release date
     */
    private String parsedDate() {
        return release_date.substring(BEGIN, END);
    }

    /**
     * This method set a ratio to current rating
     * @return percentage rating
     */
    private String parseRating() {
        return String.valueOf(rating) + OUT_OF;
    }

    /**
     * http://api.themoviedb.org/3/movie/383498/videos?api_key=20893aae2a9da0098c89e73e1dcad948
     */
    private void getRunTime() {
        mURL = NetworkUtils.buildUrl(trailerStringUrl);
        mMovieJsonString = MovieUtils.getMovieListJsonString(mURL.toString());
        runTime = MovieUtils.getRunTime(mMovieJsonString);
    }
}
