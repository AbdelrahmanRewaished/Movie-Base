package com.example.streambase.views.recyclerview_adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.streambase.R;
import com.example.streambase.views.DisplaySample;
import com.example.streambase.views.fragments.DetailsFragment;

import java.io.Serializable;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> implements Serializable {

    private final List<DisplaySample> samples;
    private final Context context;
    private final List<SubAdapter> subAdapters;
    private final FragmentManager fragmentManager;

    public MainAdapter(Context context, List<DisplaySample> samples, List<SubAdapter> subAdapters, FragmentManager manager) {
        this.context = context;
        this.samples = samples;
        this.subAdapters = subAdapters;
        this.fragmentManager = manager;
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

        holder.viewDetails.setOnClickListener(v -> replaceFragment(new DetailsFragment(context, subAdapters.get(position), sample.getStreamState(),
            sample.getStreamType())));
    }

    @SuppressLint("NotifyDataSetChanged")
    private void buildSubRecyclerView(RecyclerView recyclerView, SubAdapter subAdapter) {
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(subAdapter);
        subAdapter.notifyDataSetChanged();
    }
    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flFragment, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public int getItemCount() {
        return samples.size();
    }


    static class MainViewHolder extends RecyclerView.ViewHolder implements Serializable{
        private final TextView streamState_view;
        private final TextView streamType_view;
        private final RecyclerView recyclerView;
        private final ImageButton viewDetails;

        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            streamState_view = itemView.findViewById(R.id.watchState);
            streamType_view = itemView.findViewById(R.id.watchType);
            recyclerView = itemView.findViewById(R.id.subRecyclerView);
            viewDetails = itemView.findViewById(R.id.getDetails);
        }
    }
}
