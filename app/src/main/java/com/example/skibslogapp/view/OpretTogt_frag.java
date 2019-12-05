package com.example.skibslogapp.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.skibslogapp.R;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

public class OpretTogt_frag extends Fragment {

    private String[] skibsListe ={"Skib1","Skib2","Skib3","Skib4","Skib5","Skib6"};
    private ArrayAdapter<String> dropDownSkib;
    private MaterialBetterSpinner betterSpinner;

    public OpretTogt_frag() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_opret_togt, container, false);

        dropDownSkib = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_dropdown_item_1line,skibsListe);
        betterSpinner = view.findViewById(R.id.skibsListe);
        betterSpinner.setAdapter(dropDownSkib);

        // Inflate the layout for this fragment
        return view;
    }

}
