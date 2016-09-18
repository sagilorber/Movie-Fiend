package com.example.slorber.moviefiend;

import android.content.ClipData;
import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.github.ornolfr.ratingview.RatingView;
import com.squareup.picasso.Picasso;

/**
 * This view includes the movie poster, rating and overview.
 * after inflated setItem is called with the movie object to assign the content.
 * if called from similar movies screen the the "similar" link is hidden.
 * Created by slorber on 12/09/2016.
 */
public class MovieDetailsView extends ScrollView {

    private static final String IMAGE_URL = "http://image.tmdb.org/t/p/w500/";
    private TextView mDescriptionTextView;
    private ImageView mImageView;
    private RatingView mStar;


    public MovieDetailsView(Context c) {
        this(c, null);
    }

    public MovieDetailsView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MovieDetailsView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        LayoutInflater.from(context).inflate(R.layout.movie_details_child, this, true);
        setupChildren();
    }

    private void setupChildren() {

        mDescriptionTextView = (TextView) findViewById(R.id.overview);
        mImageView = (ImageView) findViewById(R.id.movie_large_image);
        mStar = (RatingView)findViewById(R.id.movie_star);
    }

    public void hideSimilarMovieLink(){
        ((TextView)findViewById(R.id.similar_movies_label)).setVisibility(View.GONE);
    }

    public void setMovie(Movie movie) {

        mDescriptionTextView.setText(movie.getOverview());
        mStar.setRating(movie.getVotes()/2);
        final Uri PosterUri = Uri.parse(IMAGE_URL)
                .buildUpon()
                .appendEncodedPath(movie.getPosterPath())
                .build();
        Picasso.with(mImageView.getContext()).load(PosterUri).into(mImageView);
    }

    public ImageView getImageView () {
        return mImageView;
    }



    public TextView getDescriptionTextView() {
        return mDescriptionTextView;
    }
}
