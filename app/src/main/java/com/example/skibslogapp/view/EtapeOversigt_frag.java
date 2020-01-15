package com.example.skibslogapp.view;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.skibslogapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EtapeOversigt_frag extends Fragment {

    private TextView togt, ship, header;


    public EtapeOversigt_frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_etape_oversigt, container, false);
        // Inflate the layout for this fragment

        togt = view.findViewById(R.id.etapeTogtText);
        ship = view.findViewById(R.id.skibsNavnText);
        header = view.findViewById(R.id.etapeHeader);

        return view;
    }

}
