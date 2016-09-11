package com.example.slorber.moviefiend;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements CardAdapter.OnCardClickListener{
    MovieContainer mc;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private CardAdapter mAdapter;

    private static String URL = "http://api.themoviedb.org/3/movie/now_playing";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        RequestQueue queue = Volley.newRequestQueue(this);
        Uri builtUri = Uri.parse(URL)
                .buildUpon()
                .appendQueryParameter("api_key", "f8546d2d948cd245e6cdb9d4332e6ca6")
                .build();
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, builtUri.toString(), null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson g = new Gson();
                        mc = g.fromJson(response.toString(), MovieContainer.class);
                        mLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
                        mAdapter = new CardAdapter(mc.getMovies(),MainActivity.this );
                        mRecyclerView.setLayoutManager(mLayoutManager);
                        mRecyclerView.setAdapter(mAdapter);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());
                    }
                }
        );
        queue.add(getRequest);

    }


    @Override
    public void OnCardClick(Movie movie) {
        Intent i = new Intent(MainActivity.this,MovieDetailActivity.class);
        Bundle b = new Bundle();
        b.putParcelable("Movie", movie);
        i.putExtras(b);
        startActivity(i);
    }
}
