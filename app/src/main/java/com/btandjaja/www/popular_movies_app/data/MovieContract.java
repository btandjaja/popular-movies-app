package com.btandjaja.www.popular_movies_app.data;

import android.provider.BaseColumns;

public class MovieContract {

    public static final class MovieEntry implements BaseColumns {
        public static final String TABLE_NAME = "favorite_movies";
        public static final String COLUMN_MOVIE_TITLES = "movie_title";
        public static final String COLUMN_MOVIE_ID = "id";
    }
}
