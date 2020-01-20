package com.example.skibslogapp.postOversigt;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skibslogapp.R;
import com.example.skibslogapp.model.Logpunkt;

import java.text.SimpleDateFormat;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Logpunkt> mTempLogs;
    private static boolean fillerCardEnabled = false;

    public static class NoteViewHolder extends RecyclerView.ViewHolder {

        TextView tidTextView;
        TextView vindretningTextView;
        TextView kursTextView;
        TextView sejlføringTextView;
        TextView sejlstillingTextView;
        TextView noteTextView;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            tidTextView = itemView.findViewById(R.id.tidTextView);
            vindretningTextView = itemView.findViewById(R.id.vindretningTextView);
            kursTextView = itemView.findViewById(R.id.kursTextView);
            sejlføringTextView = itemView.findViewById(R.id.sejlføringTextView);
            sejlstillingTextView = itemView.findViewById(R.id.sejlstillingTextView);
            noteTextView = itemView.findViewById(R.id.NoteTextView);
        }
    }

    public static class MOBViewHolder extends RecyclerView.ViewHolder{

        TextView tidTextView;

        public MOBViewHolder(@NonNull View itemView){
            super(itemView);
            tidTextView = itemView.findViewById(R.id.MOBTidTextView);
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

    public static void toggleFillerCard(boolean toggle){
        fillerCardEnabled = toggle;
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
                return new MOBViewHolder(v);
            case 2:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.logpunktoversigt_filler_card, parent,false);
                return new FillerViewHolder(v);
            default:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.standard_note_layout, parent, false);
                return new NoteViewHolder(v);
        }
    }


    SimpleDateFormat localDateFormat = new SimpleDateFormat("HH mm");

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()){
            case 2:
                break;
            case 1:
                Logpunkt currentMOB = mTempLogs.get(position);
                MOBViewHolder mobHolder = (MOBViewHolder) holder;
                mobHolder.tidTextView.setText(localDateFormat.format(currentMOB.getDate()));
                break;
            default:
                Logpunkt current = mTempLogs.get(position);
                NoteViewHolder noteHolder = (NoteViewHolder) holder;
                noteHolder.tidTextView.setText(localDateFormat.format(current.getDate()));
                noteHolder.vindretningTextView.setText(current.getVindretning());
                noteHolder.kursTextView.setText(current.getKursString());
                noteHolder.sejlføringTextView.setText(current.getSejlfoering());
                noteHolder.sejlstillingTextView.setText(current.getSejlstilling());
                noteHolder.noteTextView.setText(current.getNote());
                break;
        }
    }



    @Override
    public int getItemCount() {
        return mTempLogs.size() + (fillerCardEnabled ? 1 : 0) ;
    }

    public void updateData(List<Logpunkt> newLogs){
        mTempLogs = newLogs;
        notifyItemRangeRemoved(0, mTempLogs.size() - 1);
        //notifyItemInserted(mTempLogs.size() - 1);
    }
}
