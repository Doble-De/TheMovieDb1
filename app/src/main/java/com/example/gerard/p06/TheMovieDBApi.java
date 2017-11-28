package com.example.gerard.p06;

import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public abstract class TheMovieDBApi {

    static private final String BASE_URL = "http://api.themoviedb.org/3";
    static private final String API_KEY = "your_api_key";
    static private final int PAGES = 5;

    static public ArrayList<Movie> getMovies(String type) {
        ArrayList<Movie> movies = new ArrayList<>();
        for (int i = 1; i < PAGES; i++) {
            String url = getUrlPage(type, i);
            String jsonResponse = HttpUtils.get(url);
            movies.addAll(processJson(jsonResponse));
        }

        return movies;
    }

    static private String getUrlPage(String type, int page) {
        Uri builtUri = Uri.parse(BASE_URL)
                .buildUpon()
                .appendPath("movie")
                .appendPath(type)
                .appendQueryParameter("api_key", API_KEY)
                .appendQueryParameter("page", String.valueOf(page))
                .build();
        return builtUri.toString();
    }

    static private ArrayList<Movie> processJson(String jsonResponse) {
        ArrayList<Movie> movies = new ArrayList<>();

        if(jsonResponse == null) return movies;

        try {
            JSONObject data = new JSONObject(jsonResponse);
            JSONArray jsonMovies = data.getJSONArray("results");
            for (int i = 0; i < jsonMovies.length(); i++) {
                JSONObject jsonMovie = jsonMovies.getJSONObject(i);

                Movie movie = new Movie();
                movie.setTitle(jsonMovie.getString("title"));
                movie.setReleaseDate(jsonMovie.getString("release_date"));
                movie.setOverview(jsonMovie.getString("overview"));
                movie.setPosterPath(jsonMovie.getString("poster_path"));
                movie.setVoteAverage(jsonMovie.getDouble("vote_average"));

                movies.add(movie);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return movies;
    }
}
