package com.btandjaja.www.popular_movies_app.data;

import android.provider.BaseColumns;

public class MovieContract {
    /* stopping other apps to access DB */
    private MovieContract() {}

    public static final class MovieEntry implements BaseColumns {
        public static final String TABLE_NAME = "movies";
        /* column 1 */
        public static final String COLUMN_NAME_TITLE = "original_title";
        /* column 2 */
        public static final String COLUMN_NAME_POSTER_PATH = "poster_path";
        /* column 3 */
        public static final String COLUMN_NAME_OVER_VIEW = "overview";
        /* column 4 */
        public static final String COLUMN_NAME_RELEASE_DATE = "release_date";
        /* column 5 */
        public static final String COLUMN_NAME_VOTE_AVERAGE = "vote_average";
        /* column 6 */
        public static final String COLUMN_NAME_POPULARITY = "popularity";
    }
}
