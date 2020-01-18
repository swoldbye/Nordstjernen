package com.example.skibslogapp.view.redigerlogpunkt;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.skibslogapp.R;
import com.example.skibslogapp.model.Logpunkt;

public class RedigerLogpunkt_frag extends Fragment {

    private Logpunkt logpunkt;

    public RedigerLogpunkt_frag(Logpunkt logpunkt){
        this.logpunkt = logpunkt;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rediger_logpunkt, container, false);
        ((TextView) view.findViewById(R.id.test_text)).setText(logpunkt.toString());
        return view;
    }
}
