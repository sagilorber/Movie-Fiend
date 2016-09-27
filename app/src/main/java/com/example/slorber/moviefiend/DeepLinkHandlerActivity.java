package com.example.slorber.moviefiend;

import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

public class DeepLinkHandlerActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Movie> {

    public static final String DEEPLINKEXTRA = "deeplink";
    public static final String EXTRA_ID = "id";
    private static final String URL = "http://api.themoviedb.org";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkDeepLink(getIntent().getData().toString());
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
    public void onLoadFinished(Loader<Movie> loader, Movie data) {
        if (data == null) {
            Toast.makeText(this, "Server error... :(", Toast.LENGTH_LONG).show();
            return;
        }
        Intent backIntent = new Intent(this, MainActivity.class);
        Intent intent = new Intent(this, MovieDetailActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(backIntent);
        stackBuilder.addNextIntent(intent);
        Bundle b = new Bundle();
        b.putParcelable(MovieDetailActivity.EXTRA_MOVIE, data);
        intent.putExtras(b);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        final PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_ONE_SHOT);
        try {
            pendingIntent.send(this, 0, new Intent());
            overridePendingTransition(0,0);
            finish();
        } catch (PendingIntent.CanceledException e) {
            System.out.println("Sending contentIntent failed: ");
        }
    }

    @Override
    public void onLoaderReset(Loader<Movie> loader) {

    }

    private void checkDeepLink(String deepLink) {
            Uri extraUri = Uri.parse(deepLink);
            if (extraUri.getLastPathSegment().contains("-") && TextUtils.isDigitsOnly(extraUri.getLastPathSegment().split("-")[0])) {
                String[] parts = extraUri.getLastPathSegment().split("-");
                String deepLinkMovieId = parts[0];
                Bundle b = new Bundle();
                b.putString(EXTRA_ID, deepLinkMovieId);
                getSupportLoaderManager().initLoader(0, b, this);
            }
            else{
               startActivity(new Intent(this,MainActivity.class));
            }
    }
}
