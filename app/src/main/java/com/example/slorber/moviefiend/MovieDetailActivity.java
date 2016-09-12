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
    Movie movie;
    private static String IMAGE_URL = "http://image.tmdb.org/t/p/w500/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ImageView image = (ImageView)findViewById(R.id.movie_image);
        ImageView largeImage = (ImageView)findViewById(R.id.movie_large_image);
        TextView overview = (TextView)findViewById(R.id.overview);
        RatingView star  = (RatingView)findViewById(R.id.movie_star);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Bundle b = this.getIntent().getExtras();
        if (b != null)
            movie = b.getParcelable("Movie");
        getSupportActionBar().setTitle(movie.getTitle());

        Uri BackdropUri = Uri.parse(IMAGE_URL)
                .buildUpon()
                .appendEncodedPath(movie.getBackdropPath()!=null?movie.getBackdropPath():movie.getPosterPath())
                .build();
        final Uri PosterUri = Uri.parse(IMAGE_URL)
                .buildUpon()
                .appendEncodedPath(movie.getPosterPath())
                .build();
        Picasso.with(image.getContext()).load(BackdropUri).into(image);
        image.setBackgroundColor(Color.parseColor("#11000000"));
        Picasso.with(largeImage.getContext()).load(PosterUri).into(largeImage);
        largeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog d = new Dialog(MovieDetailActivity.this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                d.setCancelable(true);
                d.setContentView(R.layout.dialogbrand_layout);
                ImageView myImage = (ImageView) d.findViewById(R.id.imageView1);
                Picasso.with(myImage.getContext()).load(PosterUri).into(myImage);
                d.show();

            }
        });
        star.setRating(movie.getVotes()/2);
        overview.setText(movie.getOverview());

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
    public void onLabelClick(View v) {
        Intent intent = new Intent(this,SimilarMoviesActivity.class);
        intent.putExtra("id",movie.getId());
        startActivity(intent);
    }
}
