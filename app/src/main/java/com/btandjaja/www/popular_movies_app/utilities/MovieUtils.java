package com.btandjaja.www.popular_movies_app.utilities;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.btandjaja.www.popular_movies_app.MovieAdapters.Movie;

@SuppressWarnings("ALL")
public class MovieUtils {
    /** This method creates Movie object from JSON string and stores in movieList
     *
     * @param jsonMovies    List of movies in JSON string
     * @param movieList     List of movies to be added to
     */
    public static void getMovieList(String jsonMovies, ArrayList<Movie> movieList) {
        try {
            JSONObject movieJsonObj = new JSONObject(jsonMovies);
            JSONArray movieJsonArr = movieJsonObj.getJSONArray(Constants.RESULTS);
            for(int i = 0; i < movieJsonArr.length(); i++) {
                JSONObject singleMovie = movieJsonArr.getJSONObject(i);
                Double voteAvg = singleMovie.optDouble(Constants.VOTE_AVERAGE);
                Double popularity = singleMovie.optDouble(Constants.POPULARITY);
                String originalTitle = singleMovie.optString(Constants.ORIGINAL_TITLE);
                String posterPath = singleMovie.optString(Constants.POSTER_PATH);
                String overView = singleMovie.optString(Constants.OVERVIEW);
                String releaseDate = singleMovie.optString(Constants.RELEASE_DATE);
                int movieId = singleMovie.optInt(Constants.MOVIE_ID);
                movieList.add(new Movie(voteAvg, popularity, originalTitle,
                        Constants.IMAGE_URL + posterPath, overView,
                        releaseDate, movieId));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void sort(ArrayList<Movie> movieList, final String sortType) {
        Collections.sort(movieList, new Comparator<Movie> () {
            @Override
            public int compare(Movie o1, Movie o2) {
                if(sortType == Constants.POPULARITY)
                    return Double.compare(o2.getPopularity(), o1.getPopularity());
                return Double.compare(o2.getVoteAvg(), o1.getVoteAvg());
            }
        });
    }

    /**
     * This method connects to network and returns json data
     *
     * @param movieUrlString is the url needed to connect to network
     * @param storeUrl helps return from initial screen
     * @return a jsonString of movie data
     */
    public static String getMovieListJsonString(String movieUrlString) {
        try {
            /* check for valid url */
            if(movieUrlString == null || TextUtils.isEmpty(movieUrlString)) return null;
            URL storeUrl = new URL(movieUrlString);
            return NetworkUtils.getResponseFromHttpUrl(storeUrl);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int getRunTime(String movieJson) {
        int runtime = 0;
        try {
            JSONObject movie = new JSONObject(movieJson);
            String runTimeString = movie.getString(Constants.RUNTIME);
            int abc = 0;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return runtime;
    }
}
