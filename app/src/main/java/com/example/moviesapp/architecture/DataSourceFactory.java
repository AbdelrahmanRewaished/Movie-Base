package com.example.moviesapp.architecture;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.moviesapp.architecture.api.DataSource;

public class DataSourceFactory extends androidx.paging.DataSource.Factory {

    private MutableLiveData<DataSource> movieLiveDataSource = new MutableLiveData<>();

    private char streamType;
    private String streamState;
    private DataSource dataSource;

    public DataSourceFactory(char streamType, String streamState) {
        super();
        this.streamType = streamType;
        this.streamState = streamState;
    }


    @NonNull
    @Override
    public androidx.paging.DataSource create() {
        dataSource = new DataSource(streamType, streamState);
        movieLiveDataSource.postValue(dataSource);
        return dataSource;
    }

    public MutableLiveData<DataSource> getMovieLiveDataSource() {
        return movieLiveDataSource;
    }

     public boolean isSuccessfulConnection() {
        return dataSource.isSuccessfulConnection();
     }
}
