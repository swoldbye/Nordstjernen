package com.example.skibslogapp.view.logpunktinput;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.skibslogapp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Fragment to set time of LogViewModel automatically or by user input. With reset button to switch between manual and automatic input.
 */
public class LogTime_frag extends Fragment implements View.OnClickListener {
    private Button resetTimeButton;
    private NumberPicker hoursPicker, minutesPicker;
    private LogViewModel logVM;
    private boolean userInput = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.logpunktinput_time, container, false);
        logVM = ViewModelProviders.of(getActivity()).get(LogViewModel.class);   //Only used in other fragments, so higher ranking problem if NullPointed by getActivity()

        //NumberPickers for hours and minutes
        hoursPicker = view.findViewById(R.id.logTimeHours);
        minutesPicker = view.findViewById(R.id.logTimeMinutes);
        hoursPicker.setMaxValue(23);
        minutesPicker.setMaxValue(59);
        NumberPicker.OnValueChangeListener listener = (picker, oldVal, newVal) -> userInput = true;
        hoursPicker.setOnValueChangedListener(listener);
        minutesPicker.setOnValueChangedListener(listener);

        //Button to reset time
        resetTimeButton = view.findViewById(R.id.resetTimeButton);
        resetTimeButton.setOnClickListener(this);

        //Thread to automatically set the time
        final Handler handler =new Handler();
        final Runnable r = new Runnable() {
            public void run() {
                handler.postDelayed(this, 1000);
                String simpleDate3 = new SimpleDateFormat("kk:mm").format(Calendar.getInstance().getTime());
                if(!userInput) { //If not set by the user
                    hoursPicker.setValue(Integer.parseInt(simpleDate3.substring(0,2)));
                    minutesPicker.setValue(Integer.parseInt(simpleDate3.substring(3,5)));
                    setLogVMTime(hoursPicker.getValue(), minutesPicker.getValue());
                }
            }
        };
        handler.postDelayed(r, 0000);

        return view;
    }

    /**
     * Sets the time of the current LogViewModel
     *
     * @param hours
     * @param minutes
     */
    private void setLogVMTime(int hours, int minutes) {
        logVM.setTime(hours+":"+minutes);
    }

    @Override
    public void onClick(View v) {
        if(v == resetTimeButton){ //Reset the time to be updated automatically
            userInput = false;
            logVM.setTime(new SimpleDateFormat("kk:mm").format(Calendar.getInstance().getTime()));
            hoursPicker.setValue(Integer.parseInt(logVM.getTime().substring(0,2)));
            minutesPicker.setValue(Integer.parseInt(logVM.getTime().substring(3,5)));
        }
    }
}