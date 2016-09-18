package com.example.slorber.moviefiend;

import android.content.Context;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
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
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.List;
import java.util.Locale;

import me.relex.circleindicator.CircleIndicator;

public class SimilarMoviesActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Movie>>{

    public static final String EXTRA_ID = "id";
    private static final String URL = "http://api.themoviedb.org/3/movie/%d/similar";
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private CircleIndicator mIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_similar_movies);
        mPager = (ViewPager) findViewById(R.id.view_pager);
        mIndicator = (CircleIndicator) findViewById(R.id.indicator);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportLoaderManager().initLoader(1, null, this);

    }

    @Override
    public Loader<List<Movie>> onCreateLoader(int id, Bundle args) {
        return new GetMoviesLoader(this,String.format(Locale.getDefault(),URL, getIntent().getIntExtra(EXTRA_ID,0)));
    }

    @Override
    public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> data) {
        if(data == null){
            Toast.makeText(this,"Server error... :(",Toast.LENGTH_LONG).show();
            return;
        }
        else if(data.size()==0)
        {
            ((ViewFlipper)findViewById(R.id.view_flipper)).showNext();
            return;
        }
        mPagerAdapter = new ScreenSlidePagerAdapter(this, data);
        mPager.setAdapter(mPagerAdapter);
        mIndicator.setViewPager(mPager);
    }

    @Override
    public void onLoaderReset(Loader<List<Movie>> loader) {

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

