package com.example.skibslogapp.view.togtoversigt;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skibslogapp.GlobalContext;
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
public class TogtOversigt_frag extends Fragment implements View.OnClickListener, TogtDAO.TogtObserver {

    private RecyclerView recyclerView;
    private TogtListAdapter listAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_togt_oversigt, container, false);

        View opretTogt = view.findViewById(R.id.opretTogtBtn);
        opretTogt.setOnClickListener(this);

        recyclerView = view.findViewById(R.id.togtRecycView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        listAdapter = new TogtListAdapter();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(listAdapter);

        TogtDAO.addTogtObserver(this);

        return view;
    }

    /**
     * This function changes the fragment to the "OpretTogt" fragment
     * @param v The floating button View
     */
    @Override
    public void onClick(View v) {
        getActivity().getSupportFragmentManager()
            .beginTransaction()
            .setCustomAnimations(R.anim.slide_upslow2, R.anim.slide_upslow, R.anim.slide_downslow2, R.anim.slide_downslow)
            .add(R.id.fragContainer, new OpretTogt_frag())
            .addToBackStack(null)
            .commit();
    }

    @Override
    public void onUpdate(Togt togt) {
        listAdapter.updateTogter();
        recyclerView.smoothScrollToPosition(listAdapter.getItemCount());
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        TogtDAO.removeTogtObserver(this);
    }
}
