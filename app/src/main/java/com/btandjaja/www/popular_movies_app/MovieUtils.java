package com.btandjaja.www.popular_movies_app;

import org.json.JSONException;
import org.json.JSONObject;

public class MovieUtils {
    /* to get arrays of movies */
    private final static String RESULTS = "results";


    public String[] movies(String jsonMovies) {
        try {
            JSONObject movieJsonObj = new JSONObject(jsonMovies);
            s
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
