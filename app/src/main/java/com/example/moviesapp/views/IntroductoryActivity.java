package com.example.moviesapp.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.moviesapp.R;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class IntroductoryActivity extends AppCompatActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introductory);
        imageView = findViewById(R.id.first_image);

        startMainActivity();
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        imageView.animate().setDuration(1000).setStartDelay(4000).translationX(-1500).start();
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        executor.schedule(() -> runOnUiThread(() -> {
            startActivity(intent);
            finish();
        }),4000, TimeUnit.MILLISECONDS);
        executor.shutdown();
    }


}