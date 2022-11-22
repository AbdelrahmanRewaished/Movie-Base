package com.example.streambase.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.streambase.R;
import com.example.streambase.architecture.ViewModel;
import com.example.streambase.architecture.models.Movie;
import com.example.streambase.views.recyclerview_adapters.WatchlistAdapter;

import java.util.List;


@SuppressWarnings("ALL")
public class WatchlistFragment extends Fragment {


    private RecyclerView recyclerView;
    private Button movieButton, tvButton;

    private static Context context;
    private static ViewModel viewModel;
    private TextView pageMessage;

    public WatchlistFragment(Context context, ViewModel viewModel) {
        // Required empty public constructor
        WatchlistFragment.context = context;
        WatchlistFragment.viewModel = viewModel;
    }

    public WatchlistFragment() {

    }

    public static WatchlistFragment newInstance() {
        return new WatchlistFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_watchlist, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeComponents(view);
        setActions();
    }

    private void initializeComponents(View view) {
        recyclerView = view.findViewById(R.id.watchlist_recyclerview);
        movieButton = view.findViewById(R.id.movie_saved_Button);
        tvButton = view.findViewById(R.id.tv_saved_Button);
        pageMessage = view.findViewById(R.id.watchlist_message);

        movieButton.setEnabled(false);
    }

    private void setActions() {

        recyclerView.setLayoutManager(new GridLayoutManager(context, 2));

        setButtonAction(movieButton);
        setButtonAction(tvButton);

        List<Movie> movies = viewModel.getSavedMovies();
        setDefaultActions(movies, true);
    }

    private void setButtonAction(Button button) {
        boolean isMovie = button == movieButton;

        button.setOnClickListener(v -> {
            tvButton.setEnabled(isMovie);
            movieButton.setEnabled(! isMovie);
            List streams = isMovie ? viewModel.getSavedMovies(): viewModel.getSavedSeries();
            setDefaultActions(streams, isMovie);
        });
    }

    private void setDefaultActions(List streams, boolean isMovie) {
        if(streams == null || streams.isEmpty()) {
            pageMessage.setVisibility(View.VISIBLE);
            pageMessage.setText((isMovie? "Movies": "TV Shows") + " Watchlist is empty");
            recyclerView.setVisibility(View.GONE);
            return;
        }
        recyclerView.setVisibility(View.VISIBLE);
        pageMessage.setVisibility(View.GONE);
        WatchlistAdapter adapter = new WatchlistAdapter(context, viewModel, isMovie);
        buildRecyclerView(adapter);
    }

    private void buildRecyclerView(WatchlistAdapter adapter) {
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}