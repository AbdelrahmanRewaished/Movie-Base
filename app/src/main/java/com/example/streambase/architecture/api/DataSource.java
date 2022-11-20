package com.example.streambase.architecture.api;


import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.example.streambase.architecture.models.Stream;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


@SuppressWarnings("ALL")
public class DataSource extends PageKeyedDataSource<Integer, Stream> {

    public static final int PAGE_SIZE = 1;
    private static final int TOTAL_PAGES = 50;
    private static final int FIRST_PAGE = 1;

    private boolean successfulConnection;
    private char streamType;
    private String streamState;
    private String searchQuery;

    DataSource(char streamType, String streamState, String searchQuery) {
        super();
        this.streamType = streamType;
        this.streamState = streamState;
        this.searchQuery = searchQuery;
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> loadParams, @NonNull LoadCallback<Integer, Stream> loadCallback) {

        RetrofitClient.getClient().
                getAPIResponse(streamType, streamState, searchQuery, loadParams.key).
                enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call call, Response response) {
                        Integer key = loadParams.key < TOTAL_PAGES ? loadParams.key + 1: null;
                        loadStreams(response, loadCallback, streamType, key);
                        successfulConnection = true;
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
                getAPIResponse(streamType, streamState, searchQuery, loadParams.key).
                enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call call, Response response) {
                        Integer key = loadParams.key > 1 ? loadParams.key - 1: null;
                        loadStreams(response, loadCallback, streamType, key);
                        successfulConnection = true;
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
                getAPIResponse(streamType, streamState, searchQuery, FIRST_PAGE).
                enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call call, Response response) {
                        if(response.isSuccessful() && response.body() != null) {
                                loadInitialCallback.onResult(Arrays.asList(
                                                streamType == 'M'?
                                                        ((MovieJSONResponse)response.body()).getMovies():
                                                        ((SeriesJSONResponse)response.body()).getSeries())
                                        , null, FIRST_PAGE + 1);
                            successfulConnection = true;
                        }
                    }
                    @Override
                    public void onFailure(Call call, Throwable t) {
                        successfulConnection = false;
                        System.out.println("Network Failure");
                    }
                });


    }

    boolean isSuccessfulConnection() {
        return successfulConnection;
    }

    private void loadStreams(Response response,LoadCallback<Integer, Stream>  loadCallback, char streamType, Integer key) {
        if(response.isSuccessful() && response.body() != null) {
            loadCallback.onResult(Arrays.asList(
                            streamType == 'M' ?
                                    ((MovieJSONResponse) response.body()).getMovies() :
                                    ((SeriesJSONResponse) response.body()).getSeries())

                    , key);
        }
    }
}
