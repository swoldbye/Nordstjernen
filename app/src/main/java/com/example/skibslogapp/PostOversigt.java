package com.example.skibslogapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.skibslogapp.model.LogInstans;

import java.util.ArrayList;

public class PostOversigt extends Fragment implements View.OnClickListener{

    ListView postListView;
    Button openCloseButton;
    OnPostOversigtListener mCallback;
    ArrayList<LogInstans> tempLogs = new ArrayList<>();



    public PostOversigt(OnPostOversigtListener mCallback){
        this.mCallback = mCallback;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_post_oversigt, container, false);

        openCloseButton = view.findViewById(R.id.openCloseButton);
        openCloseButton.setOnClickListener(this);

        postListView = view.findViewById(R.id.postListView);
        tempLogs.add(new LogInstans("11:34", "SSØ","Ø", "005", "F", "LÆ", "this is a note"));
        tempLogs.add(new LogInstans("11:35", "N","Ø", "006", "Ø", "AG", "sd"));
        tempLogs.add(new LogInstans("11:37", "NØ","Ø", "026", "F", "BI", "alskjæafsdljf"));
        tempLogs.add(new LogInstans("12:00", "SØ","Ø", "010", "F", "FO", "sd"));
        tempLogs.add(new LogInstans("12:32", "NV","Ø", "500", "N1", "HA", "Et eller andet"));
        tempLogs.add(new LogInstans("12:35", "NVN","Ø", "234", "F", "HA", "det her er en rigtig rigtig rigtig rigtig lang note"));
        tempLogs.add(new LogInstans("12:50", "SSØ","Ø", "543", "N2", "LÆ", "et eller andet her"));
        tempLogs.add(new LogInstans("13:30", "SSØ","Ø", "345", "F", "FO", "simon er rigtig cool"));
        tempLogs.add(new LogInstans("13:34", "SSØ","Ø", "453", "N#", "AG", "rigtig rigtig cool"));
        tempLogs.add(new LogInstans("14:00", "SSØ","Ø", "023", "F", "BI", "super cool <3 <3"));

        //tempLogs.addAll(Togt.getTogter());

        PostListAdapter adapter = new PostListAdapter(getActivity(), R.layout.postlist_view_layout, tempLogs);
        postListView.setAdapter(adapter);

        return view;
    }

    public void setList(LogInstans nyeste){
        tempLogs.add(nyeste);
        PostListAdapter adapter = new PostListAdapter(getActivity(), R.layout.postlist_view_layout, tempLogs);
        postListView.setAdapter(adapter);
        openCloseButton.setText("open");
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
