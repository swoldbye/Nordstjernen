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
import com.example.skibslogapp.model.Etape;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Date;


public class OpretEtapeDialog extends AppCompatDialogFragment implements View.OnClickListener {

    private TextInputLayout skipperInput, startDestInput;
    private EditText navnInput;
    private View addButton;
    private TextView annullerEtape;
    private Button startEtape;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private OpretEtapeCallback onFinishCallback = null;
    private OpretEtapeCallback onCancelCallback = null;
    private Etape etape;

    public OpretEtapeDialog(Etape etape) {
        this.etape = etape;
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

        skipperInput = view.findViewById(R.id.opretetape_skipper);
        startDestInput = view.findViewById(R.id.opretetape_startdest);

        navnInput = view.findViewById(R.id.navnIndput);

        addButton = view.findViewById(R.id.addButton);
        addButton.setOnClickListener(this);

        recyclerView = view.findViewById(R.id.besaetningList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new CrewAdapter( etape.getBesaetning(), getContext());
        recyclerView.setAdapter(adapter);

        showSkipper();
        showStartDestination();

        scrollTobuttom();

        return builder.create();
    }

    @Override
    public void onClick(View v) {
        skipperInput.setError(null);
        startDestInput.setError(null);
        navnInput.setError(null);

        if (v == annullerEtape) {
            if( onCancelCallback != null ) onCancelCallback.run(this, etape);
            getFragmentManager().beginTransaction()
                    .remove(this)
                    .commit();
        }

        if (v == startEtape) {
            String skipper = skipperInput.getEditText().getText().toString();
            String startDest = startDestInput.getEditText().getText().toString();

            if (startDest.length() <= 0) {
                startDestInput.setError("Der skal indtastes en start destinationt!");
                return;
            }

            if ( skipper.length() <= 0){
                skipperInput.setError("Der skal indtastes en skipper!");
                return;
            }

            // Set information to given Etape
            etape.setSkipper(skipper);
            etape.setStartDestination(startDest);
            etape.setStartDate(new Date(System.currentTimeMillis()));
            etape.setStatus(Etape.Status.ACTIVE);

            getFragmentManager().beginTransaction()
                    .remove(this)
                    .commit();

            if( onFinishCallback != null ) onFinishCallback.run(this, etape);
        }


        if (v == addButton) {
            String navn = navnInput.getText().toString();
            if (navn.length() <= 0) {
                navnInput.setError("Der skal indtastes et navn på et besætningsmedlem!");
            } else {
                etape.addBesaetningsMedlem(navn);
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
        if(etape.getSkipper().length() > 0){
            skipperInput.getEditText().setText( etape.getSkipper() );
        }
    }

    /**
     * Show the previus slutDestination as the Start destination in the new Etape
     */
    private void showStartDestination(){
        if(etape.getStartDestination() != null && etape.getStartDestination().length()>0){
            startDestInput.getEditText().setText( etape.getStartDestination() );
        }
    }

    private void scrollTobuttom(){
        if(adapter.getItemCount()>0){
            recyclerView.smoothScrollToPosition(adapter.getItemCount()-1);
        }
    }


    // Callbacks for cancel and start buttons
    public interface OpretEtapeCallback{
        void run(OpretEtapeDialog dialog, Etape etape);
    }

    public void onCreationFinished(OpretEtapeCallback onFinishCallback){
        this.onFinishCallback = onFinishCallback;
    }

    public void onCreationCancelled(OpretEtapeCallback onCancelCallback){
        this.onCancelCallback = onCancelCallback;
    }
}




