package com.example.skibslogapp.viewControl;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.skibslogapp.Model.LogInstans;
import com.example.skibslogapp.R;

import java.util.List;

public class PostListAdapter extends ArrayAdapter<LogInstans> {

    private static final String TAG = "PostLIstAdapter";

    private Context mContext;
    int mResource;

    public PostListAdapter(@NonNull Context context, int resource, @NonNull List<LogInstans> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String tid = getItem(position).getTid();
        String vindretning = getItem(position).getVindretning();
        String kurs = getItem(position).getKurs();
        String sejlføring = getItem(position).getSejlføring();
        String sejlstilling = getItem(position).getSejlstilling();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        if (position % 2 == 1) {
            convertView.setBackgroundResource(R.color.offWhite);
        }

        TextView tidTextView = convertView.findViewById(R.id.tidTextView);
        TextView vindretningTextView = convertView.findViewById(R.id.vindretningTextView);
        TextView kursTextView = convertView.findViewById(R.id.kursTextView);
        TextView sejlføringTextView = convertView.findViewById(R.id.sejlføringTextView);
        TextView sejlstillingTextView = convertView.findViewById(R.id.sejlstillingTextView);

        tidTextView.setText(tid);
        vindretningTextView.setText(vindretning);
        kursTextView.setText(kurs);
        sejlføringTextView.setText(sejlføring);
        sejlstillingTextView.setText(sejlstilling);

        return convertView;
    }
}
