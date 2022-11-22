package com.example.streambase.views.recyclerview_adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.streambase.R;
import com.example.streambase.architecture.ViewModel;
import com.example.streambase.architecture.api.API;
import com.example.streambase.architecture.models.Movie;
import com.example.streambase.architecture.models.Stream;
import com.example.streambase.architecture.models.TVSeries;
import com.example.streambase.views.activities.MainActivity;
import com.example.streambase.views.activities.Overview;

import java.util.List;


public class WatchlistAdapter extends RecyclerView.Adapter<SubAdapter.SubViewHolder> {

    private List<Movie> movies;
    private List<TVSeries> tvShows;
    private final Context context;
    private final ViewModel viewModel;
    private final boolean isMovie;
    public WatchlistAdapter(Context context, ViewModel viewModel, boolean isMovie) {
        this.viewModel = viewModel;
        this.context = context;
        this.isMovie = isMovie;
        if(isMovie)
            movies = viewModel.getSavedMovies();
        else
            tvShows = viewModel.getSavedSeries();

    }


    @NonNull
    @Override
    public SubAdapter.SubViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stream_button_sample, parent, false);
        return new SubAdapter.SubViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull SubAdapter.SubViewHolder holder, int position) {
        Stream current_stream = isMovie ? movies.get(position): tvShows.get(position);
        if(current_stream == null)
            return;

        Glide.with(context)
                .load(API.IMAGE_FIXED_URL + current_stream.getPoster_path())
                .into(holder.streamImage);

        holder.streamImage.setOnClickListener(v -> {

            Intent intent = new Intent(context, Overview.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(MainActivity.EXTRA_STREAM, current_stream);
            intent.putExtra(MainActivity.EXTRA_ISCHACHED, true);
            Overview.setViewModel(viewModel);
            context.startActivity(intent);
        });

        holder.rating.setText("" + current_stream.getVote_average());
        if(isMovie) {
            holder.title.setText(((Movie)current_stream).getTitle());
            holder.date.setText(((Movie) current_stream).getRelease_date().split("-")[0]);
        }
        else {
            holder.title.setText(((TVSeries)current_stream).getTitle());
            holder.date.setText(((TVSeries) current_stream).getRelease_date().split("-")[0]);
        }
    }

    @Override
    public int getItemCount() {
        return isMovie? movies.size(): tvShows.size();
    }
}
