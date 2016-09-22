package com.example.slorber.moviefiend;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CardAdapter.OnCardClickListener, LoaderManager.LoaderCallbacks<List<Movie>> {

    private static final String URL = "http://api.themoviedb.org/3/movie/now_playing";
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private CardAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        getSupportLoaderManager().initLoader(0, null, this);

    }

    @Override
    public void onCardClick(Movie movie) {
        Intent intent = new Intent(this, MovieDetailActivity.class);
        Bundle b = new Bundle();
        b.putParcelable(MovieDetailActivity.EXTRA_MOVIE, movie);
        intent.putExtras(b);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    public Loader<List<Movie>> onCreateLoader(int id, Bundle args) {
        return new GetMoviesLoader(this, Uri.parse(URL)
                .buildUpon()
                .appendQueryParameter("api_key", getString(R.string.tmdb_api_key))
                .build().toString());
    }

    @Override
    public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> data) {
        if (data == null) {
            Toast.makeText(this, "Server error... :(", Toast.LENGTH_LONG).show();
            return;
        }
        //checkDeepLink(getIntent(),data);
        mAdapter = new CardAdapter(data,this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onLoaderReset(Loader<List<Movie>> loader) {

    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        //checkDeepLink(intent,mAdapter.getMovieList());
    }

//    private void checkDeepLink(Intent intent,List <Movie> data) {
//        if (intent.getData() != null && intent.getData().getLastPathSegment().contains("-")) {
//            String[] parts = intent.getData().getLastPathSegment().split("-");
//            int deepLinkMovieId = Integer.parseInt(parts[0]);
//            for (int i = 0; i < data.size(); i++) {
//                if (data.get(i).getId() == deepLinkMovieId) {
//                    Intent intent2 = new Intent(this, MovieDetailActivity.class);
//                    Bundle b = new Bundle();
//                    b.putParcelable(MovieDetailActivity.EXTRA_MOVIE, data.get(i));
//                    intent2.putExtras(b);
//                    startActivity(intent2);
//                    break;
//                }
//            }
//        }
//    }
}
