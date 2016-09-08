package com.example.slorber.moviefiend;

import android.content.Context;
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
 */
public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {
    private List<Movie> mDataset;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public RatingView mStarView;
        public CardView mCardView;
        public ImageView mImageView;
        public TextView mTextView;
        public ViewHolder(FrameLayout v) {
            super(v);
            mCardView  = (CardView) v.findViewById(R.id.card);
            mImageView = (ImageView) v.findViewById(R.id.card_image);
            mStarView = (RatingView)v.findViewById(R.id.card_star);
            mTextView = (TextView)v.findViewById(R.id.card_text);

        }

    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public CardAdapter( List<Movie> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public CardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        // create a new view
        FrameLayout v = (FrameLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_card, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.mStarView.setRating(mDataset.get(position).getVotes()/2);
        holder.mTextView.setText(mDataset.get(position).getTitle());
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("", "CardView clicked");
            }
        });

        Picasso.with(holder.mImageView.getContext()).load("http://image.tmdb.org/t/p/w500/"+(mDataset.get(position).getBackdropPath()!=null?mDataset.get(position).getBackdropPath():mDataset.get(position).getPosterPath())).into(holder.mImageView);



    }
//    public int dp2px(int dp,ViewHolder holder) {
//        WindowManager wm = (WindowManager) holder.mImageView.getContext()
//                .getSystemService(Context.WINDOW_SERVICE);
//        Display display = wm.getDefaultDisplay();
//        DisplayMetrics displaymetrics = new DisplayMetrics();
//        display.getMetrics(displaymetrics);
//        return (int) (dp * displaymetrics.density + 0.5f);
//    }
    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }



}

