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
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.NoteViewHolder> {

    private List<Logpunkt> mTempLogs;

    public static class NoteViewHolder extends RecyclerView.ViewHolder{

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

    public RecyclerAdapter(List<Logpunkt> tempLogs){
        mTempLogs = tempLogs;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_note_layout, parent, false);
        NoteViewHolder nvh = new NoteViewHolder(v);
        return nvh;
    }

    SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm");

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Logpunkt current = mTempLogs.get(position);
        holder.tidTextView.setText(localDateFormat.format(current.getDate()));
        holder.vindretningTextView.setText(current.getVindretning());
        holder.kursTextView.setText(current.getKursString());
        holder.sejlføringTextView.setText(current.getSejlfoering());
        holder.sejlstillingTextView.setText(current.getSejlstilling());
        holder.noteTextView.setText(current.getNote());
    }

    @Override
    public int getItemCount() {
        return mTempLogs.size();
    }
}
