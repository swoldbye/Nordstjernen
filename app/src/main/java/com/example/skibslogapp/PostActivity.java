package com.example.skibslogapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;

import com.example.skibslogapp.model.LogInstans;

public class PostActivity extends AppCompatActivity implements PostOversigt.OnPostOversigtListener, MainActivity.OnMainActivityListener {

    MainActivity mainActivity = new MainActivity();
    PostOversigt postOversigt = new PostOversigt();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        addOversigtFrag();
    }




    private void addOversigtFrag(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.postOversigtContainerFrame, postOversigt).commit();
    }

    @Override
    public void hideOpretPost() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.hide(mainActivity).commit();
    }

    @Override
    public void showOpretPost() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (mainActivity.isAdded()) {
            ft.show(mainActivity).commit();
        } else {
            ft.add(R.id.opretPostContainerFrame, mainActivity).commit();
        }
    }

    @Override
    public void updateList(LogInstans nyeste) {
        postOversigt.setList(nyeste);
    }
}
