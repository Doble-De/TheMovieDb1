package com.example.gerard.p06;

/**
 * Created by gerard on 30/10/2017.
 */

public class UpcomingMoviesFragment extends MoviesListFragment {

    public UpcomingMoviesFragment() {}

    @Override
    public void populateList() {
        new GetMoviesTask().execute("upcoming");
    }
}
