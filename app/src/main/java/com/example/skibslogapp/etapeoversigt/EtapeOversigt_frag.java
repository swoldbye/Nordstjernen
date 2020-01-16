package com.example.skibslogapp.etapeoversigt;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.skibslogapp.R;
import com.example.skibslogapp.datalayer.local.EtapeDAO;
import com.example.skibslogapp.model.Etape;
import com.example.skibslogapp.model.GlobalTogt;
import com.example.skibslogapp.model.Togt;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class EtapeOversigt_frag extends Fragment {

    private TextView togt_text, skib_text, header_text;
    private Togt togt;
    private FloatingActionButton createEtape_button;


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

        togt = GlobalTogt.getTogt(getContext());
        EtapeDAO etapeDAO = new EtapeDAO(getContext());
        List<Etape> etaper = etapeDAO.getEtaper(togt);

        RecyclerView recyclerView = view.findViewById(R.id.etape_recyclerview);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView.Adapter adapter = new EtapeListAdapter(etaper);
        recyclerView.setAdapter(adapter);


        view.findViewById(R.id.etapeMailBtn).setOnClickListener((View v) -> {
            etapeDAO.addEtape(togt, new Etape());
            List<Etape> etaper2 = etapeDAO.getEtaper(togt);
            recyclerView.setAdapter(new EtapeListAdapter(etaper2) );
        });

        return view;
    }
}
