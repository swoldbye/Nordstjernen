package com.example.skibslogapp.view.opretLog;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.skibslogapp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class LogTime_frag extends Fragment implements View.OnClickListener {
    String systemTime;
    Button resetTimeButton;
    EditText editTime;
    LogViewModel logVM;
    private int timeStringLengthBefore = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_opret_log_time, container, false);
        logVM = ViewModelProviders.of(getActivity()).get(LogViewModel.class);

        editTime = view.findViewById(R.id.editTime);
        resetTimeButton = view.findViewById(R.id.resetTimeButton);
        editTime.setOnClickListener(this);
        resetTimeButton.setOnClickListener(this);

        final Handler handler =new Handler();
        final Runnable r = new Runnable() {
            public void run() {
                if(!editTime.hasFocus()) {
                    handler.postDelayed(this, 1000);
                    String simpleDate3 = new SimpleDateFormat("kk:mm").format(Calendar.getInstance().getTime());
                    if(editTime.getText().length() == 0) logVM.setTime(simpleDate3); //Updates time of LogViewModel if nothing is entered
                    editTime.setHint(simpleDate3);
                }
            }
        };
        handler.postDelayed(r, 0000);

        editTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                timeControl(hasFocus);
            }
        });

        editTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int timeStringLengthAfter = editTime.getText().toString().length();
                if(timeStringLengthAfter > timeStringLengthBefore && timeStringLengthAfter == 2) { //Insert colon
                    editTime.setText(getString(R.string.time_colon, editTime.getText()));
                    editTime.setSelection(3);
                }
                timeStringLengthBefore = timeStringLengthAfter;
            }
        });

        return view;
    }

    private void timeControl(Boolean hasFocus) {
        String timeString;
        if (hasFocus) editTime.setHint("");
        else editTime.setHint(new SimpleDateFormat("kk:mm").format(Calendar.getInstance().getTime()));

        String time = editTime.getText().toString();
        //Accept 3 digit input
        if (time.length() == 1) {
            if(Integer.parseInt(time.substring(0,1)) < 10) {
                timeString = time = "0".concat(time.substring(0,1).concat(":00"));
                logVM.setTime(timeString);
                editTime.setText(timeString);
            }
        }
        if(time.length() == 3) {
            if (Integer.parseInt(time.substring(0,2)) < 24) {
                timeString = time = time.concat("00");
                logVM.setTime(timeString);
                editTime.setText(timeString);
            }
        }
        if(time.length() == 4) {
            if(Integer.parseInt(time.substring(0,1)) < 10) {
                timeString = time = "0".concat(time.substring(0,1).concat(":".concat(time.substring(1,2).concat(time.substring(3,4)))));
                logVM.setTime(timeString);
                editTime.setText(timeString);
            }
            else if(Integer.parseInt(time.substring(0,2)) < 24) {
                timeString = time = time.substring(0,2).concat(":").concat(time.substring(3,4).concat("0"));
                logVM.setTime(timeString);
                editTime.setText(timeString);
            }
        }
        //Control for correct input
        if(time.length() != 5 || time.lastIndexOf(":") != time.indexOf(":") //Control of string
                || Integer.parseInt(time.substring(0,2)) > 23 || Integer.parseInt(time.substring(3, 5)) > 59) { //Control of numbers
            logVM.setTime(editTime.getHint().toString());
            editTime.setText("");
        }
    }

    @Override
    public void onClick(View v) {
        if(v == resetTimeButton){
            logVM.setTime(new SimpleDateFormat("kk:mm").format(Calendar.getInstance().getTime()));
            editTime.setText("");
        }
    }
}
