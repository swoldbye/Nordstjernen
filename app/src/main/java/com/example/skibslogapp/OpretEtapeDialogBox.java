package com.example.skibslogapp;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skibslogapp.datalayer.local.EtapeDAO;
import com.example.skibslogapp.model.Etape;
import com.example.skibslogapp.model.Togt;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;


public class OpretEtapeDialogBox extends AppCompatDialogFragment implements View.OnClickListener {

    private EditText editSkipper, editStartDest,navnIndput ;
    private Togt togt;
    private View addButton;
    private List<String> besaetningList = new ArrayList<>();
    RecyclerView besaetning;



    public OpretEtapeDialogBox(Togt togt) {
        this.togt = togt;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        besaetningList = new ArrayList<>();
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

        besaetningList.add("ASDKASKDH");
        besaetningList.add("ASDKASKDH");

        editSkipper = view.findViewById(R.id.inputSkipper);
        editStartDest = view.findViewById(R.id.inputStartDest);
        navnIndput = view.findViewById(R.id.navnIndput);

        addButton = view.findViewById(R.id.addButton);
        addButton.setOnClickListener(this);

        besaetning = view.findViewById(R.id.besaetningList);

        besaetning.setLayoutManager(new LinearLayoutManager(getActivity()));
        besaetning.setAdapter(adapter);




        return builder.create();

    }

    RecyclerView.Adapter adapter = new RecyclerView.Adapter<ListeelemViewholder>() {
        @Override
        public int getItemCount()  {
            return besaetningList.size();
        }

        @Override
        public ListeelemViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.opret_etape_dialog_box_list_element, parent, false);
            ListeelemViewholder vh = new ListeelemViewholder(view);
            vh.nameElement = view.findViewById(R.id.listElementBesaetning);
            vh.cancleBeseatning = view.findViewById(R.id.cancleBeseatning);
            vh.cancleBeseatning.setOnClickListener(vh);
            vh.cancleBeseatning.setBackgroundResource(android.R.drawable.list_selector_background);
            return vh;
        }

        @Override
        public void onBindViewHolder(ListeelemViewholder vh, int position) {
            vh.nameElement.setText(besaetningList.get(position));
            vh.cancleBeseatning.setImageResource(android.R.drawable.ic_delete);
        }
    };


    @Override
    public void onClick(View v) {
        besaetningList.add(navnIndput.getText().toString());
        System.out.println(navnIndput.getText().toString());
        adapter.notifyDataSetChanged();
    }




    class ListeelemViewholder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView nameElement;
        ImageView cancleBeseatning;

        public ListeelemViewholder(View itemView) {
            super(itemView);
        }


        @Override
        public void onClick(View v) {
            final int position = getAdapterPosition();
            final String besaetningsmedlem = besaetningList.get(position);

            if(v ==cancleBeseatning) {

                besaetningList.remove(position);
                adapter.notifyItemRemoved(position);
            }
        }
    }

}


