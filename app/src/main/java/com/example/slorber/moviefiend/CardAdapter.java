package com.example.slorber.moviefiend;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.ornolfr.ratingview.RatingView;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by slorber on 07/09/2016.
 * This adapter class gets a list of Movie objects and sets it to the movie_card layout.
 * The card items are a card image, title and rating.
 *
 */
public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public RatingView mStarView;
        public CardView mCardView;
        public ImageView mImageView;
        public TextView mTextView;
        OnCardClickListener mListener;
        private static String IMAGE_URL = "http://image.tmdb.org/t/p/w500/";

        public ViewHolder(FrameLayout v,OnCardClickListener listener) {
            super(v);
            mCardView  = (CardView) v.findViewById(R.id.card);
            mImageView = (ImageView) v.findViewById(R.id.card_image);
            mStarView = (RatingView)v.findViewById(R.id.card_star);
            mTextView = (TextView)v.findViewById(R.id.card_text);
            mListener = listener;
        }
        public void setMovie(final Movie m)
        {
            mStarView.setRating(m.getVotes()/2);
            mTextView.setText(m.getTitle());
            Uri builtUri = Uri.parse(IMAGE_URL)
                    .buildUpon()
                    .appendEncodedPath(m.getBackdropPath()!=null?m.getBackdropPath():m.getPosterPath())
                    .build();
            Picasso.with(mImageView.getContext()).load(builtUri).resize(dp2px(220,mImageView.getContext()), 0).into(mImageView);
            mCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.OnCardClick(m);
                }
            });
        }
        public int dp2px(int dp,Context context) {

            DisplayMetrics displaymetrics = context.getResources().getDisplayMetrics();
            return (int) (dp * displaymetrics.density + 0.5f);
        }
    }

    private List<Movie> mDataset;
    private OnCardClickListener mListener;

    public CardAdapter(List<Movie> myDataset,Context context) {
        mDataset = myDataset;
        mListener = (OnCardClickListener)context;
    }

    @Override
    public CardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        // create a new view
        FrameLayout v = (FrameLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_card, parent, false);
        ViewHolder vh = new ViewHolder(v,mListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setMovie(mDataset.get(position));
    }
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface OnCardClickListener {
        // TODO: Update argument type and name
        void OnCardClick(Movie movie);
    }

}

