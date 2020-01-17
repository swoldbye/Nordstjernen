package com.example.skibslogapp.view;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
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
import android.view.KeyEvent;
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
import com.example.skibslogapp.view.opretLog.LogWind_frag;
import com.example.skibslogapp.view.utility.KingButton;
import com.example.skibslogapp.view.utility.ToggleViewList;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class OpretLog_frag extends Fragment implements View.OnClickListener {

    private boolean mobIsDown = true;

    private int timeStringLengthBefore = 0;
    private String finalSejlføring = "";
    private String styrbordEllerBagbord = "";

    //button colors:
    int basicColor;
    int standOutColor;

    Button resetTimeButton;
    KingButton fBtn, øBtn, n1Btn, n2Btn, n3Btn;
    EditText kursEditText, editTime;
    View mob;
    ToggleButtonList hals_Buttons;
    Button opretButton;
    LogViewModel logVM;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_opret_log, container, false);
        logVM = ViewModelProviders.of(getActivity()).get(LogViewModel.class);
        logVM.reset();

                //Tidsslet
                editTime = (EditText) view.findViewById(R.id.editTime);
        resetTimeButton = (Button) view.findViewById(R.id.resetTimeButton);




        //Kurs
        kursEditText = (EditText) view.findViewById(R.id.kursEditText);




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
        noteFrag.setListener(() -> {
            toggleMOBPosition();
        });

        //Mand over bord
        mob = view.findViewById(R.id.mob_button);

        hals_Buttons = new ToggleButtonList(
            view.findViewById(R.id.hals_bagbord_btn),
            view.findViewById(R.id.hals_styrbord_btn)
        );


        fBtn = view.findViewById(R.id.fButton);
        øBtn = view.findViewById(R.id.øButton);
        n1Btn = view.findViewById(R.id.n1Button);
        n2Btn = view.findViewById(R.id.n2Button);
        n3Btn = view.findViewById(R.id.n3Button);
        fBtn.createRelation(øBtn);
        n1Btn.createRelation(n2Btn);
        n1Btn.createRelation(n3Btn);
        n2Btn.createRelation(n3Btn);




        mob.setOnClickListener(this);
        opretButton.setOnClickListener(this);

        editTime.setOnClickListener(this);
        resetTimeButton.setOnClickListener(this);

        fBtn.setOnClickListener(this);
        øBtn.setOnClickListener(this);
        n1Btn.setOnClickListener(this);
        n2Btn.setOnClickListener(this);
        n3Btn.setOnClickListener(this);




        basicColor = getResources().getColor(R.color.grey);
        standOutColor = getResources().getColor(R.color.colorPrimary);

        //On Editor Listeners
