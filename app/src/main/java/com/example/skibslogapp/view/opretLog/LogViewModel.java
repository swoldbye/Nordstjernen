package com.example.skibslogapp.view.opretLog;

import androidx.lifecycle.ViewModel;

public class LogViewModel extends ViewModel {
    private String noteTxt,
            windDirection, windSpeed,
            waterCurrentDirection,
            sailPosition,
            sails, orientation;
    private int currRowers,
            course;

    public LogViewModel() {
        reset();
    }

    public void reset() {
        noteTxt = "";
        windDirection = "";
        windSpeed = "";
        waterCurrentDirection = "";
        sailPosition = "";
        sails = "";
        orientation = "";
        currRowers = 0;
        course = -1;
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

    public String getWaterCurrentDirection() {
        return this.waterCurrentDirection;
    }
    public void setWaterCurrentDirection(String waterCurrentDirection) {
        this.waterCurrentDirection = waterCurrentDirection;
    }

    public String getSailPosition() {
        return sailPosition;
    }
    public void setSailPosition(String sailPosition) {
        this.sailPosition = sailPosition;
    }

    public int getCurrRowers() {
        return currRowers;
    }
    public void setCurrRowers(String currRowers) {
        this.currRowers = Integer.parseInt(currRowers);
    }

    public String getSails() {
        return sails;
    }
    public void setSails(String sails) {
        this.sails = sails;
    }

    public String getOrientation() {
        return orientation;
    }
    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    public int getCourse() {
        return course;
    }

    public void setCourse(int course) {
        this.course = course;
    }
}
