package com.example.slorber.moviefiend;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.NavUtils;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.slorber.moviefiend.Loaders.GetMovieDetailLoader;
import com.example.slorber.moviefiend.Models.Movie;
import com.example.slorber.moviefiend.Views.MovieDetailsView;
import com.example.slorber.moviefiend.Views.RatingView;
import com.squareup.picasso.Picasso;

public class MovieDetailFragment extends Fragment {

    public static final String EXTRA_ID = "id";
    public static final String EXTRA_MOVIE = "Movie";
    private static final String IMAGE_URL = "http://image.tmdb.org/t/p/w500/";
    private static final String MOVIE_PARAM = "movie_param";
    private static final String DEEPLINK_PARAM = "deeplink_param";
    private Movie mMovie;
    private RatingView mRatingView;
    private MovieDetailsView mMovieDetailsView;
    private ImageView mImageView;
    private OnSimilarMovieClickListener mListener;
    private View mView;

    public MovieDetailFragment() {
    }

    public static MovieDetailFragment newInstance(Movie movie) {
        MovieDetailFragment fragment = new MovieDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(MOVIE_PARAM, movie);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mMovie = getArguments().getParcelable(MOVIE_PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.activity_movie_detail, container, false);
            mImageView = (ImageView) mView.findViewById(R.id.movie_image);
            mMovieDetailsView = (MovieDetailsView) mView.findViewById(R.id.movie_details_view);
            mRatingView = (RatingView) mView.findViewById(R.id.rating_view);
            mMovieDetailsView.getSimilarTextView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onSimilarMovieClick(mMovie.getId());
                }
            });
            ((AppCompatActivity) getActivity()).setSupportActionBar((Toolbar) mView.findViewById(R.id.toolbar));
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
            setMovie(mMovie);
        }
        return mView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSimilarMovieClickListener) {
            mListener = (OnSimilarMovieClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void setMovie(Movie movie) {
        mMovie = movie;
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(mMovie.getTitle());
        Uri backdropUri = Uri.parse(IMAGE_URL)
                .buildUpon()
                .appendEncodedPath(mMovie.getBackdropPath() != null ? mMovie.getBackdropPath() : mMovie.getPosterPath())
                .build();
        Picasso.with(mImageView.getContext()).load(backdropUri).into(mImageView);
        mImageView.setBackgroundColor(Color.parseColor("#11000000"));
        mMovieDetailsView.setMovie(mMovie);
        mRatingView.setRating(mMovie.getVotes() / 2);
    }

    public interface OnSimilarMovieClickListener {
        void onSimilarMovieClick(int id);
    }
}
