package com.btandjaja.www.popular_movies_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Detail extends AppCompatActivity {
    private final String TITLE;
    private final String THUMBNAIL;
    private final String OVER_VIEW;
    private final Double RATING;
    private final String RELEASE_DATE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent movieDetailIntent = getIntent();
        if(movieDetailIntent.hasExtra(Intent.EXTRA_TEXT)) {
            extractData(movieDetailIntent);
        }
    }

    private void extractData(Intent movieDetailIntent) {
        String title = movieDetailIntent.getStringExtra("original_title");
        String imageThumbnail = movieDetailIntent.getStringExtra("image_thumbnail");
        String overView = movieDetailIntent.getStringExtra("over_view");
        Double rating = movieDetailIntent.getDoubleExtra("vote_average", 0);
        String releaseDate = movieDetailIntent.getStringExtra("release_date");
    }
}
