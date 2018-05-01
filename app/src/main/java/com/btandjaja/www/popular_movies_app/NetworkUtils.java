package com.btandjaja.www.popular_movies_app;

import android.net.Uri;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * These utilities will be used to communicate with the network.
 */
public class NetworkUtils {
    /* base url & phone picture size */
    final static String MOVIES_BASE_URL = "http://image.tmdb.org/t/p/";
    final static String PHONE_BASE_SIZE = "w185";

    final static String POPULAR_MOVIES_BASE_URL = "http://api.themoviedb.org/3/movie/popular?api_key=";
    //TODO remove key prior submitting
    final static String POPULAR_MOVIES_KEY = "20893aae2a9da0098c89e73e1dcad948";

    final static String PARAM_SORT = "sort";
    final static String PARAM_QUERY = "q";
    final static String sortByRating = "vote_average";
    final static String sortByPopularity = "popularity";

    /**
     * Builds the URL used to query movie.
     *
     * @param movieSearchQuery The keyword that will be queried for.
     * @return The URL to use to query the movie server.
     */
    public static URL buildUrl(String movieSearchQuery) {
        // TODO (1) Fill in this method to build the proper Github query URL
        Uri builtUri = Uri.parse(POPULAR_MOVIES_BASE_URL+POPULAR_MOVIES_KEY).buildUpon()
                .appendQueryParameter(PARAM_QUERY, movieSearchQuery)
                .appendQueryParameter(PARAM_SORT, sortByPopularity)
                .build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());

        }catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }
}
