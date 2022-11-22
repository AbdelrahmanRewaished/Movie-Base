package com.example.streambase.architecture;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.streambase.architecture.models.Movie;
import com.example.streambase.architecture.models.Stream;
import com.example.streambase.architecture.models.TVSeries;
import com.example.streambase.views.DisplaySample;
import com.example.streambase.views.fragments.HomeFragment;
import com.example.streambase.views.recyclerview_adapters.SubAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ViewModel extends AndroidViewModel{

    private Repository repository;
    private final Application application;
    private final List<DisplaySample> displaySamples;
    private final List<SubAdapter> subAdapters;

    public ViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        setRepository();
        this.displaySamples = new ArrayList<>();
        this.subAdapters = new ArrayList<>();
        setHomeFragmentData();
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

    private void setHomeFragmentData() {
        displaySamples.add(new DisplaySample('M', "now_playing"));
        displaySamples.add(new DisplaySample('M', "popular"));
        displaySamples.add(new DisplaySample('M', "top_rated"));
        displaySamples.add(new DisplaySample('M', "upcoming"));
        displaySamples.add(new DisplaySample('T', "airing_today"));
        displaySamples.add(new DisplaySample('T', "popular"));
        displaySamples.add(new DisplaySample('T', "top_rated"));
        for (int i = 0; i < HomeFragment.STREAM_SAMPLE_SIZE; i++) {
            SubAdapter adapter = new SubAdapter(application.getApplicationContext(), this);
            subAdapters.add(adapter);
        }
    }
    public List<DisplaySample> getStreamSections() {
        return displaySamples;
    }
    public List<SubAdapter> getSubAdapters() {
        return subAdapters;
    }


}
