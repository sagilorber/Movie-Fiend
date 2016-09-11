package com.example.slorber.moviefiend;

import com.google.gson.annotations.SerializedName;

/**
 * Created by slorber on 07/09/2016.
 */
public class Movie {

    @SerializedName("title")
    private String mTitle;
    @SerializedName("backdrop_path")
    private String mBackdropPath;
    @SerializedName("poster_path")
    private String mPosterPath;
    @SerializedName("overview")
    private String mOverview;
    @SerializedName("vote_average")
    private float mVoteAverage;

    public String getTitle(){return mTitle;}
    public String getBackdropPath(){return mBackdropPath;}
    public String getPosterPath(){return mPosterPath;}
    public String getOverview(){return mOverview;}
    public float getVotes(){return mVoteAverage;}

}
