package com.example.skibslogapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.skibslogapp.model.LogInstans;
import com.example.skibslogapp.view.OpretLog_frag;

public class PostActivity extends Fragment implements PostOversigt.OnPostOversigtListener, OpretLog_frag.OnMainActivityListener {

    private static final String TAG = "PostActivity";

    int position;
    OpretLog_frag opretLog_frag;
    PostOversigt postOversigt = new PostOversigt(this);

    public PostActivity() {
    }

    public PostActivity(int position) {
        this.position = position;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_post, container, false);

        Log.d(TAG, "onCreateView: Started.");

        addOversigtFrag();
        return view;
    }


    private void addOversigtFrag(){
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.add(R.id.postOversigtContainerFrame, postOversigt).commit();
    }

    @Override
    public void hideOpretPost() {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .remove(opretLog_frag)
                .commit();
    }

    @Override
    public void showOpretPost() {
        opretLog_frag = new OpretLog_frag(this);
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.opretPostContainerFrame, opretLog_frag)
                .commit();
    }

    @Override
    public void updateList(LogInstans nyeste) {
        postOversigt.setList(nyeste);
    }
}
