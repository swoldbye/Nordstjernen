package com.example.skibslogapp.etapeoversigt;


import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
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
import com.example.skibslogapp.model.GlobalTogt;
import com.example.skibslogapp.model.Togt;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class EtapeOversigt_frag extends Fragment {

    private TextView togt_text, skib_text, header_text;
    private Togt togt;
    private Etape newEtape = null;
    private FloatingActionButton createEtape_button;
    private EtapeListAdapter listAdapter;
    private RecyclerView recyclerView;


    public EtapeOversigt_frag(Togt togt) {
        this.togt = togt;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_etape_oversigt, container, false);
        // Inflate the layout for this fragment

        togt_text = view.findViewById(R.id.etapeTogtText);
        skib_text = view.findViewById(R.id.skibsNavnText);
        header_text = view.findViewById(R.id.etapeHeader);

        togt_text.setText(togt.getName());
        skib_text.setText(togt.getSkib());

        togt = GlobalTogt.getTogt(getContext());
        EtapeDAO etapeDAO = new EtapeDAO(getContext());
        List<Etape> etaper = etapeDAO.getEtaper(togt);

        // Etape Liste
        recyclerView = view.findViewById(R.id.etape_recyclerview);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);
        listAdapter = new EtapeListAdapter(etaper);
        recyclerView.setAdapter(listAdapter);

        // Opret Etape Button
        view.findViewById(R.id.etapeOpretButton).setOnClickListener((View v) -> this.openDialog());
        return view;
    }


    private void createEtape() {



        EtapeDAO etapeDAO = new EtapeDAO(getContext());
        newEtape = new Etape();
        etapeDAO.addEtape(togt, newEtape);
        listAdapter.addEtape(newEtape);
        recyclerView.smoothScrollToPosition(listAdapter.getItemCount() - 1);
    }

    private void openDialog() {
        int numberOfEtape = new EtapeDAO(getContext()).getEtaper(togt).size();
        newEtape = new EtapeDAO(getContext()).getEtaper(togt).get(numberOfEtape-1);
        newEtape.setBesaetning(getTestListe());
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
