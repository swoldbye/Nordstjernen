package com.example.skibslogapp.view.logpunktinput;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.skibslogapp.R;

/**
 * Fragment to set course (measured in degrees) with user inputs.
 *  */
public class LogCourse_frag extends Fragment {
    private EditText kursEditText;
    private LogViewModel logVM;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.logpunktinput_kurs, container, false);
        logVM = ViewModelProviders.of(getActivity()).get(LogViewModel.class);

        kursEditText = view.findViewById(R.id.kurs_edittext);
        kursEditText.setOnFocusChangeListener((v, hasFocus) -> controlCourseInput());
        kursEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                controlCourseInput();
            }
            return true;
        });

        kursEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (kursEditText.getText().length() > 0 && Integer.parseInt(kursEditText.getText().toString()) > 360) {
                    kursEditText.setText("360");
                    kursEditText.setSelection(kursEditText.getText().length());
                }
                logVM.setCourse(kursEditText.getText().length() > 0 ?
                        Integer.parseInt(kursEditText.getText().toString()) : -1);
            }
        });

        updateViewInfo();

        return view;
    }

    private void updateViewInfo() {
        kursEditText.setText(logVM.getCourse() >= 0 ? Integer.toString(logVM.getCourse()) : "");
        controlCourseInput();
    }

    private void controlCourseInput() {
        String s = kursEditText.getText().toString();
        if (s.equals("") || Integer.parseInt(s) > 360) {
            kursEditText.setText("");
        } else {
            if (s.length() == 1) s = "00".concat(s);
            else if (s.length() == 2) s = "0".concat(s);
            kursEditText.setText(s);
        }
    }
}