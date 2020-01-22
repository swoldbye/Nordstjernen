package com.example.skibslogapp.view.etapeoversigt;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
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
import com.example.skibslogapp.view.etapeoversigt.opretetape.OpretEtapeDialog;
import com.example.skibslogapp.R;
import com.example.skibslogapp.datalayer.export.GenerateCSV;
import com.example.skibslogapp.datalayer.local.EtapeDAO;
import com.example.skibslogapp.datalayer.local.TogtDAO;
import com.example.skibslogapp.model.Etape;
import com.example.skibslogapp.model.Togt;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EtapeOversigt_frag extends Fragment {

    private Togt togt;
    private EtapeListAdapter listAdapter;
    private RecyclerView recyclerView;
    private ImageButton togtInstilling;

    public EtapeOversigt_frag(Togt togt) {
        this.togt = togt;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.etapeoversigt_frag, container, false);

        togtInstilling = view.findViewById(R.id.popUpMenuEtapeOversigt);
        togtInstilling.setOnClickListener(v -> indstillinger() );

        TextView togt_text = view.findViewById(R.id.etapeTogtText);
        TextView skib_text = view.findViewById(R.id.skibsNavnText);

        togt_text.setText(togt.getName());
        skib_text.setText(togt.getSkib());

        EtapeDAO etapeDAO = new EtapeDAO(getContext());
        List<Etape> etaper = etapeDAO.getEtaper(togt);

        // If the Togt is new, meaning no Etaper is created, then the list and the create Etape button isent
        // visible, and the "Start Togt" fragment is visible. Otherwise the opposite is true.
        if (etaper.get(0).getStatus() == Etape.Status.NEW) {
            view.findViewById(R.id.etapeoversigt_opret_container).setVisibility(View.GONE);
            view.findViewById(R.id.etape_recyclerview).setVisibility(View.GONE);
            view.findViewById(R.id.etapeoversigt_start).setVisibility(View.VISIBLE);
        } else {
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
        dialog.onCreationFinished((cbDialog, etape) -> {

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
        dialog.show(getFragmentManager(), "Dialog box");
    }

    /**
     * The user has pressed the Ny Etape button, so we open a OpretEtapeDialog
     * for the user to enter the information in.
     */
    private void createEtapePressed() {

        // Load Previous Etape
        List<Etape> currentEtaper = new EtapeDAO(getContext()).getEtaper(togt);
        Etape previousEtape = currentEtaper.get(currentEtaper.size() - 1);

        // Copy information to new Etape
        Etape newEtape = new Etape();
        newEtape.setBesaetning(new ArrayList<>(previousEtape.getBesaetning()));
        newEtape.setSkipper(previousEtape.getSkipper());

        // Create Dialog box
        OpretEtapeDialog dialog = new OpretEtapeDialog(newEtape);
        dialog.onCreationFinished((cbDialog, createdEtape) -> {

            // Update Previous Etape
            previousEtape.setStatus(Etape.Status.FINISHED);
            previousEtape.setSlutDestination(createdEtape.getStartDestination());
            previousEtape.setEndDate(createdEtape.getStartDate());

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
        dialog.show(getFragmentManager(), "Dialog box");
    }


    /**
     * This function will scroll the Etape list to the lowest Etape in the list, which is the latest created
     * if the list is big enough so it can be scrolled
     */
    private void scrollToButtom() {
        if (listAdapter.getItemCount() > 0) {
            recyclerView.smoothScrollToPosition(listAdapter.getItemCount() - 1);
        }
    }


    /**
     * Creates a pop-up dialog with two options: export and delete Togt */
    private void indstillinger(){
        PopupMenu popupMenu = new PopupMenu( getActivity(), togtInstilling);
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu_etape_oversigt, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.exportTogt:
                    exportTogt();
                    return true;

                case R.id.deleteTogt:
                    deleteTogt();
                    return true;

                default:
                    return false;
            }
        });
        popupMenu.show();
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
            fileIntent.putExtra(Intent.EXTRA_SUBJECT, "Data for '" + togt.getName() + "'");
            fileIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            fileIntent.putExtra(Intent.EXTRA_STREAM, path);

            startActivity(Intent.createChooser(fileIntent, "Send mail"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Creates a confirmation pop-up prompting the user whether or not he/she wants
     * to delete the togt. Also deletes the togt if its confirmed */
    private void deleteTogt(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Design Dialog
        TextView dialogBoxHeadline = new TextView(getContext());
        dialogBoxHeadline.setText("Er du sikker på du vil slette togtet?");
        dialogBoxHeadline.setTextSize(20f);
        dialogBoxHeadline.setTypeface(null, Typeface.BOLD);
        dialogBoxHeadline.setPadding(60, 60, 60, 4);
        dialogBoxHeadline.setTextColor(getResources().getColor(R.color.colorPrimary));

        // Seutp Builder
        builder.setCustomTitle(dialogBoxHeadline)
                .setCancelable(false);

        // Positive button
        builder.setPositiveButton("Ja", (dialog, which) -> {

                //Delete the "togt from the DB
                TogtDAO togtDAO = new TogtDAO(GlobalContext.get());
                togtDAO.deleteTogt(togt);

                //Change to "Togt oversigten" without saving the fragment to the backstack
                getActivity().getSupportFragmentManager().popBackStack();
                Toast.makeText(getActivity(), "Togt slettet", Toast.LENGTH_SHORT).show();
        });

        // Negative Button - If the negative button is pressed the dialog box is closed and the action is shut down
        builder.setNegativeButton("Nej", (dialog, which) -> dialog.cancel());

        // Create Dialog
        final AlertDialog alertDialog = builder.create();
        alertDialog.setOnShowListener( (dialog) -> {
            // Set button design
            Button btnPositive = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
            Button btnNegative = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
            btnPositive.setTextColor(getResources().getColor(R.color.colorPrimary));
            btnPositive.setTextSize(20f);
            btnNegative.setTextColor(getResources().getColor(R.color.colorPrimary));
            btnNegative.setTextSize(20f);
        });

        alertDialog.show();
    }
}
