package com.example.skibslogapp.view.utility;

import android.widget.TextView;

/**
 * Helper class to move text when adding vindretning and Strømningsretning i opret_fraq.*
 */

public class MoveButtons {

    public static void setText(TextView oleText, TextView newText){
        if(!oleText.getText().equals("")) {
            newText.setText(oleText.getText()+":");
            oleText.setText("");
        }
    }

    public static void revertText(TextView oleText, TextView newText){
        CharSequence stringTorevert = newText.getText().subSequence(0,newText.getText().length()-1);

        oleText.setText(stringTorevert);
        newText.setText("");
    }


}
