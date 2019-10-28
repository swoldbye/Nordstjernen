package com.example.skibslogapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.example.skibslogapp.viewControl.OpretLog_frag;

public class MainActivity_akt extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null){
            Fragment fragment = new OpretLog_frag();
            getSupportFragmentManager().beginTransaction().add(R.id.fragContainer, fragment).commit();
        }
    }
}
