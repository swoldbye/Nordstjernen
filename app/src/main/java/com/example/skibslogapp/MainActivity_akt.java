package com.example.skibslogapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.skibslogapp.viewControl.LogOversigt_frag;
import com.example.skibslogapp.viewControl.OpretLog_frag;
import com.example.skibslogapp.viewControl.TogtOversigt_frag;
import com.google.android.material.navigation.NavigationView;

/**
 *
 */
public class MainActivity_akt extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    OpretLog_frag opretLog_frag;
    TogtOversigt_frag togtOversigt_frag;
    LogOversigt_frag logOversigt_frag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//      Sæt Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.baseline_menu_white_18dp);

        configureNavigationDrawer();

        if (savedInstanceState == null){
            Fragment fragment = new OpretLog_frag();
            getSupportFragmentManager().beginTransaction().add(R.id.fragContainer, fragment).commit();
        }
    }

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
        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.leftMenu);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                opretLog_frag = new OpretLog_frag();
                logOversigt_frag = new LogOversigt_frag();
                togtOversigt_frag = new TogtOversigt_frag();

                Fragment fragment = null;
                int itemid = menuItem.getItemId();

                //Tilføj funktionalitet til menu items

                if (itemid == R.id.nav_opret_togt){

                }else if (itemid == R.id.nav_togt_oversigt){
                    fragmentManager = MainActivity_akt.this.getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragContainer, togtOversigt_frag);
                    fragmentTransaction.commit();
                    drawerLayout.closeDrawers();
                    return true;

                }else if (itemid == R.id.nav_opret_etape){

                }else if (itemid == R.id.nav_etape_oversigt){

                }else if (itemid == R.id.nav_opret_log){
                    Toast.makeText(MainActivity_akt.this,"Hej fra toast",Toast.LENGTH_LONG).show();
                    fragmentManager = MainActivity_akt.this.getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragContainer, opretLog_frag);
                    fragmentTransaction.commit();
                    drawerLayout.closeDrawers();
                    return true;

                }else if (itemid == R.id.nav_log_oversigt){
                    fragmentManager = MainActivity_akt.this.getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragContainer, logOversigt_frag);
                    fragmentTransaction.commit();
                    drawerLayout.closeDrawers();
                    return true;

                }
//                if (fragment != null) {
//                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//                    transaction.replace(R.id.fragContainer, fragment);
//                    transaction.commit();
//                    drawerLayout.closeDrawers();
//                    return true;
//                }
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
}
