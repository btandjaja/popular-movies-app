package com.btandjaja.www.popular_movies_app.utilities;

import android.content.Context;
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
import com.btandjaja.www.popular_movies_app.R;

@SuppressWarnings("ALL")
public class MovieUtils {
    /** This method creates Movie object from JSON string and stores in movieList.
     *
     * @param jsonMovies    List of movies in JSON string.
     * @param movieList     List of movies to be added to.
     */
    public static void getMovieList(Context context, String jsonMovies, ArrayList<Movie> movieList) {
        try {
            JSONObject movieJsonObj = new JSONObject(jsonMovies);
            JSONArray movieJsonArr = movieJsonObj.getJSONArray(context.getString(R.string.result));
            for(int i = 0; i < movieJsonArr.length(); i++) {
                JSONObject singleMovie = movieJsonArr.getJSONObject(i);
                Double voteAvg = singleMovie.optDouble(context.getString(R.string.vote_average));
                Double popularity = singleMovie.optDouble(context.getString(R.string.popular));
                String posterPath = singleMovie.optString(context.getString(R.string.poster_path));
                int movieId = singleMovie.optInt(context.getString(R.string.movie_id));
                movieList.add(new Movie(voteAvg, popularity,
                        context.getString(R.string.img_url) + posterPath,
                        String.valueOf(movieId)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method will sort movieList base on sortType.
     * @param movieList has list of movies.
     * @param sortType  has type of sort.
     */
    public static void sort(final Context context, ArrayList<Movie> movieList, final String sortType) {
        Collections.sort(movieList, new Comparator<Movie> () {
            @Override
            public int compare(Movie o1, Movie o2) {
                if(sortType == context.getString(R.string.popular))
                    return Double.compare(o2.getPopularity(), o1.getPopularity());
                return Double.compare(o2.getVoteAvg(), o1.getVoteAvg());
            }
        });
    }

    /**
     * This method connects to network and returns json data.
     *
     * @param movieUrlString is the url needed to connect to network.
     * @param storeUrl helps return from initial screen.
     * @return a jsonString of movie data.
     */
    public static String getMovieListJsonString(String movieUrlString) {
        try {
            /* check for valid url */
            if(!checkEmptyString(movieUrlString)) return null;
            URL storeUrl = new URL(movieUrlString);
            String jsonResult = NetworkUtils.getResponseFromHttpUrl(storeUrl);
            return NetworkUtils.getResponseFromHttpUrl(storeUrl);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * This method connects to the network and returns the json.
     * @param movieUrlString is the url needed to connect to the network.
     * @return the json output from the network. return null if fail.
     */
    public static String getMovieJsonString(String movieUrlString) {
        try {
            if (!checkEmptyString(movieUrlString)) return null;
            URL storeUrl = new URL(movieUrlString);
            return NetworkUtils.getResponseFromHttpUrl(storeUrl);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     ** This method gets missing data ready for detailActivity
     * @param jsonMovie has the data of the movie
     * @param movie is used to stored the data from jsonMovie
     */
    public static void getSingleMovie(Context context, String jsonMovie, Movie movie) {
        try {
            JSONObject movieJsonObj = new JSONObject(jsonMovie);
            // get basic movie details
            movie.setOverView(movieJsonObj.getString(context.getString(R.string.over_view)));
            movie.setReleaseDate(movieJsonObj.getString(context.getString(R.string.release_date))
                    .substring(Integer.parseInt(context.getString(R.string.begin_year)),
                            Integer.parseInt(context.getString(R.string.end_year))));
            movie.setRunTime(context, movieJsonObj.getInt(context.getString(R.string.run_time)));
            movie.setTitle(movieJsonObj.getString(context.getString(R.string.original_title)));

            // gets youtube key(s)
            JSONObject videos = movieJsonObj.getJSONObject(context.getString(R.string.videos));
            JSONArray videoArr = videos.getJSONArray(context.getString(R.string.result));
            for(int i = 0; i < videoArr.length(); i++) {
                JSONObject video = videoArr.getJSONObject(i);
                String key = video.getString(context.getString(R.string.key));
                movie.setTrailerKey(key);
            }

            // get reviews
            JSONObject reviews = movieJsonObj.getJSONObject(context.getString(R.string.reviews));
            JSONArray reviewArr = reviews.getJSONArray(context.getString(R.string.result));
            for(int i = 0; i < reviewArr.length(); i++) {
                JSONObject review = reviewArr.getJSONObject(i);
                String stringReview = review.getString(context.getString(R.string.content));
                movie.setReview(stringReview);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is a helper method to check for empty or null url.
     * @param movieUrlString is the url.
     * @return true if string is not empty, else false.
     */
    private static boolean checkEmptyString(String movieUrlString) {
        if (movieUrlString == null || TextUtils.isEmpty(movieUrlString)) return false;
        return true;
    }
}
