package com.example.slorber.moviefiend;

import android.app.Dialog;
import android.graphics.Color;
import android.media.Image;
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
        Picasso.with(image.getContext()).load("http://image.tmdb.org/t/p/w500/"+(movie.getBackdropPath()!=null?movie.getBackdropPath():movie.getPosterPath())).into(image);
        image.setBackgroundColor(Color.parseColor("#11000000"));
        Picasso.with(largeImage.getContext()).load("http://image.tmdb.org/t/p/w500/"+movie.getPosterPath()).into(largeImage);
        largeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog d = new Dialog(MovieDetailActivity.this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                d.setCancelable(true);
                d.setContentView(R.layout.dialogbrand_layout);
                ImageView myImage = (ImageView) d.findViewById(R.id.imageView1);
                Picasso.with(myImage.getContext()).load("http://image.tmdb.org/t/p/w500/"+movie.getPosterPath()).into(myImage);
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
}
