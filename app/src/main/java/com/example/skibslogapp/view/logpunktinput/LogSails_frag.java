package com.example.skibslogapp.view.logpunktinput;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.skibslogapp.R;
import com.example.skibslogapp.utility.KingButton;

/**
 * Fragment to set the sails and orientation with user inputs.
 *  */
public class LogSails_frag extends Fragment implements View.OnClickListener {
    private KingButton fBtn, oeBtn, n1Btn, n2Btn, n3Btn,
            port, starboard;
    private LogViewModel logVM;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.logpunktinput_sejl, container, false);
        logVM = ViewModelProviders.of(getActivity()).get(LogViewModel.class);

        //Sails
        fBtn = view.findViewById(R.id.fButton);
        oeBtn = view.findViewById(R.id.øButton);
        n1Btn = view.findViewById(R.id.n1Button);
        n2Btn = view.findViewById(R.id.n2Button);
        n3Btn = view.findViewById(R.id.n3Button);
        fBtn.setOnClickListener(this);
        oeBtn.setOnClickListener(this);
        n1Btn.setOnClickListener(this);
        n2Btn.setOnClickListener(this);
        n3Btn.setOnClickListener(this);
        fBtn.createRelation(oeBtn);
        n1Btn.createRelation(n2Btn);
        n1Btn.createRelation(n3Btn);
        n2Btn.createRelation(n3Btn);

        //Orientation
        port = view.findViewById(R.id.hals_bagbord_btn);
        starboard = view.findViewById(R.id.hals_styrbord_btn);
        port.setOnClickListener(this);
        starboard.setOnClickListener(this);
        port.createRelation(starboard);

        updateViewInfo();

        return view;
    }

    @Override
    public void onClick(View v) {
        KingButton btn = (KingButton) v;
        btn.kingSelected();
        if(v == fBtn || v == oeBtn || v == n1Btn || v == n2Btn || v == n3Btn) {
            String userInput = "";
            if(fBtn.isSelected() || oeBtn.isSelected()) {
                if(fBtn.isSelected()) userInput = fBtn.getText().toString();
                else if(oeBtn.isSelected()) userInput = oeBtn.getText().toString();
            }
            if(n1Btn.isSelected() || n2Btn.isSelected() || n3Btn.isSelected()) {
                userInput += userInput.length() > 0 ? "-" : "";
                if(n1Btn.isSelected()) userInput +=  n1Btn.getText().toString();
                else if(n2Btn.isSelected()) userInput +=  n2Btn.getText().toString();
                else if(n3Btn.isSelected()) userInput +=  n3Btn.getText().toString();
            }
            logVM.setSails(userInput);
        }
        if(btn == port || btn == starboard) {
            if(btn.isSelected()) logVM.setOrientation(btn.getText().toString().toLowerCase().equals("bagbord") ? "bb" : "sb");
            else logVM.setOrientation("");
        }
    }

    /**
     * Updates the buttons of the view elements
     */
    private void updateViewInfo() {
        String sailString = logVM.getSails().toLowerCase()+logVM.getOrientation(); //String of both sail and orientation

        //Upper sail
        if(sailString.contains("f")) fBtn.kingSelected();
        else if(sailString.contains("ø")) oeBtn.kingSelected();
        //Lower sail
        if(sailString.contains("n1")) n1Btn.kingSelected();
        else if(sailString.contains("n2")) n2Btn.kingSelected();
        else if(sailString.contains("n3")) n3Btn.kingSelected();

        //Orientation
        if(sailString.contains("bb")) port.kingSelected();
        else if(sailString.contains("sb")) starboard.kingSelected();
    }
}