package com.example.moviesapp.views;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moviesapp.R;
import com.example.moviesapp.architecture.api.API;
import com.example.moviesapp.architecture.models.Movie;
import com.example.moviesapp.architecture.models.Stream;
import com.example.moviesapp.architecture.models.TVSeries;

public class SubAdapter extends PagedListAdapter<Stream, SubAdapter.SubViewHolder> {

    private Context context;

    protected SubAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.context = context;
    }

    private static DiffUtil.ItemCallback<Stream> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<>() {
                @Override
                public boolean areItemsTheSame(@NonNull Stream oldItem, @NonNull Stream newItem) {
                    return oldItem.getId() == newItem.getId();
                }

                @Override
                public boolean areContentsTheSame(@NonNull Stream oldItem, @NonNull Stream newItem) {
                    return oldItem.equals(newItem);
                }
            };


    @NonNull
    @Override
    public SubViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.movie_button_sample, parent, false);
        return new SubViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubViewHolder holder, int position) {
        Stream current_stream = getItem(position);
        if(current_stream == null)
            return;

        Glide.with(context)
                .load(API.IMAGE_FIXED_URL + current_stream.getPoster_path())
                .into(holder.movieClick);

        holder.movieClick.setOnClickListener(v -> {
            Overview.setMovie(current_stream);
            Intent intent = new Intent(context, Overview.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });

        holder.rating.setText("" + current_stream.getVote_average());
        if(current_stream instanceof Movie) {
            holder.title.setText(((Movie)current_stream).getTitle());
            holder.date.setText("" + ((Movie) current_stream).getRelease_date().split("-")[0]);
        }
        else {
            holder.title.setText(((TVSeries)current_stream).getTitle());
            holder.date.setText("" + ((TVSeries) current_stream).getRelease_date().split("-")[0]);
        }
    }

    static class SubViewHolder extends RecyclerView.ViewHolder {
        private ImageButton movieClick;
        private TextView title;
        private TextView date;
        private TextView rating;

        public SubViewHolder(@NonNull View itemView) {
            super(itemView);
            movieClick = itemView.findViewById(R.id.imageButton_button_sample);
            title = itemView.findViewById(R.id.textView_button_sample);
            date = itemView.findViewById(R.id.stream_date);
            rating = itemView.findViewById(R.id.stream_rating);
        }
    }


}
