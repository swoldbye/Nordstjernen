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
 * Fragment to set wind direction and speed of LogViewModel with user inputs.
 */
public class LogWind_frag extends Fragment implements View.OnClickListener {
    private Button vindNordBtn, vindOestBtn, vindSydBtn, vindVestBtn, vindretning_delete;
    private TextView vindretning_input , vindretningTxtCenter, vindretningTxtLeft;
    private EditText vindHastighedEditTxt;
    private LogViewModel logVM;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.logpunktinput_vind, container, false);
        logVM = ViewModelProviders.of(getActivity()).get(LogViewModel.class);

        //Vind direction title and input
        vindretningTxtLeft = view.findViewById(R.id.vindretning_text_leftaligned);
        vindretningTxtCenter = view.findViewById(R.id.vindretning_text_centered);
        vindretning_input = view.findViewById(R.id.vindretning_input);

        //Direction Buttons
        vindNordBtn = view.findViewById(R.id.nordButton);
        vindOestBtn = view.findViewById(R.id.oestButton);
        vindSydBtn = view.findViewById(R.id.sydButton);
        vindVestBtn = view.findViewById(R.id.vestButton);
        vindNordBtn.setOnClickListener(this);
        vindOestBtn.setOnClickListener(this);
        vindSydBtn.setOnClickListener(this);
        vindVestBtn.setOnClickListener(this);
        vindretning_delete = view.findViewById(R.id.vindretning_delete);
        vindretning_delete.setOnClickListener(this);
        vindretning_delete.setVisibility(View.INVISIBLE);

        //Wind speed text input
        vindHastighedEditTxt = view.findViewById(R.id.vindhastighed_edittext);
        vindHastighedEditTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                logVM.setWindSpeed(vindHastighedEditTxt.getText().length() > 0 ?
                        Integer.parseInt(vindHastighedEditTxt.getText().toString()) : -1);
            }
        });
        updateViewInfo();

        return view;
    }

    @Override
    public void onClick(View v) {
        if(v == vindNordBtn) directionLogic(vindNordBtn.getText().toString(), vindSydBtn.getText().toString());
        else if(v == vindOestBtn) directionLogic(vindOestBtn.getText().toString(), vindVestBtn.getText().toString());
        else if(v == vindSydBtn) directionLogic(vindSydBtn.getText().toString(), vindNordBtn.getText().toString());
        else if(v == vindVestBtn) directionLogic(vindVestBtn.getText().toString(), vindOestBtn.getText().toString());

        if (v == vindretning_delete) { //Resets the user input
            logVM.setWindDirection("");
            SwapViewsTextHelper.centerText(vindretningTxtLeft, vindretningTxtCenter);
            vindretning_delete.setVisibility(View.INVISIBLE);
        }
        updateViewInfo();
    }

    /**
     * Updates the buttons and text of the view elements
     */
    private void updateViewInfo() {
        vindretning_input.setText(logVM.getWindDirection());
        if (!vindretning_input.getText().toString().equals("")) {
            SwapViewsTextHelper.leftalignText(vindretningTxtLeft, vindretningTxtCenter);
            vindretning_delete.setVisibility(View.VISIBLE);
        }
        vindHastighedEditTxt.setText(logVM.getWindSpeed() >= 0 ? Integer.toString(logVM.getWindSpeed()) : "");
    }

    /**
     *  Adds the direction to the user input, if possible after the rules of N/E/S/W on a compass
     *
     * @param btnDirection      Button pressed
     * @param counterDirection  Not applicable if this Button is pressed
     */
    private void directionLogic(String btnDirection, String counterDirection) {
        if(!logVM.getWindDirection().contains(counterDirection)) { //Control input for going opposite ways
            switch(logVM.getWindDirection().length()) {
                case 0:
                    logVM.setWindDirection(btnDirection); //Input first direction
                    vindretning_input.setText(logVM.getWindDirection());
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
        vindretning_delete.setVisibility(View.VISIBLE);
    }
}