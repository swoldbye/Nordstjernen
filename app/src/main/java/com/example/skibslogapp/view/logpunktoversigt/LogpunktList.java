package com.example.skibslogapp.view.logpunktoversigt;

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

public class LogpunktList extends Fragment {

    /**
     * Class to setup the recycler-list-adapter.
     */

    RecyclerView postRecyclerView;
    private List<Logpunkt> logs;
    private LogpunktAdapter mAdapter;


    LogpunktList(List<Logpunkt> logs) {
        this.logs = logs;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.logpunktoversigt_list, container, false);

        postRecyclerView = view.findViewById(R.id.postRecyclerView);
        postRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this.getContext());
        mAdapter = new LogpunktAdapter(logs);

        postRecyclerView.setLayoutManager(mLayoutManager);
        postRecyclerView.setAdapter(mAdapter);

        return view;
    }


    RecyclerView.Adapter getAdapter(){
        return postRecyclerView.getAdapter();
    }

    /**
     * Adds a filler card into the list on the openning of opretlogpunkt fragment.
     * @param toggle
     */
    void toggleMinimize(boolean toggle){
        mAdapter.toggleFillerCard(toggle);
        final int itemCount = mAdapter.getItemCount();
        if (itemCount > 0) {
            new Handler().postDelayed(() -> postRecyclerView.smoothScrollToPosition(mAdapter.getItemCount() - 1), 20);
        }
    }
}

