package com.btandjaja.www.popular_movies_app.utilities;

import com.btandjaja.www.popular_movies_app.BuildConfig;

public class Constants {
//    private final static String POPULAR_MOVIES_BASE_URL = "popular?api_key=";
//    private final static String TOP_RATED_MOVIES_BASE_URL = "top_rated?api_key=";
//    private final static String CURRENT_PLAYING_MOVIES_BASE_URL = "now_playing?api_key=";
    /* movie link + need key - readability */
    public final static String SCHEME = "https";
    public final static String MOVIES_AUTHORITY = "api.themoviedb.org/3/movie";
    public final static String POPULARITY = "popular";
    public final static String TOP_RATED = "top_rated";
    public final static String NOW_PLAYING = "now_playing";
    public final static String MOVIE_ID = "id";
    public final static String API_KEY = "api_key";

    public final static String MOVIE_BASE_URL = "http://api.themoviedb.org/3/movie/";
    public final static String TRAILER = "/videos?api_key=";
    public static final int SPLIT_COLUMN = 2;
    public static final int MOVIE_QUERY_LOADER = 2001;
    public static final String MOVIE_QUERY_STRING = "query";
    public static final String IMAGE_URL = "http://image.tmdb.org/t/p/w185/";
    public final static String RESULTS = "results";
    public final static String RUNTIME = "runtime";
    public final static String OVERVIEW = "overview";
    public final static String ORIGINAL_TITLE = "original_title";
    public final static String POSTER_PATH = "poster_path";
    public final static String VOTE_AVERAGE = "vote_average";
    public final static String RELEASE_DATE = "release_date";


    /* used constants */
//    public final static String POPULAR_MOVIES = MOVIE_BASE_URL + POPULAR_MOVIES_BASE_URL + BuildConfig.API_KEY;
//    public final static String TOP_RATED_MOVIES = MOVIE_BASE_URL + TOP_RATED_MOVIES_BASE_URL + BuildConfig.API_KEY;
//    public final static String CURRENT_PLAYING_MOVIES = MOVIE_BASE_URL + CURRENT_PLAYING_MOVIES_BASE_URL + BuildConfig.API_KEY;
}
