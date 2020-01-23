package com.example.skibslogapp.view.logpunktinput;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.example.skibslogapp.utility.SwapViewsTextHelper;

/**
 * Fragment to set water direction and speed of LogViewModel with user inputs.
 */
public class LogWaterCurrent_frag extends Fragment implements View.OnClickListener {
    private Button currentNorthBtn, currentEastBtn, currentSouthBtn, currentWestBtn, currentResetBtn;
    private TextView waterCurrentDirection , stroemretningTxtLeft, stroemretningTxtCenter;
    private EditText waterCurrentSpeed;
    private LogViewModel logVM;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.logpunktinput_stroem, container, false);
        logVM = ViewModelProviders.of(getActivity()).get(LogViewModel.class);

        //Strøm Retning
        stroemretningTxtLeft = view.findViewById(R.id.stroemning_text_leftaligned);
        stroemretningTxtCenter = view.findViewById(R.id.stroemning_text_center);
        waterCurrentDirection = view.findViewById(R.id.strøm_input);
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

        //Strømningshastighed
        waterCurrentSpeed = view.findViewById(R.id.strømhastighed_edittext);
        waterCurrentSpeed.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                logVM.setWaterCurrentSpeed(waterCurrentSpeed.getText().length() > 0 ?
                        Integer.parseInt(waterCurrentSpeed.getText().toString()) : -1);
            }
        });
        updateViewInfo();

        return view;
    }

    @Override
    public void onClick(View v) {
        if(v == currentNorthBtn) waterCurrentLogic( "N", "S");
        else if(v == currentEastBtn) waterCurrentLogic( "Ø", "V");
        else if(v == currentSouthBtn) waterCurrentLogic( "S", "N");
        else if(v == currentWestBtn) waterCurrentLogic("V", "Ø");

        if (v == currentResetBtn) { //Resets the user input
            logVM.setWaterCurrentDirection("");
            waterCurrentDirection.setText(logVM.getWaterCurrentDirection());
            currentResetBtn.setVisibility(View.INVISIBLE);
            SwapViewsTextHelper.centerText(stroemretningTxtLeft, stroemretningTxtCenter);
        } else SwapViewsTextHelper.leftalignText(stroemretningTxtLeft, stroemretningTxtCenter);
    }

    /**
     * Updates the buttons and text of the view elements
     */
    private void updateViewInfo() {
        waterCurrentDirection.setText(logVM.getWaterCurrentDirection());
        currentResetBtn.setVisibility(waterCurrentDirection.getText() != null && waterCurrentDirection.getText().length() != 0 ? View.VISIBLE : View.INVISIBLE);
        waterCurrentSpeed.setText(logVM.getWaterCurrentSpeed() >= 0 ? Integer.toString(logVM.getWaterCurrentSpeed()) : "");
    }

    /**
     *  Adds the direction to the user input, if possible after the rules of N/E/S/W on a compass
     *
     * @param btnDirection      Button pressed
     * @param counterDirection  Not applicable if this Button is pressed
     */
    private void waterCurrentLogic(String btnDirection, String counterDirection) {
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