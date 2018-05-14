package com.btandjaja.www.popular_movies_app.utilities;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import com.btandjaja.www.popular_movies_app.MovieAdapters.Movie;
import com.btandjaja.www.popular_movies_app.data.MovieContract.MovieEntry;

public class MovieUtils {
    /* movies data constants to retrieve data */
    private final static String RESULTS = "results";
    private final static int POPULARITY = 1;

    /** This method creates Movie object from JSONstring and stores in movieList
     *
     * @param jsonMovies    List of movies in JSON string
     * @param movieList     List of movies to be added to
     */
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
                String overView = singleMovie.getString(MovieEntry.COLUMN_NAME_OVER_VIEW);
                String releaseDate = singleMovie.getString(MovieEntry.COLUMN_NAME_RELEASE_DATE);
                movieList.add(new Movie(voteAvg, popularity, originalTitle, posterPath, overView,
                        releaseDate));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /** This method initialized the SQLite table
     *
     * @param sqLiteDatabase    empty SQLite table
     * @param movieList         list of movies to be added to the table
     */
    public static void initializedDb(SQLiteDatabase sqLiteDatabase, ArrayList<Movie> movieList) {
        if(sqLiteDatabase == null) return;

        /* create movie list from the web */
        List<ContentValues> movieContentValueList = new ArrayList<ContentValues>();
        initializeDbHelper(movieContentValueList, movieList);

        /* attempt to insert movie to sqlite table */
        try {
            sqLiteDatabase.beginTransaction();
            /* clear table */
            sqLiteDatabase.delete(MovieEntry.TABLE_NAME, null, null);
            /* add list to table */
            for (ContentValues singleMovie : movieContentValueList) {
                sqLiteDatabase.insert(MovieEntry.TABLE_NAME, null, singleMovie);
            }
            sqLiteDatabase.setTransactionSuccessful();
        } catch (SQLiteException e){
            /* something went terribly wrong */
            e.printStackTrace();
        } finally {
            /* once successful, end transaction */
            sqLiteDatabase.endTransaction();
        }
    }

    /** This method complement initializeDb method
     *
     * @param movieContentValueList empty list with movies to be added to
     * @param movieList             list of movies
     */
    private static void initializeDbHelper(List<ContentValues> movieContentValueList, ArrayList<Movie> movieList) {
        for(Movie m : movieList) {
            ContentValues singleMovie = new ContentValues();
            singleMovie.put(MovieEntry.COLUMN_NAME_TITLE, m.getTitle());
            singleMovie.put(MovieEntry.COLUMN_NAME_OVER_VIEW, m.getOverView());
            singleMovie.put(MovieEntry.COLUMN_NAME_RELEASE_DATE, m.getReleaseDate());
            singleMovie.put(MovieEntry.COLUMN_NAME_POSTER_PATH, m.getPosterPath());
            singleMovie.put(MovieEntry.COLUMN_NAME_POPULARITY, m.getPopularity());
            singleMovie.put(MovieEntry.COLUMN_NAME_VOTE_AVERAGE, m.getVoteAvg());
            movieContentValueList.add(singleMovie);
        }
    }

    public static Cursor getAllMovies(SQLiteDatabase sqLiteDatabase) {
        return sqLiteDatabase.query(MovieEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                MovieEntry._ID);
    }

    public static Cursor sort(Cursor cursor, SQLiteDatabase sqLiteDatabase, int sortType) {
        String typeSort = sortType == cursor.getColumnIndex(MovieEntry.COLUMN_NAME_POPULARITY) ?
                MovieEntry.COLUMN_NAME_POPULARITY : MovieEntry.COLUMN_NAME_VOTE_AVERAGE;
        return sqLiteDatabase.query(MovieEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                typeSort + " DESC");
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
