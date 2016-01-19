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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View settingsView = inflater.inflate(R.layout.settings_frag, container, false);

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Indstillinger");

        bgMusicSubtextView = (TextView) settingsView.findViewById(R.id.bgmusicSubtext);

        bgMusicSwitch = (Switch) settingsView.findViewById(R.id.bgmusicSwitch);

        if(musicIsPlaying){
            bgMusicSwitch.setChecked(true);
            bgMusicSubtextView.setText("Slå baggrundsmusik fra");
        }



        //toggle = (ToggleButton) toggle.findViewById(R.id.switchBackgroundMusic);

        bgMusicSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    bgMusicSubtextView.setText("Slå baggrundsmusik fra");
                    //MainActivity.bgMusicIsPlaying = true;
                    MainActivity.mySound.start();
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

        return settingsView;
    }





}
