package com.example.skibslogapp.view.opretLog;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.skibslogapp.R;

public class LogCourse_frag extends Fragment {
    EditText kursEditText;
    LogViewModel logVM;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_opret_log_course, container, false);
        logVM = ViewModelProviders.of(getActivity()).get(LogViewModel.class);

        kursEditText = view.findViewById(R.id.kursEditText);
        kursEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                controlCourseInput();
            }
        });
        kursEditText.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE) {
                    controlCourseInput();
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                return true;
            }
        });

        return view;
    }

    private void controlCourseInput() {
        String s = kursEditText.getText().toString();
        if(s.equals("") || Integer.parseInt(s) > 360) {
            logVM.setCourse(-1);
            kursEditText.setText("");
        }
        else  {
            if(s.length() == 1) s = "00".concat(s);
            else if(s.length() == 2) s = "0".concat(s);
            logVM.setCourse(Integer.parseInt(s));
            kursEditText.setText(s);
        }
    }
}