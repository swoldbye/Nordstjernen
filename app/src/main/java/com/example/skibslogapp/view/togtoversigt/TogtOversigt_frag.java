package com.example.skibslogapp.view.togtoversigt;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skibslogapp.R;
import com.example.skibslogapp.datalayer.local.TogtDAO;
import com.example.skibslogapp.model.Togt;
import com.example.skibslogapp.view.oprettogt.OpretTogt_frag;

import java.util.List;

/**
 * This fragment contanins a recycleview with the created "Togts". You can click a  list element to
 * enter the "Togt" to se the log posts of the given "Togt".
 * You can click a button on the element to erase the "Togt" from the recycleView List and the database.
 */
public class TogtOversigt_frag extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_togt_oversigt, container, false);

        View opretTogt = view.findViewById(R.id.opretTogtBtn);
        opretTogt.setOnClickListener(this);

        TogtDAO togtDAO = new TogtDAO(getContext());
        List<Togt> togtList = togtDAO.getTogter();

        RecyclerView recyclerView = view.findViewById(R.id.togtRecycView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        RecyclerView.Adapter adapter = new TogtListAdapter(togtList,getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return view;
    }

    /**
     * This function changes the fragment to the "OpretTogt" fragment
     * @param v The floating button View
     */
    @Override
    public void onClick(View v) {
        OpretTogt_frag opretTogt_frag = new OpretTogt_frag();
        changeFragment(opretTogt_frag);
    }

    /**
     * Helper function for the onClick() function.
     *
     * @param fragment The fragment that will be changed to.
     */
    private void changeFragment(Fragment fragment){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragContainer,fragment);
        fragmentTransaction.commit();
    }
}
