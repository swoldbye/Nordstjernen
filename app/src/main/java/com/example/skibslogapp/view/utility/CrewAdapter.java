package com.example.skibslogapp.view.utility;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skibslogapp.OpretEtapeDialogBox;
import com.example.skibslogapp.R;
import com.example.skibslogapp.TogtListAdapter;

import java.util.List;

public class CrewAdapter extends RecyclerView.Adapter<CrewAdapter.ListeelemViewholder> {
    private List<String> dataSet;
    private Context context;


CrewAdapter(List<String> dataSet, Context context){
        this.dataSet = dataSet;
        this.context = context;
    }

    @NonNull
    @Override
    public ListeelemViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.opret_etape_dialog_box_list_element, parent, false);
        ListeelemViewholder vh = new ListeelemViewholder(view);

        return vh;
    }


    @Override
    public void onBindViewHolder(@NonNull ListeelemViewholder holder, int position) {
        holder.nameElement.setText(dataSet.get(position));
        holder.cancleBeseatning.setImageResource(android.R.drawable.ic_delete);

    }


    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    class ListeelemViewholder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView nameElement;
        ImageView cancleBeseatning;

        public ListeelemViewholder(View itemView) {
            super(itemView);
        }


        @Override
        public void onClick(View v) {
            final int position = getAdapterPosition();

            if(v ==cancleBeseatning) {
                CrewAdapter.this.notifyItemRemoved(position);
                dataSet.remove(position);

            }
        }
    }

}
