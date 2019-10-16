package com.example.skibslogapp.Model.DB;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.skibslogapp.viewControl.TogtOversigt;

import java.text.SimpleDateFormat;
import java.util.Calendar;
//===================================================================
//===================================================================
//===================================================================
//===================================================================
//===================================================================

// This class it to be removed when the DB services have been setup

//===================================================================
//===================================================================
//===================================================================
//===================================================================
//===================================================================
//===================================================================

public class LogData extends AppCompatActivity implements View.OnClickListener{
    private Button urlOK, storeData, shareApp, takeMeTo;
    private TextView dato, time, browser, textView;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private WebView webView;
    private EditText editText;
    private String address;
    private Intent i;
    EditText editTextItemName,editTextBrand, editTextPrice;
    Button buttonAddItem;
    DBservices DB = new DBservices();

    /**
     * @param savedInstanceState This parameter is usesd when the system have to recreate a frame
     *                           on the screen, a reason to recreate it could be that it got lost
     *                           when you flipped your screen.
     *
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*setContentView(R.layout.activity_logdata);

        editTextItemName = findViewById(R.id.et_item_name);
        editTextBrand = findViewById(R.id.et_brand);
        editTextPrice = findViewById(R.id.et_price);

        buttonAddItem = findViewById(R.id.btn_add_item);
        buttonAddItem.setOnClickListener(this);*/
    }



    public void onClick(View v){
        if(v==buttonAddItem){
            DB.addItemToSheet(editTextItemName,editTextBrand,this);

            //Define what to do when button is clicked
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, 101, Menu.NONE, "javabog.dk");
        menu.add(Menu.NONE, 102, Menu.NONE, "Settings").setIcon(android.R.drawable.ic_menu_edit).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        menu.add(Menu.NONE, 103, Menu.NONE, "Terminate").setIcon(android.R.drawable.ic_menu_close_clear_cancel).setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);

        //'Inflates' the xml menu I made.
        //getMenuInflater().inflate(R.menu.usemenu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //for debugging: textView.append("\nonOptionsItemSelected called with" + item.getTitle());
        if (item.getItemId() == 101) {
            Toast.makeText(this, "Du viderstilles nu", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://javabog.dk"));
            startActivity(intent);

        } else if (item.getItemId() == 102) {

            Intent intent = new Intent(this, TogtOversigt.class);
            startActivity(intent);
        } else if (item.getItemId() == 103) {
            Toast.makeText(this, "Du har valgt at lukke appen.", Toast.LENGTH_LONG).show();
            finish();

        }
        return true;
    }


}
