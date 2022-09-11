package com.example.moviesapp.movieapi;

import com.example.moviesapp.Movie;
import com.google.gson.annotations.SerializedName;

public class MovieJSONResponse {

    @SerializedName("results")
    private Movie[] movies;

    public Movie[] getMovies() {
        return movies;
    }

}
