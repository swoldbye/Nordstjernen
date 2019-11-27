package com.example.skibslogapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.skibslogapp.model.LogInstans;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class PostOversigt extends Fragment implements View.OnClickListener{

    ListView postListView;
    Button openCloseButton;
    OnPostOversigtListener mCallback;
    ArrayList<LogInstans> tempLogs = new ArrayList<>();
    TabLayout tabLayout;
    TabItem item1, item2, item3, item4, item5;
    ViewPager viewPager;
    PageAdapter pageAdapter;

    public PostOversigt(OnPostOversigtListener mCallback){
        this.mCallback = mCallback;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.post_oversigt_frag, container, false);

        openCloseButton = view.findViewById(R.id.openCloseButton);
        openCloseButton.setOnClickListener(this);

        tabLayout = view.findViewById(R.id.tabLayout);
        item1 = view.findViewById(R.id.tab1);
        item2 = view.findViewById(R.id.tab2);
        item3 = view.findViewById(R.id.tab3);
        item4 = view.findViewById(R.id.tab4);
        item5 = view.findViewById(R.id.tab5);
        viewPager = view.findViewById(R.id.viewPager);







        postListView = view.findViewById(R.id.postListView);
        tempLogs.add(new LogInstans("11:34", "SSØ", "005", "F", "LÆ"));
        tempLogs.add(new LogInstans("11:35", "N", "006", "Ø", "AG"));
        tempLogs.add(new LogInstans("11:37", "NØ", "026", "F", "BI"));
        tempLogs.add(new LogInstans("12:00", "SØ", "010", "F", "FO"));
        tempLogs.add(new LogInstans("12:32", "NV", "500", "N1", "HA"));
        tempLogs.add(new LogInstans("12:35", "NVN", "234", "F", "HA"));
        tempLogs.add(new LogInstans("12:50", "SSØ", "543", "N2", "LÆ"));
        tempLogs.add(new LogInstans("13:30", "SSØ", "345", "F", "FO"));
        tempLogs.add(new LogInstans("13:34", "SSØ", "453", "N#", "AG"));
        tempLogs.add(new LogInstans("14:00", "SSØ", "023", "F", "BI"));

        //tempLogs.addAll(Togt.getTogter());

        PostListAdapter adapter = new PostListAdapter(getActivity(), R.layout.postlist_view_layout, tempLogs);
        postListView.setAdapter(adapter);

        return view;
    }

    public void setList(LogInstans nyeste){
        tempLogs.add(nyeste);
        PostListAdapter adapter = new PostListAdapter(getActivity(), R.layout.postlist_view_layout, tempLogs);
        postListView.setAdapter(adapter);
        openCloseButton.setText("open");
    }

    public void onClick(View v){
        if(v == openCloseButton){
            if(openCloseButton.getText().toString().equals("open")) {
                mCallback.showOpretPost();
                openCloseButton.setText("close");
            }else{
                mCallback.hideOpretPost();
                openCloseButton.setText("open");
            }
        }
    }

    public interface OnPostOversigtListener{
        void hideOpretPost();
        void showOpretPost();
    }
}
