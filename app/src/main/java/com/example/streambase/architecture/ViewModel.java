package com.example.streambase.architecture;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.streambase.architecture.api.DataSourceFactory;
import com.example.streambase.architecture.models.Movie;
import com.example.streambase.architecture.models.Stream;
import com.example.streambase.architecture.models.TVSeries;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SuppressWarnings("ALL")
public class ViewModel extends AndroidViewModel{

    private Repository repository;
    private Application application;
    private DataSourceFactory dataSourceFactory;

    public ViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        setRepository();
    }

    private void setRepository() {
        ExecutorService thread = Executors.newSingleThreadExecutor();
        repository = new Repository(application.getApplicationContext(), thread);
    }

    public void insert(Stream stream) {
        repository.insert(stream);
    }

    public List<Movie> getSavedMovies() {
        return repository.getSavedMovies();
    }
    public void delete(Stream stream) {repository.delete(stream);}

    public boolean contains(Stream stream) {return repository.contains(stream);}

    public List<TVSeries> getSavedSeries() {return repository.getSavedSeries();}

    public List<Integer> getGenres(int stream_id) {return repository.getGenres(stream_id);}

    public LiveData getHomePagedList(char streamType, String streamState) {
        return repository.getHomePagedList(streamType, streamState);
    }

    public LiveData getSearchPagedList(char streamType, String searchQuery) {

        return repository.getSearchPagedList(streamType, searchQuery);
    }

    public boolean isSuccessfulConnection() {
        return repository.isSuccessfulConnection();
    }


}
