package com.example.skibslogapp.view.opretEtape;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skibslogapp.R;


import java.util.List;

public class CrewAdapter extends RecyclerView.Adapter<CrewAdapter.ListeelemViewholder> {
    private List<String> dataSet;
    private Context context;


   public CrewAdapter(List<String> dataSet, Context context){
        this.dataSet = dataSet;
        this.context = context;
    }

    @NonNull
    @Override
    public ListeelemViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.opret_etape_dialog_box_list_element, parent, false);

        ListeelemViewholder vh = new ListeelemViewholder(view);
        vh.nameText = view.findViewById(R.id.besaetningslist_navn);
        vh.removeButton = view.findViewById(R.id.besaetningslist_cancel);
        vh.removeButton.setOnClickListener(vh);
        vh.removeButton.setBackgroundResource(android.R.drawable.list_selector_background);
        return vh;
    }


    @Override
    public void onBindViewHolder(@NonNull ListeelemViewholder holder, int position) {
        holder.nameText.setText(dataSet.get(position));
    }


    @Override
    public int getItemCount() {
        return dataSet.size();
    }


    class ListeelemViewholder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView nameText;
        ImageView removeButton;

        public ListeelemViewholder(View itemView) {
            super(itemView);
        }


        @Override
        public void onClick(View v) {
            final int position = getAdapterPosition();

            if(v == removeButton) {
                CrewAdapter.this.notifyItemRemoved(position);
                dataSet.remove(position);

            }
        }
    }

}
