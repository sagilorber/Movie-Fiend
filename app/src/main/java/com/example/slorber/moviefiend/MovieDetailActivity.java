package com.example.slorber.moviefiend;

import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity {


    public static final String EXTRA_MOVIE = "Movie";
    private static final String IMAGE_URL = "http://image.tmdb.org/t/p/w500/";
    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ImageView imageView = (ImageView) findViewById(R.id.movie_image);
        MovieDetailsView movieDetailsView = (MovieDetailsView) findViewById(R.id.movie_details_view);
        RatingView ratingView = (RatingView) findViewById(R.id.rating_view);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle b = this.getIntent().getExtras();
        if (b != null) {
            movie = b.getParcelable(EXTRA_MOVIE);
        }
        getSupportActionBar().setTitle(movie.getTitle());
        Uri backdropUri = Uri.parse(IMAGE_URL)
                .buildUpon()
                .appendEncodedPath(movie.getBackdropPath() != null ? movie.getBackdropPath() : movie.getPosterPath())
                .build();
        Picasso.with(imageView.getContext()).load(backdropUri).into(imageView);
        imageView.setBackgroundColor(Color.parseColor("#11000000"));
        movieDetailsView.setMovie(movie);
        ratingView.setRating(movie.getVotes() / 2);

    }

    public void onSimilarMoviesLabelClick(View v) {
        Intent intent = new Intent(this, SimilarMoviesActivity.class);
        intent.putExtra(SimilarMoviesActivity.EXTRA_ID, movie.getId());
        startActivity(intent);
    }

}
