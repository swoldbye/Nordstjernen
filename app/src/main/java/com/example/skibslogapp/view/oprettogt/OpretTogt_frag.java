package com.example.skibslogapp.view.oprettogt;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.example.skibslogapp.R;
import com.example.skibslogapp.datalayer.local.EtapeDAO;
import com.example.skibslogapp.datalayer.local.TogtDAO;
import com.example.skibslogapp.model.Etape;
import com.example.skibslogapp.model.Togt;
import com.example.skibslogapp.view.togtoversigt.TogtOversigt_frag;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

/**
 * In this fragment you can create a new "Togt" and save it in the DB.
 */
public class OpretTogt_frag extends Fragment implements View.OnClickListener {

    private String[] skibsListe ={"Havhingsten","Skjoldungen","Helge Ask","Ottar"};
    private MaterialBetterSpinner betterSpinner;
    private EditText togtName, skipper, startDest;
    private View opretBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_opret_togt, container, false);

        togtName = view.findViewById(R.id.togtNavn);
        skipper = view.findViewById(R.id.skipperEdit);
        startDest = view.findViewById(R.id.startDestEdit);
        opretBtn = view.findViewById(R.id.opretBtn);
        opretBtn.setOnClickListener(this);

        ArrayAdapter<String> dropDownShip = new ArrayAdapter<String>(this.getContext(),
                android.R.layout.simple_dropdown_item_1line, skibsListe);
        betterSpinner = view.findViewById(R.id.skibsListe);
        betterSpinner.setAdapter(dropDownShip);

        return view;
    }

    @Override
    public void onClick(View view) {

        togtName.setError(null);
        skipper.setError(null);
        startDest.setError(null);

        String ship = betterSpinner.getText().toString();
        String togtet = togtName.getText().toString();
        String skipper = this.skipper.getText().toString();
        String togtStartDest = startDest.getText().toString();

        if (view == opretBtn && togtet.length() <= 0){
            togtName.setError("Der skal indtastes et navn til togtet!");

        }else if (view == opretBtn && skipper.length() <= 0){
            this.skipper.setError("Der skal intastes et navn på skipperen!");

        }else if (view == opretBtn && togtStartDest.length() <= 0){
            startDest.setError("Vælg hvor togtet skal startes fra!");

        }else {
            if (view == opretBtn){

                TogtOversigt_frag togtOversigt_frag = new TogtOversigt_frag();

                // Opret Togt
                Togt togt = new Togt(togtet);
                togt.setSkib(ship);
                togt.setStartDestination(togtStartDest);
                togt.setSkipper(skipper);

                // Opret Etape
                Etape etape = new Etape(); // Første etape for togtet (ikke startet)
                etape.setStartDestination(togtStartDest);
                etape.setSkipper(skipper);

                // Gem i DB
                TogtDAO togtDAO = new TogtDAO(getContext());
                togtDAO.addTogt(togt);
                new EtapeDAO(getContext()).addEtape(togt, etape);

                Toast.makeText(this.getContext(),"Togt oprettet!",Toast.LENGTH_LONG).show();
                changeFragment(togtOversigt_frag);
            }
        }
    }


    private void changeFragment(Fragment fragment){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragContainer,fragment);
        fragmentTransaction.commit();
    }

}
