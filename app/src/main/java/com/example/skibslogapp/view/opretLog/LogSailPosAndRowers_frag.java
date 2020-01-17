package com.example.skibslogapp.view.opretLog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.skibslogapp.R;
import com.example.skibslogapp.view.utility.KingButton;

public class LogSailPosAndRowers_frag extends Fragment implements View.OnClickListener {
    KingButton læ, ag, bi, fo, ha;
    EditText numberOfRowers;
    LogViewModel logVM;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_opret_log_sailposandrowers, container, false);
        logVM = ViewModelProviders.of(getActivity()).get(LogViewModel.class);

        //Sejlstilling
        læ = view.findViewById(R.id.læ);
        ag = view.findViewById(R.id.ag);
        ha = view.findViewById(R.id.ha);
        fo = view.findViewById(R.id.fo);
        bi = view.findViewById(R.id.bi);
        læ.setOnClickListener(this);
        ag.setOnClickListener(this);
        ha.setOnClickListener(this);
        fo.setOnClickListener(this);
        bi.setOnClickListener(this);
        læ.createRelation(ag);
        læ.createRelation(ha);
        læ.createRelation(fo);
        læ.createRelation(bi);
        ag.createRelation(ha);
        ag.createRelation(fo);
        ag.createRelation(bi);
        ha.createRelation(fo);
        ha.createRelation(bi);
        fo.createRelation(bi);

        //Antal Roere
        numberOfRowers = (EditText) view.findViewById(R.id.antalRoereEditText);

        return view;
    }

    @Override
    public void onClick(View v) {
        if(v == læ) {
            læ.kingSelected();
            logVM.setSailPosition(læ.getText().toString());
        }
        else if(v == ag) {
            ag.kingSelected();
            logVM.setSailPosition(ag.getText().toString());
        }
        else if(v == bi) {
            bi.kingSelected();
            logVM.setSailPosition(bi.getText().toString());
        }
        else if(v == fo) {
            fo.kingSelected();
            logVM.setSailPosition(fo.getText().toString());
        }
        else if(v == ha) {
            ha.kingSelected();
            logVM.setSailPosition(ha.getText().toString());
        }
    }
}
