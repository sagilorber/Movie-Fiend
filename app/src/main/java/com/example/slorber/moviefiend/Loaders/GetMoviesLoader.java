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

package com.example.slorber.moviefiend.loaders;

import android.content.Context;
import android.net.Uri;
import android.support.v4.content.Loader;

import com.example.slorber.moviefiend.models.Movie;
import com.example.slorber.moviefiend.api.TMDBApi;

import java.util.List;

/**
 * This Loader handles network calls.
 * it gets a url and issue a request to TMDBApi and handles its response.
 *
 * @author slorber
 */
public class GetMoviesLoader extends Loader<List<Movie>> implements TMDBApi.Listener {

    private List<Movie> mMovieList;
    private Uri mUrl;

    public GetMoviesLoader(Context context, Uri url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        if (mMovieList != null) {
            deliverResult(mMovieList);
        } else {
            forceLoad();
        }
    }

    @Override
    protected void onForceLoad() {
        super.onForceLoad();
        TMDBApi.getHelper().getRequest(getContext(), mUrl, this);
    }

    @Override
    public void deliverResult(List<Movie> list) {
        mMovieList = list;
        super.deliverResult(list);
    }

    @Override
    public void onMoviesFetched(List<Movie> response) {
        deliverResult(response);
    }
}
