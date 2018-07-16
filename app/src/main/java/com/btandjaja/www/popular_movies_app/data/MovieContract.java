package com.btandjaja.www.popular_movies_app.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class MovieContract {
    public static final String AUTHORITY = "com.btandjaja.www.popular_movies_app";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_FAVORITE = "favorite";

    public static final class MovieEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVORITE).build();

        public static final String TABLE_NAME = "favorite_movies";
        public static final String COLUMN_MOVIE_TITLES = "movie_title";
        public static final String COLUMN_MOVIE_POSTER = "poster";
        public static final String COLUMN_MOVIE_ID = "id";
        public static final String COLUMN_MOVIE_VOTE_AVG = "vote_avg";
    }
}
