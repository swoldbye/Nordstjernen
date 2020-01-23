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
import com.example.skibslogapp.datalayer.local.LogpunktDAO;
import com.example.skibslogapp.utility.DateToString;
import com.example.skibslogapp.model.Logpunkt;
import com.example.skibslogapp.view.logpunktinput.LogCourse_frag;
import com.example.skibslogapp.view.logpunktinput.LogNote_frag;
import com.example.skibslogapp.view.logpunktinput.LogSailPosAndRowers_frag;
import com.example.skibslogapp.view.logpunktinput.LogSails_frag;
import com.example.skibslogapp.view.logpunktinput.LogViewModel;
import com.example.skibslogapp.view.logpunktinput.LogWaterCurrent_frag;
import com.example.skibslogapp.view.logpunktinput.LogWind_frag;

/**
 * Fragment to edit information of a selected 'logpunkt'
 */
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

    public RedigerLogpunkt_frag(Logpunkt logpunkt){
        this.logpunkt = logpunkt;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.redigerlogpunkt_frag, container, false);
        logVM = ViewModelProviders.of(getActivity()).get(LogViewModel.class);
        logVM.resetValues();

        logVM.prepareEditableCopy(logpunkt);

        //TextView
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

        //Containers of textviews
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

        windConta.setOnClickListener(v -> openFragmentDialog(new LogWind_frag()));
        waterConta.setOnClickListener(v -> openFragmentDialog(new LogWaterCurrent_frag()));
        sailsOrRowersConta.setOnClickListener(v -> openFragmentDialog(new LogSailPosAndRowers_frag()));
        sailsConta.setOnClickListener(v -> openFragmentDialog(new LogSails_frag()));
        courseConta.setOnClickListener(v -> openFragmentDialog(new LogCourse_frag()));
        noteConta.setOnClickListener(v -> openFragmentDialog(new LogNote_frag()));

        updatePageInformation();
        return view;
    }

    /**
     * Updates the information of the "rediger logpunkt"-page
     */
    private void updatePageInformation() {
        time.setText(DateToString.full(logpunkt.getDate()));
        latitude.setText(logpunkt.getPosition() != null && !logpunkt.getPosition().getBreddegradString().equals("") ?
                logpunkt.getPosition().getBreddegradString() : "-");
        longitude.setText(logpunkt.getPosition() != null && !logpunkt.getPosition().getLaengdegradString().equals("") ?
                logpunkt.getPosition().getLaengdegradString() : "-");
        windDirection.setText(logpunkt.getVindretning() != null && !logpunkt.getVindretning().equals("") ? logpunkt.getVindretning() : "-");
        windSpeed.setText(logpunkt.getVindhastighed() >= 0 ? String.valueOf(logpunkt.getVindhastighed()) : "-");
        currentDirection.setText(logpunkt.getStroemRetning() != null && !logpunkt.getStroemRetning().equals("") ? logpunkt.getStroemRetning() : "-");
        currentSpeed.setText(logpunkt.getStroemhastighed() >= 0 ? String.valueOf(logpunkt.getStroemhastighed()) : "-");
        String sailsOrRowersString = logpunkt.getSejlstilling() != null && !logpunkt.getSejlstilling().equals("") ?
                logpunkt.getSejlstilling() : "";
        sailsOrRowersString = sailsOrRowersString.concat(logpunkt.getRoere() >= 0 ? Integer.toString(logpunkt.getRoere()) : "");
        sailsOrRowers.setText(sailsOrRowersString.length() != 0 ? sailsOrRowersString : "-");
        sails.setText(logpunkt.getSejlfoering() != null && !logpunkt.getSejlfoering().equals("") ? logpunkt.getSejlfoering() : "-");
        course.setText(logpunkt.getKursString() != null && !logpunkt.getKursString().equals("") ? logpunkt.getKursString() : "-");
        note.setText(logpunkt.getNote() != null && !logpunkt.getNote().equals("") ? logpunkt.getNote() : "-");
    }

    /**
     * DialogFragment to open so user can edit a chosen information
     *
     * @param frag  Fragment from logpunktinput folder to edit selected information
     */
    private void openFragmentDialog(Fragment frag) {
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        EditDialogFragment edf = EditDialogFragment.newInstance(frag);
        edf.show(ft, "edf");
    }

    /**
     * If chosen not to update the current information input
     */
    private void cancel() {
        logVM.prepareEditableCopy(logpunkt); //Resets all the information
        updatePageInformation();
    }

    /**
     * If chosen to update the current information input
     */
    private void save() {
        logpunkt.setInformation(logVM);
        updatePageInformation();
        LogpunktDAO logDAO = new LogpunktDAO(getContext());
        logDAO.updateLogpunkt(logpunkt);
    }

    /**
     * Dialog box to show fragment to edit information
     */
    public static class EditDialogFragment extends DialogFragment {
        private Fragment frag;
        private EditDialogFragment(Fragment frag) {
            this.frag = frag;
        }

        private static EditDialogFragment newInstance(Fragment frag) {
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
            return inflater.inflate(R.layout.redigerlogpunkt_dialog, container);
        }

        @Override
        public void onStart() {
            super.onStart();

            //Set text views and button information
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
            FragmentManager fragmentManager = getChildFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.editLogpunktFragContainer, frag);
            fragmentTransaction.commit();
        }

        @Override
        public void onCancel(@NonNull DialogInterface dialog) {
            RedigerLogpunkt_frag frag = (RedigerLogpunkt_frag) getParentFragment();
            if(frag != null){
                frag.cancel();
            }
        }

        /**
         * Cancels dialog
         */
        private void cancelEdit() {
            getDialog().cancel();
        }

        /**
         * Call to save information
         */
        private void saveEdit() {
            RedigerLogpunkt_frag frag = (RedigerLogpunkt_frag) getParentFragment();
            dismiss();
            if(frag != null){
                frag.save();
            }
        }
    }
}