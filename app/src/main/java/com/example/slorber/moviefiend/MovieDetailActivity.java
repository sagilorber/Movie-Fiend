package com.example.slorber.moviefiend;

import android.animation.ValueAnimator;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
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

import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieDetailActivity extends AppCompatActivity {


    public static final String EXTRA_MOVIE = "Movie";
    private static final String IMAGE_URL = "http://image.tmdb.org/t/p/w500/";
    private Movie mMovie;
    private RatingView mRatingView;
    private MovieDetailsView mMovieDetailsView;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        mImageView = (ImageView) findViewById(R.id.movie_image);
        mMovieDetailsView = (MovieDetailsView) findViewById(R.id.movie_details_view);
        mRatingView = (RatingView) findViewById(R.id.rating_view);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        if (getIntent().getParcelableExtra(EXTRA_MOVIE) != null) {
            setMovie((Movie) getIntent().getExtras().getParcelable(EXTRA_MOVIE));
        }

    }

    public void onSimilarMoviesLabelClick(View v) {
        Intent intent = new Intent(this, SimilarMoviesActivity.class);
        intent.putExtra(SimilarMoviesActivity.EXTRA_ID, mMovie.getId());
        startActivity(intent);
    }

    private void setMovie(Movie movie) {
        mMovie = movie;
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(mMovie.getTitle());
        Uri backdropUri = Uri.parse(IMAGE_URL)
                .buildUpon()
                .appendEncodedPath(mMovie.getBackdropPath() != null ? mMovie.getBackdropPath() : mMovie.getPosterPath())
                .build();
        Picasso.with(mImageView.getContext()).load(backdropUri).into(mImageView);
        mImageView.setBackgroundColor(Color.parseColor("#11000000"));
        mMovieDetailsView.setMovie(mMovie);
        mRatingView.setRating(mMovie.getVotes() / 2);
    }
}
