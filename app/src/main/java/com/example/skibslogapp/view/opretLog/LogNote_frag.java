package com.example.skibslogapp.view.opretLog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.skibslogapp.R;

public class LogNote_frag extends Fragment {
    private EditText noteEditText;
    private textFieldEntered listener;
    private LogViewModel logVM;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_opret_log_note, container, false);
        logVM = ViewModelProviders.of(getActivity()).get(LogViewModel.class);

        noteEditText = view.findViewById(R.id.opretLogNoteTxt);
        noteEditText.setOnFocusChangeListener((v, hasFocus) -> {
            listener.enteredTxtField();
            logVM.setNoteTxt(noteEditText.getText().toString());
        });

        return view;
    }

    public void setListener(textFieldEntered listener) {
        this.listener = listener;
    }

    public interface textFieldEntered {
        void enteredTxtField();
    }
}