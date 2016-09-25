package com.example.slorber.moviefiend;

import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DeepLinkHandlerActivity extends AppCompatActivity {

    public static final String DEEPLINKEXTRA = "deeplink";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent backIntent = new Intent(this, MainActivity.class);
        backIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra(DEEPLINKEXTRA, getIntent().getData().toString());
        final PendingIntent pendingIntent = PendingIntent.getActivities(this, 1, new Intent[] { backIntent, intent }, PendingIntent.FLAG_ONE_SHOT);
        Intent intent2 = new Intent();
        try {
            pendingIntent.send(this, 0, intent2);
            finish();

        } catch (PendingIntent.CanceledException e) {
            System.out.println("Sending contentIntent failed: ");
        }
    }
}