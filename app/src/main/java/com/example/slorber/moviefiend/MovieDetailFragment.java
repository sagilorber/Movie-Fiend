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

public class MovieDetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Movie> {

    public static final String EXTRA_ID = "id";
    public static final String EXTRA_MOVIE = "Movie";
    private static final String URL = "http://api.themoviedb.org";
    private static final String IMAGE_URL = "http://image.tmdb.org/t/p/w500/";
    private static final String MOVIE_PARAM = "movie_param";
    private static final String DEEPLINK_PARAM = "deeplink_param";
    private Movie mMovie;
    private String mDeepLink;
    private RatingView ratingView;
    private MovieDetailsView movieDetailsView;
    private ImageView imageView;
    private OnSimilarMovieClickListener mListener;
    private View view;

    public MovieDetailFragment() {
    }

    public static MovieDetailFragment newInstance(Movie movie, String fromDeepLink) {
        MovieDetailFragment fragment = new MovieDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(MOVIE_PARAM, movie);
        args.putString(DEEPLINK_PARAM, fromDeepLink);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mMovie = getArguments().getParcelable(MOVIE_PARAM);
            mDeepLink = getArguments().getString(DEEPLINK_PARAM);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.activity_movie_detail, container, false);
            imageView = (ImageView) view.findViewById(R.id.movie_image);
            movieDetailsView = (MovieDetailsView) view.findViewById(R.id.movie_details_view);
            ratingView = (RatingView) view.findViewById(R.id.rating_view);
            movieDetailsView.getSimilarTextView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onSimilarMovieClick(mMovie.getId());
                }
            });
            ((AppCompatActivity) getActivity()).setSupportActionBar((Toolbar) view.findViewById(R.id.toolbar));
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
            if (mDeepLink != null) {
                checkDeepLink();
            } else {
                setMovie(mMovie);
            }
        }

        return view;
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

    @Override
    public Loader<Movie> onCreateLoader(int id, Bundle args) {
        return new GetMovieDetailLoader(getActivity(), Uri.parse(URL)
                .buildUpon()
                .appendPath("3")
                .appendPath("movie")
                .appendPath(String.valueOf(args.get(EXTRA_ID)))
                .appendQueryParameter("api_key", getString(R.string.tmdb_api_key))
                .build().toString());
    }

    @Override
    public void onLoadFinished(Loader<Movie> loader, Movie data) {
        if (data == null) {
            Toast.makeText(getActivity(), "Server error... :(", Toast.LENGTH_LONG).show();
            return;
        }
        setMovie(data);
    }

    @Override
    public void onLoaderReset(Loader<Movie> loader) {

    }

    private void checkDeepLink() {
        Uri extraUri = Uri.parse(mDeepLink);
        if (extraUri.getLastPathSegment().contains("-") && TextUtils.isDigitsOnly(extraUri.getLastPathSegment().split("-")[0])) {
            String[] parts = extraUri.getLastPathSegment().split("-");
            int deepLinkMovieId = Integer.parseInt(parts[0]);
            getActivity().getSupportLoaderManager().destroyLoader(deepLinkMovieId);
            Bundle b = new Bundle();
            b.putInt(EXTRA_ID, deepLinkMovieId);
            getActivity().getSupportLoaderManager().initLoader(deepLinkMovieId, b, this);
        } else {
            Toast.makeText(getActivity(), "Movie id not found", Toast.LENGTH_LONG).show();
        }

    }

    public void setDeepLink(String deepLink) {
        mDeepLink = deepLink;
        checkDeepLink();

    }

    public void setMovie(Movie movie) {
        mMovie = movie;
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(mMovie.getTitle());
        Uri backdropUri = Uri.parse(IMAGE_URL)
                .buildUpon()
                .appendEncodedPath(mMovie.getBackdropPath() != null ? mMovie.getBackdropPath() : mMovie.getPosterPath())
                .build();
        Picasso.with(imageView.getContext()).load(backdropUri).into(imageView);
        imageView.setBackgroundColor(Color.parseColor("#11000000"));
        movieDetailsView.setMovie(mMovie);
        ratingView.setRating(mMovie.getVotes() / 2);
    }

    public interface OnSimilarMovieClickListener {
        void onSimilarMovieClick(int id);
    }
}
