package com.attosec.galgeleg_v400b;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;



public class Settings_Frag extends Fragment implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    public TextView bgMusicSubtextView;
    public Switch bgMusicSwitch;
    public static boolean musicIsPlaying;
    public TextView soundFXSubtextView;
    public Switch soundFXSwitch;
    public static boolean effectIsPlaying;
    public Switch insaneSwitch;
    public static boolean insaneIsActive;
    public Switch pushSwitch;
    public static boolean pushIsActive;
    public Button pushNotiButton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View settingsView = inflater.inflate(R.layout.settings_frag, container, false);

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Indstillinger");

        bgMusicSubtextView = (TextView) settingsView.findViewById(R.id.bgmusicSubtext);
        bgMusicSwitch = (Switch) settingsView.findViewById(R.id.bgmusicSwitch);

        soundFXSubtextView = (TextView) settingsView.findViewById(R.id.effectSubtext);
        soundFXSwitch = (Switch) settingsView.findViewById(R.id.effectSwitch);

        insaneSwitch = (Switch) settingsView.findViewById(R.id.insaneSwitch);

        pushSwitch = (Switch) settingsView.findViewById(R.id.pushNotiSwitch);

        pushNotiButton = (Button) settingsView.findViewById((R.id.pushNotiButton));
        pushNotiButton.setOnClickListener(this);

        if(musicIsPlaying){
            bgMusicSwitch.setChecked(true);
            bgMusicSubtextView.setText("Slå baggrundsmusik fra");
        }
        if(effectIsPlaying){
            soundFXSwitch.setChecked(true);
            soundFXSubtextView.setText("Slå lyde og lydeffekter fra");
        }
        if(insaneIsActive){
            insaneSwitch.setChecked(true);
        }
        if(pushIsActive){
            pushSwitch.setChecked(true);
        }

        bgMusicSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    bgMusicSubtextView.setText("Slå baggrundsmusik fra");
                    //MainActivity.bgMusicIsPlaying = true;
                    MainActivity.mySound.start();
                    MainActivity.mySound.setLooping(true);
                    musicIsPlaying = true;
                } else {
                    // The toggle is disabled
                    bgMusicSubtextView.setText("Slå baggrundsmusik til");
                    //MainActivity.bgMusicIsPlaying = false;
                    MainActivity.mySound.pause();
                    musicIsPlaying = false;
                }
            }
        });
        soundFXSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    soundFXSubtextView.setText("Slå lyde og lydeffekter fra");
                    effectIsPlaying = true;
                } else {
                    // The toggle is disabled
                    soundFXSubtextView.setText("Slå lyde og lydeffekter til");
                    effectIsPlaying = false;
                }
            }
        });
        insaneSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    // The toggle is enabled
                    insaneIsActive = true;
                } else {
                    // The toggle is disabled
                    insaneIsActive = false;
                }
            //MainActivity.game.opdaterOrdliste();
            }
        });
        pushSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    pushIsActive = true;
                } else {
                    // The toggle is disabled
                    pushIsActive = false;
                }
            }
        });

        return settingsView;
    }

    public void pushNotification(){
            NotificationManager notiMan = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
            PendingIntent pending = PendingIntent.getActivity(getActivity().getApplicationContext(), 0, new Intent(),0);
            Notification.Builder builder = new Notification.Builder(getActivity());

            builder.setSmallIcon(R.drawable.appicon)
                    .setContentTitle("Galgeleg")
                    .setContentText("Du har ikke spillet længe! Kom og spil!")
                    .setContentInfo("Gør det!")
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.appicon03))
                    .setContentIntent(pending);

            Notification notification = builder.getNotification();
            notiMan.notify(R.drawable.appicon03, notification);

    }


    @Override
    public void onClick(View v) {
        if(v == pushNotiButton){
            //pushIsPushing = true;

            if(pushIsActive){
                pushNotification();
            }else{
                Toast.makeText(getActivity(), "Push notifications er slået fra", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
    }
}
