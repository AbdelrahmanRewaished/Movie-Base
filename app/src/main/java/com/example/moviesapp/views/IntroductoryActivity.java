package com.example.moviesapp.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.moviesapp.Movie;
import com.example.moviesapp.R;
import com.example.moviesapp.componentArchitecture.MovieViewModel;

import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class IntroductoryActivity extends AppCompatActivity {

    private ImageView imageView;
    private MovieViewModel movieViewModel;
    private Intent intent;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introductory);

        imageView = findViewById(R.id.first_image);
        imageView.animate().setDuration(1000).setStartDelay(3000).translationX(-1500).start();
        movieViewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        progressBar = findViewById(R.id.progressBar);

        intent = new Intent(this, MainActivity.class);
        loadData();
    }
    private void loadData() {

        movieViewModel.setMovieRepository();
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        executor.schedule(new Runnable() {
            @Override
            public void run() {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        MainActivity.setMovieViewModel(movieViewModel);
                        progressBar.setVisibility(View.GONE);
                        finish();
                        startActivity(intent);
                    }
                });
            }
        }, 3200, TimeUnit.MILLISECONDS);
        executor.shutdown();
    }

}