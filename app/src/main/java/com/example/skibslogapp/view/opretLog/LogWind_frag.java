package com.example.skibslogapp.view.opretLog;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.skibslogapp.R;

public class LogWind_frag extends Fragment implements View.OnClickListener {
    private Button vindNordBtn, vindØstBtn, vindSydBtn, vindVestBtn, vindretning_delete;
    private TextView vindretning_input;
    private EditText vindHastighedEditTxt;
    private LogViewModel logVM;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_opret_log_wind, container, false);
        logVM = ViewModelProviders.of(getActivity()).get(LogViewModel.class);

        //Vind Retning
        vindNordBtn = view.findViewById(R.id.nordButton);
        vindØstBtn = view.findViewById(R.id.østButton);
        vindSydBtn = view.findViewById(R.id.sydButton);
        vindVestBtn = view.findViewById(R.id.vestButton);
        vindretning_delete = view.findViewById(R.id.vindretning_delete);
        vindretning_delete.setOnClickListener(this);
        vindretning_delete.setVisibility(View.INVISIBLE);

        vindretning_input = view.findViewById(R.id.vindretning_input);
        vindretning_input.setText("");

        //Vindhastighed
        vindHastighedEditTxt = view.findViewById(R.id.vindhastighed_edittext);

        //On click Listeners:
        vindNordBtn.setOnClickListener(this);
        vindØstBtn.setOnClickListener(this);
        vindSydBtn.setOnClickListener(this);
        vindVestBtn.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        // Vindretning
        if(v == vindNordBtn) vindDirectionLogic("N", "S");
        else if(v == vindØstBtn) vindDirectionLogic("Ø", "V");
        else if(v == vindSydBtn) vindDirectionLogic("S", "N");
        else if(v == vindVestBtn) vindDirectionLogic("V", "Ø");

        else if (v == vindretning_delete) {
            logVM.setWindDirection("");
            vindretning_input.setText(logVM.getWindDirection());
            vindretning_delete.setVisibility(View.INVISIBLE);
        }
    }

    private void vindDirectionLogic(String btnDirection, String counterDirection) {
        if(!logVM.getWindDirection().contains(counterDirection)) { //Control input for going opposite ways
            switch(logVM.getWindDirection().length()) {
                case 0:
                    logVM.setWindDirection(btnDirection); //Input first direction
                    vindretning_input.setText(logVM.getWindDirection());
                    vindretning_delete.setVisibility(View.VISIBLE); //Show button to user for reset
                    break;

                case 1:
                    if(btnDirection.equals("N") || btnDirection.equals("S")) {
                        logVM.setWindDirection(btnDirection.concat(logVM.getWindDirection())); //Put in the front
                        vindretning_input.setText(logVM.getWindDirection());
                    }
                    else {
                        logVM.setWindDirection(logVM.getWindDirection().concat(btnDirection)); //Put in the back
                        vindretning_input.setText(logVM.getWindDirection());
                    }
                    break;

                case 2:
                    if(logVM.getWindDirection().indexOf(btnDirection) == logVM.getWindDirection().lastIndexOf(btnDirection)) {
                        if(logVM.getWindDirection().contains(btnDirection)) {
                            logVM.setWindDirection(btnDirection.concat(logVM.getWindDirection())); //Put in front
                            vindretning_input.setText(logVM.getWindDirection());
                        }
                        else if(btnDirection.equals("N") || btnDirection.equals("S")) {
                            logVM.setWindDirection(logVM.getWindDirection().substring(0,1).concat(btnDirection).concat(logVM.getWindDirection().substring(1,2))); //Put in the middle
                            vindretning_input.setText(logVM.getWindDirection()); //Put in the middle
                        }
                        else {
                            logVM.setWindDirection(logVM.getWindDirection().concat(btnDirection)); //Put in the back
                            vindretning_input.setText(logVM.getWindDirection());
                        }
                    }
                    break;
            }
        }
    }
}
