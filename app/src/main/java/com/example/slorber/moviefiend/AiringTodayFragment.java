package com.example.slorber.moviefiend;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.slorber.moviefiend.adapters.CardAdapter;
import com.example.slorber.moviefiend.loaders.GetMoviesLoader;
import com.example.slorber.moviefiend.models.Movie;

import java.util.List;

public class AiringTodayFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<Movie>> {

    private static final String URL = "http://api.themoviedb.org/3/movie/now_playing";
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private CardAdapter mAdapter;

    public AiringTodayFragment() {
    }

    public static AiringTodayFragment newInstance() {
        return new AiringTodayFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        this.getLoaderManager().initLoader(0, null, this);
        return view;
    }

    public void setMovies(List<Movie> movies) {
        mAdapter = new CardAdapter(movies, getContext());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public Loader<List<Movie>> onCreateLoader(int id, Bundle args) {
        return new GetMoviesLoader(getActivity(), Uri.parse(URL)
                .buildUpon()
                .appendQueryParameter("api_key", getString(R.string.tmdb_api_key))
                .build());
    }

    @Override
    public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> data) {
        if (data == null) {
            Toast.makeText(getActivity(), "Server error... :(", Toast.LENGTH_LONG).show();
            return;
        }
        setMovies(data);
    }

    @Override
    public void onLoaderReset(Loader<List<Movie>> loader) {

    }
}
