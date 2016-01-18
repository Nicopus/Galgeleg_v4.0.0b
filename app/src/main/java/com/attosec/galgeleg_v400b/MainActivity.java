package com.attosec.galgeleg_v400b;

import android.content.Context;
import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.firebase.client.Firebase;



/*
Lavet af:
Sazvan Kasim Ali - S144884
Mathias Petersen - S144874
Bao Duy Nguyen - S144880
Christian Jappe - S144866
Magnus Nielsen - S141899
Nicolai Hansen - S133974
*/


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static HangmanLogic game;
    private RelativeLayout loadingView;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;
    public static boolean isLetterBox;
    //public static BrugerDAO brugerDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_main);

        isLetterBox = true;

        if(game == null) {
            game = new HangmanLogic();
        }

        if (savedInstanceState == null && game.getMuligeOrd().size() > 8) {
            Fragment fragment = new MainMenu();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.include, fragment)  // tom container i layout
                    .commit();
            loadingView.setVisibility(View.GONE);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Galgeleg");
        loadingView = (RelativeLayout) findViewById(R.id.loadingView);
        loadingView.setVisibility(View.VISIBLE);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //DrawerLayout mainFrag = (DrawerLayout) findViewById(R.id.main_drawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        DrAsync firebaseOrdliste = new DrAsync();
        firebaseOrdliste.execute();
        game.nulstil();
        loadList();

        // ShakeDetector initialization
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector();
        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {

            @Override
            public void onShake(int count) {
				/*
				 * The following method, "handleShakeEvent(count):" is a stub //
				 * method you would use to setup whatever you want done once the
				 * device has been shook.
				 */
                handleShakeEvent(count);
            }
        });

    }

    private boolean isShaking = false;

    private void handleShakeEvent(int count) {
        Spil_Frag mySpilFrag = (Spil_Frag)getSupportFragmentManager().findFragmentByTag("SPIL_FRAG");



        if(mySpilFrag != null && mySpilFrag.isVisible() && isShaking == false && Spil_Frag.spilIgang){

            //Toast.makeText(this, "Shaking", Toast.LENGTH_SHORT).show();
            isShaking = true;
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Andet ord?")
                    .setMessage("Du vil miste 30 point ved at få nyt ord")
                    .setPositiveButton("Nyt ord", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //game.nulstil();
                            //Spil_Frag.wordText.setText(MainActivity.game.getSynligtOrd());
                            Spil_Frag.spilRefresh();
                            isShaking = false;
                        }
                    })
                    .setNegativeButton("Annuller", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                            isShaking = false;
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }

    }

    @Override
    public void onPause() {
        // Add the following line to unregister the Sensor Manager onPause
        mSensorManager.unregisterListener(mShakeDetector);
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //Fragment mainFragView = getSupportFragmentManager().findFragmentById(R.id.mainFragView);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
 //       }else if (mainFragView.isVisible()){finish();
        } else if (getSupportFragmentManager().getBackStackEntryCount()>0){
  //          Fragment fragment = new MainMenu(); getSupportFragmentManager().beginTransaction() .replace(R.id.include, fragment) .commit();
            //fragment.getActivity().setTitle("Galgeleg");
            //toolbar.setTitle("Galgeleg");
            //getActionBar().setTitle("Galgeleg");
            //finish();
            getSupportFragmentManager().popBackStack();
        } else{
            finish();
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
        // Add the following line to register the Session Manager Listener onResume
        mSensorManager.registerListener(mShakeDetector, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
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
            getSupportFragmentManager().popBackStack();
            /*getSupportFragmentManager().beginTransaction()
                    .replace(R.id.include, fragment)  // tom container i layout
                    .addToBackStack(null)
                    .commit();
*/
            //getSupportActionBar().setTitle("Galgeleg");
        } else if (id == R.id.nav_play) {
            Fragment fragment = new Spil_Frag();
            getSupportFragmentManager().popBackStack();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.include, fragment, "SPIL_FRAG")  // tom container i layout
                    .addToBackStack(null)
                    .commit();
            //getSupportActionBar().setTitle("Spil");
            //Toast.makeText(this, "Denne funktion er endnu ikke implementeret", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_wordlist) {
            Fragment fragment = new Ordliste_Frag();
            getSupportFragmentManager().popBackStack();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.include, fragment)  // tom container i layout
                    .addToBackStack(null)
                    .commit();
            //getSupportActionBar().setTitle("Ordliste");
            //Toast.makeText(this, "Denne funktion er endnu ikke implementeret", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_highscore) {
            Fragment fragment = new Scoreboard_Frag();
            getSupportFragmentManager().popBackStack();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.include, fragment)  // tom container i layout
                    .addToBackStack(null)
                    .commit();
            //getSupportActionBar().setTitle("Om Appen");
            //Toast.makeText(this, "Denne funktion er endnu ikke implementeret", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_about) {
            Fragment fragment = new OmAppen_Frag();
            getSupportFragmentManager().popBackStack();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.include, fragment)  // tom container i layout
                    .addToBackStack(null)
                    .commit();
            //getSupportActionBar().setTitle("Om Appen");
            //Toast.makeText(this, "Denne funktion er endnu ikke implementeret", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_help) {
            Fragment fragment = new Hjaelp_Frag();
            getSupportFragmentManager().popBackStack();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.include, fragment)  // tom container i layout
                    .addToBackStack(null)
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


    //Fejl i opdate af ordliste.. Men fixed (dårligt)her
    public void loadList(){
        //Loading bar
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (game.getMuligeOrd().size() >= 8){
                    errorToast("Loading complete!");
                    loadingView.setVisibility(View.GONE);
                    //findViewById(R.id.fab).setVisibility(View.VISIBLE);
                    onResume();
                }
                if (game.getMuligeOrd().size() == 8){
                    //errorToast("Connection error. Check internet connection. If your internet connection is on, our servers might be down.");
                    game.opdaterOrdliste();
                    //loadingView.setVisibility(View.GONE);
                    //findViewById(R.id.dilemmaList).setVisibility(View.VISIBLE);
                    //findViewById(R.id.fab).setVisibility(View.VISIBLE);

                    if (game.getMuligeOrd().size() >= 8) {
                        Fragment fragment = new MainMenu();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.include, fragment)  // tom container i layout
                                .commit();
                        loadingView.setVisibility(View.GONE);
                        errorToast("Loading complete!");
                    }else{
                        errorToast("Connection error. Check internet connection. If your internet connection is on, our servers might be down.");
                    }

                }
                if(game.getTop30Highscores().size() == 0) {
                   game.opdaterScoreboard();

                }


            }
        }, 4000); //Find smartere metode til at tjekke når isloading er færdig og isconnected er færdig?
    }


    private class DrAsync extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            if (game.getTop30Highscores().isEmpty()) {
                try {
                    game.opdaterScoreboard();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (game.getMuligeOrd().size() == 8) {
                try {
                    game.opdaterOrdliste();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

       @Override
       protected void onPostExecute(Void result){
           if (game.getMuligeOrd().size() >= 8) {
               Fragment fragment = new MainMenu();
               getSupportFragmentManager().beginTransaction()
                       .replace(R.id.include, fragment)  // tom container i layout
                       .commit();
           }
           //Spil_Frag.spilRefresh();
       }

    }
}


