package com.example.skibslogapp.etapeoversigt;


import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skibslogapp.R;
import com.example.skibslogapp.model.Etape;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class EtapeListAdapter extends RecyclerView.Adapter<EtapeListAdapter.EtapeListViewHolder> {

    private List<Etape> etaper;

    EtapeListAdapter(List<Etape> etaper){
        this.etaper = etaper;
    }

    public void addEtape(Etape etape){
        etaper.add(etape);
        notifyItemInserted(etaper.size()-1);
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
        holder.setEtape( etaper.get(position) );
    }


    @Override
    public int getItemCount() {
        return etaper.size();
    }




    class EtapeListViewHolder extends RecyclerView.ViewHolder{
        private Etape etape;
        private CardView cardView;

        EtapeListViewHolder(CardView cardView) {
            super(cardView);
            this.cardView = cardView;
        }

        // Updates the etape of the ViewHolder, changes the date to fit the Etape
        void setEtape(Etape etape){
            this.etape = etape;

            // Update the card information
            Calendar cal = Calendar.getInstance();
            cal.setTime(etape.getStartDate());

            String dateString = String.format( Locale.US, "%02d/%02d %02d:%02d",
                    cal.get(Calendar.DAY_OF_MONTH),
                    cal.get(Calendar.MONTH)+1,
                    cal.get(Calendar.HOUR_OF_DAY),
                    cal.get(Calendar.MINUTE));

            ((TextView) cardView.findViewById(R.id.etapeDate)).setText(dateString);
        }
    }
}
