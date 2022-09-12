package com.example.moviesapp.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.moviesapp.Movie;
import com.example.moviesapp.R;

@SuppressWarnings("FieldCanBeLocal")
public class MovieOverview extends AppCompatActivity {

    private ImageView view_imageView;
    private TextView view_title;
    private RatingBar view_ratingBar;
    private TextView view_overview;

    private static String title;
    private static String overview;
    private static String poster_path;
    private static float rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_overview);
        view_imageView = findViewById(R.id.imageView);
        view_title = findViewById(R.id.title);
        view_ratingBar = findViewById(R.id.ratingBar2);
        view_overview = findViewById(R.id.overview);
        setUI();
    }
    static void setFields(Movie movie) {
        title = movie.getTitle() + "\n(" + movie.getRelease_date().split("-")[0] + ")";
        overview = movie.getOverview();
        poster_path = "https://image.tmdb.org/t/p/w500/" + movie.getPoster_path();
        rating = movie.getVote_average() / 2;
    }

    private void setUI() {
        Glide.with(getApplicationContext())
                .load(poster_path)
                .into(view_imageView);
        view_title.setText(title);
        view_overview.setText(overview);
        view_ratingBar.setRating(rating);
    }

}