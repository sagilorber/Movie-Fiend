package com.example.slorber.moviefiend;

import android.content.Context;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.List;
import java.util.Locale;

public class SimilarMoviesActivity extends AppCompatActivity implements TMDBApi.Listener{

    private static final String URL = "http://api.themoviedb.org/3/movie/%d/similar";
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private TMDBApi.Listener mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_similar_movies);
        Uri builtUri = Uri.parse(String.format(Locale.getDefault(),URL, getIntent().getIntExtra(MovieDetailActivity.EXTRA_ID,0)))
                .buildUpon()
                .appendQueryParameter("api_key", getString(R.string.tmdb_api_key))
                .build();
        mListener = this;
        mPager = (ViewPager) findViewById(R.id.view_pager);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TMDBApi.getHelper().getRequest(this,builtUri.toString(),mListener);

    }

    @Override
    public void onServerResponse(List<Movie> response) {

        mPagerAdapter = new ScreenSlidePagerAdapter(this, response);
        mPager.setAdapter(mPagerAdapter);
    }

    private class ScreenSlidePagerAdapter extends PagerAdapter {

        private Context mContext;
        private List<Movie> mMovieList;
        public ScreenSlidePagerAdapter(Context context,List<Movie> movieList) {
            mContext = context;
            mMovieList = movieList;
        }

        public Object instantiateItem(ViewGroup collection, int position) {

            LayoutInflater inflater = LayoutInflater.from(mContext);
            MovieDetailsView movieDetailsView = (MovieDetailsView) inflater.inflate(R.layout.movie_details, collection, false);
            movieDetailsView.setItem(mMovieList.get(position));
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
            return arg0 == ((View) arg1);
        }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
}

