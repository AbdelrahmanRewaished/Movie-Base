package com.example.streambase.architecture.api;

import com.example.streambase.architecture.models.Movie;
import com.example.streambase.architecture.models.TVSeries;
import com.google.gson.annotations.SerializedName;

class MovieJSONResponse {

    @SerializedName("results")
    private Movie[] movies;

    public Movie[] getMovies() {
        return movies;
    }

    public void setMovies(Movie[] movies) {
        this.movies = movies;
    }
}

class SeriesJSONResponse {

    @SerializedName("results")
    private TVSeries[] series;

    public TVSeries[] getSeries() {
        return series;
    }

    public void setSeries(TVSeries[] series) {
        this.series = series;
    }
}
