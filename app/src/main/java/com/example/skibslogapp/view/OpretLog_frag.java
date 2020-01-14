package com.example.skibslogapp.view;



import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.example.skibslogapp.model.Togt;
import com.example.skibslogapp.model.LogInstans;
import com.example.skibslogapp.R;
import com.example.skibslogapp.model.LogInstans;
import com.example.skibslogapp.model.Togt;
import com.example.skibslogapp.view.utility.ToggleViewList;

import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class OpretLog_frag extends Fragment implements View.OnClickListener {

    OnMainActivityListener mCallback;

    private int timeStringLengthBefore = 0;
    private String finalVindRetning = "";
    private String finalSejlføring = "";
    private String styrbordEllerBagbord = "";
    private String sejlStilling = "";

    //button colors:
    int basicColor;
    int standOutColor;

    Button resetTimeButton;
    Button nordButton, østButton, sydButton, vestButton;
    EditText kursEditText, antalRoereEditText, editTime,vindHastighedEditTxt;
    Button opretButton;
    TextView vindretning_input;
    Button vindretning_delete;
    Switch sbBb;
    View mob;
    ToggleButtonList hals_Buttons;
    ToggleButtonList sejlStilling_Buttons;
    private ToggleButtonList sejlføring_Buttons;
    EditText noteEditText;

    String simpleDate3;

    public OpretLog_frag() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_opret_log, container, false);

        //Tidsslet
        editTime = (EditText) view.findViewById(R.id.editTime);
        resetTimeButton = (Button) view.findViewById(R.id.resetTimeButton);

        //Vind Retning
        nordButton = (Button) view.findViewById(R.id.nordButton);
        østButton = (Button) view.findViewById(R.id.østButton);
        sydButton = (Button) view.findViewById(R.id.sydButton);
        vestButton = (Button) view.findViewById(R.id.vestButton);

        //Kurs
        kursEditText = (EditText) view.findViewById(R.id.kursEditText);


        //Antal Roere
        antalRoereEditText = (EditText) view.findViewById(R.id.antalRoereEditText);

        vindHastighedEditTxt = view.findViewById(R.id.vindhastighed_edittext);

        //Opret Post
        opretButton = (Button) view.findViewById(R.id.opretButton);

        //Note
        noteEditText = (EditText) view.findViewById(R.id.noteEditText);

        //Mand over bord
        mob = view.findViewById(R.id.mob_button);

        hals_Buttons = new ToggleButtonList(
                view.findViewById(R.id.hals_bagbord_btn),
                view.findViewById(R.id.hals_styrbord_btn)
        );

        sejlStilling_Buttons = new ToggleButtonList(
                view.findViewById(R.id.læ),
                view.findViewById(R.id.ag),
                view.findViewById(R.id.bi),
                view.findViewById(R.id.fo),
                view.findViewById(R.id.ha)
        );

        sejlføring_Buttons = new ToggleButtonList(
                view.findViewById(R.id.fButton),
                view.findViewById(R.id.øButton),
                view.findViewById(R.id.n1Button),
                view.findViewById(R.id.n2Button),
                view.findViewById(R.id.n3Button)
        );


        //On click Listeners:
        nordButton.setOnClickListener(this);
        østButton.setOnClickListener(this);
        sydButton.setOnClickListener(this);
        vestButton.setOnClickListener(this);

        mob.setOnClickListener(this);
        opretButton.setOnClickListener(this);

        editTime.setOnClickListener(this);
        resetTimeButton.setOnClickListener(this);

        vindretning_delete = view.findViewById(R.id.vindretning_delete);
        vindretning_delete.setOnClickListener(this);
        vindretning_delete.setVisibility(View.INVISIBLE);

        vindretning_input = view.findViewById(R.id.vindretning_input);
        vindretning_input.setText("");

        basicColor = getResources().getColor(R.color.grey);
        standOutColor = getResources().getColor(R.color.colorPrimary);


        //On Editor Listeners
        antalRoereEditText.setOnEditorActionListener(clearFocusOnDone);
        kursEditText.setOnEditorActionListener(clearFocusOnDone);
        editTime.setOnEditorActionListener(clearFocusOnDone);
        vindHastighedEditTxt.setOnEditorActionListener(clearFocusOnDone);

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
    TextView.OnEditorActionListener clearFocusOnDone = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if(actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT) {
                v.clearFocus(); //Clears focus, which cascade into it resetting through OnFocusChange()
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
            return true;
        }
    };



    private void vindDirectionLogic(String currDirection, String btnDirection, String counterDirection) {
        if(!currDirection.contains(counterDirection)) {
            switch(currDirection.length()) {
                case 0:
                    vindretning_input.setText(btnDirection);
                    break;

                case 1:
                    if(btnDirection.equals("N") || btnDirection.equals("S")) vindretning_input.setText(btnDirection.concat(currDirection)); //Put in the front
                    else vindretning_input.setText(currDirection.concat(btnDirection)); //Put in the back
                    break;

                case 2:
                    if(currDirection.indexOf(btnDirection) == currDirection.lastIndexOf(btnDirection)) {
                        if(currDirection.contains(btnDirection)) vindretning_input.setText(btnDirection.concat(currDirection)); //Put in front
                        else if(btnDirection.equals("N") || btnDirection.equals("S"))
                            vindretning_input.setText(currDirection.substring(0,1).concat(btnDirection).concat(currDirection.substring(1,2))); //Put in the middle
                        else vindretning_input.setText(currDirection.concat(btnDirection)); //Put in the back
                    }
                    break;
            }
            vindretning_delete.setVisibility(View.VISIBLE);
        }
    }




    @Override
    public void onClick(View v) {

        LogOversigt_frag logOversigt_frag;
        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;

        // Vindretning
        if(v == nordButton) vindDirectionLogic(vindretning_input.getText().toString(), "N", "S");
        else if(v == østButton) vindDirectionLogic(vindretning_input.getText().toString(), "Ø", "V");
        else if(v == sydButton) vindDirectionLogic(vindretning_input.getText().toString(), "S", "N");
        else if(v == vestButton) vindDirectionLogic(vindretning_input.getText().toString(), "V", "Ø");

        else if (v == vindretning_delete) {
            vindretning_input.setText("");
            vindretning_delete.setVisibility(View.INVISIBLE);

        }else if (v == opretButton || v == mob) {

            // Henter hals
            Button btn_styrbord = getView().findViewById(R.id.hals_styrbord_btn);
            Button pressedHals = hals_Buttons.getToggledView();
            String hals = "";
            if( pressedHals != null ){
                hals = "-";
                hals += pressedHals == btn_styrbord ? "sb" : "bb";
            }

            // Henter tiden
            String time = editTime.getText().toString();
            if(time.length() == 0){
                time = editTime.getHint().toString();
            }

            // Henter sejlføring
            String sejlføring = "";
            Button pressedSejlføring = sejlføring_Buttons.getToggledView();
            if(pressedSejlføring != null){
                sejlføring = pressedSejlføring.getText().toString() + hals;
            }

            // Henter sejlstilling
            String sejlstilling = "";
            Button pressedSejlstilling = sejlStilling_Buttons.getToggledView();
            if(pressedSejlstilling != null){
                sejlstilling = pressedSejlstilling.getText().toString();
            }

            LogInstans nyeste = new LogInstans(
                    time,
                    vindretning_input.getText().toString(),
                    kursEditText.getText().toString(),
                    sejlføring,
                    sejlstilling,
                    noteEditText.getText().toString()
            );

            Togt.addLogPost(nyeste);
            mCallback.updateList(nyeste);
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .remove(this)
                    .commit();

        }else if(v == resetTimeButton){
            editTime.setText("");
            resetTimeButton.setVisibility(View.INVISIBLE);
        }

    }



    public interface OnMainActivityListener{
        void updateList(LogInstans nyeste);
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
