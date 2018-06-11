package com.btandjaja.www.popular_movies_app.utilities;

import android.content.ContentProvider;
import android.net.Uri;
import android.os.Build;

import com.btandjaja.www.popular_movies_app.BuildConfig;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Scanner;

/**
 * These utilities will be used to communicate with the network.
 */
public class NetworkUtils {
    /**
     * Builds the URL used to query movie.
     *
     * @return The URL to use to query the movie server.
     */
    public static URL buildUrl(String moviesQuery, boolean singleMovie) {
        Uri.Builder builder = new Uri.Builder();
        if(singleMovie) {
            builder.scheme(Constants.SCHEME)
                    .authority(Constants.MOVIES_AUTHORITY)
                    .path(moviesQuery)
                    .appendQueryParameter(Constants.API_KEY, BuildConfig.API_KEY)
                    .appendQueryParameter(Constants.APPEND_TO_RESPONSE, Constants.VIDEO_PLUS_REVIEW);
        } else {
            builder.scheme(Constants.SCHEME)
                    .authority(Constants.MOVIES_AUTHORITY)
                    .path(moviesQuery)
                    .appendQueryParameter(Constants.API_KEY, BuildConfig.API_KEY);
        }
        URL url = null;
        try {
            url = new URL(URLDecoder.decode(builder.build().toString(), "UTF-8"));
        }catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return url;
    }

    /** original copy
     * Builds the URL used to query movie.
     *
     * @return The URL to use to query the movie server.
     */
//    public static URL buildUrl(String moviesQuery) {
//        Uri builtUri = Uri.parse(moviesQuery).buildUpon().build();
//        URL url = null;
//        try {
//            url = new URL(builtUri.toString());
//        }catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//        return url;
//    }

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
