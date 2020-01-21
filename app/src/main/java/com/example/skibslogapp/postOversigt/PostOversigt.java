package com.example.skibslogapp.postOversigt;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skibslogapp.R;
import com.example.skibslogapp.model.Logpunkt;

import java.util.List;

public class PostOversigt extends Fragment {

    public static final String ARG_PAGE = "arg_page";

    RecyclerView postRecyclerView;
    Button openCloseButton;
    List<Logpunkt> logs;
    int position;

    RecyclerAdapter mAdapter;

    private RecyclerView.LayoutManager mLayoutManager;


    public PostOversigt(List<Logpunkt> logs) {
        this.logs = logs;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_oversigt, container, false);

//        Bundle arguments = getArguments();
//        int dayNumber = arguments.getInt(ARG_PAGE);


//        day = view.findViewById(R.id.timeOfDay);
//        day.setText(dayNumber);


        //tempLogs.addAll(Togt.getTogter());

        postRecyclerView = view.findViewById(R.id.postRecyclerView);
        postRecyclerView.setHasFixedSize(true);
        //postRecyclerView.setNestedScrollingEnabled(false);
        mLayoutManager = new LinearLayoutManager(this.getContext());
        mAdapter = new RecyclerAdapter(logs);

        postRecyclerView.setLayoutManager(mLayoutManager);
        postRecyclerView.setAdapter(mAdapter);

        return view;
    }


//    public static PostOversigt newInstance(int dayNumber) {
//
//        OnPostOversigtListener call = new OnPostOversigtListener() {
//            @Override
//            public void hideOpretPost() {
//
//            }
//
//            @Override
//            public void showOpretPost() {
//
//            }
//        };
//
//        PostOversigt fragment = new PostOversigt(call);
//        Bundle args = new Bundle();
//        args.putInt(ARG_PAGE,dayNumber);
//        fragment.setArguments(args);
//        return fragment;
//    }


    public void updateAdapter(List<Logpunkt> logs2){
        //this.logs = logs2;
        mAdapter.updateData(logs2);
        postRecyclerView.smoothScrollToPosition(logs2.size()-1);
        //mAdapter.notifyDataSetChanged();
        //mAdapter.notifyDataSetChanged();
    }

    public RecyclerView.Adapter getAdapter(){
        //return mAdapter;
        return postRecyclerView.getAdapter();
    }

    public void toggleMinimize(boolean toggle){
        mAdapter.toggleFillerCard(toggle);
        final int itemCount = mAdapter.getItemCount();
        if (itemCount > 0) {
            new Handler().postDelayed(() -> postRecyclerView.smoothScrollToPosition(mAdapter.getItemCount() - 1), 20);
        }
    }

}

