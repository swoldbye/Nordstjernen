package com.example.skibslogapp.view;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.skibslogapp.datalayer.local.LogpunktDAO;
import com.example.skibslogapp.model.GlobalTogt;
import com.example.skibslogapp.model.Position.PositionController;
import com.example.skibslogapp.model.Logpunkt;
import com.example.skibslogapp.R;
import com.example.skibslogapp.view.opretLog.LogNote_frag;
import com.example.skibslogapp.view.opretLog.LogViewModel;

import java.util.Calendar;
import java.util.Date;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class OpretLog_frag extends Fragment implements View.OnClickListener {
    private boolean mobIsDown = true; //Starts with the "Mand Over Bord" btn in the button of the page.
    private View mob;
    private PositionController testCoordinates;

    private Button opretButton;
    private LogViewModel logVM;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Activate logging of coordinates. This is placed in onCreate to ensure that the logging will start at
        //The first time the logging is activated.
        testCoordinates = new PositionController(getActivity().getApplicationContext(), this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0] == PERMISSION_GRANTED){
            testCoordinates.startMeassureKoordinat();
            Toast.makeText(getActivity(), "Lokation er aktiveret", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getActivity(), "Lokation er ikke aktiveret", Toast.LENGTH_SHORT).show();
            // Will retur false if the user tabs "Bont ask me again/Permission denied".
            // Returns true if the user previusly rejected the message and now try to access it again. -> Indication of user confussion
            if (this.shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                Toast.makeText(getActivity(), "Lokation skal være aktiveret for at GPS lokation kan logges. Klik \"CLOSE\" og \"OPEN\" for at acceptere lokation", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_opret_log, container, false);
        logVM = ViewModelProviders.of(getActivity()).get(LogViewModel.class);
        logVM.reset();

        //Opret Post
        opretButton = (Button) view.findViewById(R.id.opretButton);

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
        logpunkt.setVindhastighed( logVM.getWindSpeed() );
        logpunkt.setStroemRetning( logVM.getWaterCurrentDirection() );
        logpunkt.setStroemhastighed( logVM.getWaterCurrentSpeed() );
        logpunkt.setSejlstilling( logVM.getSailPosition() );
        logpunkt.setRoere(logVM.getCurrRowers());
        logpunkt.setSejlfoering( logVM.getSails().equals("") ?
                logVM.getSails().concat(logVM.getOrientation()) : logVM.getSails().concat("-" + logVM.getOrientation() ));
        logpunkt.setKurs(logVM.getCourse());
        logpunkt.setNote( logVM.getNoteTxt() );
        logpunkt.setPosition(testCoordinates.getKoordinates());

        LogpunktDAO logpunktDAO = new LogpunktDAO(getContext());
        logpunktDAO.addLogpunkt(GlobalTogt.getEtape(getContext()), logpunkt);

        System.out.printf("Created logpunkt: %s", logpunkt.toString());

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

    @Override
    public void onStart() {
        super.onStart();
        System.out.println("Measure koordinate");
        testCoordinates.startMeassureKoordinat();
    }

    @Override
    public void onStop() {
        super.onStop();
        System.out.println("Stopping measure koordinates");
        testCoordinates.removeLocationUpdates();
    }
}