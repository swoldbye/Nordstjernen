package com.example.skibslogapp.view.utility;

import android.content.Context;
import android.widget.EditText;
import android.widget.TextView;

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
