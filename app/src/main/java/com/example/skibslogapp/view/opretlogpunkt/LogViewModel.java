package com.example.skibslogapp.view.opretlogpunkt;

import androidx.lifecycle.ViewModel;

import com.example.skibslogapp.model.Logpunkt;

public class LogViewModel extends ViewModel {
    private String time,
            windDirection,
            waterCurrentDirection,
            sailPosition,
            sails, orientation,
            noteTxt;
    private int windSpeed,
            waterCurrentSpeed,
            currRowers,
            course;

    public LogViewModel() {
        resetValues();
    }

    public void resetValues() {
        time = "";
        windDirection = "";
        windSpeed = -1;
        waterCurrentDirection = "";
        waterCurrentSpeed = -1;
        sailPosition = "";
        currRowers = -1;
        sails = "";
        orientation = "";
        course = -1;
        noteTxt = "";
    }

    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }

    public String getWindDirection() {
        return windDirection;
    }
    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public int getWindSpeed() {
        return windSpeed;
    }
    public void setWindSpeed(int windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getWaterCurrentDirection() {
        return this.waterCurrentDirection;
    }
    public void setWaterCurrentDirection(String waterCurrentDirection) {
        this.waterCurrentDirection = waterCurrentDirection;
    }

    public int getWaterCurrentSpeed() {
        return waterCurrentSpeed;
    }
    public void setWaterCurrentSpeed(int waterCurrentSpeed) {
        this.waterCurrentSpeed = waterCurrentSpeed;
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
    public void setCurrRowers(int currRowers) {
        this.currRowers = currRowers;
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

    public String getNoteTxt() {
        return noteTxt;
    }
    public void setNoteTxt(String noteTxt) {
        this.noteTxt = noteTxt;
    }

    public void prepareEditableCopy(Logpunkt logpunkt) {
        //Does not take time or position, as that information is not editable.
        this.windDirection = logpunkt.getVindretning();
        this.windSpeed = logpunkt.getVindhastighed();
        this.waterCurrentDirection = logpunkt.getStroemRetning();
        this.waterCurrentSpeed = logpunkt.getStroemhastighed();
        this.sailPosition = logpunkt.getSejlstilling();
        this.currRowers = logpunkt.getRoere();
        this.sails = logpunkt.getSejlfoering();
        this.course = logpunkt.getKurs();
        this.noteTxt = logpunkt.getNote();

    }
}