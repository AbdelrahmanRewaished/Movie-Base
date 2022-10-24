package com.example.moviesapp.views;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviesapp.R;
import com.example.moviesapp.architecture.ViewModel;
import com.example.moviesapp.architecture.models.Stream;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("FieldCanBeLocal")
public class MainActivity extends AppCompatActivity{

    private ViewModel viewModel;
    private ImageButton imageButton;
    private TextView watchNow;

    private TextView errorText;
    private Button retryConnection;
    private ProgressBar loadingBar;
    private RecyclerView mainRecyclerView;

    private List<DisplaySample> displaySampleList;
    private List<SubAdapter> subAdapters;
    private static final int STREAM_SAMPLE_SIZE = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeViews();

        viewModel = new ViewModelProvider(this).get(ViewModel.class);
        Overview.setMovieViewModel(viewModel);

        subAdapters = new ArrayList<>();
        displaySampleList = new ArrayList<>();
        setStreams();
        buildMainRecyclerView();

//        if(! viewModel.isSuccessfulConnection()) {
//            Toast.makeText(this, "Bad Connection !!!", Toast.LENGTH_SHORT).show();
//        }
    }

    private void initializeViews() {
        imageButton = findViewById(R.id.imageButton2);
        watchNow = findViewById(R.id.watch_now_text);
        errorText = findViewById(R.id.error_text);
        retryConnection = findViewById(R.id.button_retry);
        loadingBar = findViewById(R.id.main_loading_bar);
        mainRecyclerView = findViewById(R.id.mainRecyclerView);
    }

    private void setViews(boolean failing_connection) {
        loadingBar.setVisibility(View.GONE);
        if(! failing_connection) {
            watchNow.setVisibility(View.VISIBLE);
            imageButton.setVisibility(View.VISIBLE);
            return;
        }
        errorText.setVisibility(View.VISIBLE);
        retryConnection.setVisibility(View.VISIBLE);
        watchNow.setVisibility(View.GONE);
        imageButton.setVisibility(View.GONE);
    }

    private void setStreams() {
//        displaySampleList.add(new DisplaySample('M', "latest"));
        displaySampleList.add(new DisplaySample('M', "now_playing"));
        displaySampleList.add(new DisplaySample('M', "popular"));
        displaySampleList.add(new DisplaySample('M', "top_rated"));
        displaySampleList.add(new DisplaySample('M', "upcoming"));
        displaySampleList.add(new DisplaySample('T', "airing_today"));
        displaySampleList.add(new DisplaySample('T', "popular"));
        displaySampleList.add(new DisplaySample('T', "top_rated"));

        for (int i = 0; i < STREAM_SAMPLE_SIZE; i++) {
            SubAdapter adapter = new SubAdapter(getApplicationContext());
            char streamType = displaySampleList.get(i).getStreamType().charAt(0);
            String streamState = displaySampleList.get(i).getStreamStateOriginal();
            viewModel.addPagedList(streamType, streamState).observe(this, (Observer<PagedList<Stream>>) adapter::submitList);
            subAdapters.add(adapter);
        }
    }

    private void buildMainRecyclerView() {
        mainRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        mainRecyclerView.setHasFixedSize(true);
        MainAdapter mainAdapter = new MainAdapter(getApplicationContext(), displaySampleList, subAdapters);
        mainRecyclerView.setAdapter(mainAdapter);
        mainAdapter.notifyDataSetChanged();
    }

}
