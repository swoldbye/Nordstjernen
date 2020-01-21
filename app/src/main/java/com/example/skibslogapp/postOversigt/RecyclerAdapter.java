package com.example.skibslogapp.postOversigt;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skibslogapp.GlobalContext;
import com.example.skibslogapp.R;
import com.example.skibslogapp.model.Logpunkt;
import com.example.skibslogapp.view.redigerlogpunkt.RedigerLogpunkt_frag;

import java.text.SimpleDateFormat;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Logpunkt> mTempLogs;
    private boolean fillerCardEnabled = false;


    public static class NoteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private View view;
        private Logpunkt logpunkt;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            this.view = itemView;
            itemView.setOnClickListener(this);
        }

        public void setLogpunkt(Logpunkt logpunkt, boolean mob) {
            this.logpunkt = logpunkt;


            SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm");

            if( mob ){
                ((TextView) view.findViewById(R.id.MOBTidTextView)).setText(localDateFormat.format(logpunkt.getDate()));
            }else{
                TextView tidTextView = itemView.findViewById(R.id.tidTextView);
                TextView vindretningTextView = itemView.findViewById(R.id.vindretningTextView);
                TextView kursTextView = itemView.findViewById(R.id.kursTextView);
                TextView sejlføringTextView = itemView.findViewById(R.id.sejlføringTextView);
                TextView sejlstillingTextView = itemView.findViewById(R.id.sejlstillingTextView);
                TextView noteTextView = itemView.findViewById(R.id.NoteTextView);

                tidTextView.setText(localDateFormat.format(logpunkt.getDate()));
                vindretningTextView.setText(logpunkt.getVindretning());
                kursTextView.setText(logpunkt.getKursString());
                sejlføringTextView.setText(logpunkt.getSejlfoering());
                sejlstillingTextView.setText(logpunkt.getSejlstilling());
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


    public static class FillerViewHolder extends RecyclerView.ViewHolder{
        public FillerViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public RecyclerAdapter(List<Logpunkt> tempLogs) {
        mTempLogs = tempLogs;
    }

    void toggleFillerCard(boolean toggle){
        if(!toggle){
            notifyItemRemoved(getItemCount()-1);
        }
        fillerCardEnabled = toggle;
        if(toggle){
            notifyItemInserted(getItemCount()-1);
        }
    }

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
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.mob_note_layout, parent, false);
                return new NoteViewHolder(v);
            case 2:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.logpunktoversigt_filler_card, parent,false);
                return new FillerViewHolder(v);
            default:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.standard_note_layout, parent, false);
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

    public void updateData(List<Logpunkt> newLogs){
        mTempLogs = newLogs;
        //notifyItemRangeRemoved(0, mTempLogs.size() - 1);
        notifyItemInserted(mTempLogs.size()-1);
        //notifyItemInserted(mTempLogs.size() - 1);
    }
}
