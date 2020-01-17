package com.example.skibslogapp.view;


import android.content.Context;
import android.os.Bundle;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.skibslogapp.datalayer.local.LogpunktDAO;
import com.example.skibslogapp.model.GlobalTogt;
import com.example.skibslogapp.model.Logpunkt;
import com.example.skibslogapp.R;
import com.example.skibslogapp.view.opretLog.LogNote_frag;
import com.example.skibslogapp.view.opretLog.LogViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class OpretLog_frag extends Fragment implements View.OnClickListener {
    private boolean mobIsDown = true; //Starts with the "Man Over Bord" btn in the buttom of the page.



    View mob;
    Button opretButton;
    LogViewModel logVM;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_opret_log, container, false);
        logVM = ViewModelProviders.of(getActivity()).get(LogViewModel.class);
        logVM.reset();

                //Tidsslet





        //Opret Post
        opretButton = (Button) view.findViewById(R.id.opretButton);

        //LogNote_frag
//        noteEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                toggleMOBPosition(hasFocus);
//            }
//        });
//        noteFrag = new LogNote_frag();
        //Reference to implement listener
        LogNote_frag noteFrag = (LogNote_frag) getChildFragmentManager().findFragmentById(R.id.fragment_opretLog_note);
        noteFrag.setListener(this::toggleMOBPosition);

        //Mand over bord
        mob = view.findViewById(R.id.mob_button);

        mob.setOnClickListener(this);
        opretButton.setOnClickListener(this);

        return view;
    }

    /**
     * Clears the focus when clicking the "Done" or "Next" button on the keyboard
     */
    private TextView.OnEditorActionListener clearFocusOnNextClick = (v, keyCode, event) -> {
        if(keyCode == EditorInfo.IME_ACTION_DONE || keyCode == EditorInfo.IME_ACTION_NEXT) {
            clearFocusOnDone(v);
        }
        return true;
    };

    private void clearFocusOnDone(TextView v) {
        v.clearFocus(); //Clears focus, which cascade into it resetting through OnFocusChange()
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
//        toggleMOBPosition(getView().findViewById(R.id.noteEditText).hasFocus());
//        toggleMOBPosition();
    }

    @Override
    public void onClick(View v) {

        LogOversigt_frag logOversigt_frag;
        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;


        if (v == opretButton || v == mob) {
            createLogpunkt();
        }
    }

    private void createLogpunkt() {

        // Fetching time ---------------------------------------------------------------
        String timeStr = logVM.getTime();

        // Getting calender instance
        Calendar calendar= Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        // Setting minutes and hour
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeStr.substring(0, 2)));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeStr.substring(3, 5)));

        // Create Logpunkt from time in calendar
        Logpunkt logpunkt = new Logpunkt( new Date(calendar.getTimeInMillis()) );
        logpunkt.setVindretning( logVM.getWindDirection() );
        //TODO Vindhastighed
        logpunkt.setStroem( logVM.getWaterCurrentDirection() );
        //TODO Strømhastighed

        logpunkt.setKurs(logVM.getCourse());
        logpunkt.setSejlfoering( logVM.getSails().equals("") ? logVM.getSails().concat(logVM.getOrientation()) : logVM.getSails().concat("-" + logVM.getOrientation() ));
        logpunkt.setSejlstilling( logVM.getSailPosition() );
        logpunkt.setRoere(logVM.getCurrRowers());

        logpunkt.setNote( logVM.getNoteTxt() );

        LogpunktDAO logpunktDAO = new LogpunktDAO(getContext());
        logpunktDAO.addLogpunkt(GlobalTogt.getEtape(getContext()), logpunkt);

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .remove(this)
                .commit();
    }

    private void toggleMOBPosition() {
        FrameLayout mob_container = getView().findViewById(R.id.mob_container);
        CoordinatorLayout.LayoutParams params = new CoordinatorLayout.LayoutParams(mob_container.getLayoutParams());
        if(mobIsDown) {
            params.gravity = Gravity.TOP;
        } else {
            params.gravity = Gravity.BOTTOM;
        }
        mobIsDown = !mobIsDown;
        mob_container.setLayoutParams(params);
    }
}