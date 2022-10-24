package com.example.moviesapp.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviesapp.R;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {

    private List<DisplaySample> samples;
    private Context context;
    private List<SubAdapter> subAdapters;

    public MainAdapter(Context context, List<DisplaySample> samples, List<SubAdapter> subAdapters) {
        this.context = context;
        this.samples = samples;
        this.subAdapters = subAdapters;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.display_recyclerview_sample, parent, false);
        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        DisplaySample sample = samples.get(position);
        holder.streamType_view.setText(sample.getStreamType());
        holder.streamState_view.setText(sample.getStreamState());
        buildSubRecyclerView(holder.recyclerView, subAdapters.get(position));
    }

    private void buildSubRecyclerView(RecyclerView recyclerView, SubAdapter subAdapter) {
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(subAdapter);
        subAdapter.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return samples.size();
    }


    static class MainViewHolder extends RecyclerView.ViewHolder {
        private TextView streamState_view;
        private TextView streamType_view;
        private RecyclerView recyclerView;
        private ImageButton viewDetails;

        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            streamState_view = itemView.findViewById(R.id.watchState);
            streamType_view = itemView.findViewById(R.id.watchType);
            recyclerView = itemView.findViewById(R.id.subRecyclerView);
            viewDetails = itemView.findViewById(R.id.getDetails);
        }
    }
}
