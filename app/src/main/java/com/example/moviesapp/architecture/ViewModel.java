package com.example.moviesapp.architecture;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.moviesapp.architecture.api.DataSource;
import com.example.moviesapp.architecture.models.Movie;
import com.example.moviesapp.architecture.models.Stream;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ViewModel extends AndroidViewModel {

    private Repository repository;
    private Application application;
    private List<LiveData> pagedLists;
    private DataSourceFactory dataSourceFactory;

    public ViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        setRepository();
        pagedLists = new ArrayList<>();
    }

     private void setRepository() {
        ExecutorService thread = Executors.newSingleThreadExecutor();
        repository = new Repository(application.getApplicationContext(), thread);
    }
    public void insert(Stream stream) {
        repository.insert(stream);
    }
    public LiveData<List<Movie>> getSavedMovies() {
        return repository.getSavedMovies();
    }
    public void delete(Stream stream) {repository.delete(stream);}
    public boolean contains(Stream stream) {return repository.contains(stream);}

    public LiveData addPagedList(char streamType, String streamState) {

        dataSourceFactory = new DataSourceFactory(streamType, streamState);
        PagedList.Config config = (new PagedList.Config.Builder()).setEnablePlaceholders(false)
                .setPageSize(DataSource.PAGE_SIZE)
                .build();

        LiveData pagedList = (new LivePagedListBuilder<>(dataSourceFactory, config)).build();

        pagedLists.add(pagedList);
        return pagedList;
    }

    public List<LiveData> getPagedLists() {
        return pagedLists;
    }

    public boolean isSuccessfulConnection() {
        return dataSourceFactory.isSuccessfulConnection();
    }
}
