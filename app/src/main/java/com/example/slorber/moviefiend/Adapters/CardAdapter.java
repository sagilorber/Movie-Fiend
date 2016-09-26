package com.example.slorber.moviefiend.Adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.slorber.moviefiend.Models.Movie;
import com.example.slorber.moviefiend.R;
import com.github.ornolfr.ratingview.RatingView;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * This adapter class gets a list of Movie objects and sets it to the movie_card layout.
 * The card items are a card image, title and rating.
 * Created by slorber on 07/09/2016.
 */
public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    private List<Movie> mDataset;
    private OnCardClickListener mListener;

    public CardAdapter(List<Movie> myDataset, Context context) {
        mDataset = myDataset;
        mListener = (OnCardClickListener) context;
    }

    @Override
    public CardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        FrameLayout v = (FrameLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_card, parent, false);
        ViewHolder vh = new ViewHolder(v, mListener);
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

    public List<Movie> getMovieList() {
        return mDataset;
    }

    public interface OnCardClickListener {
        void onCardClick(Movie movie);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private static final String IMAGE_URL = "http://image.tmdb.org/t/p/w500/";
        private RatingView mStarView;
        private CardView mCardView;
        private ImageView mImageView;
        private TextView mTextView;
        private OnCardClickListener mListener;

        public ViewHolder(FrameLayout v, OnCardClickListener listener) {
            super(v);
            mCardView = (CardView) v.findViewById(R.id.card);
            mImageView = (ImageView) v.findViewById(R.id.card_image);
            mStarView = (RatingView) v.findViewById(R.id.card_star);
            mTextView = (TextView) v.findViewById(R.id.card_text);
            mListener = listener;
        }

        public void setMovie(final Movie movie) {
            mStarView.setRating(movie.getVotes() / 2);
            mTextView.setText(movie.getTitle());
            Uri builtUri = Uri.parse(IMAGE_URL)
                    .buildUpon()
                    .appendEncodedPath(movie.getBackdropPath() != null ? movie.getBackdropPath() : movie.getPosterPath())
                    .build();
            Picasso.with(mImageView.getContext()).load(builtUri).into(mImageView);
            mCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onCardClick(movie);
                }
            });
        }

        public int dp2px(int dp, Context context) {
            DisplayMetrics displaymetrics = context.getResources().getDisplayMetrics();
            return (int) (dp * displaymetrics.density + 0.5f);
        }
    }

}

