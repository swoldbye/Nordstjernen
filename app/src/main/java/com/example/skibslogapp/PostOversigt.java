package com.example.skibslogapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.skibslogapp.datalayer.local.LogpunktDAO;
import com.example.skibslogapp.datalayer.local.TogtDAO;
import com.example.skibslogapp.model.GlobalTogt;
import com.example.skibslogapp.model.Logpunkt;
import com.example.skibslogapp.model.Togt;

import java.util.ArrayList;
import java.util.List;

public class PostOversigt extends Fragment implements View.OnClickListener, TogtDAO.TogtObserver {

    ListView postListView;
    Button openCloseButton;
    OnPostOversigtListener mCallback;

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

        //tempLogs.addAll(Togt.getTogter());

        setList();

        TogtDAO.addTogtObserver(this);

        /*
        This is to fix the that it that the we can also access postOversigt.
        If a Bundle is sent from Main_akt, when navigating to opretLog, the PostActivity will first be createt
        and in the PostActivity passes the Bundle to PostOversigt. The reason for passing the Bundle many times is because
        we need to access the openCloseButton to set the text so we can use the OnPostOversigtListener.
         */
        Bundle args = getArguments();
        if(args != null){
            if(args.containsKey("SideMenu")){
                System.out.println("Bundle recieved");

                mCallback.showOpretPost();
                openCloseButton.setText("close");

                args.clear();
            }
        }

        return view;
    }

    public void setList(){
        List<Logpunkt> logpunkter = new LogpunktDAO(getContext()).getLogpunkter(GlobalTogt.getEtape(getContext()));
        PostListAdapter adapter = new PostListAdapter(getActivity(), R.layout.postlist_view_layout, logpunkter);
        postListView.setAdapter(adapter);
        openCloseButton.setText("open");
    }

    @Override
    public void onUpdate(Togt togt) {
        setList();
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        TogtDAO.removeTogtObserver(this);
    }

    public interface OnPostOversigtListener{
        void hideOpretPost();
        void showOpretPost();
    }
}
