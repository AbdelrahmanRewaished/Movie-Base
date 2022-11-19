package com.example.streambase.views;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.streambase.R;
import com.example.streambase.architecture.ViewModel;
import com.example.streambase.architecture.models.Stream;

import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@SuppressWarnings("ALL")
public class HomeFragment extends Fragment {



   private List<SubAdapter> subAdapters;
   private List<DisplaySample> displaySamples;
   private ViewModel viewModel;
   private Context context;
   private RecyclerView mainRecyclerView;
   private SwipeRefreshLayout swipeRefreshLayout;
   private TextView errorText;
   private ImageButton searchButton;
   private static MainActivity mainActivity;
   private ScheduledThreadPoolExecutor executor;
   private boolean adapterSet;
   private TextView watchNowText;

    public HomeFragment() {
        // Required empty public constructor
    }

    public HomeFragment(MainActivity mainActivity) {
        HomeFragment.mainActivity =  mainActivity;
        initializeComponents();
    }

    private void initializeComponents() {
        this.context = mainActivity.getApplicationContext();
        this.displaySamples = mainActivity.getDisplaySampleList();
        this.viewModel = mainActivity.getViewModel();
        this.subAdapters = mainActivity.getSubAdapters();
    }
    public static HomeFragment newInstance() {return new HomeFragment();}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeComponents();

        searchButton = view.findViewById(R.id.search_main_button);
        watchNowText = view.findViewById(R.id.watch_now_text);
        errorText = view.findViewById(R.id.error_text);
        searchButton.setOnClickListener(v -> {
            FragmentTransaction fragmentTransaction = mainActivity.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.flFragment, new SearchFragment(context, viewModel, mainActivity));
            fragmentTransaction.commit();
        });

        swipeRefreshLayout = view.findViewById(R.id.home_fragment);

        executor = new ScheduledThreadPoolExecutor(1);
        swipeRefreshLayout.setOnRefreshListener(() -> reloadStreamsData(0));

        buildMainRecyclerView(view);
        reloadStreamsData(0);
    }

    private void buildMainRecyclerView(View view) {
        if(mainRecyclerView != null)
            return;
        mainRecyclerView = view.findViewById(R.id.mainRecyclerView);
        mainRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        mainRecyclerView.setHasFixedSize(true);
    }

    private void reloadStreamsData(int time_so_far) {

        if(getView() == null)
            return;

        if(time_so_far == 4000) {
            setConnectionError(true);
            return;
        }
        swipeRefreshLayout.setRefreshing(true);
        for (int i = 0; i < displaySamples.size(); i++) {
            SubAdapter adapter = subAdapters.get(i);
            char streamType = displaySamples.get(i).getStreamType().charAt(0);
            String streamState = displaySamples.get(i).getStreamStateOriginal();
            viewModel.getMainPagedList(streamType, streamState).observe(getViewLifecycleOwner(), (Observer<PagedList<Stream>>) adapter::submitList);
        }
        executor.schedule(() -> mainActivity.runOnUiThread(() -> {
            if(! viewModel.isSuccessfulConnection()) {
                reloadStreamsData(time_so_far + 500);
            }
            else {
                if(! adapterSet) {
                    loadMainAdapter();
                }
                setConnectionError(false);
            }
        }), 500, TimeUnit.MILLISECONDS);

    }

    private void setConnectionError(boolean errorExisting) {
        swipeRefreshLayout.setRefreshing(false);
        if(errorExisting) {
            mainRecyclerView.setVisibility(View.GONE);
            errorText.setVisibility(View.VISIBLE);
            searchButton.setVisibility(View.GONE);
            watchNowText.setVisibility(View.GONE);
            return;
        }
        mainRecyclerView.setVisibility(View.VISIBLE);
        errorText.setVisibility(View.GONE);
        searchButton.setVisibility(View.VISIBLE);
        watchNowText.setVisibility(View.VISIBLE);
    }

    private void loadMainAdapter() {
        MainAdapter mainAdapter = new MainAdapter(context, displaySamples, subAdapters, mainActivity.getSupportFragmentManager());
        mainRecyclerView.setAdapter(mainAdapter);
        mainAdapter.notifyDataSetChanged();
        adapterSet = true;
    }
}