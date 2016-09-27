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

package com.example.slorber.moviefiend.Loaders;

import android.content.Context;
import android.net.Uri;
import android.support.v4.content.Loader;

import com.example.slorber.moviefiend.Models.Movie;
import com.example.slorber.moviefiend.Api.TMDBApi;

/**
 * TODO: Write Javadoc for GetMovieDetailLoader.
 *
 * @author slorber
 */
public class GetMovieDetailLoader extends Loader<Movie> implements TMDBApi.SingleMovieListener {

    private Movie mMovie;
    private Uri mUrl;

    public GetMovieDetailLoader(Context context, Uri url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        if (mMovie != null) {
            deliverResult(mMovie);
        } else {
            forceLoad();
        }
    }

    @Override
    protected void onForceLoad() {
        super.onForceLoad();
        TMDBApi.getHelper().getMovieDetails(getContext(), mUrl, this);
    }

    @Override
    public void deliverResult(Movie movie) {
        mMovie = movie;
        super.deliverResult(movie);
    }

    @Override
    public void onMovieFetched(Movie response) {
        deliverResult(response);
    }
}
