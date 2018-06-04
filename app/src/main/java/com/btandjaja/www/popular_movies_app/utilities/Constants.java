package com.btandjaja.www.popular_movies_app.utilities;


public class Constants {
    /* movie link + need key - readability */
    public final static String SCHEME = "https";
    public final static String MOVIES_AUTHORITY = "api.themoviedb.org/3/movie";
    public final static String POPULARITY = "popular";
    public final static String TOP_RATED = "top_rated";
    public final static String NOW_PLAYING = "now_playing";
    public final static String MOVIE_ID = "id";
    public final static String API_KEY = "api_key";
    public final static String TRAILER = "/videos?api_key=";
    public static final int SPLIT_COLUMN = 2;
    public static final int MOVIE_QUERY_LOADER = 2001;
    public static final String MOVIE_QUERY_STRING = "query";
    public static final String IMAGE_URL = "http://image.tmdb.org/t/p/w185/";
    public final static String RESULTS = "results";
    public final static String POSTER_PATH = "poster_path";
    public final static String VOTE_AVERAGE = "vote_average";

    /* constants needed for detailActivity not pass from Main Acitivity */
    public final static String RELEASE_DATE = "release_date";
    public final static String OVERVIEW = "overview";
    public final static String ORIGINAL_TITLE = "original_title";
    public final static String RUNTIME = "runtime";
    /* default Avenger Infinity movie id */
    public final static int DEFAULT_MOVIE_ID = 299536;
}
