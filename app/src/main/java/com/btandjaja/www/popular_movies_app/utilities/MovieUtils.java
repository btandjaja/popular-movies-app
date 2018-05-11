package com.btandjaja.www.popular_movies_app.utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import com.btandjaja.www.popular_movies_app.MovieAdapters.Movie;

public class MovieUtils {
    /* movies data constants to retrieve data*/
    private final static String RESULTS = "results";
    private final static String AVERAGE = "vote_average";
    private final static String POPULARITY = "popularity";
    private final static String ORIGINAL_TITLE = "original_title";
    private final static String POSTER_PATH = "poster_path";
    private final static String OVERVIEW = "overview";
    private final static String RELEASE_DATE = "release_date";

    public static void getMovieList(String jsonMovies, ArrayList<Movie> movieList) {
        try {
            JSONObject movieJsonObj = new JSONObject(jsonMovies);
            JSONArray movieJsonArr = movieJsonObj.getJSONArray(RESULTS);
            for(int i = 0; i < movieJsonArr.length(); i++) {
                JSONObject singleMovie = movieJsonArr.getJSONObject(i);
                Double voteAvg = singleMovie.getDouble(AVERAGE);
                Double popularity = singleMovie.getDouble(POPULARITY);
                String originalTitle = singleMovie.getString(ORIGINAL_TITLE);
                String posterPath = singleMovie.getString(POSTER_PATH);
                String overView = singleMovie.getString(OVERVIEW);
                String releaseDate = singleMovie.getString(RELEASE_DATE);
                movieList.add(new Movie(voteAvg, popularity, originalTitle, posterPath, overView,
                        releaseDate));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /** This method will take the movies base on user input (popularity or rating)
     *
     *  @param movieList    contains the list of movies to be sorted
     *  @param sortType     sort type, 0 = popularity, 1 = rating
     */
    public static void sort(ArrayList<Movie> movieList, int sortType) {
        Movie tempMovie;

    }

    /** This method will sort the movie list based on sorting types, 0 = popularity, 1 = rating
     *
     * @param movieList     List of movies
     * @param sortType
     * @return
     */
    private static ArrayList<Movie> mergeSort(ArrayList<Movie> movieList, int sortType) {
        if (movieList == null || movieList.size() <= 1 ) return movieList;
        int mid = movieList.size() / 2 - 1;
        int end = movieList.size() - 1;
        ArrayList<Movie> leftList = (ArrayList)
                mergeSort((ArrayList) movieList.subList(0, mid), sortType);
        ArrayList<Movie> rightList = (ArrayList)
                mergeSort((ArrayList) movieList.subList(mid + 1, end), sortType);
        merge(leftList, rightList, sortType);
        return movieList;
    }

    private static void merge(ArrayList<Movie> leftList, ArrayList<Movie> rightList, int sortType) {
        Movie tempMovie;
        int i = 0, j = 0;
        while(i < leftList.size() - 1 && j < rightList.size() - 1) {
            switch(sortType) {
                case 0:

                    break;
                case 1:
                    break;
            }
        }
    }

//    public static void copy(ArrayList<Movie> movieList, ArrayList<Movie> newList) {
//        if (movieList.size() == 0) return;
//        for (Movie movie : movieList) {
//            Double voteAvg = movie.getVoteAvg();
//            Double popularity = movie.getPopularity();
//            String originalTitle = movie.getTitle();
//            String posterPath = movie.getPosterPath();
//            String overView = movie.getOverView();
//            String releaseDate = movie.getReleaseDate();
//            newList.add(new Movie(voteAvg, popularity, originalTitle, posterPath, overView,
//                    releaseDate));
//        }
//    }
}
