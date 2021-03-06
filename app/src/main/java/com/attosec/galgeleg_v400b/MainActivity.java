package com.attosec.galgeleg_v400b;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import com.firebase.client.Firebase;

import java.lang.ref.WeakReference;
import java.util.Set;

/*
Lavet af:
Sazvan Kasim Ali - S144884
Mathias Petersen - S144874
Bao Duy Nguyen - S144880
Christian Jappe - S144866
Magnus Nielsen - S141899
Nicolai Hansen - S133974
*/


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static HangmanLogic game;
    private RelativeLayout loadingView;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;

    public static MediaPlayer mySound;
    public static MediaPlayer soundDeath;
    public static MediaPlayer soundAlive;
    public static MediaPlayer soundOuch;
    public static MediaPlayer soundYes;
    public static MediaPlayer soundButton;
    public static MediaPlayer soundDoor;
    public static boolean bgMusicIsPlaying;

    private Animation animation;
    private RelativeLayout logo;
    private TextView title1, title2;
    private Handler loadHandler;
    private static final int MSG_LOAD_COMPLETE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_main);

        loadHandler = new LoadHandler();

        loadHandler.post(new LoadConfig());

        logo = (RelativeLayout) findViewById(R.id.splash_cirkel);
        logo.startAnimation(AnimationUtils.loadAnimation(this, R.anim.step_number_fader));
        title2 = (TextView) findViewById(R.id.splash_text_left);
        title1 = (TextView) findViewById(R.id.splash_text_right);



        //bgMusicIsPlaying = true;
        mySound = MediaPlayer.create(this, R.raw.background);
        soundDeath = MediaPlayer.create(this, R.raw.death);
        soundAlive = MediaPlayer.create(this, R.raw.relief);
        soundOuch = MediaPlayer.create(this, R.raw.ouch);
        soundYes = MediaPlayer.create(this, R.raw.yes);
        soundButton = MediaPlayer.create(this, R.raw.button);
        soundDoor = MediaPlayer.create(this, R.raw.door);

        mySound.start();
        mySound.setLooping(true);
        Settings_Frag.musicIsPlaying = true;
        Settings_Frag.effectIsPlaying = true;
        Settings_Frag.insaneIsActive = false;

        /*if(bgMusicIsPlaying){
            mySound.start();
        }else{
            mySound.release();
        }*/


        if(game == null) {
            game = new HangmanLogic();

        }
        if (savedInstanceState == null)
            flyIn();

        /*if (savedInstanceState == null && game.getMuligeOrd().size() > 8) {
            Fragment fragment = new MainMenu();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.include, fragment)  // tom container i layout
                    .commit();
            //loadingView.setVisibility(View.GONE);
        }*/

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Galgeleg");
        loadingView = (RelativeLayout) findViewById(R.id.loadingView);
        //loadingView.setVisibility(View.VISIBLE);
        loadingView.setVisibility(View.GONE);

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
        //loadList();

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
        String notiSubject = "Subject";
        String notiBody = "Body";

        pushNoti();
        Settings_Frag.pushIsActive = true;

    }


    //Start på Load Animation
    private class LoadConfig implements Runnable {

        @Override
        public void run() {




            if(game.getMuligeOrd().size() == 8 && game.getTop30Highscores().size() == 0){
                game.opdaterOrdliste();
                game.opdaterScoreboard();
                //loadList();
            }else if(game.getMuligeOrd().size() >= 8 && game.getTop30Highscores().size() == 0){
                game.opdaterScoreboard();
                //loadList();
            }else if(game.getMuligeOrd().size() == 8 && game.getTop30Highscores().size() >= 1){
                game.opdaterOrdliste();
                //loadList();
            }else if(game.getMuligeOrd().size() >= 8 && game.getTop30Highscores().size() >= 1){
                errorToast("Loading complete!");
                //loadingView.setVisibility(View.GONE);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        endSplash();
                        onResume();
                    }}, 400);
            } else {
                errorToast("Connection error. Check internet connection. If your internet connection is on, our servers might be down.");
                //loadList();
            }





            final Message message = loadHandler.obtainMessage(MSG_LOAD_COMPLETE);
            message.sendToTarget();
        }
    }

   /* private static void setScreenDimension(final AppCompatActivity appCompatActivity) {
        final Display display = appCompatActivity.getWindowManager().getDefaultDisplay();
        display.getSize(WindowLayout.screenDimension);
    }*/

    private void flyIn() {
//        animation = AnimationUtils.loadAnimation(this, R.anim.logo_animation);
//        logo.startAnimation(animation);

        animation = AnimationUtils.loadAnimation(this, R.anim.app_name_animation);
        title1.startAnimation(animation);

        animation = AnimationUtils.loadAnimation(this, R.anim.pro_animation);
        title2.startAnimation(animation);
    }

    private void endSplash() {
        logo.setAnimation(null);
        // just use YoYo for the nice rotate out :-)
        YoYo.with(Techniques.FlipOutY).duration(400).withListener(new SplashEndAnimatorListener(this)).playOn(logo);

//        animation = AnimationUtils.loadAnimation(this, R.anim.step_number_back);
//        logo.startAnimation(animation);

//        animation = AnimationUtils.loadAnimation(this, R.anim.logo_animation_back);
//        logo.startAnimation(animation);

        animation = AnimationUtils.loadAnimation(this, R.anim.app_name_animation_back);
        title1.startAnimation(animation);

        animation = AnimationUtils.loadAnimation(this, R.anim.pro_animation_back);
        title2.startAnimation(animation);

//        animation.setAnimationListener(new SplashEndAnimationListener(this));


    }

    private class LoadHandler extends Handler {
        @Override
        public void handleMessage(final Message msg) {
            super.handleMessage(msg);
            if (msg != null && msg.what == MSG_LOAD_COMPLETE) {
                loadHandler.postDelayed(new EndSplash(), 5000);
            }
        }
    }

    private class SplashEndAnimatorListener implements Animator.AnimatorListener, com.nineoldandroids.animation.Animator.AnimatorListener {

        private final WeakReference<MainActivity> mainSplashActivityWeakReference;

        public SplashEndAnimatorListener(final MainActivity splashActivity) {
            mainSplashActivityWeakReference = new WeakReference<>(splashActivity);
        }

        @Override
        public void onAnimationStart(final Animator animation) { }

        @Override
        public void onAnimationEnd(final Animator animation) {


        }

        @Override
        public void onAnimationCancel(final Animator animation) { }

        @Override
        public void onAnimationRepeat(final Animator animation) { }

        @Override
        public void onAnimationStart(com.nineoldandroids.animation.Animator animation) {  }

        @Override
        public void onAnimationEnd(com.nineoldandroids.animation.Animator animation) {
            errorToast("Loading complete!");
            final MainActivity mainSplashActivity = mainSplashActivityWeakReference.get();
            //MainMenu myMainMenu = (MainMenu)getSupportFragmentManager().findFragmentByTag("MAIN_MENU");
            if (mainSplashActivity != null) {
                Fragment fragment = new MainMenu();
                getSupportFragmentManager().popBackStack();
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.fade_in_repeat_backwards, R.anim.logo_animation_back)
                        .replace(R.id.include, fragment, "MAIN_MENU")  // tom container i layout
                        .commit();
            }
        }

        @Override
        public void onAnimationCancel(com.nineoldandroids.animation.Animator animation) {

        }

        @Override
        public void onAnimationRepeat(com.nineoldandroids.animation.Animator animation) {

        }
    }

    private class EndSplash implements Runnable {
        @Override
        public void run() { endSplash(); }
    }

    //Slut på Load Animation



    //@TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void pushNotification(){
            NotificationManager notiMan = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
            PendingIntent pending = PendingIntent.getActivity(getApplicationContext(), 0, new Intent(this, MainActivity.class),0);
            Notification.Builder builder = new Notification.Builder(this);

            builder.setSmallIcon(R.drawable.appicon)
                    .setContentTitle("Galgeleg")
                    .setContentText("Du har ikke spillet længe!")
                    .setContentInfo("Kom og spil!")
                    .setAutoCancel(true)
                    .setColor(getResources().getColor(R.color.colorPrimary))
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.appicon03))
                    .setContentIntent(pending);

            Notification notification = builder.getNotification();
            notiMan.notify(R.drawable.appicon03, notification);
    }



    private boolean isShaking = false;

    private void handleShakeEvent(int count) {
        Spil_Frag mySpilFrag = (Spil_Frag)getSupportFragmentManager().findFragmentByTag("SPIL_FRAG");
        Vibrator myVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        if(mySpilFrag != null && mySpilFrag.isVisible() && isShaking == false && Spil_Frag.spilIgang){
            myVibrator.vibrate(100);
            //Toast.makeText(this, "Shaking", Toast.LENGTH_SHORT).show();
            isShaking = true;
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Andet ord?")
                    .setMessage("Er du sikker på du vil starte forfra?")
                    .setPositiveButton("Nyt ord", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //game.nulstil();
                            //Spil_Frag.wordText.setText(MainActivity.game.getSynligtOrd());
                            Spil_Frag.spilRefresh();
                            isShaking = false;
                            Spil_Frag.scoreText.setText(String.valueOf(MainActivity.game.getScore()));
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
        mySound.release();
        finish();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if(Settings_Frag.effectIsPlaying){soundButton.start();}
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
            getSupportFragmentManager().popBackStack();
            finish();
            mySound.release();
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
        //mySound.start();
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
            if(Settings_Frag.effectIsPlaying){soundButton.start();}
            Fragment fragment = new MainMenu();
            getSupportFragmentManager().popBackStack();
            /*getSupportFragmentManager().beginTransaction()
                    .replace(R.id.include, fragment)  // tom container i layout
                    .addToBackStack(null)
                    .commit();
*/
            //getSupportActionBar().setTitle("Galgeleg");
        } else if (id == R.id.nav_play) {
            if(Settings_Frag.effectIsPlaying){soundButton.start();}
            Fragment fragment = new Spil_Frag();
            getSupportFragmentManager().popBackStack();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.include, fragment, "SPIL_FRAG")  // tom container i layout
                    .addToBackStack(null)
                    .commit();
            //getSupportActionBar().setTitle("Spil");
            //Toast.makeText(this, "Denne funktion er endnu ikke implementeret", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_wordlist) {
            if(Settings_Frag.effectIsPlaying){soundButton.start();}
            Fragment fragment = new Ordliste_Frag();
            getSupportFragmentManager().popBackStack();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.include, fragment)  // tom container i layout
                    .addToBackStack(null)
                    .commit();
            //getSupportActionBar().setTitle("Ordliste");
            //Toast.makeText(this, "Denne funktion er endnu ikke implementeret", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_highscore) {
            if(Settings_Frag.effectIsPlaying){soundButton.start();}
            Fragment fragment = new Scoreboard_Frag();
            getSupportFragmentManager().popBackStack();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.include, fragment)  // tom container i layout
                    .addToBackStack(null)
                    .commit();
            //getSupportActionBar().setTitle("Om Appen");
            //Toast.makeText(this, "Denne funktion er endnu ikke implementeret", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_about) {
            if(Settings_Frag.effectIsPlaying){soundButton.start();}
            Fragment fragment = new OmAppen_Frag();
            getSupportFragmentManager().popBackStack();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.include, fragment)  // tom container i layout
                    .addToBackStack(null)
                    .commit();
            //getSupportActionBar().setTitle("Om Appen");
            //Toast.makeText(this, "Denne funktion er endnu ikke implementeret", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_help) {
            if(Settings_Frag.effectIsPlaying){soundButton.start();}
            Fragment fragment = new Hjaelp_Frag();
            getSupportFragmentManager().popBackStack();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.include, fragment)  // tom container i layout
                    .addToBackStack(null)
                    .commit();
        } else if (id == R.id.nav_settings) {
            if(Settings_Frag.effectIsPlaying){soundButton.start();}
            Fragment fragment = new Settings_Frag();
            getSupportFragmentManager().popBackStack();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.include, fragment)  // tom container i layout
                    .addToBackStack(null)
                    .commit();
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


    public void pushNoti(){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(Settings_Frag.pushIsActive){
                    pushNotification();
                }
                pushNoti();
            }
        }, 3 * 60 * 1000);
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
           /*if (game.getMuligeOrd().size() >= 8) {
               Fragment fragment = new MainMenu();
               getSupportFragmentManager().beginTransaction()
                       .replace(R.id.include, fragment)  // tom container i layout
                       .commit();
           }*/
           //Spil_Frag.spilRefresh();
       }

    }
}


