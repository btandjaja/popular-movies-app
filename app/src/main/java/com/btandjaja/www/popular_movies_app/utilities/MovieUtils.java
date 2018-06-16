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
     * This method gets missing data ready for detailActivity
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

            // TODO need to find what is needed for the reviews
            // get movie review(s)
//            JSONObject reviews = movieJsonObj.getJSONObject(context.getString(R.string.reviews));
//            JSONArray reviewArr = reviews.getJSONArray(context.getString(R.string.results));
//            for(int i = 0; i < reviewArr.length(); i++) {
//                JSONObject review = reviewArr.getJSONObject(i);
//            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /*
    {"adult":false,"backdrop_path":"/jjuDhXlAY7yHc50t8gEZWvlkoEn.jpg",
    "belongs_to_collection":{"id":344830,"name":"Fifty Shades Collection","poster_path":"/oJrMaAhQlV5K9QFhulFehTn7JVn.jpg","backdrop_path":"/5OmblvyjPX4QRI77y9LPGttbHct.jpg"},
    "budget":55000000,"genres":[{"id":18,"name":"Drama"},{"id":10749,"name":"Romance"}],
    "homepage":"http://www.fiftyshadesmovie.com",
    "id":337167,
    "imdb_id":"tt4477536",
    "original_language":"en",
    "original_title":"Fifty Shades Freed",
    "overview":"Believing they have left behind shadowy figures from their past, newlyweds Christian and Ana fully embrace an inextricable connection and shared life of luxury. But just as she steps into her role as Mrs. Grey and he relaxes into an unfamiliar stability, new threats could jeopardize their happy ending before it even begins.",
    "popularity":107.722553,
    "poster_path":"/jjPJ4s3DWZZvI4vw8Xfi4Vqa1Q8.jpg",
    "production_companies":[{"id":33,"logo_path":"/8lvHyhjr8oUKOOy2dKXoALWKdp0.png","name":"Universal Pictures","origin_country":"US"}],
    "production_countries":[{"iso_3166_1":"US","name":"United States of America"}],
    "release_date":"2018-01-17",
    "revenue":368307760,
    "runtime":105,
    "spoken_languages":[{"iso_639_1":"ny","name":""}],
    "status":"Released",
    "tagline":"Don't miss the climax",
    "title":"Fifty Shades Freed",
    "video":false,
    "vote_average":6.0,
    "vote_count":2047,
    "videos":{"results":[{"id":"59b5e37bc3a3682b0a0049e6","iso_639_1":"en","iso_3166_1":"US","key":"GpAuCG6iUcA","name":"Teaser","site":"YouTube","size":1080,"type":"Teaser"},{"id":"5a972f4792514128a40052ea","iso_639_1":"en","iso_3166_1":"US","key":"nJCc5HRPxYA","name":"Official Trailer","site":"YouTube","size":1080,"type":"Trailer"},{"id":"5ae5e14a925141309a006d84","iso_639_1":"en","iso_3166_1":"US","key":"Aj6I54yrsF0","name":"Christian Surprises Ana","site":"YouTube","size":1080,"type":"Clip"},{"id":"5ae5e17692514102b50059eb","iso_639_1":"en","iso_3166_1":"US","key":"fHKZeHeNOTw","name":"Ana & Christian Are Followed","site":"YouTube","size":1080,"type":"Clip"},{"id":"5ae5e18dc3a36806460032b7","iso_639_1":"en","iso_3166_1":"US","key":"S9XpznYp3H4","name":"Ana Asks Christian","site":"YouTube","size":1080,"type":"Clip"},{"id":"5ae5e1a10e0a26792e005a3c","iso_639_1":"en","iso_3166_1":"US","key":"3jKInIOqyug","name":"Christian Asks Ana","site":"YouTube","size":1080,"type":"Clip"},{"id":"5ae5e1b69251417ca5004a32","iso_639_1":"en","iso_3166_1":"US","key":"OUSuHTgDSls","name":"Ana Surprises Christian","site":"YouTube","size":1080,"type":"Clip"},{"id":"5ae5e1cb0e0a267c54005bb2","iso_639_1":"en","iso_3166_1":"US","key":"p2BzY8eX2Bc","name":"Ana Confronts Gia","site":"YouTube","size":1080,"type":"Clip"},{"id":"5ae5e2e70e0a2678e3006788","iso_639_1":"en","iso_3166_1":"US","key":"b8iGoNlWFY4","name":"Behind the Scenes - The Honeymoon","site":"YouTube","size":1080,"type":"Featurette"}]},
    "reviews":{"page":1,"results":[{"author":"ehabsalah","content":"Nice Movie ♥","id":"5ace51a9c3a36834de09f933","url":"https://www.themoviedb.org/review/5ace51a9c3a36834de09f933"}],"total_pages":1,"total_results":1}}
    */

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
