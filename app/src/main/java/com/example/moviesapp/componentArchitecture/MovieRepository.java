package com.example.moviesapp.componentArchitecture;

import android.content.Context;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.moviesapp.Movie;
import com.example.moviesapp.database.MovieDao;
import com.example.moviesapp.database.MovieDatabase;
import com.example.moviesapp.movieapi.MovieAPI;
import com.example.moviesapp.movieapi.MovieJSONResponse;
import com.example.moviesapp.views.MainActivity;

import java.io.File;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieRepository {

    private MovieDao movieDao;
    private ExecutorService thread;

    public MovieRepository(final Context context, ExecutorService thread) {
        this.thread = thread;
        MovieDatabase database;
        if(MovieDatabase.DatabaseExists(context, "movies_database")) {
            database = MovieDatabase.getMovieDatabase(context);
            movieDao = database.movieDao();
            return;
        }
        database = MovieDatabase.getMovieDatabase(context);
        movieDao = database.movieDao();
        callMovieAPI();
    }

    public void insert(Movie movie) {
        thread.execute(new Runnable() {
            @Override
            public void run() {
                movieDao.insert(movie);
            }
        });
    }

    public List<Movie> getAllMovies() {
        Future<List<Movie>> future = thread.submit(new Callable<List<Movie>>() {
            @Override
            public List<Movie> call() throws Exception {
                return movieDao.getAllMovies();
            }
        });
        try {
            return future.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Movie> getMoviesWithTitle(String title) {
        return movieDao.getMoviesWithTitle(title);
    }

    private void callMovieAPI() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MovieAPI.fixed_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MovieAPI api = retrofit.create(MovieAPI.class);
        Call<MovieJSONResponse> call = api.getAPIResponse();
        call.enqueue(new Callback<MovieJSONResponse>() {
            @Override
            public void onResponse(Call<MovieJSONResponse> call, Response<MovieJSONResponse> response) {
                if(response.isSuccessful()) {
                    Movie[] movies = response.body().getMovies();
                    insertAllMoviesToDatabase(movies);
                }

            }

            @Override
            public void onFailure(Call<MovieJSONResponse> call, Throwable t) {

            }
        });
    }


    private void insertAllMoviesToDatabase(Movie[] movies) {
        for(Movie movie: movies) {
            insert(movie);
        }
    }


}
