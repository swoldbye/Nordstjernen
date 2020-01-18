package com.example.skibslogapp.etapeoversigt;


import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.skibslogapp.OpretEtapeDialogBox;
import com.example.skibslogapp.R;
import com.example.skibslogapp.datalayer.local.EtapeDAO;
import com.example.skibslogapp.model.Etape;
import com.example.skibslogapp.model.Togt;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 */
public class EtapeOversigt_frag extends Fragment {

    private TextView togt_text, skib_text;
    private Togt togt;
    private Etape newEtape = null;
    private FloatingActionButton createEtape_button;
    private EtapeListAdapter listAdapter;
    private RecyclerView recyclerView;
    private List<Etape> etaper;


    public EtapeOversigt_frag(Togt togt) {
        this.togt = togt;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_etape_oversigt, container, false);
        // Inflate the layout for this fragment

        togt_text = view.findViewById(R.id.etapeTogtText);
        skib_text = view.findViewById(R.id.skibsNavnText);

        togt_text.setText(togt.getName());
        skib_text.setText(togt.getSkib());

        EtapeDAO etapeDAO = new EtapeDAO(getContext());
        etaper = etapeDAO.getEtaper(togt);

        if( etaper.get(0).getStatus() == Etape.Status.NEW){
            view.findViewById(R.id.etapeOpretButton).setVisibility(View.GONE);
            view.findViewById(R.id.etape_recyclerview).setVisibility(View.GONE);
            view.findViewById(R.id.etapeoversigt_start).setVisibility(View.VISIBLE);
        }else{
            view.findViewById(R.id.etapeOpretButton).setVisibility(View.VISIBLE);
            view.findViewById(R.id.etape_recyclerview).setVisibility(View.VISIBLE);
            view.findViewById(R.id.etapeoversigt_start).setVisibility(View.GONE);
        }

        view.findViewById(R.id.etapeoversigt_start_button).setOnClickListener((View v) -> startTogt());

        // Etape Liste
        recyclerView = view.findViewById(R.id.etape_recyclerview);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);
        listAdapter = new EtapeListAdapter(etaper);
        recyclerView.setAdapter(listAdapter);
        recyclerView.smoothScrollToPosition(listAdapter.getItemCount() - 1);

        // Opret Etape Button
        view.findViewById(R.id.etapeOpretButton).setOnClickListener((View v) -> this.openDialog());
        return view;
    }


    private void startTogt() {
        getView().findViewById(R.id.etapeOpretButton).setVisibility(View.VISIBLE);
        getView().findViewById(R.id.etape_recyclerview).setVisibility(View.VISIBLE);
        getView().findViewById(R.id.etapeoversigt_start).setVisibility(View.GONE);

        Etape firstEtape = etaper.get(0);
        firstEtape.setStatus(Etape.Status.ACTIVE);
        firstEtape.setStartDate(new Date(System.currentTimeMillis()) );
        listAdapter.updateEtapeList(etaper);

        new EtapeDAO(getContext()).updateEtape(etaper.get(0));
    }


    private void createEtape() {



        EtapeDAO etapeDAO = new EtapeDAO(getContext());

        Etape newEtape = new Etape();
        newEtape.setStatus(Etape.Status.ACTIVE);
        Random random = new Random();
        String[] destinationer = {"København", "Nyborg", "Roskilde", "Kattinge", "Ejby", "Køge", "Odense", "Aalborg", "Stege", "Kalvehave", "Jylling", "Helsingør"};
        newEtape.setStartDestination( destinationer[random.nextInt(destinationer.length) ]);
        newEtape.setSkipper("Troels");
        etapeDAO.addEtape(togt, newEtape);

        // TODO: Change this when we when we have merged Togt
        if( etaper.size() > 0 ){
            Etape previousEtape = etaper.get(etaper.size()-1);
            previousEtape.setSlutDestination( newEtape.getStartDestination() );
            previousEtape.setEndDate( newEtape.getStartDate() );
            previousEtape.setStatus(Etape.Status.FINISHED);
            etapeDAO.updateEtape(previousEtape);
        }

        etaper.add(newEtape);
        listAdapter.updateEtapeList(etaper);
        recyclerView.smoothScrollToPosition(listAdapter.getItemCount() - 1);
    }

    private void openDialog() {
        int numberOfEtape = new EtapeDAO(getContext()).getEtaper(togt).size();
        newEtape = new EtapeDAO(getContext()).getEtaper(togt).get(numberOfEtape-1);

        OpretEtapeDialogBox dialogBox = new OpretEtapeDialogBox(togt,newEtape);
        dialogBox.show(getFragmentManager(),"Dialog box");
    }


    private List<String> getTestListe(){
        List<String> testListe = new ArrayList<>();
        testListe.add("Troels");
        testListe.add("Per");
        testListe.add("Knud");

        return testListe;
    }

}
