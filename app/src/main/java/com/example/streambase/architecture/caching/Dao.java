package com.example.streambase.architecture.caching;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.streambase.architecture.models.Genre;
import com.example.streambase.architecture.models.Movie;
import com.example.streambase.architecture.models.Stream;
import com.example.streambase.architecture.models.TVSeries;

import java.util.List;

@androidx.room.Dao
public interface Dao {

    @Insert
    void insertStream(Stream stream);

    @Delete
    void deleteStream(Stream stream);

    @Insert
    void insertMovie(Movie movie);

    @Insert
    void insertTVSeries(TVSeries tvSeries);


    @Query("SELECT EXISTS (SELECT * FROM streams WHERE id = :id)")
    boolean containsStream(int id);

    @Query("SELECT * FROM movies ORDER BY vote_average DESC")
    List<Movie> getSavedMovies();

    @Query("SELECT * FROM tv_series ORDER BY vote_average DESC")
    List<TVSeries> getSavedSeries();

    @Insert
    void insertGenre(Genre genre);

    @Query("SELECT genre_id FROM streamGenres WHERE stream_id = :streamID")
    List<Integer> getGenres(int streamID);
}
