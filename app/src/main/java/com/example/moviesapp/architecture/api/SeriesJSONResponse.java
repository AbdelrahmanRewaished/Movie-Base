package com.example.moviesapp.architecture.api;

import com.example.moviesapp.architecture.models.Movie;
import com.example.moviesapp.architecture.models.TVSeries;
import com.google.gson.annotations.SerializedName;

public class SeriesJSONResponse {

    @SerializedName("results")
    private TVSeries[] series;

    public TVSeries[] getSeries() {
        return series;
    }

    public void setSeries(TVSeries[] series) {
        this.series = series;
    }
}