package com.btandjaja.www.popular_movies_app;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * These utilities will be used to communicate with the network.
 */
public class NetworkUtils {
    /* base url & phone picture size */
    final static String MOVIES_BASE_URL = "http://image.tmdb.org/t/p/";
    final static String PHONE_BASE_SIZE = "w185";

    final static String POPULAR_MOVIES_BASE_URL = "http://api.themoviedb.org/3/movie/popular";
    final static String API_KEY = "api_key=";
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
        Uri builtUri = Uri.parse(POPULAR_MOVIES_BASE_URL).buildUpon()
                .appendQueryParameter(API_KEY, POPULAR_MOVIES_KEY)
                .appendQueryParameter(PARAM_QUERY, movieSearchQuery)
                .build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());

        }catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
