package com.example.skibslogapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.skibslogapp.model.Logpunkt;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PostListAdapter extends ArrayAdapter<Logpunkt> {

    private static final String TAG = "PostLIstAdapter";

    private Context mContext;
    int mResource;

    public PostListAdapter(@NonNull Context context, int resource, @NonNull List<Logpunkt> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Logpunkt logpunkt = getItem(position);

        // Time
        Date date = logpunkt.getDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        String tid = String.format( "%02d:%02d", calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
        String vindretning = logpunkt.getVindretning();
        String sejlfoering = logpunkt.getSejlfoering();
        String sejlstilling = logpunkt.getSejlstilling();
        String kurs = logpunkt.getKurs() > -1 ? Integer.toString(logpunkt.getKurs()) : "";
        String note = logpunkt.getNote();

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
        TextView noteTextView = convertView.findViewById(R.id.NoteTextView);

        tidTextView.setText(tid);
        vindretningTextView.setText(vindretning);
        kursTextView.setText(kurs);
        sejlføringTextView.setText(sejlfoering);
        sejlstillingTextView.setText(sejlstilling);
        noteTextView.setText(note);

        return convertView;
    }
}
