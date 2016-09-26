package com.example.slorber.moviefiend;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.slorber.moviefiend.Adapters.CardAdapter;
import com.example.slorber.moviefiend.Models.Movie;

public class OneActivityToRuleThemAll extends AppCompatActivity implements CardAdapter.OnCardClickListener, MovieDetailFragment.OnSimilarMovieClickListener {

    private static final String MAIN_FRAGMENT_TAG = "mainFragment";
    private static final String DETAILS_FRAGMENT_TAG = "detailsFragment";
    private static final String SIMILAR_MOVIES_FRAGMENT_TAG = "similarFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_activity_to_rule_them_all);
        loadAiringTodayFragment();
        if(getIntent().getData() != null){
            loadMovieDetailFragment(null,getIntent().getData().toString());
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
        loadMovieDetailFragment(movie,null);
    }

    @Override
    public void onSimilarMovieClick(int id) {
        loadSimilarMoviesFragment(id);
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        loadAiringTodayFragment();
        if(intent.getData() != null){
            loadMovieDetailFragment(null,intent.getData().toString());
        }
    }

    private void loadAiringTodayFragment()
    {
        AiringTodayFragment airingNowFragment = (AiringTodayFragment) getSupportFragmentManager().findFragmentByTag(MAIN_FRAGMENT_TAG);
        if (airingNowFragment == null) {
            AiringTodayFragment firstFragment = AiringTodayFragment.newInstance();
            firstFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.enter_from_bottom, R.anim.exit_to_bottom)
                    .add(R.id.container, firstFragment,MAIN_FRAGMENT_TAG)
                    .commit();
        }
    }

    private void loadMovieDetailFragment(Movie movie,String deepLink)
    {
        if(deepLink == null) {
            MovieDetailFragment movieDetailFragment = (MovieDetailFragment) getSupportFragmentManager().findFragmentByTag(DETAILS_FRAGMENT_TAG);
            if (movieDetailFragment != null) {
                movieDetailFragment.setMovie(movie);
            } else {
                MovieDetailFragment newFragment = MovieDetailFragment.newInstance(movie, null);
                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,R.anim.enter_from_left,R.anim.exit_to_right)
                        .replace(R.id.container, newFragment, DETAILS_FRAGMENT_TAG)
                        .addToBackStack(null)
                        .commit();
            }
        }
        else {
            MovieDetailFragment movieDetailFragment = (MovieDetailFragment) getSupportFragmentManager().findFragmentByTag(DETAILS_FRAGMENT_TAG);
            if (movieDetailFragment != null) {
                movieDetailFragment.setDeepLink(deepLink);
            } else {
                MovieDetailFragment newFragment = MovieDetailFragment.newInstance(null, deepLink);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, newFragment, DETAILS_FRAGMENT_TAG)
                        .addToBackStack(null)
                        .commit();
            }
        }

    }

    private void loadSimilarMoviesFragment(int id)
    {
        SimilarMoviesFragment similarMoviesFragment = (SimilarMoviesFragment) getSupportFragmentManager().findFragmentByTag(SIMILAR_MOVIES_FRAGMENT_TAG);
        if (similarMoviesFragment != null) {
            similarMoviesFragment.setId(id);
        } else {
            SimilarMoviesFragment newFragment = SimilarMoviesFragment.newInstance(id);
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,R.anim.enter_from_left,R.anim.exit_to_right)
                    .replace(R.id.container, newFragment, SIMILAR_MOVIES_FRAGMENT_TAG)
                    .addToBackStack(null)
                    .commit();
        }
    }

}
