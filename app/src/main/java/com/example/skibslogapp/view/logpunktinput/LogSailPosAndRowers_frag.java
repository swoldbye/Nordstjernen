package com.example.skibslogapp.view.logpunktinput;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.skibslogapp.R;
import com.example.skibslogapp.utility.KingButton;

public class LogSailPosAndRowers_frag extends Fragment implements View.OnClickListener {
    private KingButton læ, ag, bi, fo, ha;
    private EditText numberOfRowers;
    private LogViewModel logVM;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.logpunktinput_sejlstilling, container, false);
        logVM = ViewModelProviders.of(getActivity()).get(LogViewModel.class);

        //Sejlstilling
        læ = view.findViewById(R.id.læ);
        ag = view.findViewById(R.id.ag);
        bi = view.findViewById(R.id.bi);
        fo = view.findViewById(R.id.fo);
        ha = view.findViewById(R.id.ha);
        læ.setOnClickListener(this);
        ag.setOnClickListener(this);
        bi.setOnClickListener(this);
        fo.setOnClickListener(this);
        ha.setOnClickListener(this);
        læ.createRelation(ag);
        læ.createRelation(ha);
        læ.createRelation(fo);
        læ.createRelation(bi);
        ag.createRelation(ha);
        ag.createRelation(fo);
        ag.createRelation(bi);
        bi.createRelation(fo);
        bi.createRelation(ha);
        fo.createRelation(ha);

        //Antal Roere
        numberOfRowers = (EditText) view.findViewById(R.id.antalRoereEditText);
        numberOfRowers.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                logVM.setCurrRowers(numberOfRowers.getText() != null && numberOfRowers.getText().length() > 0 ?
                        Integer.parseInt(numberOfRowers.getText().toString()) : -1);
                resetSailPosInput();
            }
        });
        updateViewInfo();

        return view;
    }

    private void updateViewInfo() {
        switch(logVM.getSailPosition().toLowerCase()) {
            case "læ":
                læ.kingSelected();
                break;
            case "ag":
                ag.kingSelected();
                break;
            case "bi":
                bi.kingSelected();
                break;
            case "fo":
                fo.kingSelected();
                break;
            case "ha":
                ha.kingSelected();
                break;
        }
        numberOfRowers.setText(logVM.getCurrRowers() >= 0 ? Integer.toString(logVM.getCurrRowers()) : "");
    }

    @Override
    public void onClick(View v) {
        if(v == læ) {
            læ.kingSelected();
            if(v.isSelected()) logVM.setSailPosition(læ.getText().toString());
            else logVM.setSailPosition("");
        }
        else if(v == ag) {
            ag.kingSelected();
            if(v.isSelected()) logVM.setSailPosition(ag.getText().toString());
            else logVM.setSailPosition("");
        }
        else if(v == bi) {
            bi.kingSelected();
            if(v.isSelected()) logVM.setSailPosition(bi.getText().toString());
            else logVM.setSailPosition("");
        }
        else if(v == fo) {
            fo.kingSelected();
            if(v.isSelected()) logVM.setSailPosition(fo.getText().toString());
            else logVM.setSailPosition("");
        }
        else if(v == ha) {
            ha.kingSelected();
            if(v.isSelected()) logVM.setSailPosition(ha.getText().toString());
            else logVM.setSailPosition("");
        }
        resetRowersInput();
    }

    private void resetSailPosInput() {
        if(logVM.getCurrRowers() >= 0 && logVM.getSailPosition().length() > 0) {
            {
                logVM.setSailPosition("");
                læ.kingDeselected();
                ag.kingDeselected();
                bi.kingDeselected();
                fo.kingDeselected();
                ha.kingDeselected();
            }
        }
    }

    private void resetRowersInput() {
        logVM.setCurrRowers(-1);
        numberOfRowers.setText("");
    }
}
