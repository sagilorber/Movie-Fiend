package com.example.slorber.moviefiend;

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
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.ornolfr.ratingview.RatingView;
import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity {


    public static final String EXTRA_MOVIE = "Movie";
    private static final String IMAGE_URL = "http://image.tmdb.org/t/p/w500/";
    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ImageView image = (ImageView) findViewById(R.id.movie_image);
        ImageView largeImage = (ImageView) findViewById(R.id.movie_large_image);
        TextView overview = (TextView) findViewById(R.id.overview);
        RatingView star = (RatingView) findViewById(R.id.movie_star);
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
        final Uri posterUri = Uri.parse(IMAGE_URL)
                .buildUpon()
                .appendEncodedPath(movie.getPosterPath())
                .build();
        Picasso.with(image.getContext()).load(backdropUri).into(image);
        image.setBackgroundColor(Color.parseColor("#11000000"));
        Picasso.with(largeImage.getContext()).load(posterUri).into(largeImage);
        largeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog d = new Dialog(MovieDetailActivity.this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                d.setCancelable(true);
                d.setContentView(R.layout.dialogbrand_layout);
                ImageView myImage = (ImageView) d.findViewById(R.id.imageView1);
                Picasso.with(myImage.getContext()).load(posterUri).into(myImage);
                d.show();

            }
        });
        star.setRating(movie.getVotes() / 2);
        overview.setText(movie.getOverview());

    }

    public void onSimilarMoviesLabelClick(View v) {
        Intent intent = new Intent(this, SimilarMoviesActivity.class);
        intent.putExtra(SimilarMoviesActivity.EXTRA_ID, movie.getId());
        startActivity(intent);
    }

}
