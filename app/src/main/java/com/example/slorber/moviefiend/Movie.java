package com.example.slorber.moviefiend;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * Created by slorber on 07/09/2016.
 */
public class Movie implements Parcelable {

    private String title;
    private String backdrop_path;
    private String poster_path;
    private String overview;
    private float vote_average;

    protected Movie(Parcel in) {
        title = in.readString();
        backdrop_path = in.readString();
        poster_path = in.readString();
        overview = in.readString();
        vote_average = in.readFloat();
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

    public String getTitle(){return title;}
    public String getBackdropPath(){return backdrop_path;}
    public String getPosterPath(){return poster_path;}
    public String getOverview(){return overview;}
    public float getVotes(){return vote_average;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(backdrop_path);
        dest.writeString(poster_path);
        dest.writeString(overview);
        dest.writeFloat(vote_average);
    }
}
