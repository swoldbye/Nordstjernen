package com.example.skibslogapp.view;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.skibslogapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EtapeOversigt_frag extends Fragment {



    public EtapeOversigt_frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_etape_oversigt, container, false);
        // Inflate the layout for this fragment

        EtapeHeaderFrag_frag etapeHeaderFrag_frag = (EtapeHeaderFrag_frag)
                getChildFragmentManager().findFragmentById(R.id.etapeHeaderFragment);

        EtapeListFrag_frag etapeListFrag_frag = (EtapeListFrag_frag)
                getChildFragmentManager().findFragmentById(R.id.etapeListFragment);

        if (null == etapeHeaderFrag_frag){
            etapeHeaderFrag_frag = new EtapeHeaderFrag_frag();
            FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.headerFragment,etapeHeaderFrag_frag);
        }

        if (null == etapeListFrag_frag){
            etapeListFrag_frag = new EtapeListFrag_frag();
            FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.etapeRecycleViewFragment,etapeListFrag_frag);
        }

        return view;
    }

}
