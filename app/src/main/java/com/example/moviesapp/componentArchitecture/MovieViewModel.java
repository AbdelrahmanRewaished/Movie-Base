package com.example.moviesapp.componentArchitecture;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.moviesapp.Movie;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MovieViewModel extends AndroidViewModel {

    private MovieRepository movieRepository;
    private Application application;

    public MovieViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
    }

    public List<Movie> getAllMovies() {
        return movieRepository.getAllMovies();
    }

    public void setMovieRepository() {
        ExecutorService thread = Executors.newSingleThreadExecutor();
        movieRepository = new MovieRepository(application, thread);
    }
}
