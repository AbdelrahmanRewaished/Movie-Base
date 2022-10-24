package com.example.moviesapp.architecture.models;

import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class Stream {

    @PrimaryKey
    private int id;

    private String overview;

    private float vote_average;

    private String poster_path;

    @Ignore
    @SerializedName("genre_ids")
    private int[] genres;

    public Stream(int id, String overview, float vote_average, String poster_path) {
        this.id = id;
        this.overview = overview;
        this.vote_average = vote_average;
        this.poster_path = poster_path;
    }

    public int getId() {
        return id;
    }

    public String getOverview() {
        return overview;
    }

    public float getVote_average() {
        return vote_average;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public int[] getGenres() { return genres; }

    public void setGenres(int[] genres) {
        this.genres = genres;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Stream)) return false;
        Stream stream = (Stream) o;
        return id == stream.id && Float.compare(stream.vote_average, vote_average) == 0 && overview.equals(stream.overview) && poster_path.equals(stream.poster_path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id,  overview, vote_average, poster_path);
    }


}
