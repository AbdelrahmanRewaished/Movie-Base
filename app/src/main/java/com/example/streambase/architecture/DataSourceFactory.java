package com.example.streambase.architecture;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.streambase.architecture.api.DataSource;

@SuppressWarnings("ALL")
public class DataSourceFactory extends androidx.paging.DataSource.Factory {

    private MutableLiveData<DataSource> liveDataSource = new MutableLiveData<>();

    private char streamType;
    private String streamState;
    private String searchQuery;
    private DataSource dataSource;

    public DataSourceFactory(char streamType, String streamState, String searchQuery) {
        super();
        this.streamType = streamType;
        this.streamState = streamState;
        this.searchQuery = searchQuery;
    }

    @NonNull
    @Override
    public androidx.paging.DataSource create() {
         dataSource = new DataSource(streamType, streamState, searchQuery);
         liveDataSource.postValue(dataSource);
         return dataSource;
    }


    public boolean isSuccessfulConnection() {
         return dataSource.isSuccessfulConnection();
    }
}
