package com.example.slorber.moviefiend;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.NavUtils;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.slorber.moviefiend.Adapters.CardAdapter;
import com.example.slorber.moviefiend.Loaders.GetMoviesLoader;
import com.example.slorber.moviefiend.Models.Movie;

import java.util.List;

public class OneActivityToRuleThemAll extends AppCompatActivity implements CardAdapter.OnCardClickListener, MovieDetailFragment.OnSimilarMovieClickListener {

    private static final String MAIN_FRAGMENT_TAG = "mainFragment";
    private static final String DETAILS_FRAGMENT_TAG = "detailsFragment";
    private static final String SIMILAR_MOVIES_FRAGMENT_TAG = "similarFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_activity_to_rule_them_all);
        if(getIntent().getData() != null){
            AiringNowFragment firstFragment = AiringNowFragment.newInstance();
            MovieDetailFragment newFragment = MovieDetailFragment.newInstance(null, getIntent().getData().toString());
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, newFragment, DETAILS_FRAGMENT_TAG)
                    .addToBackStack(firstFragment.getClass().getName())
                    .commit();
        }
        else{
            AiringNowFragment airingNowFragment = (AiringNowFragment) getSupportFragmentManager().findFragmentByTag(MAIN_FRAGMENT_TAG);
            if (airingNowFragment == null) {
                AiringNowFragment firstFragment = AiringNowFragment.newInstance();
                firstFragment.setArguments(getIntent().getExtras());
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.container, firstFragment,MAIN_FRAGMENT_TAG)
                        .commit();
            }
        }

    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            moveTaskToBack(true);
        } else {
            getSupportFragmentManager().popBackStack();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            getSupportFragmentManager().popBackStack();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCardClick(Movie movie) {
        MovieDetailFragment movieDetailFragment = (MovieDetailFragment) getSupportFragmentManager().findFragmentByTag(DETAILS_FRAGMENT_TAG);
        if (movieDetailFragment != null) {
            movieDetailFragment.setMovie(movie);
        } else {
            MovieDetailFragment newFragment = MovieDetailFragment.newInstance(movie, null);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, newFragment, DETAILS_FRAGMENT_TAG)
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public void onSimilarMovieClick(int id) {
        SimilarMoviesFragment similarMoviesFragment = (SimilarMoviesFragment) getSupportFragmentManager().findFragmentByTag(SIMILAR_MOVIES_FRAGMENT_TAG);
        if (similarMoviesFragment != null) {
            similarMoviesFragment.setId(id);
        } else {
            SimilarMoviesFragment newFragment = SimilarMoviesFragment.newInstance(id);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, newFragment, SIMILAR_MOVIES_FRAGMENT_TAG)
                    .addToBackStack(null)
                    .commit();
        }
    }


}
