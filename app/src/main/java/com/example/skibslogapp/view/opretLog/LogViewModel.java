package com.example.skibslogapp.view.opretLog;

import androidx.lifecycle.ViewModel;

public class LogViewModel extends ViewModel {
    private String noteTxt;

    public String getNoteTxt() {
        return noteTxt;
    }
    public void setNoteTxt(String noteTxt) {
        this.noteTxt = noteTxt;
    }
}
