package com.example.skibslogapp.view.redigerlogpunkt;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.skibslogapp.R;
import com.example.skibslogapp.model.Logpunkt;

public class RedigerLogpunkt_frag extends Fragment {
    private TextView    time,
                        latitude,
                        longitude,
                        windDirection,
                        windSpeed,
                        currentDirection,
                        currentSpeed,
                        sailsOrRowers,
                        sails,
                        course,
                        note;
    private Logpunkt logpunkt;
    ConstraintLayout    timeConta,
                        positionConta,
                        windConta,
                        waterConta,
                        sailsOrRowersConta,
                        sailsConta,
                        courseConta,
                        noteConta;

    public RedigerLogpunkt_frag(Logpunkt logpunkt){
        this.logpunkt = logpunkt;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rediger_logpunkt, container, false);
        ((TextView) view.findViewById(R.id.test_text)).setText(logpunkt.toString());

        time = view.findViewById(R.id.editLogTimeInfo);
        latitude = view.findViewById(R.id.editLogLatitude);
        longitude = view.findViewById(R.id.editLogLongitude);
        windDirection = view.findViewById(R.id.editLogWindDirectionInfo);
        windSpeed = view.findViewById(R.id.editLogWindSpeedInfo);
        currentDirection = view.findViewById(R.id.editLogWaterCurrentDirectionInfo);
        currentSpeed = view.findViewById(R.id.editLogWaterCurrentSpeedInfo);
        sailsOrRowers = view.findViewById(R.id.editLogSailsOrRowersInfo);
        sails = view.findViewById(R.id.editLogSailsPositionInfo);
        course = view.findViewById(R.id.editLogCourseInfo);
        note = view.findViewById(R.id.editLogNoteInfo);

        timeConta = view.findViewById(R.id.editLogTimeContainer);
        positionConta = view.findViewById(R.id.editLogPositionContainer);
        windConta = view.findViewById(R.id.editLogWindContainer);
        waterConta = view.findViewById(R.id.editLogWaterCurrentContainer);
        sailsOrRowersConta = view.findViewById(R.id.editLogSailsOrRowersContainer);
        sailsConta = view.findViewById(R.id.editLogSailsPositionContainer);
        courseConta = view.findViewById(R.id.editLogCourseContainer);
        noteConta = view.findViewById(R.id.editLogNoteContainer);

        updateInformation();

        return view;
    }

    private void updateInformation() {
        time.setText(logpunkt.getTimeString() != null && !logpunkt.getTimeString().equals("") ? logpunkt.getTimeString() : "-");
        latitude.setText(logpunkt.getPosition() != null && !logpunkt.getPosition().getBreddegradString().equals("") ?
                logpunkt.getPosition().getBreddegradString() : "-");
        longitude.setText(logpunkt.getPosition() != null && !logpunkt.getPosition().getLaengdegradString().equals("") ?
                logpunkt.getPosition().getLaengdegradString() : "-");
        windDirection.setText(logpunkt.getVindretning() != null && !logpunkt.getVindretning().equals("") ? logpunkt.getVindretning() : "-");
        windSpeed.setText(logpunkt.getVindhastighed() >= 0 ? String.valueOf(logpunkt.getVindhastighed()) : "-");
        currentDirection.setText(logpunkt.getStroemRetning() != null && !logpunkt.getStroemRetning().equals("") ? logpunkt.getStroemRetning() : "-");
        currentSpeed.setText(logpunkt.getStroemhastighed() >= 0 ? String.valueOf(logpunkt.getStroemhastighed()) : "-");
        String sailsOrRowersString = logpunkt.getSejlstillingString() != null && !logpunkt.getSejlstillingString().equals("") ?
                logpunkt.getSejlstillingString() : "";
        sailsOrRowersString.concat(logpunkt.getRoere() >= 0 ? Integer.toString(logpunkt.getRoere()) : "");
        sailsOrRowers.setText(sailsOrRowersString.length() != 0 ? sailsOrRowersString : "-");
        sails.setText(logpunkt.getSejloeringString() != null && !logpunkt.getSejloeringString().equals("") ? logpunkt.getSejloeringString() : "-");
        course.setText(logpunkt.getKursString() != null && !logpunkt.getKursString().equals("") ? logpunkt.getKursString() : "-");
        note.setText(logpunkt.getNote() != null && !logpunkt.getNote().equals("") ? logpunkt.getNote() : "-");
    }
}