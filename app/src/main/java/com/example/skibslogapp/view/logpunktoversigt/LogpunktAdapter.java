package com.example.skibslogapp.view.logpunktoversigt;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skibslogapp.R;
import com.example.skibslogapp.utility.DateToString;
import com.example.skibslogapp.model.Logpunkt;
import com.example.skibslogapp.view.redigerlogpunkt.RedigerLogpunkt_frag;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class LogpunktAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    /**
     * A listadapter to set the views of the recyclerlist holding log-points.
     */

    private List<Logpunkt> mTempLogs;
    private boolean fillerCardEnabled = false;

    public static class NoteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private View view;
        private Logpunkt logpunkt;

        NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            this.view = itemView;
            itemView.setOnClickListener(this);
        }

        void setLogpunkt(Logpunkt logpunkt, boolean mob) {
            this.logpunkt = logpunkt;

            if( mob ){
                ((TextView) view.findViewById(R.id.MOBTidTextView)).setText(DateToString.time(logpunkt.getDate()));
            }else{
                TextView tidTextView = itemView.findViewById(R.id.tidTextView);
                TextView vindTextView = itemView.findViewById(R.id.vindretningTextView);
                TextView kursTextView = itemView.findViewById(R.id.kursTextView);
                TextView sejlfoeringTextView = itemView.findViewById(R.id.sejlføringTextView);
                TextView sejlstillingTextView = itemView.findViewById(R.id.sejlstillingTextView);
                TextView stroemTextView = itemView.findViewById(R.id.stroemTextView);
                TextView noteTextView = itemView.findViewById(R.id.NoteTextView);

                /*
                The following sets the card view depending on what points have been entered
                into the log-point.
                 */

                //Set time
                tidTextView.setText(DateToString.time(logpunkt.getDate()));

                //Set vind
                String vindHastighed = Integer.toString(logpunkt.getVindhastighed());
                if(!logpunkt.getVindretning().equals(null)){
                    if(logpunkt.getVindhastighed() == -1){
                        vindTextView.setText(logpunkt.getVindretning());
                    }
                    else{
                        vindTextView.setText(logpunkt.getVindretning() + "-" + vindHastighed);
                    }
                }else if(logpunkt.getVindhastighed() != -1){
                    vindTextView.setText(vindHastighed);
                }

                //Set Stroem
                String stroemHastighed = Integer.toString(logpunkt.getStroemhastighed());
                if(!logpunkt.getStroemRetning().equals(null)){
                    if(logpunkt.getStroemhastighed() == -1){
                        stroemTextView.setText(logpunkt.getStroemRetning());
                    }
                    else{
                        stroemTextView.setText(logpunkt.getStroemRetning() + "-" + stroemHastighed);
                    }
                }else if(logpunkt.getStroemhastighed() != -1){
                    stroemTextView.setText(stroemHastighed);
                }

                //Set føring
                sejlfoeringTextView.setText(logpunkt.getSejlfoering());

                //Set stilling and Roere
                if(logpunkt.getRoere() != -1){
                    sejlstillingTextView.setText(Integer.toString(logpunkt.getRoere()));
                }else{
                    sejlstillingTextView.setText(logpunkt.getSejlstilling());
                }

                //Set kurs

                if(!logpunkt.getKursString().equals("")){
                    kursTextView.setText(logpunkt.getKursString() + "°");
                }else{
                    kursTextView.setText("");
                }

                //Set note
                noteTextView.setText(logpunkt.getNote());
            }
        }

        @Override
        public void onClick(View view) {
            AppCompatActivity activity = (AppCompatActivity) view.getContext();
            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(
                            R.anim.enter_right_to_left,
                            R.anim.exit_right_to_left,
                            R.anim.enter_left_to_right,
                            R.anim.exit_left_to_right)
                    .replace(R.id.fragContainer, new RedigerLogpunkt_frag(logpunkt))
                    .addToBackStack(null)
                    .commit();
        }
    }


    /**
     * View Holder to simply fill the bottom of the list, when the
     * OpretLog needs to be shown. Used to "minimize" the list. */
    public static class FillerViewHolder extends RecyclerView.ViewHolder{
        FillerViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }


    LogpunktAdapter(List<Logpunkt> tempLogs) {
        tempLogs.sort(Logpunkt::compareTo);
        mTempLogs = tempLogs;
    }

    /**
     * Toggle the Filler Card in the bottom of the list, which fills
     * the bottom of the list, esentially "minimizing" it.
     * @param toggle True: "Show" filler card (minimize list), False: "Hide" filler card (maximize list)
     */
    void toggleFillerCard(boolean toggle){
        if(!toggle){
            notifyItemRemoved(getItemCount()-1);
        }
        fillerCardEnabled = toggle;
        if(toggle){
            notifyItemInserted(getItemCount()-1);
        }
    }

    /**
     * In order to reduce the size of the list, such that the entire list is visible from the small
     * window that is left after the "opretlogpunk" fragment comes in from the bottom.
     *
     * A "fillerCard" with the same size as the "opretlogpunkt" fragment is placed at the bottom of the list.
     *
     * Otherwise the itemView will either return a normal logpoint view or a MOB (mand over bord) view.
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        if( fillerCardEnabled && (position+1) == getItemCount()){
            return 2; // Filler
        }
        Logpunkt current = mTempLogs.get(position);
        if(current.getMandOverBord()){
            return 1;
        }else {
            return 0;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        switch (viewType){
            case 1:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.logpunktoversigt_card_mob, parent, false);
                return new NoteViewHolder(v);
            case 2:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.logpunktoversigt_card_filler, parent,false);
                return new FillerViewHolder(v);
            default:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.logpunktoversigt_card_standard, parent, false);
                return new NoteViewHolder(v);
        }
    }



    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()){
            case 2:
                break;
            case 1:
                Logpunkt currentMOB = mTempLogs.get(position);
                ((NoteViewHolder) holder).setLogpunkt(currentMOB, true);
                break;
            default:
                Logpunkt current = mTempLogs.get(position);
                ((NoteViewHolder) holder).setLogpunkt(current, false);
                break;
        }
    }



    @Override
    public int getItemCount() {
        return mTempLogs.size() + (fillerCardEnabled ? 1 : 0) ;
    }


    /**
     * Method takes a new array of logpoints that need to be updated on the list. New logpoint
     * will be at the end of the list, therefore the adapter is notified about this.
     *
     * @param newLogs
     */
    void updateData(List<Logpunkt> newLogs){
        newLogs.sort(Logpunkt::compareTo);
        mTempLogs = newLogs;
        notifyItemInserted(mTempLogs.size()-1);
    }
}
