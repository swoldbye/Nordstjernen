package com.example.skibslogapp.viewControl;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.skibslogapp.Model.LogInstans;
import com.example.skibslogapp.Model.Togt;
import com.example.skibslogapp.R;

import java.util.ArrayList;

public class LogOversigt_frag extends Fragment implements AdapterView.OnItemClickListener {

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    private TogtOversigt_frag togtOversigt_frag;
    private OpretLog_frag opretLog_frag;

    public LogOversigt_frag() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_log_oversigt, container, false);

        ArrayAdapter adapter = new ArrayAdapter(getActivity(), R.layout.fragment_log_oversigt, R.id.overskrift,(ArrayList<LogInstans>) Togt.getTogter());

        ListView logOversigt = new ListView(getActivity());
        logOversigt.setOnItemClickListener(this);
        logOversigt.setAdapter(adapter);

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getActivity(), "Klik på " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        menu.add(Menu.NONE, 101, Menu.NONE, "Togt Oversigt");
        menu.add(Menu.NONE, 102, Menu.NONE, "NyNote").setIcon(android.R.drawable.ic_menu_edit).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        menu.add(Menu.NONE, 103, Menu.NONE, "Luk Appen").setIcon(android.R.drawable.ic_menu_close_clear_cancel).setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);

        //'Inflates' the xml menu I made.
        //getMenuInflater().inflate(R.menu.usemenu, menu);
//        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //for debugging: textView.append("\nonOptionsItemSelected called with" + item.getTitle());
        if (item.getItemId() == 101) {
            Toast.makeText(getActivity(), "Denne Side tilgår", Toast.LENGTH_LONG).show();

            togtOversigt_frag = new TogtOversigt_frag();

            fragmentManager = getActivity().getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragContainer,togtOversigt_frag);
            fragmentTransaction.commit();

        } else if (item.getItemId() == 102) {

            opretLog_frag = new OpretLog_frag();

            fragmentManager = getActivity().getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragContainer,opretLog_frag);
            fragmentTransaction.commit();

        } else if (item.getItemId() == 103) {
            Toast.makeText(getActivity(), "Denne Funktion tilgår", Toast.LENGTH_LONG).show();

        }
        return true;
    }
}
