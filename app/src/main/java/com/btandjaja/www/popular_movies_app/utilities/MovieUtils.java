package com.btandjaja.www.popular_movies_app.utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import com.btandjaja.www.popular_movies_app.MovieAdapters.Movie;
import com.btandjaja.www.popular_movies_app.data.MovieContract.MovieEntry;

public class MovieUtils {
    /* movies data constants to retrieve data*/
    private final static String RESULTS = "results";

    public static void getMovieList(String jsonMovies, ArrayList<Movie> movieList) {
        try {
            JSONObject movieJsonObj = new JSONObject(jsonMovies);
            JSONArray movieJsonArr = movieJsonObj.getJSONArray(RESULTS);
            for(int i = 0; i < movieJsonArr.length(); i++) {
                JSONObject singleMovie = movieJsonArr.getJSONObject(i);
                Double voteAvg = singleMovie.getDouble(MovieEntry.COLUMN_NAME_VOTE_AVERAGE);
                Double popularity = singleMovie.getDouble(MovieEntry.COLUMN_NAME_POPULARITY);
                String originalTitle = singleMovie.getString(MovieEntry.COLUMN_NAME_TITLE);
                String posterPath = singleMovie.getString(MovieEntry.COLUMN_NAME_POSTER_PATH);
                String overView = singleMovie.getString(MovieEntry.COLUM_NAME_OVER_VIEW);
                String releaseDate = singleMovie.getString(MovieEntry.COLUMN_NAME_RELEASE_DATE);
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

    //TODO remove
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