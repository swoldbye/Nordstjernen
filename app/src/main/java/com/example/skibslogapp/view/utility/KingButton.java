package com.example.skibslogapp.view.utility;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatButton;

import com.example.skibslogapp.R;

import java.util.ArrayList;

public class KingButton extends AppCompatButton {
    private ArrayList<KingButton> relations = new ArrayList<>();
    int basicColor = getResources().getColor(R.color.grey);
    int standOutColor = getResources().getColor(R.color.colorPrimary);
    boolean isSelected;

    public KingButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void addRelation(KingButton newRelation) {
        relations.add(newRelation);
    }

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

    public void kingDeselected() {
        isSelected = false;
        Resources res = this.getContext().getResources();
        setTextColor(standOutColor);
        setBackgroundTintList(res.getColorStateList(R.color.grey));
    }

    public boolean isSelected() {
        return isSelected;
    }
}
