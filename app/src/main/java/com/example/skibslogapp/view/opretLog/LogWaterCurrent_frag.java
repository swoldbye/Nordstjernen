package com.example.skibslogapp.view.opretLog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.skibslogapp.R;

public class LogWaterCurrent_frag extends Fragment implements View.OnClickListener {
    private Button currentNorthBtn, currentEastBtn, currentSouthBtn, currentWestBtn, currentResetBtn;
    private TextView waterCurrentDirection;
    private EditText waterCurrentSpeed;
    private LogViewModel logVM;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_opret_log_watercurrent, container, false);
        logVM = ViewModelProviders.of(getActivity()).get(LogViewModel.class);

        //Strøm Retning
        currentNorthBtn = view.findViewById(R.id.nordButton_strøm);
        currentEastBtn = view.findViewById(R.id.østButton_strøm);
        currentSouthBtn = view.findViewById(R.id.sydButton_strøm);
        currentWestBtn = view.findViewById(R.id.vestButton_strøm);
        currentNorthBtn.setOnClickListener(this);
        currentEastBtn.setOnClickListener(this);
        currentSouthBtn.setOnClickListener(this);
        currentWestBtn.setOnClickListener(this);
        currentResetBtn = view.findViewById(R.id.strøm_delete);
        currentResetBtn.setOnClickListener(this);
        currentResetBtn.setVisibility(View.INVISIBLE);
        waterCurrentDirection = view.findViewById(R.id.strøm_input);
        waterCurrentDirection.setText("");

        //Strømningshastighed
        waterCurrentSpeed = view.findViewById(R.id.strømhastighed_edittext);
        waterCurrentSpeed.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(waterCurrentSpeed.getText().length() != 0)
                    logVM.setWaterCurrentSpeed(Integer.parseInt(waterCurrentSpeed.getText().toString()));
            }
        });

        return view;
    }

    @Override
    public void onClick(View v) {
        if(v == currentNorthBtn) strømDirectionLogic( "N", "S");
        else if(v == currentEastBtn) strømDirectionLogic( "Ø", "V");
        else if(v == currentSouthBtn) strømDirectionLogic( "S", "N");
        else if(v == currentWestBtn) strømDirectionLogic("V", "Ø");
        else if (v == currentResetBtn) {
            logVM.setWaterCurrentDirection("");
            waterCurrentDirection.setText(logVM.getWaterCurrentDirection());
            currentResetBtn.setVisibility(View.INVISIBLE);
        }
    }

    private void strømDirectionLogic(String btnDirection, String counterDirection) {
        if(!logVM.getWaterCurrentDirection().contains(counterDirection)) {
            switch(logVM.getWaterCurrentDirection().length()) {
                case 0:
                    logVM.setWaterCurrentDirection(btnDirection);
                    waterCurrentDirection.setText(logVM.getWaterCurrentDirection());
                    break;

                case 1:
                    if(btnDirection.equals("N") || btnDirection.equals("S")) {
                        logVM.setWaterCurrentDirection(btnDirection.concat(logVM.getWaterCurrentDirection())); //Put in the front
                        waterCurrentDirection.setText(logVM.getWaterCurrentDirection());

                    }
                    else {
                        logVM.setWaterCurrentDirection(logVM.getWaterCurrentDirection().concat(btnDirection)); //Put in the back
                        waterCurrentDirection.setText(logVM.getWaterCurrentDirection());
                    }
                    break;

                case 2:
                    if(logVM.getWaterCurrentDirection().indexOf(btnDirection) == logVM.getWaterCurrentDirection().lastIndexOf(btnDirection)) {
                        if(logVM.getWaterCurrentDirection().contains(btnDirection)) {
                            logVM.setWaterCurrentDirection(btnDirection.concat(logVM.getWaterCurrentDirection())); //Put in front
                            waterCurrentDirection.setText(logVM.getWaterCurrentDirection());
                        }
                        else if(btnDirection.equals("N") || btnDirection.equals("S")) {
                            logVM.setWaterCurrentDirection(logVM.getWaterCurrentDirection().substring(0,1).concat(btnDirection).concat(logVM.getWaterCurrentDirection().substring(1,2))); //Put in the middle
                            waterCurrentDirection.setText(logVM.getWaterCurrentDirection());
                        }
                        else {
                            logVM.setWaterCurrentDirection(logVM.getWaterCurrentDirection().concat(btnDirection)); //Put in the back
                            waterCurrentDirection.setText(logVM.getWaterCurrentDirection());
                        }
                    }
                    break;
            }
            currentResetBtn.setVisibility(View.VISIBLE);
        }
    }
}
