package com.example.skibslogapp.view.opretLog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.skibslogapp.R;
import com.example.skibslogapp.view.utility.KingButton;

public class LogSails_frag extends Fragment implements View.OnClickListener {
    KingButton fBtn, øBtn, n1Btn, n2Btn, n3Btn,
            port, starboard;
    LogViewModel logVM;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_opret_log_sails, container, false);
        logVM = ViewModelProviders.of(getActivity()).get(LogViewModel.class);


        fBtn = view.findViewById(R.id.fButton);
        øBtn = view.findViewById(R.id.øButton);
        n1Btn = view.findViewById(R.id.n1Button);
        n2Btn = view.findViewById(R.id.n2Button);
        n3Btn = view.findViewById(R.id.n3Button);
        fBtn.setOnClickListener(this);
        øBtn.setOnClickListener(this);
        n1Btn.setOnClickListener(this);
        n2Btn.setOnClickListener(this);
        n3Btn.setOnClickListener(this);
        fBtn.createRelation(øBtn);
        n1Btn.createRelation(n2Btn);
        n1Btn.createRelation(n3Btn);
        n2Btn.createRelation(n3Btn);

        port = view.findViewById(R.id.hals_bagbord_btn);
        starboard = view.findViewById(R.id.hals_styrbord_btn);
        port.setOnClickListener(this);
        starboard.setOnClickListener(this);
        port.createRelation(starboard);

        return view;
    }



    @Override
    public void onClick(View v) {
        if(v == fBtn || v == øBtn || v == n1Btn || v == n2Btn || v == n3Btn) {
            KingButton btn = (KingButton) v;
            String BtnTxt = btn.getText().toString();
            if(btn == fBtn || btn == øBtn) {
                if(logVM.getSails().contains(n1Btn.getText().toString()) || logVM.getSails().contains(n2Btn.getText().toString()) || logVM.getSails().contains(n3Btn.getText().toString())) {
                    logVM.setSails(BtnTxt.concat("-" + logVM.getSails()));
                } else {
                    logVM.setSails(BtnTxt);
                }
            } else if(btn == n1Btn || btn == n2Btn || btn == n3Btn) {
                if(logVM.getSails().contains(fBtn.getText().toString()) || logVM.getSails().contains(øBtn.getText().toString())) {
                    logVM.setSails(logVM.getSails().concat("-" + BtnTxt));
                } else {
                    logVM.setSails(BtnTxt);
                }
            }
            btn.kingSelected();
//            logVM.setSails(btn.getText().toString());
        } else if(v == port || v == starboard) {
            KingButton btn = (KingButton) v;
            btn.kingSelected();
            logVM.setOrientation(btn.getText().toString().toLowerCase() == "bagbord" ? "bb" : "sb");
        }
    }
}
