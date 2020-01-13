package com.example.skibslogapp.view;


import android.content.SharedPreferences;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skibslogapp.Main_akt;
import com.example.skibslogapp.R;
import com.example.skibslogapp.TogtListAdapter;
import com.example.skibslogapp.datalayer.local.TogtDAO;
import com.example.skibslogapp.model.Togt;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * This fragment contanins a recycleview with the created "Togts". You can click a  list element to
 * enter the "Togt" to se the log posts of the given "Togt".
 * You can click a button on the element to erase the "Togt" from the recycleView List and the database
 * and you can edit a "Togt" by clicken another button.
 */
public class TogtOversigt_frag extends Fragment implements View.OnClickListener {

    private static final String TAG = "TogtOversigt_frag";

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private TogtDAO togtDAO;

    View opretTogt;

    List<Togt> togtList;

    public TogtOversigt_frag() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_togt_oversigt, container, false);

        opretTogt = view.findViewById(R.id.opretTogtBtn);
        opretTogt.setOnClickListener(this);

//        loadFromPrefs();

        togtDAO = new TogtDAO(getContext());
        togtList = togtDAO.getTogter();


        recyclerView = view.findViewById(R.id.togtRecycView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this.getContext());
        adapter = new TogtListAdapter(togtList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        // Inflate the layout for this fragment


        return view;
    }

//    /**
//     * this loads the togt list from sharedPreferences. It will probably be replaced with a load mechanism
//     * from SQLite, because that is where we save our data.
//     */
//    private void loadFromPrefs(){
//        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
//        Gson gson = new Gson();
//        String json = sharedPreferences.getString("togterList",null);
//        if (!(sharedPreferences.contains("togterList"))){
//            System.out.println("Nothing in list");
//        }else {
//            Type type = new TypeToken<ArrayList<Togt>>(){}.getType();
//            togtList = gson.fromJson(json,type);
//            System.out.println("Loaded json");
//        }
//    }

//    @Override
//    public void onTogtClick(int position) {
//        Log.d(TAG,"onTogtClick: clicked");
//
//    }

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
