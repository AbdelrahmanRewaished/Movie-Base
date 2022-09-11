package com.example.moviesapp.views;

import static android.os.Build.VERSION_CODES.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moviesapp.Movie;
import com.example.moviesapp.R.*;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private List<Movie> movies;
    private Context context;
    public ItemAdapter(List<Movie> movies, Context context) {
        this.movies = movies;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(layout.movie_button_sample, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Movie current_movie = movies.get(position);

        Glide.with(context)
                .load("https://image.tmdb.org/t/p/w500/" + current_movie.getPoster_path())
                .into(holder.imageButton);
        holder.textView.setText(current_movie.getTitle());
        holder.ratingBar.setRating(current_movie.getVote_average());
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageButton imageButton;
        public TextView textView;
        public RatingBar ratingBar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageButton = itemView.findViewById(id.imageButton_button_sample);
            textView = itemView.findViewById(id.textView_button_sample);
            ratingBar = itemView.findViewById(id.ratingBar);
        }
    }
}
