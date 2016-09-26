package com.example.slorber.moviefiend;

import android.content.Context;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.slorber.moviefiend.Loaders.GetMoviesLoader;
import com.example.slorber.moviefiend.Models.Movie;
import com.example.slorber.moviefiend.Views.MovieDetailsView;

import java.util.List;

import me.relex.circleindicator.CircleIndicator;

public class SimilarMoviesFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<Movie>> {

    public static final String EXTRA_ID = "id";
    private static final String URL = "http://api.themoviedb.org";
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private CircleIndicator mIndicator;
    private ViewFlipper mViewFlipper;
    private int mId;

    public SimilarMoviesFragment() {
    }

    public static SimilarMoviesFragment newInstance(int id) {
        SimilarMoviesFragment fragment = new SimilarMoviesFragment();
        Bundle args = new Bundle();
        args.putInt(EXTRA_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mId = getArguments().getInt(EXTRA_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.activity_similar_movies, container, false);
        mPager = (ViewPager) view.findViewById(R.id.view_pager);
        mIndicator = (CircleIndicator) view.findViewById(R.id.indicator);
        mViewFlipper = (ViewFlipper) view.findViewById(R.id.view_flipper);
        ((AppCompatActivity)getActivity()).setSupportActionBar((Toolbar) view.findViewById(R.id.toolbar));
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getActivity().getSupportLoaderManager().initLoader(mId, null, this);
        return view;
    }

    public void setId(int id){
        mId = id;
        getActivity().getSupportLoaderManager().initLoader(mId, null, this);
    }
    @Override
    public Loader<List<Movie>> onCreateLoader(int id, Bundle args) {
        return new GetMoviesLoader(getActivity(), Uri.parse(URL)
                .buildUpon()
                .appendPath("3")
                .appendPath("movie")
                .appendPath(String.valueOf(mId))
                .appendPath("similar")
                .appendQueryParameter("api_key", getString(R.string.tmdb_api_key))
                .build().toString());
    }

    @Override
    public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> data) {
        if (data == null) {
            Toast.makeText(getActivity(), "Server error... :(", Toast.LENGTH_LONG).show();
            return;
        } else if (data.size() == 0) {
            mViewFlipper.showNext();
            return;
        }
        mPagerAdapter = new ScreenSlidePagerAdapter(getActivity(), data);
        mPager.setAdapter(mPagerAdapter);
        mIndicator.setViewPager(mPager);
    }

    @Override
    public void onLoaderReset(Loader<List<Movie>> loader) {

    }

    private class ScreenSlidePagerAdapter extends PagerAdapter {

        private Context mContext;
        private List<Movie> mMovieList;

        public ScreenSlidePagerAdapter(Context context, List<Movie> movieList) {
            mContext = context;
            mMovieList = movieList;
        }

        public Object instantiateItem(ViewGroup collection, int position) {

            LayoutInflater inflater = LayoutInflater.from(mContext);
            MovieDetailsView movieDetailsView = (MovieDetailsView) inflater.inflate(R.layout.movie_details, collection, false);
            movieDetailsView.hideSimilarMovieLink();
            movieDetailsView.setMovie(mMovieList.get(position));
            collection.addView(movieDetailsView);
            return movieDetailsView;

        }

        @Override
        public void destroyItem(ViewGroup collection, int position, Object view) {
            collection.removeView((View) view);
        }

        @Override
        public int getCount() {
            return mMovieList.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

    }
}

