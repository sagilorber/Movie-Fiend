package com.example.slorber.moviefiend;

import android.content.Context;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.List;

public class SimilarMoviesActivity extends AppCompatActivity implements OnServerResponseListener{

    ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private OnServerResponseListener mListener;
    private static String URL = "http://api.themoviedb.org/3/movie/%d/similar";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_similar_movies);
        Uri builtUri = Uri.parse(String.format(URL, getIntent().getIntExtra("id",0)))
                .buildUpon()
                .appendQueryParameter("api_key", getString(R.string.tmdb_api_key))
                .build();
        mListener = this;
        mPager = (ViewPager) findViewById(R.id.view_pager);
        TMDBApi.getHelper().getRequest(this,builtUri.toString(),mListener);

    }

    @Override
    public void OnServerResponse(JSONObject response) {

        Gson g = new Gson();
        MovieContainer mc = g.fromJson(response.toString(), MovieContainer.class);
        mPagerAdapter = new ScreenSlidePagerAdapter(this, mc.getMovies());
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

}

