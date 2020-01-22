package com.example.skibslogapp.etapeoversigt;

import android.content.res.Resources;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skibslogapp.GlobalContext;
import com.example.skibslogapp.R;
import com.example.skibslogapp.model.DateToString;
import com.example.skibslogapp.model.Etape;
import com.example.skibslogapp.postOversigt.PostActivity;

import java.util.Date;
import java.util.List;


/** List Adapter for Etape Oversigt
 *  It splits cards into two types: etape card and destinations (esentially not cards). Every second
 *  element in the list is an etape card (i.e. Destination, Etape, Destination ... ).
 */
public class EtapeListAdapter extends RecyclerView.Adapter<EtapeListAdapter.EtapeListViewHolder> {

    private List<Etape> etaper;

    EtapeListAdapter(List<Etape> etaper){
        this.etaper = etaper;
    }

    // Update the list of Etaper to display
    void updateEtapeList(List<Etape> etaper){
        this.etaper = etaper;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        // The list holds a destination for each etape, thus its "double in size"
        return etaper.size()*2;
    }


    @Override
    public int getItemViewType(int position) {
        // 0 == Destination and 1 == Etape
        return position % 2;
    }

    @NonNull
    @Override
    public EtapeListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        EtapeListViewHolder viewHolder;
        if( viewType == 0 ){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.etape_list_destination, parent, false);
            viewHolder = new EtapeListViewHolder(view, false);
        }else{
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.etape_list_card, parent, false);
            viewHolder = new EtapeListViewHolder(view, true);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull EtapeListViewHolder holder, int position) {
        if( getItemViewType(position) == 0 ){
            Etape etape = etaper.get( (position)/2 );
            holder.setDestination( etape.getStartDestination(), etape.getStartDate(), position == 0);
        }
        if( getItemViewType(position) == 1){
            int etapePosition = (position-1)/2;
            holder.position = etapePosition;
            holder.setEtape( etaper.get(etapePosition), etapePosition+1);
        }
    }




    /**
     * The view holder for the EtapeList.
     * It holds both kind of cards (Etape cards and Destination cards). The difference
     * lies in the view its inflated with and the method used to update it ( setDestination(..)
     * or setEtape(..) )
     */
    class EtapeListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private Etape etape;
        private View view;
        private int position;

        EtapeListViewHolder(View view, boolean isEtapeCard){
            super(view);
            this.view = view;
            // Only Etape cards are clickable
            if(isEtapeCard){
                view.setOnClickListener(this);
            }
        }


        /** Set / update the Etape for the view holder (this means its an Etape card) */
        void setEtape(Etape etape, int listIndex){
            this.etape = etape;

            String indexString = Integer.toString(listIndex);
            String skipper = "<b>"+etape.getSkipper()+"</b>";
            String besaetning = "<b>" + etape.getBesaetning().size() + "</b>";

            // Update Text of Card
            ((TextView) view.findViewById(R.id.etape_number_text)).setText(indexString);
            ((TextView) view.findViewById(R.id.skipperTextEtapeKort)).setText(Html.fromHtml("Skipper: "+skipper));
            ((TextView) view.findViewById(R.id.besaetningTextEtapeKort)).setText(Html.fromHtml("Besætning: "+besaetning));

            // Color / Design of card is changed if its the currently ACTIVE Etape
            Resources res = GlobalContext.get().getResources();
            if( etape.getStatus() == Etape.Status.ACTIVE ){
                ((CardView) view.findViewById(R.id.etape_card)).setCardBackgroundColor(res.getColor(R.color.colorAccent));
                ((CardView) view.findViewById(R.id.etape_list_number_card)).setCardBackgroundColor(res.getColor(R.color.colorPrimary));
                ((TextView) view.findViewById(R.id.etape_number_text)).setTextColor(res.getColor(R.color.white));
            }else{
                ((CardView) view.findViewById(R.id.etape_card)).setCardBackgroundColor(res.getColor(R.color.offWhite));
                ((CardView) view.findViewById(R.id.etape_list_number_card)).setCardBackgroundColor(res.getColor(R.color.offWhite));
                ((TextView) view.findViewById(R.id.etape_number_text)).setTextColor(res.getColor(R.color.colorPrimary));
            }
        }



        /** Set / Update the destination of the card (this means its a Destination Card) */
        void setDestination(String destination, Date date, boolean isFirst){
            // Don't show the dashed line for first distnation
            if( isFirst ){
                (view.findViewById(R.id.etape_list_destination_line)).setVisibility(View.GONE);
            }else{
                (view.findViewById(R.id.etape_list_destination_line)).setVisibility(View.VISIBLE);
            }

            // Update Text
            ((TextView) view.findViewById(R.id.etape_destination_text)).setText(destination == null ? "-" : destination);
            ((TextView) view.findViewById(R.id.etape_dato_text)).setText(DateToString.full(date));
        }


        @Override
        public void onClick(View view) {
            // Start Logpunktoversigt with current Etape
            AppCompatActivity activity = (AppCompatActivity) view.getContext();
            activity.getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(
                        R.anim.enter_right_to_left,
                        R.anim.exit_right_to_left,
                        R.anim.enter_left_to_right,
                        R.anim.exit_left_to_right)
                .replace(R.id.fragContainer, new PostActivity(etape, position))
                .addToBackStack(null)
                .commit();
        }
    }
}
