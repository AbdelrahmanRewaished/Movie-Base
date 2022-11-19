package com.example.streambase.architecture;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.streambase.architecture.caching.Dao;
import com.example.streambase.architecture.caching.Database;
import com.example.streambase.architecture.models.Genre;
import com.example.streambase.architecture.models.Movie;
import com.example.streambase.architecture.models.Stream;
import com.example.streambase.architecture.models.TVSeries;

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
           dao.insertStream(stream);
           if(stream instanceof Movie)
               dao.insertMovie((Movie)stream);
           else
               dao.insertTVSeries((TVSeries) stream);

            for(int genre_id: stream.getGenres()) {
                dao.insertGenre(new Genre(genre_id, stream.getId()));
            }
        });

    }
    void delete(Stream stream) {
        thread.execute(() -> dao.deleteStream(stream));
    }

    boolean contains(Stream stream) {
        Future<Boolean> future = thread.submit(() ->  dao.containsStream(stream.getId()));
        try {
            return future.get();
        }catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    List<Integer> getGenres(int stream_id) {
        Future<List<Integer>> future = thread.submit(() -> dao.getGenres(stream_id));
        try {
            return future.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
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

    LiveData<List<TVSeries>> getSavedSeries() {
        Future<LiveData<List<TVSeries>>> future = thread.submit(() -> dao.getSavedSeries());
        try {
            return future.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
