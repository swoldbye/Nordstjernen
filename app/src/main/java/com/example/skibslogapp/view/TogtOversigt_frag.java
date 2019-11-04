package com.example.skibslogapp.view;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.skibslogapp.R;

public class TogtOversigt_frag extends Fragment {


    public TogtOversigt_frag() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_togt_oversigt, container, false);
        // Inflate the layout for this fragment
        return view;
    }

}
