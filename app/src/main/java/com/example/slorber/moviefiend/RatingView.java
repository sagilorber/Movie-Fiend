/*
 * Copyright (c) 2016 PayPal, Inc.
 *
 * All rights reserved.
 *
 * THIS CODE AND INFORMATION ARE PROVIDED "AS IS" WITHOUT WARRANTY OF ANY
 * KIND, EITHER EXPRESSED OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND/OR FITNESS FOR A
 * PARTICULAR PURPOSE.
 */

package com.example.slorber.moviefiend;

import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.media.Rating;
import android.net.Uri;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.StringTokenizer;

/**
 * TODO: Write Javadoc for RatingView.
 *
 * @author slorber
 */
public class RatingView extends RelativeLayout{

    private RatingBar mStar;
    private TextView mTextView;

    public static MovieDetailsView inflate(ViewGroup parent) {
        MovieDetailsView itemView = (MovieDetailsView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rating_view, parent, false);
        return itemView;
    }

    public RatingView(Context c) {
        this(c, null);
    }

    public RatingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RatingView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        LayoutInflater.from(context).inflate(R.layout.rating_view_child, this, true);
        setupChildren();
    }

    private void setupChildren() {

        mStar = (RatingBar)findViewById(R.id.movie_star);
        mTextView = (TextView)findViewById(R.id.rating_view_text);
    }


    public void setRating(double newRating) {

        int color = Color.RED;
        int animTime = 1000;
        if(2<newRating && newRating<3){
            color = Color.YELLOW;
            animTime = 1200;
        }
        else if(newRating>3){
            color = Color.GREEN;
            animTime = 1500;
        }

        LayerDrawable stars = (LayerDrawable) mStar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(color, PorterDuff.Mode.SRC_IN);
        stars.getDrawable(1).setColorFilter(color, PorterDuff.Mode.SRC_IN);

        ObjectAnimator anim = ObjectAnimator.ofFloat(mStar, "rating",0f, (float)newRating);
        anim.setDuration(animTime);
        animateTextView(0, (float)newRating, mTextView,animTime);

        anim.start();

    }
    public void animateTextView(float initialValue, float finalValue, final TextView  textview, int animTime) {

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(initialValue, finalValue);
        valueAnimator.setDuration(animTime);

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                textview.setText(String.format("%.1f", valueAnimator.getAnimatedValue()).toString()+"/5");

            }
        });
        valueAnimator.start();

    }
    public RatingBar getRatingView () {
        return mStar;
    }

}

