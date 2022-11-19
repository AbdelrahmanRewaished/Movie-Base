package com.example.streambase.architecture.models;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

@Entity(tableName = "movies", foreignKeys = {@ForeignKey(entity = Stream.class,
        parentColumns = "id",
        childColumns = "id",
        onDelete = ForeignKey.CASCADE)})
public class Movie extends Stream {

    @SerializedName("release_date")
    private String movie_release_date;
    private String title;

    public Movie(int id, String overview, float vote_average, String poster_path, String title, String movie_release_date) {
        super(id, overview, vote_average, poster_path);
        this.title = title;
        this.movie_release_date = movie_release_date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Movie)) return false;
        if (!super.equals(o)) return false;
        Movie movie = (Movie) o;
        return Objects.equals(movie_release_date, movie.movie_release_date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), movie_release_date, title);
    }

    public String getRelease_date() {
        return movie_release_date;
    }

    public String getMovie_release_date() {
        return movie_release_date;
    }

    public String getTitle() {
        return title;
    }
}
