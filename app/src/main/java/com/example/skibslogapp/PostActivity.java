package com.example.skibslogapp;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.skibslogapp.datalayer.local.TogtDAO;
import com.example.skibslogapp.view.OpretLog_frag;

public class PostActivity extends Fragment implements PostOversigt.OnPostOversigtListener {

    OpretLog_frag opretLog_frag;
    PostOversigt postOversigt = new PostOversigt(this);


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_post, container, false);
        addOversigtFrag();

        return view;
    }


    private void addOversigtFrag(){
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.add(R.id.postOversigtContainerFrame, postOversigt).commit();
    }

    @Override
    public void hideOpretPost() {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .remove(opretLog_frag)
                .commit();
    }

    @Override
    public void showOpretPost() {
        opretLog_frag = new OpretLog_frag();
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.opretPostContainerFrame, opretLog_frag)
                .commit();
    }
}
