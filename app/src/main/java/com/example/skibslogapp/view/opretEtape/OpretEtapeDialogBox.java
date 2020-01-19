package com.example.skibslogapp.view.opretEtape;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skibslogapp.R;
import com.example.skibslogapp.datalayer.local.EtapeDAO;
import com.example.skibslogapp.model.Etape;
import com.example.skibslogapp.model.Togt;

import java.util.ArrayList;
import java.util.List;


public class OpretEtapeDialogBox extends AppCompatDialogFragment implements View.OnClickListener {

    private EditText editSkipper, editStartDest, navnInput;
    private Togt togt;
    private View addButton;
    private Etape previousEtape;
    private String skipper = "";
    private String startDestination = "";
    private TextView annullerEtape;
    private Button startEtape;

    private List<String> beseatningsList;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    public OpretEtapeDialogBox(Togt togt, Etape previousEtape) {
        this.togt = togt;
        this.previousEtape = previousEtape;

        skipper = previousEtape.getSkipper();

        // Copy Besaetning from previous Etape
        beseatningsList = new ArrayList<>(previousEtape.getBesaetning());
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.opret_etape_dialog_box,null);

        builder.setView(view);

        annullerEtape = view.findViewById(R.id.opretEtape_annuler_button);
        annullerEtape.setOnClickListener(this);

        startEtape = view.findViewById(R.id.opretEtape_start_button);
        startEtape.setOnClickListener(this);

        editSkipper = view.findViewById(R.id.inputSkipper);
        editStartDest = view.findViewById(R.id.inputStartDest);
        navnInput = view.findViewById(R.id.navnIndput);

        addButton = view.findViewById(R.id.addButton);
        addButton.setOnClickListener(this);

        recyclerView = view.findViewById(R.id.besaetningList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        adapter = new CrewAdapter(beseatningsList,getContext());
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);

        showSkipper();
        showSlutDestination();

        scrollTobuttom();

        return builder.create();

    }




    @Override
    public void onClick(View v) {
        if (v == annullerEtape) {
            getFragmentManager().beginTransaction()
                    .remove(this)
                    .commit();
        }

        if (v == startEtape) {
            String skipper = editSkipper.getText().toString();
            String startDest = editStartDest.getText().toString();

            /*
            Ensure that we have a slutdestination for the former previousEtape
             */
            if (startDest.length() <= 0) {
                editStartDest.setError("Der skal indtastes en start destinationt!");
                return;
            }
                EtapeDAO etapeDAO = new EtapeDAO(getContext());

                // Create new Etape
                Etape newEtape = new Etape();
                newEtape.setBesaetning(beseatningsList);
                newEtape.setSkipper(skipper);
                newEtape.setStartDestination(startDest);
                etapeDAO.addEtape(togt, newEtape);

                // Update previous Etape
                previousEtape.setStatus(Etape.Status.FINISHED);
                previousEtape.setSlutDestination(startDest);
                etapeDAO.updateEtape(previousEtape);

                scrollTobuttom();
                getFragmentManager().beginTransaction()
                        .remove(this)
                        .commit();
        }


        if (v == addButton) {
            String navn = navnInput.getText().toString();
            if (navn.length() <= 0) {
                navnInput.setError("Der skal indtastes et navn på et besætningsmedlem!");
            } else {
                beseatningsList.add(navn);
                System.out.println(navnInput.getText().toString());
                navnInput.setText("");
                adapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(adapter.getItemCount() - 1);
                clearFocusOnDone(v);
            }
        }
    }

    /**
     * Makes the keyboard dissapere
     * @param v
     */
    private void clearFocusOnDone(View v) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    /**
     * Show the previus Skipper in the new Etape
     */
    private void showSkipper(){
        if(skipper.length() > 0){
            editSkipper.setText(skipper);
        }
    }

    /**
     * Show the previus slutDestination as the Start destination in the new Etape
     */
    private void showSlutDestination(){
        if(startDestination != null && startDestination.length()>0){
            editStartDest.setText(startDestination);
        }
    }

    private void scrollTobuttom(){
        if(adapter.getItemCount()>0){
            recyclerView.smoothScrollToPosition(adapter.getItemCount()-1);
        }
    }
}




