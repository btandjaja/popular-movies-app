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
class NetworkUtils {
    /* movie link + need key */
    private final static String POPULAR_MOVIES_BASE_URL = "http://api.themoviedb.org/3/movie/popular?api_key=";
    //TODO Please provide API key
    private final static String API_KEY = "20893aae2a9da0098c89e73e1dcad948";

    /**
     * Builds the URL used to query movie.
     *
     * @return The URL to use to query the movie server.
     */
    public static URL buildUrl() {
        Uri builtUri = Uri.parse(POPULAR_MOVIES_BASE_URL+API_KEY).buildUpon()
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
