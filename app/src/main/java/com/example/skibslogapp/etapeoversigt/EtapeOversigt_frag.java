package com.example.skibslogapp.etapeoversigt;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.skibslogapp.GlobalContext;
import com.example.skibslogapp.view.opretEtape.OpretEtapeDialog;
import com.example.skibslogapp.R;
import com.example.skibslogapp.datalayer.global.GenerateCSV;
import com.example.skibslogapp.datalayer.local.EtapeDAO;
import com.example.skibslogapp.datalayer.local.TogtDAO;
import com.example.skibslogapp.model.Etape;
import com.example.skibslogapp.model.Togt;
import com.example.skibslogapp.view.togtoversigt.TogtOversigt_frag;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class EtapeOversigt_frag extends Fragment {

    private TextView togt_text, skib_text;
    private Togt togt;
    private EtapeListAdapter listAdapter;
    private RecyclerView recyclerView;
    private ImageButton togtInstilling;
    private EtapeDAO etapeDAO;


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
         * If you press delete a Alert dialog box pops up to generateEtape sure that you are certain that you
         * want to delet the "Togt".
         */
        togtInstilling.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(getContext(),togtInstilling);
            popupMenu.getMenuInflater().inflate(R.menu.popup_menu_etape_oversigt,popupMenu.getMenu());

            popupMenu.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()){

                    case R.id.exportTogt:
                        exportTogt();
                        return true;

                    case R.id.deleteTogt:

                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                        //Custom title for the dialog box
                        TextView dialogBoxHeadline = new TextView(getContext());
                        dialogBoxHeadline.setText("Er du sikker på du vil slette togtet?");
                        dialogBoxHeadline.setTextSize(20f);
                        dialogBoxHeadline.setTypeface(null, Typeface.BOLD);
                        dialogBoxHeadline.setPadding(60,60,60,4);
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

        etapeDAO = new EtapeDAO(getContext());
        List<Etape> etaper = etapeDAO.getEtaper(togt);

        if( etaper.get(0).getStatus() == Etape.Status.NEW){
            view.findViewById(R.id.etapeoversigt_opret_container).setVisibility(View.GONE);
            view.findViewById(R.id.etape_recyclerview).setVisibility(View.GONE);
            view.findViewById(R.id.etapeoversigt_start).setVisibility(View.VISIBLE);
        }else{
            view.findViewById(R.id.etapeoversigt_opret_container).setVisibility(View.VISIBLE);
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

        //Auto scroll to buttom
        scrollToButtom();

        // Opret Etape Button
        view.findViewById(R.id.etapeoversigt_opret_button).setOnClickListener((View v) -> this.createEtapePressed());

        return view;
    }


    /**
     * The use pressed the 'Start Togt'-button. We open an OpretEtapeDialog with the
     * first Etape (initial Togt information), so the user may edit it.
     */
    private void startTogt() {
        // Load First Etape (was created when creating the Togt)
        Etape firstEtape = new EtapeDAO(GlobalContext.get()).getEtaper(togt).get(0);

        // Open dialog
        OpretEtapeDialog dialog = new OpretEtapeDialog(firstEtape);
        dialog.onCreationFinished( (cbDialog, etape) -> {

            // Update the Etape in DB
            EtapeDAO etapeDAO = new EtapeDAO(GlobalContext.get());
            etapeDAO.updateEtape(etape);

            // Adjust visibile views (hide start button, show ny etape)
            getView().findViewById(R.id.etapeoversigt_opret_container).setVisibility(View.VISIBLE);
            getView().findViewById(R.id.etape_recyclerview).setVisibility(View.VISIBLE);
            getView().findViewById(R.id.etapeoversigt_start).setVisibility(View.GONE);

            // Update Etape Oversigt list
            listAdapter.updateEtapeList(etapeDAO.getEtaper(togt));
        });
        dialog.show(getFragmentManager(),"Dialog box");
    }


    /**
     * The user has pressed the Ny Etape button, so we open a OpretEtapeDialog
     * for the user to enter the information in.
     */
    private void createEtapePressed() {

        // Load Previous Etape
        List<Etape> currentEtaper = new EtapeDAO(getContext()).getEtaper(togt);
        Etape previousEtape = currentEtaper.get(currentEtaper.size()-1);

        // Copy information to new Etape
        Etape newEtape = new Etape();
        newEtape.setBesaetning( new ArrayList<>(previousEtape.getBesaetning()));
        newEtape.setSkipper(previousEtape.getSkipper());

        // Create Dialog box
        OpretEtapeDialog dialog = new OpretEtapeDialog(newEtape);
        dialog.onCreationFinished( (cbDialog, createdEtape) -> {

            // Update Previous Etape
            previousEtape.setStatus(Etape.Status.FINISHED);
            previousEtape.setSlutDestination(createdEtape.getStartDestination());
            previousEtape.setEndDate( createdEtape.getStartDate() );

            // Update Database
            EtapeDAO etapeDAO = new EtapeDAO(GlobalContext.get());
            etapeDAO.addEtape(togt, createdEtape);
            etapeDAO.updateEtape(previousEtape);

            // Update Etape Oversigt list
            List<Etape> etaper = etapeDAO.getEtaper(togt);
            listAdapter.updateEtapeList(etaper);
            scrollToButtom();
        });
        // Show dialog
        dialog.show(getFragmentManager(),"Dialog box");
    }


    /**
     * Create CSV file and run a "share" function, in order to, for example,
     * upload to Google Drive or send via GMail.
     */
    private void exportTogt() {
        // Convert Togt CSV string
        String csvString = new GenerateCSV().generateTogt(togt);

        try {
            Context context = GlobalContext.get();

            // Save CSV String to local file
            String fileName = togt.getName().replace(" ", "") + ".csv";
            FileOutputStream out = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            out.write(csvString.getBytes());
            out.close();

            // Send File (mail, drive etc.)
            File filelocation = new File(context.getFilesDir(), fileName);
            Uri path = FileProvider.getUriForFile(context, "com.example.exportcsv.fileprovider", filelocation);
            Intent fileIntent = new Intent(Intent.ACTION_SEND);
            fileIntent.setType("text/csv");
            fileIntent.putExtra(Intent.EXTRA_SUBJECT, "Data for '"+togt.getName()+"'");
            fileIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            fileIntent.putExtra(Intent.EXTRA_STREAM, path);

            startActivity(Intent.createChooser(fileIntent, "Send mail"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void scrollToButtom(){
        if(listAdapter.getItemCount()>0){
            recyclerView.smoothScrollToPosition(listAdapter.getItemCount() - 1);
        }
    }
}
