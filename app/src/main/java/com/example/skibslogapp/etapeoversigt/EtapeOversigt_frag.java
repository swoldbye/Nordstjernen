package com.example.skibslogapp.etapeoversigt;


import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.skibslogapp.GlobalContext;
import com.example.skibslogapp.R;
import com.example.skibslogapp.datalayer.local.EtapeDAO;
import com.example.skibslogapp.datalayer.local.TogtDAO;
import com.example.skibslogapp.model.Etape;
import com.example.skibslogapp.model.Togt;
import com.example.skibslogapp.view.togtoversigt.TogtOversigt_frag;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 */
public class EtapeOversigt_frag extends Fragment {

    private TextView togt_text, skib_text;
    private Togt togt;
    private FloatingActionButton createEtape_button;
    private EtapeListAdapter listAdapter;
    private RecyclerView recyclerView;
    private List<Etape> etaper;
    private ImageButton togtInstilling;


    public EtapeOversigt_frag(Togt togt) {
        this.togt = togt;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_etape_oversigt, container, false);
        // Inflate the layout for this fragment

        togt_text = view.findViewById(R.id.etapeTogtText);
        skib_text = view.findViewById(R.id.skibsNavnText);
        togtInstilling = view.findViewById(R.id.popUpMenuEtapeOversigt);

        /**
         * When you click on this "burger" icon you get a Popup menu where you get the choice to either:
         *
         * -  Export data from a "Togt"
         * -  Delete a "Togt"
         *
         * If you press delete a Alert dialog box pops up to make sure that you are certain that you
         * want to delet the "Togt".
         */
        togtInstilling.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(getContext(),togtInstilling);
            popupMenu.getMenuInflater().inflate(R.menu.popup_menu_etape_oversigt,popupMenu.getMenu());

            popupMenu.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()){

                    case R.id.exportTogt:

                        Toast.makeText(getActivity(),"Togt exportet",Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.deleteTogt:

                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                        //Custom title for the dialog box
                        TextView dialogBoxHeadline = new TextView(getContext());
                        dialogBoxHeadline.setText("Er du sikker på du vil slette togtet?");
                        dialogBoxHeadline.setTextSize(20f);
                        dialogBoxHeadline.setTypeface(null, Typeface.BOLD);
                        dialogBoxHeadline.setPadding(20,4,4,4);
                        dialogBoxHeadline.setTextColor(getResources().getColor(R.color.colorPrimary));

                        builder.setCustomTitle(dialogBoxHeadline)
                                .setCancelable(false)
                                .setPositiveButton("Ja", (dialog, which) -> {

                                    //Delete the "togt from the DB
                                    TogtDAO togtDAO = new TogtDAO(GlobalContext.get());
                                    togtDAO.deleteTogt(togt);

                                    //Change to "Togt oversigten" without saving the fragment to the backstack
                                    TogtOversigt_frag togtOversigt_frag = new TogtOversigt_frag();
                                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    fragmentTransaction.replace(R.id.fragContainer,togtOversigt_frag);
                                    fragmentTransaction.commit();

                                    Toast.makeText(getActivity(),"Togt slettet",Toast.LENGTH_SHORT).show();
                                })
                                .setNegativeButton("Nej", (dialog, which) -> dialog.cancel());

                        final AlertDialog alertDialog = builder.create();
                        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {

                            /**
                             * This method sets the negative and positive button attributes
                             */
                            @Override
                            public void onShow(DialogInterface dialog) {
                                Button btnPositive = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                                Button btnNegative = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                                btnPositive.setTextColor(getResources().getColor(R.color.colorPrimary));
                                btnPositive.setTextSize(20f);
                                btnNegative.setTextColor(getResources().getColor(R.color.colorPrimary));
                                btnNegative.setTextSize(20f);
                            }
                        });

                        alertDialog.show();
                        return true;

                    default:
                        return false;

                }
            });
            popupMenu.show();
        });

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
        view.findViewById(R.id.etapeOpretButton).setOnClickListener((View v) -> this.createEtape());
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
}
