package com.example.moviesapp.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.moviesapp.Movie;

import java.util.List;

@Dao
public interface MovieDao {

    @Insert
    void insert(Movie movie);

    @Query("SELECT * FROM movies ORDER BY release_date DESC")
    List<Movie> getAllMovies();

    @Query("SELECT * FROM movies WHERE title = :title + '%' ORDER BY release_date")
    List<Movie> getMoviesWithTitle(String title);

}
