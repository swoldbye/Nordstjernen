package com.example.skibslogapp.postOversigt;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skibslogapp.R;
import com.example.skibslogapp.model.Logpunkt;

import java.util.List;

public class PostOversigt extends Fragment {

    RecyclerView postRecyclerView;
    private List<Logpunkt> logs;
    private RecyclerAdapter mAdapter;


    PostOversigt(List<Logpunkt> logs) {
        this.logs = logs;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_oversigt, container, false);

        postRecyclerView = view.findViewById(R.id.postRecyclerView);
        postRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this.getContext());
        mAdapter = new RecyclerAdapter(logs);

        postRecyclerView.setLayoutManager(mLayoutManager);
        postRecyclerView.setAdapter(mAdapter);

        return view;
    }


    RecyclerView.Adapter getAdapter(){
        return postRecyclerView.getAdapter();
    }

    void toggleMinimize(boolean toggle){
        mAdapter.toggleFillerCard(toggle);
        final int itemCount = mAdapter.getItemCount();
        if (itemCount > 0) {
            new Handler().postDelayed(() -> postRecyclerView.smoothScrollToPosition(mAdapter.getItemCount() - 1), 20);
        }
    }
}

