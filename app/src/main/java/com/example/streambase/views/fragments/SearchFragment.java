package com.example.streambase.views.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.streambase.R;
import com.example.streambase.architecture.ViewModel;
import com.example.streambase.architecture.models.Stream;
import com.example.streambase.views.recyclerview_adapters.SubAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@SuppressWarnings("ALL")
public class SearchFragment extends Fragment implements TextWatcher {


    private Context context;
    private static ViewModel viewModel;
    private LifecycleOwner owner;
    private Button movieSearch, tv_showSearch;
    private RecyclerView searchRecyclerview;
    private ImageButton searchButton;
    private TextView pageMessage;

    private char streamType;
    private SubAdapter subAdapter;
    EditText searchBar;

    public SearchFragment() {
        // Required empty public constructor
    }

    public SearchFragment(Context context, ViewModel viewModel, LifecycleOwner owner) {
        this.context = context;
        SearchFragment.viewModel = viewModel;
        this.owner = owner;
    }

    public static SearchFragment newInstance() { return new SearchFragment();}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeComponents(view);
        setActions();
    }

    private void initializeComponents(View view) {
        subAdapter = new SubAdapter(context, viewModel);
        searchRecyclerview = view.findViewById(R.id.search_recyclerview);

        searchBar = view.findViewById(R.id.search_bar);

        searchButton = view.findViewById(R.id.search_button);

        movieSearch = view.findViewById(R.id.movieButton);

        pageMessage = view.findViewById(R.id.search_page_message);

        tv_showSearch = view.findViewById(R.id.TVButton);
    }

    private void setActions() {
        pageMessage.setVisibility(View.GONE);

        movieSearch.setEnabled(false);

        streamType = 'M';

        searchRecyclerview.setLayoutManager(new GridLayoutManager(context, 2));
        searchRecyclerview.setAdapter(subAdapter);

        searchBar.addTextChangedListener(this);

        searchButton.setOnClickListener(v -> updateSearchRecyclerView(searchBar.getText().toString()));

        setButtonAction(movieSearch);
        setButtonAction(tv_showSearch);
    }


    private void setButtonAction(Button button) {
        boolean isMovie = button == movieSearch;
        button.setOnClickListener(v -> {
            movieSearch.setEnabled(! isMovie);
            tv_showSearch.setEnabled(isMovie);
            streamType = isMovie? 'M': 'T';
        });
    }
    @SuppressLint("NotifyDataSetChanged")
    private void updateSearchRecyclerView(String searchQuery) {
        viewModel.getSearchPagedList(streamType, searchQuery).observe(owner, (Observer<PagedList<Stream>>) subAdapter::submitList);
        subAdapter.notifyDataSetChanged();

        if(subAdapter.getCurrentList().isEmpty())
            pageMessage.setVisibility(View.VISIBLE);
        else
            pageMessage.setVisibility(View.GONE);

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        if(s.length() == 0)
            return;
        String searchQuery = s.toString();
        updateSearchRecyclerView(searchQuery);
    }
}