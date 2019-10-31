package com.example.skibslogapp.Model.DB;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.skibslogapp.R;
import com.example.skibslogapp.viewControl.TogtOversigt_frag;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class LogData_frag extends Fragment implements View.OnClickListener {

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
//    DBservices DB = new DBservices();

    public LogData_frag() {
    }

    /**
     *
     *
     * @param inflater
     * @param container
     * @param savedInstanceState This parameter is usesd when the system have to recreate a frame
     *                           on the screen, a reason to recreate it could be that it got lost
     *                           when you flipped your screen.
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_log_data, container, false);

//        editTextItemName = view.findViewById(R.id.et_item_name);
//        editTextBrand = view.findViewById(R.id.et_brand);
//        editTextPrice = view.findViewById(R.id.et_price);
//
//        buttonAddItem = view.findViewById(R.id.btn_add_item);
//        buttonAddItem.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {

//        if(v == buttonAddItem) {
//            DB.addItemToSheet(editTextItemName, editTextBrand, this);
//        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.add(Menu.NONE, 101, Menu.NONE, "javabog.dk");
        MenuItemCompat.setShowAsAction(menu.add(Menu.NONE, 102, Menu.NONE, "Settings").setIcon(android.R.drawable.ic_menu_edit), MenuItem.SHOW_AS_ACTION_IF_ROOM);
        MenuItemCompat.setShowAsAction(menu.add(Menu.NONE, 103, Menu.NONE, "Terminate").setIcon(android.R.drawable.ic_menu_close_clear_cancel), MenuItem.SHOW_AS_ACTION_NEVER);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;
        TogtOversigt_frag togtOversigt_frag = new TogtOversigt_frag();

        //for debugging: textView.append("\nonOptionsItemSelected called with" + item.getTitle());
        if (item.getItemId() == 101) {
            Toast.makeText(getActivity(), "Du viderstilles nu", Toast.LENGTH_LONG).show();

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://javabog.dk"));
            startActivity(intent);

        } else if (item.getItemId() == 102) {

            fragmentManager = getActivity().getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragContainer,togtOversigt_frag);
            fragmentTransaction.commit();

        } else if (item.getItemId() == 103) {
            Toast.makeText(getActivity(), "Du har valgt at lukke appen.", Toast.LENGTH_LONG).show();
            getActivity().finish();

        }
        return true;
    }

}
