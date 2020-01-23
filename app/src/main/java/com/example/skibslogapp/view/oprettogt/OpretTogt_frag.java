package com.example.skibslogapp.view.oprettogt;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.skibslogapp.GlobalContext;
import com.example.skibslogapp.R;
import com.example.skibslogapp.datalayer.local.EtapeDAO;
import com.example.skibslogapp.datalayer.local.TogtDAO;
import com.example.skibslogapp.model.Etape;
import com.example.skibslogapp.model.Togt;
import com.google.android.material.textfield.TextInputLayout;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

/**
 * In this DialogFragment you can create a new "Togt" and save it in the DB.
 */
public class OpretTogt_frag extends AppCompatDialogFragment implements View.OnClickListener {

    private String[] skibsListe ={"Havhingsten","Skjoldungen","Helge Ask","Ottar"};
    private MaterialBetterSpinner betterSpinner;
    private TextInputLayout togtName, skipper, startDest;
    private View opretBtn;
    private View annullerText;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.oprettogt_frag, null);

        builder.setView(view);

        togtName = view.findViewById(R.id.togtNavn);
        skipper = view.findViewById(R.id.skipperEdit);
        startDest = view.findViewById(R.id.startDestEdit);
        opretBtn = view.findViewById(R.id.oprettogt_opretbutotn);
        opretBtn.setOnClickListener(this);
        annullerText = view.findViewById(R.id.oprettogt_annuller);
        annullerText.setOnClickListener(this);

        ArrayAdapter<String> dropDownShip = new ArrayAdapter<>(GlobalContext.get(),
                android.R.layout.simple_dropdown_item_1line, skibsListe);
        betterSpinner = view.findViewById(R.id.skibsListe);
        betterSpinner.setAdapter(dropDownShip);

        return builder.create();
    }

    @Override
    public void onClick(View view) {
        togtName.setError(null);
        skipper.setError(null);
        startDest.setError(null);
        betterSpinner.setError(null);

        String ship = betterSpinner.getText().toString();
        String togtet = togtName.getEditText().getText().toString();
        String skipper = this.skipper.getEditText().getText().toString();
        String togtStartDest = startDest.getEditText().getText().toString();

        if (view == annullerText) {
            getFragmentManager().beginTransaction()
                    .remove(this)
                    .commit();

        }else if(view == opretBtn){

            if ( togtet.length() <= 0){
                togtName.setError("Der skal indtastes et navn til togtet!");

            }else if (skipper.length() <= 0){
                this.skipper.setError("Der skal intastes et navn på skipperen!");

            }else if (togtStartDest.length() <= 0) {
                startDest.setError("Vælg hvor togtet skal startes fra!");

            }else if (ship.length() <= 0){
                betterSpinner.setError("Vælg et skib!");

            }else {
                // Opret Togt
                Togt togt = new Togt(togtet);
                togt.setSkib(ship);
                togt.setStartDestination(togtStartDest);
                togt.setSkipper(skipper);

                // Opret Etape
                Etape etape = new Etape(); // Første etape for togtet (ikke startet)
                etape.setStartDestination(togtStartDest);
                etape.setSkipper(skipper);
                etape.addBesaetningsMedlem(skipper);


                // Gem i DB
                TogtDAO togtDAO = new TogtDAO(getContext());
                togtDAO.addTogt(togt);
                new EtapeDAO(getContext()).addEtape(togt, etape);

                Toast.makeText(this.getContext(),"Togt oprettet!",Toast.LENGTH_LONG).show();

                getFragmentManager().beginTransaction()
                        .remove(this)
                        .commit();
            }
        }
    }
}
