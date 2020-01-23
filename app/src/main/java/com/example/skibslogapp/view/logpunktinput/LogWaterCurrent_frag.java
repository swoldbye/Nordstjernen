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
    private Button stroemNordBtn, stroemOestBtn, stroemSydBtn, stroemVestBtn, stroemRetningDelete;
    private TextView stroemRetning_input, stroemretningTxtLeft, stroemretningTxtCenter;
    private EditText waterCurrentSpeed;
    private LogViewModel logVM;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.logpunktinput_stroem, container, false);
        logVM = ViewModelProviders.of(getActivity()).get(LogViewModel.class);

        //Strøm direction title and input
        stroemretningTxtLeft = view.findViewById(R.id.stroemning_text_leftaligned);
        stroemretningTxtCenter = view.findViewById(R.id.stroemning_text_center);
        stroemRetning_input = view.findViewById(R.id.strøm_input);

        //Direction Buttons
        stroemNordBtn = view.findViewById(R.id.nordButton_strøm);
        stroemOestBtn = view.findViewById(R.id.østButton_strøm);
        stroemSydBtn = view.findViewById(R.id.sydButton_strøm);
        stroemVestBtn = view.findViewById(R.id.vestButton_strøm);
        stroemNordBtn.setOnClickListener(this);
        stroemOestBtn.setOnClickListener(this);
        stroemSydBtn.setOnClickListener(this);
        stroemVestBtn.setOnClickListener(this);
        stroemRetningDelete = view.findViewById(R.id.strøm_delete);
        stroemRetningDelete.setOnClickListener(this);
        stroemRetningDelete.setVisibility(View.INVISIBLE);

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
        if(v == stroemNordBtn) waterCurrentLogic( "N", "S");
        else if(v == stroemOestBtn) waterCurrentLogic( "Ø", "V");
        else if(v == stroemSydBtn) waterCurrentLogic( "S", "N");
        else if(v == stroemVestBtn) waterCurrentLogic("V", "Ø");

        if (v == stroemRetningDelete) { //Resets the user input
            logVM.setWaterCurrentDirection("");
            SwapViewsTextHelper.centerText(stroemretningTxtLeft, stroemretningTxtCenter);
            stroemRetningDelete.setVisibility(View.INVISIBLE);
        }
        updateViewInfo();
    }

    /**
     * Updates the buttons and text of the view elements
     */
    private void updateViewInfo() {
        stroemRetning_input.setText(logVM.getWaterCurrentDirection());
        if(!stroemRetning_input.getText().toString().equals("")) {
            SwapViewsTextHelper.leftalignText(stroemretningTxtLeft, stroemretningTxtCenter);
            stroemRetningDelete.setVisibility(View.VISIBLE);
        }
        stroemRetningDelete.setVisibility(stroemRetning_input.getText() != null && stroemRetning_input.getText().length() != 0 ? View.VISIBLE : View.INVISIBLE);
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
                    stroemRetning_input.setText(logVM.getWaterCurrentDirection());
                    break;

                case 1:
                    if(btnDirection.equals("N") || btnDirection.equals("S")) {
                        logVM.setWaterCurrentDirection(btnDirection.concat(logVM.getWaterCurrentDirection())); //Put in the front
                        stroemRetning_input.setText(logVM.getWaterCurrentDirection());

                    }
                    else {
                        logVM.setWaterCurrentDirection(logVM.getWaterCurrentDirection().concat(btnDirection)); //Put in the back
                        stroemRetning_input.setText(logVM.getWaterCurrentDirection());
                    }
                    break;

                case 2:
                    if(logVM.getWaterCurrentDirection().indexOf(btnDirection) == logVM.getWaterCurrentDirection().lastIndexOf(btnDirection)) {
                        if(logVM.getWaterCurrentDirection().contains(btnDirection)) {
                            logVM.setWaterCurrentDirection(btnDirection.concat(logVM.getWaterCurrentDirection())); //Put in front
                            stroemRetning_input.setText(logVM.getWaterCurrentDirection());
                        }
                        else if(btnDirection.equals("N") || btnDirection.equals("S")) {
                            logVM.setWaterCurrentDirection(logVM.getWaterCurrentDirection().substring(0,1).concat(btnDirection).concat(logVM.getWaterCurrentDirection().substring(1,2))); //Put in the middle
                            stroemRetning_input.setText(logVM.getWaterCurrentDirection());
                        }
                        else {
                            logVM.setWaterCurrentDirection(logVM.getWaterCurrentDirection().concat(btnDirection)); //Put in the back
                            stroemRetning_input.setText(logVM.getWaterCurrentDirection());
                        }
                    }
                    break;
            }
            stroemRetningDelete.setVisibility(View.VISIBLE);
        }
    }
}