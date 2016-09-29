package com.example.slorber.moviefiend;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.slorber.moviefiend.adapters.CardAdapter;
import com.example.slorber.moviefiend.loaders.GetMovieDetailLoader;
import com.example.slorber.moviefiend.models.Movie;

public class OneActivityToRuleThemAll extends AppCompatActivity implements CardAdapter.OnCardClickListener, MovieDetailFragment.OnSimilarMovieClickListener, LoaderManager.LoaderCallbacks<Movie> {

    public static final String EXTRA_ID = "id";
    private static final String URL = "http://api.themoviedb.org";
    private static final String MAIN_FRAGMENT_TAG = "mainFragment";
    private static final String DETAILS_FRAGMENT_TAG = "detailsFragment";
    private static final String SIMILAR_MOVIES_FRAGMENT_TAG = "similarFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_activity_to_rule_them_all);
        loadAiringTodayFragment();
        if (getIntent().getData() != null) {
            checkDeepLink(getIntent().getData());
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
        loadMovieDetailFragment(movie);
    }

    @Override
    public void onSimilarMovieClick(int id) {
        loadSimilarMoviesFragment(id);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        loadAiringTodayFragment();
        if (intent.getData() != null) {
            checkDeepLink(intent.getData());
        }
    }

    private void loadAiringTodayFragment() {
        AiringTodayFragment airingNowFragment = (AiringTodayFragment) getSupportFragmentManager().findFragmentByTag(MAIN_FRAGMENT_TAG);
        if (airingNowFragment == null) {
            AiringTodayFragment firstFragment = AiringTodayFragment.newInstance();
            firstFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.enter_from_bottom, R.anim.exit_to_bottom)
                    .replace(R.id.container, firstFragment, MAIN_FRAGMENT_TAG)
                    .commit();
        }
    }

    private void loadMovieDetailFragment(Movie movie) {
        MovieDetailFragment movieDetailFragment = (MovieDetailFragment) getSupportFragmentManager().findFragmentByTag(DETAILS_FRAGMENT_TAG);
        if (movieDetailFragment != null) {
            movieDetailFragment.setMovie(movie);
        } else {
            MovieDetailFragment newFragment = MovieDetailFragment.newInstance(movie);
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                    .replace(R.id.container, newFragment, DETAILS_FRAGMENT_TAG)
                    .addToBackStack(null)
                    .commit();
        }
    }

    private void loadSimilarMoviesFragment(int id) {
        SimilarMoviesFragment similarMoviesFragment = (SimilarMoviesFragment) getSupportFragmentManager().findFragmentByTag(SIMILAR_MOVIES_FRAGMENT_TAG);
        if (similarMoviesFragment != null) {
            similarMoviesFragment.setId(id);
        } else {
            SimilarMoviesFragment newFragment = SimilarMoviesFragment.newInstance(id);
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                    .replace(R.id.container, newFragment, SIMILAR_MOVIES_FRAGMENT_TAG)
                    .addToBackStack(null)
                    .commit();
        }
    }

    private void checkDeepLink(Uri deepLink) {
        if (deepLink.getLastPathSegment().contains("-") && TextUtils.isDigitsOnly(deepLink.getLastPathSegment().split("-")[0])) {
            String[] parts = deepLink.getLastPathSegment().split("-");
            String deepLinkMovieId = parts[0];
            getSupportLoaderManager().destroyLoader(Integer.parseInt(deepLinkMovieId));
            Bundle b = new Bundle();
            b.putString(EXTRA_ID, deepLinkMovieId);
            getSupportLoaderManager().initLoader(Integer.parseInt(deepLinkMovieId), b, this);
        } else {
            Toast.makeText(this, "Movie id not found", Toast.LENGTH_LONG).show();
            loadAiringTodayFragment();
        }
    }

    @Override
    public Loader<Movie> onCreateLoader(int id, Bundle args) {
        return new GetMovieDetailLoader(this, Uri.parse(URL)
                .buildUpon()
                .appendPath("3")
                .appendPath("movie")
                .appendPath(args.getString(EXTRA_ID))
                .appendQueryParameter("api_key", getString(R.string.tmdb_api_key))
                .build());
    }

    @Override
    public void onLoadFinished(Loader<Movie> loader, final Movie data) {
        if (data == null) {
            Toast.makeText(this, "Server error... :(", Toast.LENGTH_LONG).show();
            return;
        }
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                loadMovieDetailFragment(data);
            }
        });
    }

    @Override
    public void onLoaderReset(Loader<Movie> loader) {

    }
}