//        antalRoereEditText.setOnEditorActionListener(clearFocusOnNextClick);
//        kursEditText.setOnEditorActionListener(clearFocusOnNextClick);
//        editTime.setOnEditorActionListener(clearFocusOnNextClick);
//        strømNingsretningEditText.setOnEditorActionListener(clearFocusOnNextClick);

        final Handler handler =new Handler();
        final Runnable r = new Runnable() {
            public void run() {
                if(!editTime.hasFocus()) {
                    handler.postDelayed(this, 1000);
                    String simpleDate3 = new SimpleDateFormat("kk:mm").format(Calendar.getInstance().getTime());
                    editTime.setHint(simpleDate3);
                }
            }
        };
        handler.postDelayed(r, 0000);

        editTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) editTime.setHint("");
                else editTime.setHint(new SimpleDateFormat("kk:mm").format(Calendar.getInstance().getTime()));

                String time = editTime.getText().toString();
                //Accept 3 digit input
                if (time.length() == 1) {
                    if(Integer.parseInt(time.substring(0,1)) < 10) {
                        editTime.setText(time = "0".concat(time.substring(0,1).concat(":00")));
                    }
                }
                if(time.length() == 3) {
                    if (Integer.parseInt(time.substring(0,2)) < 24) {
                        editTime.setText(time = time.concat("00"));
                    }
                }
                if(time.length() == 4) {
                    if(Integer.parseInt(time.substring(0,1)) < 10) {
                        editTime.setText(time = "0".concat(time.substring(0,1).concat(":".concat(time.substring(1,2).concat(time.substring(3,4))))));
                    }
                    else if(Integer.parseInt(time.substring(0,2)) < 24) {
                        editTime.setText(time = time.substring(0,2).concat(":").concat(time.substring(3,4).concat("0")));
                    }
                }
                //Control for correct input
                if(time.length() != 5 || time.lastIndexOf(":") != time.indexOf(":") //Control of string
                        || Integer.parseInt(time.substring(0,2)) > 23 || Integer.parseInt(time.substring(3, 5)) > 59) { //Control of numbers
                    editTime.setText("");
                }
            }
        });

        editTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int timeStringLengthAfter = editTime.getText().toString().length();
                if(timeStringLengthAfter > timeStringLengthBefore && timeStringLengthAfter == 2) { //Insert colon
                    editTime.setText(getString(R.string.time_colon, editTime.getText()));
                    editTime.setSelection(3);
                }
                timeStringLengthBefore = timeStringLengthAfter;
            }
        });

        kursEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String s = kursEditText.getText().toString();
                try {
                    if(Integer.parseInt(s) <= 360) {
                        //Correct lesser inputs
                        if(s.length() == 1) s = "00".concat(s);
                        else if(s.length() == 2) s = "0".concat(s);
                        kursEditText.setText(s);
                    } else kursEditText.setText("");
                } catch (NumberFormatException e) {} //Do nothing

            }
        });

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


        if(v == fBtn || v == øBtn || v == n1Btn || v == n2Btn || v == n3Btn) {
            KingButton btn = (KingButton) v;
            btn.kingSelected();
        }
        else if (v == opretButton || v == mob) {
            createLogpunkt();
        }else if(v == resetTimeButton){
            editTime.setText("");
        }
    }

    private void createLogpunkt() {
        // Henter hals
        Button btn_styrbord = getView().findViewById(R.id.hals_styrbord_btn);
        Button pressedHals = hals_Buttons.getToggledView();
        String hals = "";
        if( pressedHals != null ){
            hals = "-";
            hals += pressedHals == btn_styrbord ? "sb" : "bb";
        }



        // Henter sejlføring
        String sejlføring = "";
        //Øverste sejldel
        if(fBtn.isSelected()) sejlføring += fBtn.getText().toString();
        else if(øBtn.isSelected()) sejlføring += øBtn.getText().toString();
        //Nederste sejl del
        if(n1Btn.isSelected()) sejlføring += sejlføring.length() > 0 ? "+" + n1Btn.getText().toString() : n1Btn.getText().toString();
        else if(n2Btn.isSelected()) sejlføring += sejlføring.length() > 0 ? "+" + n2Btn.getText().toString() : n2Btn.getText().toString();
        else if(n3Btn.isSelected()) sejlføring += sejlføring.length() > 0 ? "+" + n3Btn.getText().toString() : n3Btn.getText().toString();
        //Sætsammen med hals
        sejlføring += hals;


        String kursStr = kursEditText.getText().toString();


        // Fetching time ---------------------------------------------------------------

        String timeStr = editTime.getText().toString();
        if(timeStr.length() == 0){
            timeStr = editTime.getHint().toString();
        }

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
        logpunkt.setKurs( kursStr.equals("") ? -1 : Integer.parseInt(kursStr) );
        logpunkt.setSejlfoering( sejlføring );
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


    /**
     * Implementation of the ToggleViewList specific for the buttons
     * on the log entry creation page. */
    private class ToggleButtonList extends ToggleViewList{

        /* This constructor is necessary, as the default
            parent class constructor takes 0 views. (ask Malte)*/
        ToggleButtonList(View ... views){
            super(views);
        }

        /**
         * What to do when a view (in this case button) is disabled
         * @param view The view (button) being disabled */
        @Override
        public void onViewUntoggled(View view) {
            Button btn = (Button) view;
            Resources res = btn.getContext().getResources();
            btn.setBackgroundTintList( res.getColorStateList(R.color.grey) );
            btn.setTextColor( res.getColorStateList(R.color.colorPrimary) );
        }

        /**
         * What to do when a view (in this case button) is enabled
         * @param view The view (button) being disabled */
        @Override
        public void onViewToggled(View view) {
            Button btn = (Button) view;
            Resources res = btn.getContext().getResources();
            btn.setBackgroundTintList( res.getColorStateList(R.color.colorPrimary) );
            btn.setTextColor( Color.WHITE );
        }

        /**
         * We know we're using buttons, so to make life easier, we convert
         * the views to buttons, before returning them         */
        @Override
        public Button getToggledView() {
            return (Button) super.getToggledView();
        }
    }
}