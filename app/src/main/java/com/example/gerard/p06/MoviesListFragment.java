package com.example.gerard.p06;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public abstract class MoviesListFragment extends Fragment {
    public interface OnMovieSelectedListener {
        void onMovieSelected(Movie movie);
    }

    public MoviesListFragment(){}

    OnMovieSelectedListener mListener;
    private RecyclerView mRecycler;
    RecyclerAdapter mRecyclerAdapter;
    ArrayList<Movie> movies = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_movies_list, container, false);

        mRecycler = rootView.findViewById(R.id.movies_list);
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecycler.setHasFixedSize(true);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mRecyclerAdapter = new RecyclerAdapter(movies);
        mRecycler.setAdapter(mRecyclerAdapter);

        populateList();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnMovieSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnArticleSelectedListener");
        }
    }

    public abstract void populateList();

    /* Memory leak demo*/
    /* Use LiveData, ViewModel, Room (and retrofit) */
    class GetMoviesTask extends AsyncTask<String, Void, List<Movie>> {
        @Override
        protected List<Movie> doInBackground(String... types) {
            return TheMovieDBApi.getMovies(types[0]);
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            super.onPostExecute(movies);
            MoviesListFragment.this.movies.clear();
            MoviesListFragment.this.movies.addAll(movies);
            mRecyclerAdapter.notifyDataSetChanged();
        }
    }

    class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MovieViewHolder> {

        private List<Movie> movies;

        RecyclerAdapter(List<Movie> list) {
            this.movies = list;
        }

        @Override
        public MovieViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_movie, viewGroup,false);
            return new MovieViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MovieViewHolder viewHolder, int i) {
            final Movie movie = movies.get(i);

            viewHolder.movieTitle.setText(movie.getTitle());
            viewHolder.movieVoteAverage.setRating((float) movie.getVoteAverage());
            viewHolder.movieReleaseDate.setText(movie.getReleaseDate());

            Glide.with(getActivity()).load(
                    "https://image.tmdb.org/t/p/w500/" + movie.getPosterPath()
            ).into(viewHolder.moviePoster);

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onMovieSelected(movie);
                }
            });
        }

        @Override
        public int getItemCount() {
            return (movies != null ? movies.size() : 0);
        }

        class MovieViewHolder extends RecyclerView.ViewHolder {
            private TextView movieTitle;
            private ImageView moviePoster;
            private RatingBar movieVoteAverage;
            private TextView movieReleaseDate;

            MovieViewHolder(View view) {
                super(view);
                this.movieTitle = view.findViewById(R.id.movie_title);
                this.moviePoster = view.findViewById(R.id.movie_poster);
                this.movieVoteAverage = view.findViewById(R.id.movie_vote_average);
                this.movieReleaseDate = view.findViewById(R.id.movie_release_date);
            }
        }
    }
}

