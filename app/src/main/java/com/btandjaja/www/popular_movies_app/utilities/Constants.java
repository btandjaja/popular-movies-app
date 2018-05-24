package com.btandjaja.www.popular_movies_app.utilities;

public class Constants {
    /* movie link + need key */
    private final static String MOVIE_BASE_URL = "http://api.themoviedb.org/3/movie/";
    private final static String POPULAR_MOVIES_BASE_URL = MOVIE_BASE_URL + "popular?api_key=";
    private final static String TOP_RATED_MOVIES_BASE_URL = MOVIE_BASE_URL + "top_rated?api_key=";
    private final static String CURRENT_PLAYING_MOVIES_BASE_URL = MOVIE_BASE_URL + "now_playing?api_key=";
    private final static String TRAILER = "videos?api_key=";

    //TODO Please provide API key
    private final static String API_KEY = "20893aae2a9da0098c89e73e1dcad948";
    private final static String POPULAR_MOVIES = POPULAR_MOVIES_BASE_URL + API_KEY;
    private final static String TOP_RATED_MOVIES = TOP_RATED_MOVIES_BASE_URL + API_KEY;
    private final static String CURRENT_PLAYING_MOVIES = CURRENT_PLAYING_MOVIES_BASE_URL + API_KEY;
    /* constants */
    private static final int SPLIT_COLUMN = 2;
    private static final int MOVIE_QUERY_LOADER = 2001;
    private static final String MOVIE_QUERY_STRING = "query";
}
