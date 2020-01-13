package com.example.skibslogapp;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skibslogapp.model.Togt;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TogtListAdapter extends RecyclerView.Adapter<TogtListAdapter.TogtListViewHolder> {

    private List<Togt> togtArrayList;
//    private OnTogtListener togtListener;

    public TogtListAdapter(List<Togt> list) {
        togtArrayList = list;
//        this.togtListener = onTogtListener;
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
    public void onBindViewHolder(@NonNull TogtListViewHolder holder, final int position) {
        Togt currTogt = togtArrayList.get(position);
        holder.togtName.setText(currTogt.getName());
    }

    /**
     * Returns the amount of items in the list, which is the size of the ArrayList.
     * If this returns 0, the list and the recycleview will be empty
     */
    @Override
    public int getItemCount() {
        return togtArrayList.size();
    }

    /**
     *
     * @param position
     */
    public void delete(int position){
        togtArrayList.remove(position);
        notifyItemRemoved(position);
    }

    public class TogtListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView togtName;
        ImageView delete;

        public TogtListViewHolder(@NonNull View itemView) {
            super(itemView);
            togtName = itemView.findViewById(R.id.togtNameListItem);
//            edit = itemView.findViewById(R.id.togtEdit);
            delete = itemView.findViewById(R.id.togtDelete);
            delete.setOnClickListener(this::onClick);

        }

        @Override
        public void onClick(View v) {
            delete(getAdapterPosition());
        }
    }
}














