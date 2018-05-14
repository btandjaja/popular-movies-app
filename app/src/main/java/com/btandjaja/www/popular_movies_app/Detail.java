package com.btandjaja.www.popular_movies_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.btandjaja.www.popular_movies_app.data.MovieContract.MovieEntry;
import com.squareup.picasso.Picasso;

public class Detail extends AppCompatActivity {
    /* constant */
    private static final int BEGIN = 0;
    private static final int END = 4;
    private static final String OUT_OF = "/10";
    /* views from detail activity */
    private ImageView mThumbnail;
    private TextView mTitle, mRating, mOverView, mReleaseDate;

    /* extract data variables */
    private static String title;
    private static String thumbnail;
    private static String over_view;
    private static Double rating;
    private static String release_date;

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
        }
    }

    private void extractData(Intent movieDetailIntent) {
        title = movieDetailIntent.getStringExtra(MovieEntry.COLUMN_NAME_TITLE);
        thumbnail = movieDetailIntent.getStringExtra(MovieEntry.COLUMN_NAME_POSTER_PATH);
        over_view = movieDetailIntent.getStringExtra(MovieEntry.COLUMN_NAME_OVER_VIEW);
        rating = movieDetailIntent.getDoubleExtra(MovieEntry.COLUMN_NAME_VOTE_AVERAGE, 0);
        release_date = movieDetailIntent.getStringExtra(MovieEntry.COLUMN_NAME_RELEASE_DATE);
    }

    private void getDetailLayoutId() {
        mThumbnail = findViewById(R.id.thumbnail);
        mTitle = findViewById(R.id.tv_title);
        mRating = findViewById(R.id.tv_rating);
        mOverView = findViewById(R.id.tv_over_view);
        mReleaseDate = findViewById(R.id.tv_release_date);
    }

    private void fillData() {
        mTitle.setText(title);
        mRating.setText(parseRating());
        mOverView.setText(over_view);
        mReleaseDate.setText(parsedDate());
        Picasso.with(this).load(thumbnail).into(mThumbnail);
    }

    private String parsedDate() {
        return release_date.substring(BEGIN, END);
    }

    private String parseRating() {
        return String.valueOf(rating) + OUT_OF;
    }

}
