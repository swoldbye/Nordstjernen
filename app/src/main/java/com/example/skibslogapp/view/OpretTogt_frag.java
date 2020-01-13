package com.example.skibslogapp.view;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.example.skibslogapp.R;
import com.example.skibslogapp.model.Togt;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class OpretTogt_frag extends Fragment implements View.OnClickListener {

    private String[] skibsListe ={"Skib1","Skib2","Skib3","Skib4","Skib5","Skib6"};
    private ArrayAdapter<String> dropDownShip;
    private MaterialBetterSpinner betterSpinner;
    private EditText togtName, skipper, startDest;
    private View opretBtn;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private ArrayList<Togt> togter;
    private Gson gson;

    public OpretTogt_frag() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_opret_togt, container, false);

        togtName = view.findViewById(R.id.togtNavn);
        skipper = view.findViewById(R.id.skipperEdit);
        startDest = view.findViewById(R.id.startDestEdit);
        opretBtn = view.findViewById(R.id.opretBtn);
        opretBtn.setOnClickListener(this);

        dropDownShip = new ArrayAdapter<String>(this.getContext(),android.R.layout.simple_dropdown_item_1line,skibsListe);
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
        String kaptajn = skipper.getText().toString();
        String togtStartDest = startDest.getText().toString();

        TogtOversigt_frag togtOversigt_frag;

        if (view == opretBtn && togtet.length() <= 0){
            togtName.setError("Der skal indtastes et navn til togtet!");
            return;

        }else if (view == opretBtn && kaptajn.length() <= 0){
            skipper.setError("Der skal intastes et navn på skipperen!");
            return;

        }else if (view == opretBtn && togtStartDest.length() <= 0){
            startDest.setError("Vælg hvor togtet skal startes fra!");
            return;

        }else {
            if (view == opretBtn){

                togtOversigt_frag = new TogtOversigt_frag();

                loadFromPrefs();

                Togt togt = new Togt(kaptajn,togtStartDest,togtet,ship);
                togter.add(togt);

                saveToPrefs(togter);

                Toast.makeText(this.getContext(),"Togt oprettet!",Toast.LENGTH_LONG).show();

                changeFragment(togtOversigt_frag);
            }
        }
    }

    /**
     * Save a list to PreferenceManager
     */
    private void saveToPrefs(ArrayList list){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor = sharedPreferences.edit();
        gson = new Gson();
        String json = gson.toJson(list);
        editor.putString("togterList", json);
        editor.apply();
    }

    /**
     * Load the togter list from PreferenceManager
     */
    private void loadFromPrefs(){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        gson = new Gson();
        String json = sharedPreferences.getString("togterList", null);
        Type type = new TypeToken<ArrayList<Togt>>() {}.getType();
        togter = gson.fromJson(json,type);

        if (togter == null){
            togter = new ArrayList<>();
        }
    }

    private void changeFragment(Fragment fragment){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragContainer,fragment);
        fragmentTransaction.commit();
    }

}
