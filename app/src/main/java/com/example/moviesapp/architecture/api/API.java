package com.example.moviesapp.architecture.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface API {

    String movie_end_point = "3/movie/{watch_type}";
    String series_end_point = "3/tv/{watch_type}";
    String IMAGE_FIXED_URL = "https://image.tmdb.org/t/p/w500/";

    @GET(movie_end_point)
    Call<MovieJSONResponse> getMovieAPIResponse(
            @Path("watch_type") String streamState,
            @Query("api_key") String api_key,
            @Query("language") String language,
            @Query("page") int page
    );

    @GET(series_end_point)
    Call<SeriesJSONResponse> getSeriesAPIResponse(
            @Path("watch_type") String streamState,
            @Query("api_key") String api_key,
            @Query("language") String language,
            @Query("page") int page
    );


}
