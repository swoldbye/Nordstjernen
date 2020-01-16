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

    public static class OpretLogHolder extends RecyclerView.ViewHolder {
        public OpretLogHolder(@NonNull View itemView) {
            super(itemView);
        }
    }


    @Override
    public int getItemViewType(int position) {
        if (position < (getItemCount() - 1)) {
            return 0;
        } else {
            return 1;
        }
    }

    public RecyclerAdapter(List<Logpunkt> tempLogs) {
        mTempLogs = tempLogs;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_note_layout, parent, false);
                return new NoteViewHolder(v);
            case 1:
                View v2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_opret_log, parent, false);
                return new OpretLogHolder(v2);
        }
        return null;
    }


    SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm");


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()){
            case 0:
                NoteViewHolder noteViewHolder = (NoteViewHolder) holder;
                Logpunkt current = mTempLogs.get(position);
                noteViewHolder.tidTextView.setText(localDateFormat.format(current.getDate()));
                noteViewHolder.vindretningTextView.setText(current.getVindretning());
                noteViewHolder.kursTextView.setText(current.getKursString());
                noteViewHolder.sejlføringTextView.setText(current.getSejlfoering());
                noteViewHolder.sejlstillingTextView.setText(current.getSejlstilling());
                noteViewHolder.noteTextView.setText(current.getNote());
                break;
            case 1:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mTempLogs.size() + 1;
    }
}
