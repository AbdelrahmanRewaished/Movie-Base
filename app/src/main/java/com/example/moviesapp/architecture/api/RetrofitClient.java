package com.example.moviesapp.architecture.api;

import com.example.moviesapp.architecture.models.Stream;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String BASE_URL = "https://api.themoviedb.org/";
    public static final String api_key = "f439a4565d0903c6b9916f22446f3d38";
    public static final String default_language = "en-US";

    private static RetrofitClient apiCall;
    private API api;

    private RetrofitClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(API.class);
    }

    public static synchronized RetrofitClient getClient() {
        if(apiCall == null) {
            apiCall = new RetrofitClient();
        }
        return apiCall;
    }

    public API getApi() {
        return api;
    }

    public Call getAPIResponse(char type, String streamState, int page) {
        return type == 'M'?
                api.getMovieAPIResponse(streamState, api_key, default_language, page):
                api.getSeriesAPIResponse(streamState, api_key, default_language, page);
    }
}
