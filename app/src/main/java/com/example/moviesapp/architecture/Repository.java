package com.example.moviesapp.architecture;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.moviesapp.architecture.caching.Dao;
import com.example.moviesapp.architecture.caching.Database;
import com.example.moviesapp.architecture.models.Movie;
import com.example.moviesapp.architecture.models.Stream;
import com.example.moviesapp.architecture.models.TVSeries;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

class Repository {

    private Dao dao;
    private ExecutorService thread;

    Repository(final Context context, ExecutorService thread) {
        this.thread = thread;
        Database database = Database.getDatabase(context);
        dao = database.dao();
    }

    void insert(Stream stream) {
        thread.execute(() -> {
            if(stream instanceof Movie)
                dao.insertMovie((Movie) stream);
            else
                dao.insertSeries((TVSeries) stream);
        });
    }
    void delete(Stream stream) {
        thread.execute(() -> {
            if(stream instanceof Movie)
                dao.deleteMovie((Movie) stream);
            else
                dao.deleteSeries((TVSeries) stream);
        });
    }

    boolean contains(Stream stream) {
        Future<Boolean> future = thread.submit(() -> stream instanceof Movie? dao.containsMovie(stream.getId()):
                dao.containsSeries(stream.getId()));
        try {
            return future.get();
        }catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    LiveData<List<Movie>> getSavedMovies() {
        Future<LiveData<List<Movie>>> future = thread.submit(() -> dao.getSavedMovies());
        try {
            return future.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

}
