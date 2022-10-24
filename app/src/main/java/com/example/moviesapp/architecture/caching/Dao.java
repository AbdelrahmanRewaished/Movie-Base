package com.example.moviesapp.architecture.caching;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.moviesapp.architecture.models.Movie;
import com.example.moviesapp.architecture.models.Stream;
import com.example.moviesapp.architecture.models.TVSeries;

import java.util.List;

@androidx.room.Dao
public interface Dao {

    @Insert
    void insertMovie(Movie movie);

    @Delete
    void deleteMovie(Movie movie);

    @Insert
    void insertSeries(TVSeries tvSeries);

    @Delete
    void deleteSeries(TVSeries tvSeries);

    @Query("SELECT EXISTS (SELECT * FROM movies WHERE id = :id)")
    boolean containsMovie(int id);

    @Query("SELECT EXISTS (SELECT * FROM tv_series WHERE id = :id)")
    boolean containsSeries(int id);

    @Query("SELECT * FROM movies ORDER BY vote_average DESC")
    LiveData<List<Movie>> getSavedMovies();

    @Query("SELECT * FROM tv_series ORDER BY vote_average DESC")
    LiveData<List<TVSeries>> getSavedSeries();

}
