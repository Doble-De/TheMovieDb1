package com.example.gerard.p06;

public class PopularMoviesFragment extends MoviesListFragment{

    public PopularMoviesFragment(){}

    @Override
    public void populateList() {
        new GetMoviesTask().execute("popular");
    }
}
