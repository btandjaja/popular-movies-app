package com.btandjaja.www.popular_movies_app.data;

import android.provider.BaseColumns;

public class MovieContract {
    /* stopping other apps to access DB */
    private MovieContract() {}

    public static final class MovieEntry implements BaseColumns {
        public static final String TABLE_NAME = "movies";
        public static final String COLUMN_NAME_TITLE = "original_title";
        public static final String COLUMN_NAME_POSTER_PATH = "image_link";
        public static final String COLUM_NAME_OVER_VIEW = "over_view";
        public static final String COLUMN_NAME_RELEASE_DATE = "release_date";
        public static final String COLUMN_NAME_VOTE_AVERAGE = "vote_average";
        public static final String COLUMN_NAME_POPULARITY = "popularity";
    }

}
