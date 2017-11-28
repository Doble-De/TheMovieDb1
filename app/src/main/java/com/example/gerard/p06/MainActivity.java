package com.example.gerard.p06;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements MoviesListFragment.OnMovieSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(null);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onMovieSelected(Movie movie) {

        if(isLand()){
            DetailFragment fragment = (DetailFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentDetail);
            fragment.updateUi(movie);

        }else {
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra("movie", movie);
            startActivity(intent);
        }
    }


    boolean isLand() {
        return getResources().getBoolean(R.bool.land);
    }
}
