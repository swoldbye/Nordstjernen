package com.example.skibslogapp.view.opretLog;

import androidx.lifecycle.ViewModel;

public class LogViewModel extends ViewModel {
    private String noteTxt,
            windDirection, windSpeed;

    public LogViewModel() {
        reset();
    }

    public void reset() {
        noteTxt = "";
        windDirection = "";
    }
    public String getNoteTxt() {
        return noteTxt;
    }

    public void setNoteTxt(String noteTxt) {
        this.noteTxt = noteTxt;
    }
    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public String getWindSpeed() {
        return windSpeed;
    }
    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }
}
