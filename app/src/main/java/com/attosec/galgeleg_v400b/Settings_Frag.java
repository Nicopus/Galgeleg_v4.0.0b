package com.attosec.galgeleg_v400b;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;



public class Settings_Frag extends Fragment {

    public TextView bgMusicSubtextView;
    public Switch bgMusicSwitch;
    public static boolean musicIsPlaying;
    public TextView soundFXSubtextView;
    public Switch soundFXSwitch;
    public static boolean effectIsPlaying;
    public Switch insaneSwitch;
    public static boolean insaneIsActive;

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
            }
        });

        return settingsView;
    }





}
