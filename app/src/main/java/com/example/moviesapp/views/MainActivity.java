package com.example.moviesapp.views;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviesapp.Movie;
import com.example.moviesapp.R;
import com.example.moviesapp.componentArchitecture.MovieViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static List<Movie> movies;
    private static Context context;
    private static MovieViewModel movieViewModel;
    private RecyclerView recyclerView;
    private ItemAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ItemAdapter(movies, context);
        recyclerView.setAdapter(adapter);
    }

    public static void setMovieViewModel(MovieViewModel viewModel) {
        movieViewModel = viewModel;
        movies = movieViewModel.getAllMovies();
    }

    private static List<ImageButton> getMoviesButtons() {
        List<ImageButton> buttons = new ArrayList<>();
        for(Movie movie: movies) {

        }
        return buttons;
    }

}
