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

import android.content.Context;
import android.net.Uri;
import android.support.v4.content.Loader;

import java.util.List;

/**
 * TODO: Write Javadoc for GetMovieDetailLoader.
 *
 * @author slorber
 */
public class GetMovieDetailLoader extends Loader<Movie> implements TMDBApi.SingleMovieListener {

    private Movie mMovie;
    private Uri mUri;

    public GetMovieDetailLoader(Context context, Uri uri) {
        super(context);
        mUri = uri;
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
        TMDBApi.getHelper().getMovieDetails(getContext(), mUri, this);
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
