package com.example.skibslogapp.view.utility;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatButton;

import com.example.skibslogapp.R;

import java.util.ArrayList;

/**
 * Class to implement RadioButton logic unto our Buttons with our desired visuals.
 * Logic derived from there can only be one King, but multiple realms can have multiple Kings (if the King doesn't know about another King, it is not a problem).
 */
public class KingButton extends AppCompatButton {
    private ArrayList<KingButton> relations = new ArrayList<>();
    int basicColor = getResources().getColor(R.color.grey);
    int standOutColor = getResources().getColor(R.color.colorPrimary);
    boolean isSelected;

    public KingButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Adds a relation between this and another KingButton
     *
     * @param newRelation   KingButton to get relation to
     */
    public void createRelation(KingButton newRelation) {
        relations.add(newRelation);
        newRelation.addRelation(this);
    }

    /**
     * Adds a relation to another KingButton (the other KingButton might not know this KingButton)
     *
     * @param newRelation   KingButton to get relation to
     */
    public void addRelation(KingButton newRelation) {
        relations.add(newRelation);
    }

    /**
     * Switches this KingButton to be 'King' and dethrones (deselects) the previously 'King'
     * or
     * Deselects this KingButton as 'King'
     */
    public void kingSelected() {
        if(isSelected) kingDeselected(); //Dethrones the king, essentially resetting the button
        else {
            isSelected = true;
            Resources res = this.getContext().getResources();
            setTextColor(Color.WHITE);
            setBackgroundTintList(res.getColorStateList(R.color.colorPrimary));
            for(KingButton btn : relations) { btn.kingDeselected(); }
        }
    }

    /**
     * Sets the visuals of the KingButton to be deselected
     */
    public void kingDeselected() {
        isSelected = false;
        Resources res = this.getContext().getResources();
        setTextColor(standOutColor);
        setBackgroundTintList(res.getColorStateList(R.color.grey));
    }

    /**
     * Returns true if KingButton is current 'King'.

     * @return  true if selected, else false
     */
    public boolean isSelected() {
        return isSelected;
    }
}
