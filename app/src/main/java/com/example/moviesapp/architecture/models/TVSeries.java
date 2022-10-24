package com.example.moviesapp.architecture.models;

import androidx.room.Entity;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

@Entity(tableName = "tv_series")
public class TVSeries extends Stream {

    @SerializedName("first_air_date")
    private String release_date;

    @SerializedName("name")
    private String title;

    public TVSeries(int id, String overview, float vote_average, String poster_path, String title, String release_date) {
        super(id, overview, vote_average, poster_path);
        this.title = title;
        this.release_date = release_date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TVSeries)) return false;
        if (!super.equals(o)) return false;
        TVSeries tvSeries = (TVSeries) o;
        return Objects.equals(release_date, tvSeries.release_date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), release_date);
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getTitle() {
        return title;
    }
}
