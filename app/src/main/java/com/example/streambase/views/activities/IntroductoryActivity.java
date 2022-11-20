package com.example.streambase.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.streambase.R;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class IntroductoryActivity extends AppCompatActivity {

    private ImageView imageView;
    private boolean nextActivityCreated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introductory);
        imageView = findViewById(R.id.first_image);
        nextActivityCreated = false;

        startMainActivity();
    }

    private void startMainActivity() {
        if(nextActivityCreated)
            return;
        Intent intent = new Intent(this, MainActivity.class);
        imageView.animate().setDuration(1000).setStartDelay(4000).translationX(-1500).start();
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        executor.schedule(() -> runOnUiThread(() -> {
            startActivity(intent);
            nextActivityCreated = true;
            finish();
        }),4000, TimeUnit.MILLISECONDS);
        executor.shutdown();
    }


}