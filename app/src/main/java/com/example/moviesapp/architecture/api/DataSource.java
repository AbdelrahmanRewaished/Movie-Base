package com.example.moviesapp.architecture.api;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.example.moviesapp.architecture.models.Stream;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DataSource extends PageKeyedDataSource<Integer, Stream> {

    public static final int PAGE_SIZE = 3;
    private static final int TOTAL_PAGES = 50;
    private static final int FIRST_PAGE = 1;

    private boolean successfulConnection;
    private char streamType;
    private String streamState;

    public DataSource(char streamType, String streamState) {
        super();
        this.streamType = streamType;
        this.streamState = streamState;
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> loadParams, @NonNull LoadCallback<Integer, Stream> loadCallback) {

        RetrofitClient.getClient().
                getAPIResponse(streamType, streamState, loadParams.key).
                enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call call, Response response) {
                        if(response.isSuccessful()) {
                            Integer key = loadParams.key < TOTAL_PAGES ? loadParams.key + 1: null;
                            if (response.body() != null) {
                                loadCallback.onResult(Arrays.asList(
                                                streamType == 'M'?
                                                        ((MovieJSONResponse)response.body()).getMovies():
                                                        ((SeriesJSONResponse)response.body()).getSeries())
                                        , key);
                                successfulConnection = true;
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call call, Throwable t) {
                        successfulConnection = false;
                    }
                });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> loadParams, @NonNull LoadCallback<Integer, Stream> loadCallback) {

        RetrofitClient.getClient().
                getAPIResponse(streamType, streamState, loadParams.key).
                enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call call, Response response) {
                        if(response.isSuccessful()) {
                            Integer key = loadParams.key > 1 ? loadParams.key - 1: null;
                            if (response.body() != null) {
                                loadCallback.onResult(Arrays.asList(
                                                streamType == 'M'?
                                                        ((MovieJSONResponse)response.body()).getMovies():
                                                        ((SeriesJSONResponse)response.body()).getSeries())
                                        , key);
                                successfulConnection = true;
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        successfulConnection = false;
                    }
                });
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> loadInitialParams, @NonNull LoadInitialCallback<Integer, Stream> loadInitialCallback) {
        RetrofitClient.getClient().
                getAPIResponse(streamType, streamState,  FIRST_PAGE).
                enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call call, Response response) {
                        if(response.isSuccessful()) {
                            if (response.body() != null) {
                                loadInitialCallback.onResult(Arrays.asList(
                                                streamType == 'M'?
                                                        ((MovieJSONResponse)response.body()).getMovies():
                                                        ((SeriesJSONResponse)response.body()).getSeries())
                                        , null, FIRST_PAGE + 1);
                                successfulConnection = true;
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call call, Throwable t) {
                        successfulConnection = false;
                    }
                });


    }

    public boolean isSuccessfulConnection() {
        return successfulConnection;
    }
}
