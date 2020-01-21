package com.example.skibslogapp.view.redigerlogpunkt;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.example.skibslogapp.R;
import com.example.skibslogapp.model.Logpunkt;
import com.example.skibslogapp.view.opretLog.LogCourse_frag;
import com.example.skibslogapp.view.opretLog.LogNote_frag;
import com.example.skibslogapp.view.opretLog.LogSailPosAndRowers_frag;
import com.example.skibslogapp.view.opretLog.LogSails_frag;
import com.example.skibslogapp.view.opretLog.LogViewModel;
import com.example.skibslogapp.view.opretLog.LogWaterCurrent_frag;
import com.example.skibslogapp.view.opretLog.LogWind_frag;

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
    private LogViewModel logVM;
    private View vv; //TODO DELETE THIS AND USAGES - TESTING ONLY

    public RedigerLogpunkt_frag(Logpunkt logpunkt){
        this.logpunkt = logpunkt;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rediger_logpunkt, container, false);
        vv = view;
        updatetopthing();
        logVM = ViewModelProviders.of(getActivity()).get(LogViewModel.class);
        logVM.resetValues();

        logVM.prepareEditableCopy(logpunkt);

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

        ConstraintLayout
                windConta,
                waterConta,
                sailsOrRowersConta,
                sailsConta,
                courseConta,
                noteConta;

        windConta = view.findViewById(R.id.editLogWindContainer);
        waterConta = view.findViewById(R.id.editLogWaterCurrentContainer);
        sailsOrRowersConta = view.findViewById(R.id.editLogSailsOrRowersContainer);
        sailsConta = view.findViewById(R.id.editLogSailsPositionContainer);
        courseConta = view.findViewById(R.id.editLogCourseContainer);
        noteConta = view.findViewById(R.id.editLogNoteContainer);

        windConta.setOnClickListener(v -> openFragmentDialog(R.id.fragment_opretLog_wind, new LogWind_frag()));
        waterConta.setOnClickListener(v -> openFragmentDialog(R.id.fragment_opretLog_waterCurrent, new LogWaterCurrent_frag()));
        sailsOrRowersConta.setOnClickListener(v -> openFragmentDialog(R.id.fragment_opretLog_sailsAndRowers, new LogSailPosAndRowers_frag()));
        sailsConta.setOnClickListener(v -> openFragmentDialog(R.id.fragment_opretLog_sails, new LogSails_frag()));
        courseConta.setOnClickListener(v -> openFragmentDialog(R.id.fragment_opretLog_course, new LogCourse_frag()));
        noteConta.setOnClickListener(v -> openFragmentDialog(R.id.fragment_opretLog_note, new LogNote_frag()));

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
        sailsOrRowersString = sailsOrRowersString.concat(logpunkt.getRoere() >= 0 ? Integer.toString(logpunkt.getRoere()) : "");
        sailsOrRowers.setText(sailsOrRowersString.length() != 0 ? sailsOrRowersString : "-");
        sails.setText(logpunkt.getSejloeringString() != null && !logpunkt.getSejloeringString().equals("") ? logpunkt.getSejloeringString() : "-");
        course.setText(logpunkt.getKursString() != null && !logpunkt.getKursString().equals("") ? logpunkt.getKursString() : "-");
        note.setText(logpunkt.getNote() != null && !logpunkt.getNote().equals("") ? logpunkt.getNote() : "-");

        updatetopthing();
    }

    private void updatetopthing() {
       // ((TextView) vv.findViewById(R.id.test_text)).setText(logpunkt.toString());
    }

    private void openFragmentDialog(int fragID, Fragment frag) {
        System.out.println("prik");
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();

        EditDialogFragment edf = EditDialogFragment.newInstance(frag);
        edf.show(ft, "edf");
    }

    private void cancel() {
        System.out.println("Cancel called");
        logVM.prepareEditableCopy(logpunkt); //Resets all the information
        updateInformation();
    }
    private void save() {
        logpunkt.setInformation(logVM);
        updateInformation();
    }

    /**
     * Dialog box to show fragment to edit information
     */
    public static class EditDialogFragment extends DialogFragment {
        private Fragment frag;

        private EditDialogFragment(Fragment frag) {
            this.frag = frag;
        }

        public static EditDialogFragment newInstance(Fragment frag) {
            System.out.println("newInstance initiated");
            Bundle args = new Bundle();
            EditDialogFragment fragment = new EditDialogFragment(frag);
            fragment.setArguments(args);

            return fragment;
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            System.out.println("onCreateView initiated");
            View view = inflater.inflate(R.layout.fragment_rediger_logpunkt_dialogfragment, container);
            return view;
        }

        @Override
        public void onStart() {
            super.onStart();

            TextView cancelTxt = getView().findViewById(R.id.editLogpunktDialogCancel);
            cancelTxt.setOnClickListener(v -> cancelEdit());
            Button saveBtn = getView().findViewById(R.id.editLogpunktDialogSave);
            saveBtn.setOnClickListener(v -> saveEdit());

            Dialog dialog = getDialog();
            if (dialog != null) {
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            System.out.println("onViewCreated initiated");
            FragmentManager fragmentManager = getChildFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.editLogpunktFragContainer, frag);
            fragmentTransaction.commit();
        }

        @Override
        public void onCancel(@NonNull DialogInterface dialog) {
            RedigerLogpunkt_frag frag = (RedigerLogpunkt_frag) getParentFragment();
            if(frag != null){
                System.out.println("Parent found, cancel information");
                frag.cancel();
            }
        }

        private void cancelEdit() {
            getDialog().cancel();
        }

        private void saveEdit() {
            RedigerLogpunkt_frag frag = (RedigerLogpunkt_frag) getParentFragment();
            dismiss();
            if(frag != null){
                System.out.println("Parent found, save information");
                frag.save();
            }
        }
    }
}