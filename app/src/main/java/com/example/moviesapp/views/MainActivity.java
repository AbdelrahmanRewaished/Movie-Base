package com.example.moviesapp.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviesapp.Movie;
import com.example.moviesapp.R;
import com.example.moviesapp.componentArchitecture.MovieViewModel;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("FieldCanBeLocal")
public class MainActivity extends AppCompatActivity implements TextWatcher{

    private static List<Movie> movies;
    private Context context;
    private static MovieViewModel movieViewModel;
    private RecyclerView recyclerView;
    private ItemAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ImageButton imageButton;
    private EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        movies = movieViewModel.getAllMovies();

        imageButton = findViewById(R.id.imageButton2);
        search = findViewById(R.id.editText);
        recyclerView = findViewById(R.id.recyclerView);

        buildRecyclerView();

        imageButton.setOnClickListener(v -> search.setVisibility(View.VISIBLE));
        search.setVisibility(View.INVISIBLE);
        search.addTextChangedListener(this);
    }

    static void setMovieViewModel(MovieViewModel viewModel) {
        movieViewModel = viewModel;
    }
    private void buildRecyclerView() {

        layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ItemAdapter(movies, context);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        updateRecyclerView(s.toString());
    }


    @SuppressLint("NotifyDataSetChanged")
    private void updateRecyclerView(String s) {
        List<Movie> current_set_movies = getMoviesWithTitle(s);
        movies.clear();
        movies.addAll(current_set_movies);
        adapter.notifyDataSetChanged();
    }

    private List<Movie> getMoviesWithTitle(String title) {
        List<Movie> targeted_movies = new ArrayList<>();
        title = title.toLowerCase();
        for(Movie movie: movieViewModel.getAllMovies()) {
            String movie_title = movie.getTitle().toLowerCase();
            if(movie_title.startsWith(title)) {
                targeted_movies.add(movie);
            }
        }
        return targeted_movies;
    }
}
