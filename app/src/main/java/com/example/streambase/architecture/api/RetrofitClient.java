package com.example.streambase.architecture.api;

import com.example.streambase.views.MainActivity;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@SuppressWarnings("ALL")
public class RetrofitClient {

    private static final String BASE_URL = "https://api.themoviedb.org/";
    private static String api_key;
    private static final String default_language = "en-US";

    private static RetrofitClient apiCall;
    private API api;
    private RetrofitClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(API.class);
        api_key = MainActivity.API_KEY;
    }

    public static synchronized RetrofitClient getClient() {
        if(apiCall == null) {
            apiCall = new RetrofitClient();
        }
        return apiCall;
    }

    public Call getAPIResponse(char type, String streamState, String search_query, int page) {
        if(streamState != null) {
            return type == 'M'?
                    api.getMovieAPIResponse(streamState, api_key, default_language, page):
                    api.getSeriesAPIResponse(streamState, api_key, default_language, page);
        }
        // searching state
        return type == 'M'?
                api.getMovieSearchResponse(api_key, default_language, search_query, page):
                api.getSeriesSearchResponse(api_key, default_language, search_query, page);
    }

}
