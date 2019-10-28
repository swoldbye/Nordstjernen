package com.example.skibslogapp.viewControl;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.example.skibslogapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class OpretLog_frag extends Fragment {

    private int timeStringLengthBefore = 0;
    private String finalVindRetning = "";
    private String finalSejlføring = "";
    private String styrbordEllerBagbord = "";
    private String sejlStilling = "";

    //button colors:
    private int basicColor;
    private int standOutColor;
    private Button resetTimeButton;
    private Button nordButton, østButton, sydButton, vestButton;
    private EditText kursEditText, antalRoereEditText, editTime;
    private Button fButton, øButton, n1Button, n2Button, n3Button;
    private Button læ, ag, ha, fo, bi;
    private Button opretButton;
    private TextView vindretning_input;
    private Button vindretning_delete;
    private Switch sbBb;


    public OpretLog_frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_opret_log, container, false);



        return view;
    }


}
