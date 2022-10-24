package com.example.moviesapp.architecture.models;

import androidx.room.Entity;
import androidx.room.Ignore;

import java.util.Objects;

@Entity(tableName = "movies")
public class Movie extends Stream {

    private String release_date;
    private String title;

    public Movie(int id, String overview, float vote_average, String poster_path, String title, String release_date) {
        super(id, overview, vote_average, poster_path);
        this.title = title;
        this.release_date = release_date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Movie)) return false;
        if (!super.equals(o)) return false;
        Movie movie = (Movie) o;
        return Objects.equals(release_date, movie.release_date);
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
