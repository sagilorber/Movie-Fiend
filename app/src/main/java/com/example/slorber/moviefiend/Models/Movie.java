package com.example.slorber.moviefiend.Models;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


/**
 * Created by slorber on 07/09/2016.
 */
public class Movie implements Parcelable {

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
    @SerializedName("id")
    private int mId;

    protected Movie(Parcel in) {
        mTitle = in.readString();
        mBackdropPath = in.readString();
        mPosterPath = in.readString();
        mOverview = in.readString();
        mVoteAverage = in.readFloat();
        mId = in.readInt();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };


    public String getTitle() {
        return mTitle;
    }

    public String getBackdropPath() {
        return mBackdropPath;
    }

    public String getPosterPath() {
        return mPosterPath;
    }

    public String getOverview() {
        return mOverview;
    }

    public float getVotes() {
        return mVoteAverage;
    }

    public int getId() {
        return mId;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTitle);
        dest.writeString(mBackdropPath);
        dest.writeString(mPosterPath);
        dest.writeString(mOverview);
        dest.writeFloat(mVoteAverage);
        dest.writeInt(mId);
    }
}
