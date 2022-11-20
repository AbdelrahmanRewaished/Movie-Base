package com.example.streambase.views.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.streambase.R;
import com.example.streambase.architecture.ViewModel;
import com.example.streambase.databinding.ActivityMainBinding;
import com.example.streambase.views.fragments.HomeFragment;
import com.example.streambase.views.fragments.SearchFragment;
import com.example.streambase.views.fragments.WatchlistFragment;

@SuppressWarnings("ALL")
public class MainActivity extends AppCompatActivity {

    private ViewModel viewModel;

    private ActivityMainBinding binding;

    public static String API_KEY;

    public final static String EXTRA_STREAM = "com.example.streambase.architecture.models.STREAM";
    public static final String EXTRA_ISCHACHED = "com.example.streambase.architecture.models.GENRE";

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initializeComponents();

        binding.bottomNavView.setOnItemSelectedListener(item -> {
            switch(item.getItemId()) {
                case R.id.home:
                    replaceFragment(new HomeFragment(this)); break;
                case R.id.search_nav:
                    replaceFragment(new SearchFragment(getApplicationContext(), viewModel, this)); break;
                case R.id.watchlist:
                    replaceFragment(new WatchlistFragment(getApplicationContext(), viewModel)); break;
            }
            return true;
        });

        if(savedInstanceState == null)
            replaceFragment(new HomeFragment(this));
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flFragment, fragment);
        fragmentTransaction.commit();
    }
    private void initializeComponents() {
        setContentView(R.layout.activity_main);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        API_KEY = getString(R.string.API_KEY);

        viewModel = new ViewModelProvider(this).get(ViewModel.class);
    }

    public ViewModel getViewModel() {
        return viewModel;
    }
}
