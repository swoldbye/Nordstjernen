package com.example.skibslogapp;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.skibslogapp.view.LogOversigt_frag;
import com.example.skibslogapp.view.OpretLog_frag;
import com.example.skibslogapp.view.TogtOversigt_frag;
import com.google.android.material.navigation.NavigationView;

/**
 *  Denne klasse indeholder hovedaktiviteten og dens funktionaliteter.
 *
 *  - Toolbar
 *  - Venstre menu
 *
 *  Hovedaktiviteten har en fragment container under toolbar som skifter mellem appens fragmenter
 */
public class Main_akt extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private DrawerLayout drawerLayout;
    private OpretLog_frag opretLog_frag;
    private TogtOversigt_frag togtOversigt_frag;

    private LogOversigt_frag logOversigt_frag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

//      Sæt Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

//        actionBar.setDisplayHomeAsUpEnabled(true);
//        actionBar.setHomeAsUpIndicator(R.drawable.baseline_menu_white_18dp);

        configureNavigationDrawer();

        if (savedInstanceState == null){
            Fragment fragment = new PostActivity();
            getSupportFragmentManager().beginTransaction().add(R.id.fragContainer, fragment).commit();
        }
    }

    /**
     * Hvis denne metode bliver sat til en hvis menu, så kan man trykke på tre prikker i top højre hjørne
     * af toolbar, også kommer der en menu frem der.
     *
     * Vi har dog kun brug for venstremenuen ind til videre, så jeg sætter denne til en tom menu, så der ikke
     * kommer noget i højre hjørne
     *
     * @param menu den menu der skal inflates
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.empty, menu);
        return true;
    }

    /**
     * Denne funktion giver funktioner til de forskellige elementer i venstre menuen.
     *
     */
    private void configureNavigationDrawer(){
        NavigationView navigationView;
        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.leftMenu);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                logOversigt_frag = new LogOversigt_frag();
                togtOversigt_frag = new TogtOversigt_frag();

                int itemid = menuItem.getItemId();

                //Tilføj funktionalitet til menu items

                if (itemid == R.id.nav_opret_togt){


                }else if (itemid == R.id.nav_togt_oversigt){
                    //changeFragFromMenu(togtOversigt_frag);

                }else if (itemid == R.id.nav_opret_etape){


                }else if (itemid == R.id.nav_etape_oversigt){

                }else if (itemid == R.id.nav_opret_log){
                    changeFragFromMenu(opretLog_frag);

                }else if (itemid == R.id.nav_log_oversigt){
                   //changeFragFromMenu(logOversigt_frag);

                }else {
                    Toast.makeText(Main_akt.this,"Du klikkede på noget ikke funktionelt. prøv igen",
                            Toast.LENGTH_LONG).show();

                }
                return false;
            }
        });
    }

    /**
     * Denne metode giver de forskellige toolbar views funktionalitet
     *
     * @param menuItem De forskellige elementer i toolbar
     * @return true hvis funktionen kan udføres
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        switch(itemId) {

            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            // Indsæt flere entries, hvis der er...
        }
        return true;
    }

    /**
     * Code snippet taken from https://stackoverflow.com/questions/4828636/edittext-clear-focus-on-touch-outside
     * Hides the keyboard when going out of focus. Should also work for fragments
     *
     * @param event
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent( event );
    }

    /**
     * Metode til at skifte fragment inde fra venstre menuen.
     *
     * @param fragment Det fragment man vil skifte til
     * @return true
     */
    public boolean changeFragFromMenu(Fragment fragment){
        fragmentManager = Main_akt.this.getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragContainer, fragment);
        fragmentTransaction.commit();
        drawerLayout.closeDrawers();
        return true;
    }

    /**
     * Ved at kalde denne metode gemmes toolbar
     */
    public void hideToolbar(){
        this.getSupportActionBar().hide();
    }

    /**
     * Ved at kalde denne metode vises toolbar
     */
    public void showToolbar(){
        this.getSupportActionBar().show();
    }





}
