package com.example.skibslogapp.view;


import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
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

public class TogtOversigt_frag extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    ArrayList<Togt> togtList;

    public TogtOversigt_frag() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_togt_oversigt, container, false);

        togtList = new ArrayList<>();

        loadFromPrefs();

        recyclerView = view.findViewById(R.id.togtRecycView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this.getContext());
        adapter = new TogtListAdapter(togtList);
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

}
