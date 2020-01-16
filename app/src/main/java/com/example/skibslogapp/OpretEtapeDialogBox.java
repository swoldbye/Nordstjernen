package com.example.skibslogapp;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.skibslogapp.datalayer.local.EtapeDAO;
import com.example.skibslogapp.model.Etape;
import com.example.skibslogapp.model.Togt;


public class OpretEtapeDialogBox extends AppCompatDialogFragment {

    private EditText editSkipper, editStartDest;
    private Togt togt;

    public OpretEtapeDialogBox(Togt togt) {
        this.togt = togt;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.opret_etape_dialog_box,null);

        builder.setView(view)
                .setNegativeButton("Anuller", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Start", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String skipper = editSkipper.getText().toString();
                        String startDest = editStartDest.getText().toString();
                        EtapeDAO etapeDAO = new EtapeDAO(getContext());
                        Etape etape = new Etape();
                        etapeDAO.addEtape(togt,etape);



                    }
                });

        editSkipper = view.findViewById(R.id.inputSkipper);
        editStartDest = view.findViewById(R.id.inputStartDest);

        return builder.create();

    }

}
