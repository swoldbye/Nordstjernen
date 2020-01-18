package com.example.skibslogapp.etapeoversigt;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.skibslogapp.GlobalContext;
import com.example.skibslogapp.R;
import com.example.skibslogapp.datalayer.local.TogtDAO;
import com.example.skibslogapp.model.Togt;
import com.example.skibslogapp.view.togtoversigt.TogtOversigt_frag;

public class AlertDialogAndPopup {

    /**
     * When you click on this "burger" icon you get a Popup menu where you get the choice to either:
     *
     * -  Export data from a "Togt"
     * -  Delete a "Togt"
     *
     * If you press delete a Alert dialog box pops up to make sure that you are certain that you
     * want to delet the "Togt".
     */
    public void popupMenuAndAlertDialog(ImageButton v, FragmentActivity activity, Togt togt){

        PopupMenu popupMenu = new PopupMenu(GlobalContext.get(),v);
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu_etape_oversigt, popupMenu.getMenu());
//
//        popupMenu.setOnMenuItemClickListener(item -> {
//            switch (item.getItemId()){
//
//                case R.id.exportTogt:
////
////                    Toast.makeText(activity,"Togt exportet",Toast.LENGTH_SHORT).show();
////                    return true;
//
//                case R.id.deleteTogt:
//
////                    AlertDialog.Builder builder = new AlertDialog.Builder(GlobalContext.get());
////
////                    //Custom title for the dialog box
////                    TextView dialogBoxHeadline = new TextView(GlobalContext.get());
////                    dialogBoxHeadline.setText("Er du sikker på du vil slette togtet?");
////                    dialogBoxHeadline.setTextSize(20f);
////                    dialogBoxHeadline.setTypeface(null, Typeface.BOLD);
////                    dialogBoxHeadline.setPadding(20,4,4,4);
////                    dialogBoxHeadline.setTextColor(GlobalContext.get().getResources().getColor(R.color.colorPrimary));
////
////                    builder.setCustomTitle(dialogBoxHeadline)
////                            .setCancelable(false)
////                            .setPositiveButton("Ja", (dialog, which) -> {
////
////                                //Delete the "togt from the DB
////                                TogtDAO togtDAO = new TogtDAO(GlobalContext.get());
////                                togtDAO.deleteTogt(togt);
////
////                                //Change to "Togt oversigten" without saving the fragment to the backstack
////                                TogtOversigt_frag togtOversigt_frag = new TogtOversigt_frag();
////                                FragmentManager fragmentManager = activity.getSupportFragmentManager();
////                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
////                                fragmentTransaction.replace(R.id.fragContainer,togtOversigt_frag);
////                                fragmentTransaction.commit();
////
////                                Toast.makeText(activity,"Togt slettet",Toast.LENGTH_SHORT).show();
////                            })
////                            .setNegativeButton("Nej", (dialog, which) -> dialog.cancel());
////
////                    final AlertDialog alertDialog = builder.create();
////                    alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
////
////                        /**
////                         * This method sets the negative and positive button attributes
////                         */
////                        @Override
////                        public void onShow(DialogInterface dialog) {
////                            Button btnPositive = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
////                            Button btnNegative = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
////                            btnPositive.setTextColor(GlobalContext.get().getResources().getColor(R.color.colorPrimary));
////                            btnPositive.setTextSize(20f);
////                            btnNegative.setTextColor(GlobalContext.get().getResources().getColor(R.color.colorPrimary));
////                            btnNegative.setTextSize(20f);
////                        }
////                    });
////
////                    alertDialog.show();
////                    return true;
//
//                default:
//                    return false;
//
//            }
//        });
        popupMenu.show();
    }


}
