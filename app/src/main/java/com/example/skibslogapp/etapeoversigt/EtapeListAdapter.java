package com.example.skibslogapp.etapeoversigt;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skibslogapp.R;
import com.example.skibslogapp.model.Etape;

import java.util.List;

public class EtapeListAdapter extends RecyclerView.Adapter<EtapeListAdapter.EtapeListViewHolder> {

    private List<Etape> etaper;

    EtapeListAdapter(List<Etape> etaper){
        this.etaper = etaper;
    }

    public void addEtape(Etape etape){
        etaper.add(etape);
    }

    @NonNull
    @Override
    public EtapeListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.etape_list_item, parent, false);
        return new EtapeListViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull EtapeListViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return etaper.size();
    }

    public class EtapeListViewHolder extends RecyclerView.ViewHolder{

        private CardView cardView;

        public EtapeListViewHolder(CardView cardView) {
            super(cardView);
            this.cardView = cardView;
        }
    }
}
