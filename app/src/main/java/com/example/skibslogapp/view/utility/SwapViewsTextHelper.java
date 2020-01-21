package com.example.skibslogapp.view.utility;

import android.widget.TextView;

/**
 * Helper class to move text when adding vindretning and Strømningsretning i opret_fraq.*
 */

public class SwapViewsTextHelper {

    public static void setText(TextView oldText, TextView newText){
        if(!oldText.getText().equals("")) {
            newText.setText(oldText.getText().toString().contains(":") ? oldText.getText() : oldText.getText()+":");
            oldText.setText("");
        }
    }

    public static void revertText(TextView oleText, TextView newText){
        CharSequence stringTorevert = newText.getText().subSequence(0,newText.getText().length()-1);

        oleText.setText(stringTorevert);
        newText.setText("");
    }
}