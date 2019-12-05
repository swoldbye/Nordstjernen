package com.example.skibslogapp.view;


import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.skibslogapp.R;
import com.example.skibslogapp.TogtListAdapter;
import com.example.skibslogapp.model.Togt;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class TogtOversigt_frag extends Fragment implements TogtListAdapter.OnTogtListener, View.OnClickListener {

    private static final String TAG = "TogtOversigt_frag";

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    View opretTogt;

    ArrayList<Togt> togtList;

    public TogtOversigt_frag() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_togt_oversigt, container, false);

        togtList = new ArrayList<>();

        opretTogt = view.findViewById(R.id.opretTogtBtn);
        opretTogt.setOnClickListener(this);

        loadFromPrefs();

        recyclerView = view.findViewById(R.id.togtRecycView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this.getContext());
        adapter = new TogtListAdapter(togtList, this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return view;
    }

    private void loadFromPrefs(){

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Gson gson = new Gson();
        String json = sharedPreferences.getString("togterList",null);
        if (!(sharedPreferences.contains("togterList"))){
            System.out.println("Nothing in list");
        }else {
            Type type = new TypeToken<ArrayList<Togt>>(){}.getType();
            togtList = gson.fromJson(json,type);
            System.out.println("Loaded json");
        }
    }

    @Override
    public void onTogtClick(int position) {
        Log.d(TAG,"onTogtClick: clicked");

    }

    @Override
    public void onClick(View v) {
        OpretTogt_frag opretTogt_frag = new OpretTogt_frag();
        changeFragment(opretTogt_frag);
    }

    private void changeFragment(Fragment fragment){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragContainer,fragment);
        fragmentTransaction.commit();
    }
}
