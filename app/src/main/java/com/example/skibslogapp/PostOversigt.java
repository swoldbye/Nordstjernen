package com.example.skibslogapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.skibslogapp.model.LogInstans;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class PostOversigt extends Fragment implements View.OnClickListener{

    public static final String ARG_PAGE = "arg_page";

    RecyclerView postRecyclerView;
    Button openCloseButton;
    OnPostOversigtListener mCallback;
    List<LogInstans> logs;
    int position;

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    public PostOversigt(OnPostOversigtListener mCallback, List<LogInstans> logs) {
        this.mCallback = mCallback;
        this.logs = logs;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_oversigt, container, false);

//        Bundle arguments = getArguments();
//        int dayNumber = arguments.getInt(ARG_PAGE);

        openCloseButton = view.findViewById(R.id.openCloseButton);
        openCloseButton.setOnClickListener(this);

//        day = view.findViewById(R.id.timeOfDay);
//        day.setText(dayNumber);




        //tempLogs.addAll(Togt.getTogter());

        postRecyclerView = view.findViewById(R.id.postRecyclerView);
        postRecyclerView.setHasFixedSize(true);
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

    public void setList(LogInstans nyeste){
        return;
        /*tempLogs.add(nyeste);
        PostListAdapter adapter = new PostListAdapter(getActivity(), R.layout.postlist_view_layout, tempLogs);
        postListView.setAdapter(adapter);
        openCloseButton.setText("open");*/
    }



    public void onClick(View v){
        if(v == openCloseButton){
            if(openCloseButton.getText().toString().equals("open")) {
                mCallback.showOpretPost();
                openCloseButton.setText("close");
            }else{
                mCallback.hideOpretPost();
                openCloseButton.setText("open");
            }
        }
    }

    public interface OnPostOversigtListener{
        void hideOpretPost();
        void showOpretPost();
    }
}
