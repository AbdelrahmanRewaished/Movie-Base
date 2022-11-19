package com.example.streambase.architecture.models;

import androidx.room.Entity;
import androidx.room.ForeignKey;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

@Entity(tableName = "tv_series", foreignKeys = {@ForeignKey(entity = Stream.class,
        parentColumns = "id",
        childColumns = "id",
        onDelete = ForeignKey.CASCADE)})

public class TVSeries extends Stream {

    @SerializedName("first_air_date")
    private String series_release_date;

    private String name;

    public TVSeries(int id, String overview, float vote_average, String poster_path, String name, String series_release_date) {
        super(id, overview, vote_average, poster_path);
        this.name = name;
        this.series_release_date = series_release_date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TVSeries)) return false;
        if (!super.equals(o)) return false;
        TVSeries tvSeries = (TVSeries) o;
        return series_release_date.equals(tvSeries.series_release_date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), series_release_date, name);
    }

    public String getRelease_date() {
        return series_release_date;
    }

    public String getSeries_release_date() {
        return series_release_date;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return name;
    }
}
