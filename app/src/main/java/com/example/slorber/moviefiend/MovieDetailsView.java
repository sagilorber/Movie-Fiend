package com.example.slorber.moviefiend;

import android.app.Dialog;
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
    private Context mContext;


    public MovieDetailsView(Context c) {
        this(c, null);
    }

    public MovieDetailsView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MovieDetailsView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.movie_details_child, this, true);
        setupChildren();
    }

    private void setupChildren() {

        mDescriptionTextView = (TextView) findViewById(R.id.overview);
        mImageView = (ImageView) findViewById(R.id.movie_large_image);
    }

    public void hideSimilarMovieLink(){
        ((TextView)findViewById(R.id.similar_movies_label)).setVisibility(View.GONE);
    }

    public void setMovie(Movie movie) {

        mDescriptionTextView.setText(movie.getOverview());
        final Uri posterUri = Uri.parse(IMAGE_URL)
                .buildUpon()
                .appendEncodedPath(movie.getPosterPath())
                .build();
        Picasso.with(mImageView.getContext()).load(posterUri).into(mImageView);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog d = new Dialog(mContext, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                d.setCancelable(true);
                d.setContentView(R.layout.dialogbrand_layout);
                ImageView myImage = (ImageView) d.findViewById(R.id.imageView1);
                Picasso.with(myImage.getContext()).load(posterUri).into(myImage);
                d.show();

            }
        });    }

    public ImageView getImageView () {
        return mImageView;
    }



    public TextView getDescriptionTextView() {
        return mDescriptionTextView;
    }
}
