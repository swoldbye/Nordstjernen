package com.example.skibslogapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skibslogapp.model.Togt;

import java.util.ArrayList;

public class TogtListAdapter extends RecyclerView.Adapter<TogtListAdapter.TogtListViewHolder> {

    private ArrayList<Togt> togtArrayList;

    public static class TogtListViewHolder extends RecyclerView.ViewHolder {

        public TextView togtName;

        public TogtListViewHolder(@NonNull View itemView) {
            super(itemView);
            togtName = itemView.findViewById(R.id.togtNameListItem);
        }
    }

    public TogtListAdapter(ArrayList<Togt> list) {
        togtArrayList = list;
    }

    @NonNull
    @Override
    public TogtListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.togt_list_item, parent,false);
        TogtListViewHolder viewHolder = new TogtListViewHolder(view);
        return viewHolder;
    }

    /**
     *
     * @param holder
     * @param position what item that is looked at
     */
    @Override
    public void onBindViewHolder(@NonNull TogtListViewHolder holder, int position) {
        Togt currTogt = togtArrayList.get(position);
        holder.togtName.setText(currTogt.getName());
    }

    /**
     * Returns the amount of items in the list, which is the size of the ArrayList
     *
     */
    @Override
    public int getItemCount() {
        return togtArrayList.size();
    }
}
