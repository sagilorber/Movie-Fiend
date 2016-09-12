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
import android.widget.TextView;

import com.github.ornolfr.ratingview.RatingView;
import com.squareup.picasso.Picasso;

/**
 * Created by slorber on 12/09/2016.
 */
public class MovieDetailsView extends RelativeLayout {
    private TextView mDescriptionTextView;
    private ImageView mImageView;
    private RatingView mStar;
    private static String IMAGE_URL = "http://image.tmdb.org/t/p/w500/";

    public static MovieDetailsView inflate(ViewGroup parent) {
        MovieDetailsView itemView = (MovieDetailsView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_details, parent, false);
        return itemView;
    }

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
        ((TextView)findViewById(R.id.similar_movies_label)).setVisibility(View.GONE);
    }

    public void setItem(Movie movie) {

        mDescriptionTextView.setText(movie.getOverview());
        mStar.setRating(movie.getVotes());
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
