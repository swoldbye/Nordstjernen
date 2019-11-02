package com.example.skibslogapp.viewControl;

import android.view.View;

import java.util.ArrayList;



public abstract class ToggleViewList implements View.OnClickListener {

    private View currentlyToggled = null;
    private ArrayList<View> views = new ArrayList<>();

    public ToggleViewList( View ... views ){
        for( View view : views ){
            if( view == null ){
                throw new Error("View is null");
            }
            view.setOnClickListener(this);
            this.views.add(view);
        }
    }

    public abstract void onViewToggled(View view);

    public abstract void onViewUntoggled(View view );

    @Override
    public void onClick(View view) {


        if( view == currentlyToggled){
            // The clicked view was the currently toggled view
            currentlyToggled = null;
            onViewUntoggled(view);

        }else{
            // Clicked view wasn't currently togglet

            // Disable currently toggled view
            if(  currentlyToggled != null)
                onViewUntoggled(currentlyToggled);

            currentlyToggled = view;

            // Activate new view as currently toglget
            onViewToggled( view);
        }
    }

    public View getToggledView(){
        return currentlyToggled;
    }
}