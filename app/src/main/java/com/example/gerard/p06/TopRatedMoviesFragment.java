package com.example.gerard.p06;

/**
 * Created by gerard on 30/10/2017.
 */

public class TopRatedMoviesFragment extends MoviesListFragment {
    public TopRatedMoviesFragment(){}

    @Override
    public void populateList() {
        new GetMoviesTask().execute("top_rated");
    }
}
