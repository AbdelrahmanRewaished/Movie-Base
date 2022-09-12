package com.example.moviesapp.movieapi;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MovieAPI {

    String fixed_url = "https://api.themoviedb.org/";
    String end_point = "3/movie/now_playing?api_key=f439a4565d0903c6b9916f22446f3d38&language=en-US&page=1";

    @GET(end_point)
    Call<MovieJSONResponse> getAPIResponse();
}
