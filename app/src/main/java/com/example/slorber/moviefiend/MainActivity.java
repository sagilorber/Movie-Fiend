package com.example.slorber.moviefiend;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements CardAdapter.OnCardClickListener,OnServerResponseListener{
    MovieContainer mc;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private CardAdapter mAdapter;
    private OnServerResponseListener mListener;
    private static String URL = "http://api.themoviedb.org/3/movie/now_playing";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        Uri builtUri = Uri.parse(URL)
                .buildUpon()
                .appendQueryParameter("api_key", getString(R.string.tmdb_api_key))
                .build();
        mListener = this;
        TMDBApi.getHelper().getRequest(this,builtUri.toString(),mListener);
    }


    @Override
    public void OnCardClick(Movie movie) {
        Intent i = new Intent(MainActivity.this,MovieDetailActivity.class);
        Bundle b = new Bundle();
        b.putParcelable("Movie", movie);
        i.putExtras(b);
        startActivity(i);
    }


    @Override
    public void OnServerResponse(JSONObject response) {
        if(response == null)
            Toast.makeText(this,"Server error... :(",Toast.LENGTH_LONG).show();
        else
        {
            Gson g = new Gson();
            mc = g.fromJson(response.toString(), MovieContainer.class);
            mLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
            mAdapter = new CardAdapter(mc.getMovies(),MainActivity.this );
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setAdapter(mAdapter);
        }

    }
}
