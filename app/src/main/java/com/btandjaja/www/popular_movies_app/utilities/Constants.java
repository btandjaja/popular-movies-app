package com.btandjaja.www.popular_movies_app.utilities;

public class Constants {
    /* movie link + need key - readability */
    public final static String MOVIE_BASE_URL = "http://api.themoviedb.org/3/movie/";
    private final static String POPULAR_MOVIES_BASE_URL = "popular?api_key=";
    private final static String TOP_RATED_MOVIES_BASE_URL = "top_rated?api_key=";
    private final static String CURRENT_PLAYING_MOVIES_BASE_URL = "now_playing?api_key=";
    public final static String TRAILER = "/videos?api_key=";
    public static final int SPLIT_COLUMN = 2;
    public static final int MOVIE_QUERY_LOADER = 2001;
    public static final String MOVIE_QUERY_STRING = "query";
    public static final String IMAGE_URL = "http://image.tmdb.org/t/p/w185/";
    //TODO Please provide API key
    public final static String API_KEY = "20893aae2a9da0098c89e73e1dcad948";
    public final static String RUNTIME = "runtime";
    /* used constants */
    public final static String POPULAR_MOVIES = MOVIE_BASE_URL + POPULAR_MOVIES_BASE_URL + API_KEY;
    public final static String TOP_RATED_MOVIES = MOVIE_BASE_URL + TOP_RATED_MOVIES_BASE_URL + API_KEY;
    public final static String CURRENT_PLAYING_MOVIES = MOVIE_BASE_URL + CURRENT_PLAYING_MOVIES_BASE_URL + API_KEY;
}
