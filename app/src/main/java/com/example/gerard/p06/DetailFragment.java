package com.example.gerard.p06;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/**
 * Created by gerard on 06/11/2017.
 */

public class DetailFragment extends Fragment {
    View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, null);
        mView = inflater.inflate(R.layout.fragment_detail, container, false);

        Intent i = getActivity().getIntent();

        if (i != null) {
            Movie movie = (Movie) i.getSerializableExtra("movie");
            updateUi(movie);
        }

        return mView;
    }

    public void updateUi(Movie movie) {
        if(movie != null) {
            ((TextView) mView.findViewById(R.id.tvTitle)).setText(movie.getTitle());
            ((TextView) mView.findViewById(R.id.tvReleaseDate)).setText(movie.getReleaseDate());
            ((TextView) mView.findViewById(R.id.tvSynopsis)).setText(Html.fromHtml("<b>Synopsis:</b> " + movie.getOverview()));
            Glide.with(this).load(
                    "https://image.tmdb.org/t/p/w500/" + movie.getPosterPath()
            ).into(((ImageView) mView.findViewById(R.id.ivPosterImage)));
        }
    }
}
