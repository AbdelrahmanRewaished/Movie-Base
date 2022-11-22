package com.example.streambase.views.recyclerview_adapters;

import android.annotation.SuppressLint;
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
import com.example.streambase.R;
import com.example.streambase.architecture.ViewModel;
import com.example.streambase.architecture.api.API;
import com.example.streambase.architecture.models.Movie;
import com.example.streambase.architecture.models.Stream;
import com.example.streambase.architecture.models.TVSeries;
import com.example.streambase.views.activities.MainActivity;
import com.example.streambase.views.activities.Overview;

@SuppressWarnings("ALL")
public class SubAdapter extends PagedListAdapter<Stream, SubAdapter.SubViewHolder> {

    private Context context;
    private ViewModel viewModel;

    public SubAdapter(Context context, ViewModel viewModel) {
        super(DIFF_CALLBACK);
        this.context = context;
        this.viewModel = viewModel;
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
        View view = LayoutInflater.from(context).inflate(R.layout.stream_button_sample, parent, false);
        return new SubViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull SubViewHolder holder, int position) {
        Stream current_stream = getItem(position);
        if(current_stream == null)
            return;

        Glide.with(context)
                .load(API.IMAGE_FIXED_URL + current_stream.getPoster_path())
                .into(holder.streamImage);

        holder.streamImage.setOnClickListener(v -> {

            Intent intent = new Intent(context, Overview.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(MainActivity.EXTRA_STREAM, current_stream);
            Overview.setViewModel(viewModel);
            context.startActivity(intent);
        });

        holder.rating.setText("" + current_stream.getVote_average());
        if(current_stream instanceof Movie) {
            holder.title.setText(((Movie)current_stream).getTitle());

            String date_text;
            if(((Movie)current_stream).getRelease_date() == null)
                date_text = "Not Found";
            else
                date_text = ((Movie) current_stream).getRelease_date().split("-")[0];
            holder.date.setText(date_text);
        }
        else {
            holder.title.setText(((TVSeries)current_stream).getTitle());

            String date_text;
            if(((TVSeries)current_stream).getRelease_date() == null)
                date_text = "Not Found";
            else
                date_text = ((TVSeries) current_stream).getRelease_date().split("-")[0];
            holder.date.setText(date_text);
        }
    }

    static class SubViewHolder extends RecyclerView.ViewHolder{
        ImageButton streamImage;
        TextView title;
        TextView date;
        TextView rating;

        public SubViewHolder(@NonNull View itemView) {
            super(itemView);
            streamImage = itemView.findViewById(R.id.imageButton_button_sample);
            title = itemView.findViewById(R.id.textView_button_sample);
            date = itemView.findViewById(R.id.stream_date);
            rating = itemView.findViewById(R.id.stream_rating);
        }
    }


}
