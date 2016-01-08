package com.attosec.galgeleg_v400b;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

//import com.firebase.client.Firebase;



/*
Lavet af:
Sazvan Kasim Ali - S144884
Mathias Petersen - S144874
Bao Duy Nguyen - S144880
Christian Jappe - S144866
Magnus Nielsen - S141899
Nicolai Hansen - S133974
*/
/*

public class Main extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private TextView gravityText;
    private ListView dilemmaList;

    private ProgressBar prog;
    private String[] dilemmaTitles;
    private String[] dilemmaGravities;
    private RelativeLayout loadingView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            Fragment fragment = new MainMenu();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.include, fragment)  // tom container i layout
                    .commit();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Galgeleg");
        loadingView = (RelativeLayout) findViewById(R.id.loadingView);


        dilemmaList = (ListView) findViewById(R.id.dilemmaList);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //loading bar
        prog = (ProgressBar) findViewById(R.id.progressBar2);
        loadList();

    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Fragment fragment = new MainMenu();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.include, fragment)  // tom container i layout
                    .commit();
            //getActionBar().setTitle("Galgeleg");
            //finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onResume(){
        super.onResume();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Fragment fragment = new MainMenu();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.include, fragment)  // tom container i layout
                    .commit();

            //getSupportActionBar().setTitle("Galgeleg");
        } else if (id == R.id.nav_play) {
            Fragment fragment = new Spil_Frag();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.include, fragment)  // tom container i layout
                    .commit();
            //getSupportActionBar().setTitle("Spil");
            //Toast.makeText(this, "Denne funktion er endnu ikke implementeret", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_wordlist) {
            Fragment fragment = new Ordliste_Frag();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.include, fragment)  // tom container i layout
                    .commit();
            //getSupportActionBar().setTitle("Ordliste");
            //Toast.makeText(this, "Denne funktion er endnu ikke implementeret", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_about) {
            Fragment fragment = new OmAppen_Frag();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.include, fragment)  // tom container i layout
                    .commit();
            //getSupportActionBar().setTitle("Om Appen");
            //Toast.makeText(this, "Denne funktion er endnu ikke implementeret", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_profile) {
            Toast.makeText(this, "Denne funktion er endnu ikke implementeret", Toast.LENGTH_SHORT).show();
            //startActivity(new Intent(MainActivity.this, LoginActivity.class));
        } else if (id == R.id.nav_register) {
            Toast.makeText(this, "Denne funktion er endnu ikke implementeret", Toast.LENGTH_SHORT).show();
            //startActivity(new Intent(MainActivity.this, RegisterActivity.class));
        } else if (id == R.id.nav_editUser) {
            Toast.makeText(this, "Denne funktion er endnu ikke implementeret", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void errorToast(String str){
        Context context = getApplicationContext();
        CharSequence text =str;
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public void onItemClick(AdapterView<?> l, View v, int position, long id) {
        Toast.makeText(this, "Klik på " + position, Toast.LENGTH_SHORT).show();
    }

    public void loadList(){
        //Loading bar
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(1==1){
                    errorToast("Loading complete!");
                    loadingView.setVisibility(View.GONE);
                    findViewById(R.id.dilemmaList).setVisibility(View.VISIBLE);
                    //findViewById(R.id.fab).setVisibility(View.VISIBLE);
                    onResume();
                }
                if(1!=1){
                    errorToast("Connection error. Check internet connection. If your internet connection is on, our servers might be down.");
                    loadingView.setVisibility(View.GONE);
                    findViewById(R.id.dilemmaList).setVisibility(View.VISIBLE);
                    //findViewById(R.id.fab).setVisibility(View.VISIBLE);
                }
            }
        }, 4000); //Find smartere metode til at tjekke når isloading er færdig og isconnected er færdig?
    }
}


*/