package com.example.moviesapp.architecture.api;

import com.example.moviesapp.architecture.models.Movie;
import com.google.gson.annotations.SerializedName;

public class MovieJSONResponse {

    @SerializedName("results")
    private Movie[] movies;

    public Movie[] getMovies() {
        return movies;
    }

    public void setMovies(Movie[] movies) {
        this.movies = movies;
    }
}