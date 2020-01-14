package com.example.skibslogapp.view.opretLog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.skibslogapp.R;

public class Note extends Fragment {
    private EditText noteEditText;
    private textFieldEntered listener;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_opret_log_note, container, false);

        noteEditText = view.findViewById(R.id.opretLogNoteTxt);
        noteEditText.setOnFocusChangeListener((v, hasFocus) -> {
            listener.enteredTxtField();
        });

        return view;
    }

    public void setListener(textFieldEntered listener) {
        this.listener = listener;
    }

    public String getNoteText() {
        return noteEditText.getText().toString();
    }

    public interface textFieldEntered {
        void enteredTxtField();
    }
}