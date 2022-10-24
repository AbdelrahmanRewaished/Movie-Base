package com.example.moviesapp.views;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.moviesapp.architecture.api.API;
import com.example.moviesapp.architecture.models.Genre;
import com.example.moviesapp.architecture.models.Movie;
import com.example.moviesapp.architecture.models.Stream;
import com.example.moviesapp.R;
import com.example.moviesapp.architecture.ViewModel;
import com.example.moviesapp.architecture.models.TVSeries;

@SuppressWarnings("FieldCanBeLocal")
public class Overview extends AppCompatActivity {

    private static ViewModel viewModel;
    private ImageView view_imageView;
    private TextView view_title;
    private RatingBar view_ratingBar;
    private TextView view_overview;
    private ImageButton save;
    private TextView genres;

    private static Stream stream;

    static void setMovie(Stream current_stream) {
        stream = current_stream;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_overview);
        view_imageView = findViewById(R.id.imageView);
        view_title = findViewById(R.id.title);
        view_ratingBar = findViewById(R.id.ratingBar2);
        view_overview = findViewById(R.id.overview);
        save = findViewById(R.id.saveButton_overview);
        save.setOnClickListener(v -> {
            if(! viewModel.contains(stream)) {
                save.setImageResource(R.drawable.saved_button);
                viewModel.insert(stream);
            }
            else {
                save.setImageResource(R.drawable.unsaved_button);
                viewModel.delete(stream);
            }
        });
        genres = findViewById(R.id.genres);
        setUI();
    }
    static void setMovieViewModel(ViewModel viewModel) {
        Overview.viewModel = viewModel;
    }

    private void setUI() {
        Glide.with(getApplicationContext())
                .load(API.IMAGE_FIXED_URL + stream.getPoster_path())
                .into(view_imageView);

        if(stream instanceof Movie)
            view_title.setText(((Movie)stream).getTitle());
        else
            view_title.setText(((TVSeries)stream).getTitle());

        view_overview.setText(stream.getOverview());
        view_ratingBar.setRating((float)(stream.getVote_average() / 2.0));
        fillGenres();
    }

    private void fillGenres() {
        StringBuilder sb = new StringBuilder();
        for(int genre_id: stream.getGenres()) {
            sb.append(Genre.getGenre(genre_id) + "    ");
        }
        genres.setText(sb.toString());
    }

}