package com.example.streambase.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.streambase.R;
import com.example.streambase.views.recyclerview_adapters.SubAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@SuppressWarnings("ALL")
public class DetailsFragment extends Fragment {

    private Context context;
    private SubAdapter subAdapter;
    private RecyclerView recyclerView;
    private String title;
    private String type;

    public DetailsFragment() {
        // Required empty public constructor
    }

    public DetailsFragment(Context context, SubAdapter subAdapter, String title, String type) {
        this.context = context;
        this.subAdapter = subAdapter;
        this.title = title;
        this.type = type;
    }

    public static DetailsFragment newInstance() {
        return new DetailsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.details_recyclerView);
        ((TextView)view.findViewById(R.id.detailsTitle)).setText(title);
        ((TextView)view.findViewById(R.id.detailsType)).setText(type);
        buildRecyclerView();
    }

    private void buildRecyclerView() {
        recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
        recyclerView.setAdapter(subAdapter);
        subAdapter.notifyDataSetChanged();
    }
}