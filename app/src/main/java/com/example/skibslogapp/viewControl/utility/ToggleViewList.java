package com.example.skibslogapp.viewControl.utility;

import android.view.View;

import java.util.ArrayList;


/**
 * A generic class for creating a list of toggleable views.
 *
 * Adds click listeners to all given views, and calls the
 * onViewToggled() method whenever a button is toggled,
 * and onViewUntoggled() whenever a button is untoggled.
 *
 * Only 1 view may be toggled at a time, and clicking an
 * untoggled view automatically calls unViewUnToggled()
 * on the previously toggled view.
 */
public abstract class ToggleViewList implements View.OnClickListener {

    private View currentlyToggled = null;
    private ArrayList<View> views = new ArrayList<>();

    /**
     * Creates a new ToggleViewList with a variable
     * number of views.
     * @param views The views which should be added to the list.
     *              Must more than 0, and no views can be null.
     */
    public ToggleViewList( View ... views ){
        if( views.length == 0 ){
            throw new Error("No views given");
        }
        for( View view : views ){
            if( view == null ){
                throw new Error("View is null");
            }
            view.setOnClickListener(this);
            this.views.add(view);
        }
    }

    /**
     * Logic for clicking one of the listed views */
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

    /**
     * Retrieves the currently toggled view.
     * @return The toggled view, or null if no view is toggled.
     */
    public View getToggledView(){
        return currentlyToggled;
    }


    /**
     * Method called when a view is toggled. Implement whatever
     * logic you want to happen when either of the views are toggled
     * in this method.
     * @param view The view which has been toggled
     */
    public abstract void onViewToggled(View view);


    /**
     * Method called when a view is untoggled. Implement whatever
     * logic you want to happen when either of the views are untoggled
     * in this method.
     * @param view The view which has been toggled
     */
    public abstract void onViewUntoggled(View view );

}