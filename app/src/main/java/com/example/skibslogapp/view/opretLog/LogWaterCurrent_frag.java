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
    Button nordButton_Strøm, østButton_Strøm, sydButton_Strøm, vestButton_Strøm, strømningsretning_delete;
    TextView strømretning_input;
    EditText strømNingsretningEditText;
    LogViewModel logVM;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_opret_log_watercurrent, container, false);
        logVM = ViewModelProviders.of(getActivity()).get(LogViewModel.class);

        //Strøm Retning
        nordButton_Strøm = view.findViewById(R.id.nordButton_strøm);
        østButton_Strøm = view.findViewById(R.id.østButton_strøm);
        sydButton_Strøm = view.findViewById(R.id.sydButton_strøm);
        vestButton_Strøm = view.findViewById(R.id.vestButton_strøm);
        nordButton_Strøm.setOnClickListener(this);
        østButton_Strøm.setOnClickListener(this);
        sydButton_Strøm.setOnClickListener(this);
        vestButton_Strøm.setOnClickListener(this);
        strømningsretning_delete = view.findViewById(R.id.strøm_delete);
        strømningsretning_delete.setOnClickListener(this);
        strømningsretning_delete.setVisibility(View.INVISIBLE);
        strømretning_input = view.findViewById(R.id.strøm_input);
        strømretning_input.setText("");

        //Strømningshastighed
        strømNingsretningEditText = view.findViewById(R.id.strømhastighed_edittext);


        return view;
    }

    @Override
    public void onClick(View v) {
        if(v == nordButton_Strøm) strømDirectionLogic( "N", "S");
        else if(v == østButton_Strøm) strømDirectionLogic( "Ø", "V");
        else if(v == sydButton_Strøm) strømDirectionLogic( "S", "N");
        else if(v == vestButton_Strøm) strømDirectionLogic("V", "Ø");
        else if (v == strømningsretning_delete) {
            logVM.setWaterCurrentDirection("");
            strømretning_input.setText(logVM.getWaterCurrentDirection());
            strømningsretning_delete.setVisibility(View.INVISIBLE);
        }
    }

    private void strømDirectionLogic(String btnDirection, String counterDirection) {
        if(!logVM.getWaterCurrentDirection().contains(counterDirection)) {
            switch(logVM.getWaterCurrentDirection().length()) {
                case 0:
                    logVM.setWaterCurrentDirection(btnDirection);
                    strømretning_input.setText(logVM.getWaterCurrentDirection());
                    break;

                case 1:
                    if(btnDirection.equals("N") || btnDirection.equals("S")) {
                        logVM.setWaterCurrentDirection(btnDirection.concat(logVM.getWaterCurrentDirection())); //Put in the front
                        strømretning_input.setText(logVM.getWaterCurrentDirection());

                    }
                    else {
                        logVM.setWaterCurrentDirection(logVM.getWaterCurrentDirection().concat(btnDirection)); //Put in the back
                        strømretning_input.setText(logVM.getWaterCurrentDirection());
                    }
                    break;

                case 2:
                    if(logVM.getWaterCurrentDirection().indexOf(btnDirection) == logVM.getWaterCurrentDirection().lastIndexOf(btnDirection)) {
                        if(logVM.getWaterCurrentDirection().contains(btnDirection)) {
                            logVM.setWaterCurrentDirection(btnDirection.concat(logVM.getWaterCurrentDirection())); //Put in front
                            strømretning_input.setText(logVM.getWaterCurrentDirection());
                        }
                        else if(btnDirection.equals("N") || btnDirection.equals("S")) {
                            logVM.setWaterCurrentDirection(logVM.getWaterCurrentDirection().substring(0,1).concat(btnDirection).concat(logVM.getWaterCurrentDirection().substring(1,2))); //Put in the middle
                            strømretning_input.setText(logVM.getWaterCurrentDirection());
                        }
                        else {
                            logVM.setWaterCurrentDirection(logVM.getWaterCurrentDirection().concat(btnDirection)); //Put in the back
                            strømretning_input.setText(logVM.getWaterCurrentDirection());
                        }
                    }
                    break;
            }
            strømningsretning_delete.setVisibility(View.VISIBLE);
        }
    }
}
