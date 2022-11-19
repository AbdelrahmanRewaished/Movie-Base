package com.example.streambase.views;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;

import com.example.streambase.R;
import com.example.streambase.architecture.ViewModel;
import com.example.streambase.architecture.models.Stream;
import com.example.streambase.databinding.ActivityMainBinding;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
public class MainActivity extends AppCompatActivity {

    private ViewModel viewModel;

    private ActivityMainBinding binding;

    private List<DisplaySample> displaySampleList;
    private List<SubAdapter> subAdapters;
    private static final int STREAM_SAMPLE_SIZE = 7;
    public static String API_KEY;

    public final static String EXTRA_STREAM = "com.example.streambase.architecture.models.STREAM";

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        API_KEY = getString(R.string.API_KEY);

        initializeComponents();
        
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.bottomNavView.setOnItemSelectedListener(item -> {
            switch(item.getItemId()) {
                case R.id.home:
                    replaceFragment(new HomeFragment(this)); break;
                case R.id.search_nav:
                    replaceFragment(new SearchFragment(getApplicationContext(), viewModel, this)); break;
                case R.id.bookmarks:
                    replaceFragment(new BookmarksFragment()); break;
            }
            return true;
        });
        replaceFragment(new HomeFragment(this));


    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flFragment, fragment);
        fragmentTransaction.commit();
    }
    private void initializeComponents() {
        viewModel = new ViewModelProvider(this).get(ViewModel.class);
        displaySampleList = new ArrayList<>();
        subAdapters = new ArrayList<>();
        setMainStreams();
    }
    private void setMainStreams() {
        displaySampleList.add(new DisplaySample('M', "now_playing"));
        displaySampleList.add(new DisplaySample('M', "popular"));
        displaySampleList.add(new DisplaySample('M', "top_rated"));
        displaySampleList.add(new DisplaySample('M', "upcoming"));
        displaySampleList.add(new DisplaySample('T', "airing_today"));
        displaySampleList.add(new DisplaySample('T', "popular"));
        displaySampleList.add(new DisplaySample('T', "top_rated"));
        setStreamsAdapters();
    }

    private void setStreamsAdapters() {
        for (int i = 0; i < STREAM_SAMPLE_SIZE; i++) {
            SubAdapter adapter = new SubAdapter(getApplicationContext(), viewModel);
            char streamType = displaySampleList.get(i).getStreamType().charAt(0);
            String streamState = displaySampleList.get(i).getStreamStateOriginal();
            viewModel.getMainPagedList(streamType, streamState).observe(this, (Observer<PagedList<Stream>>) adapter::submitList);
            subAdapters.add(adapter);
        }
    }

    public ViewModel getViewModel() {
        return viewModel;
    }

    public List<DisplaySample> getDisplaySampleList() {
        return displaySampleList;
    }

    public List<SubAdapter> getSubAdapters() {
        return subAdapters;
    }
}
