package com.example.skibslogapp.utility;

import android.widget.TextView;

/**
 * Helper class to move text when adding vindretning and Strømningsretning i opret_fraq.*
 */

public class SwapViewsTextHelper {

    public static void centerText(TextView leftTxtView, TextView centerTxtView){
        CharSequence stringToRevert = leftTxtView.getText().toString();
        centerTxtView.setText(stringToRevert);
        leftTxtView.setText("");
    }

    public static void leftalignText(TextView leftTxtView, TextView centerTxtView){
        CharSequence stringToRevert = centerTxtView.getText().toString();
        if(!stringToRevert.equals("")) leftTxtView.setText(stringToRevert);
        centerTxtView.setText("");
    }
}