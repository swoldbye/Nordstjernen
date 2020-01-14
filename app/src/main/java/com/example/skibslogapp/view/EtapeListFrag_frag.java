package com.example.skibslogapp.view;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.skibslogapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EtapeListFrag_frag extends Fragment {


    public EtapeListFrag_frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_etape_list, container, false);
    }

}
