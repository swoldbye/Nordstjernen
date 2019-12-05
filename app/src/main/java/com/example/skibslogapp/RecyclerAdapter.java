package com.example.skibslogapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skibslogapp.model.LogInstans;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.NoteViewHolder> {

    private ArrayList<LogInstans> mTempLogs;

    public static class NoteViewHolder extends RecyclerView.ViewHolder{

        TextView tidTextView;
        TextView vindretningTextView;
        TextView kursTextView;
        TextView sejlføringTextView;
        TextView sejlstillingTextView;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            tidTextView = itemView.findViewById(R.id.tidTextView);
            vindretningTextView = itemView.findViewById(R.id.vindretningTextView);
            kursTextView = itemView.findViewById(R.id.kursTextView);
            sejlføringTextView = itemView.findViewById(R.id.sejlføringTextView);
            sejlstillingTextView = itemView.findViewById(R.id.sejlstillingTextView);

        }
    }

    public RecyclerAdapter(ArrayList<LogInstans> tempLogs){
        mTempLogs = tempLogs;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_note_layout, parent, false);
        NoteViewHolder nvh = new NoteViewHolder(v);
        return nvh;
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        LogInstans current = mTempLogs.get(position);
        holder.tidTextView.setText(current.getTid());
        holder.vindretningTextView.setText(current.getVindretning());
        holder.kursTextView.setText(current.getKurs());
        holder.sejlføringTextView.setText(current.getSejlføring());
        holder.sejlstillingTextView.setText(current.getSejlstilling());
    }

    @Override
    public int getItemCount() {
        return mTempLogs.size();
    }
}
