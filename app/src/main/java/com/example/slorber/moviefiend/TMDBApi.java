package com.example.slorber.moviefiend;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by slorber on 12/09/2016.
 */
public class TMDBApi {

    private static TMDBApi sInstance = new TMDBApi();
    private Gson mGson = new Gson();

    public static TMDBApi getHelper() {
        return sInstance;
    }

    private TMDBApi() {
    }

    public void getRequest(Context context, String url, final Listener listener) {
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        MovieContainer mc = mGson.fromJson(response.toString(), MovieContainer.class);
                        listener.onMoviesFetched(mc.getMovies());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());
                        listener.onMoviesFetched(null);
                    }
                }
        );
        queue.add(getRequest);

    }
    public void getMovieDetails(Context context, Uri uri, final SingleMovieListener listener) {
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, uri.toString(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Movie movie = mGson.fromJson(response.toString(), Movie.class);
                        listener.onMovieFetched(movie);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());
                        listener.onMovieFetched(null);
                    }
                }
        );
        queue.add(getRequest);

    }

    public interface Listener {
        void onMoviesFetched(List<Movie> response);
    }
    public interface SingleMovieListener {
        void onMovieFetched(Movie response);
    }
}
