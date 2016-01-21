package com.attosec.galgeleg_v400b;

import android.animation.Animator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.lang.ref.WeakReference;


public class MainMenu extends Fragment implements View.OnClickListener {
    Button startSpil, highscore;
    ImageView galgePicture;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle("Galgeleg");
        View mainView = inflater.inflate(R.layout.main_menu_frag, container, false);

        startSpil = (Button) mainView.findViewById(R.id.btnStartGame);
        startSpil.setText("Start Spil");
        startSpil.setOnClickListener(this);
        highscore = (Button) mainView.findViewById(R.id.btnHighscore);
        highscore.setText("Highscore");
        highscore.setOnClickListener(this);
        galgePicture = (ImageView) mainView.findViewById(R.id.galgePic);
        //galgePicture.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.anim_hangman_game_seperator));
        animator();
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Galgeleg");
        return mainView;


    }

    public void animator (){
        YoYo.with(Techniques.RotateOutDownLeft)
                .delay(0)
                .duration(1200)
                //.withListener(aL)
                .playOn(galgePicture);

        //YoYo.with(Techniques.Landing).delay(15000).duration(5000).playOn(galgePicture);
    }

    private final com.nineoldandroids.animation.Animator.AnimatorListener aL = new AnimatorListener() {
        @Override
        public void onAnimationStart(com.nineoldandroids.animation.Animator animation) {}
        @Override
        public void onAnimationEnd(com.nineoldandroids.animation.Animator animation) {
            YoYo.with(Techniques.BounceIn)
                    .delay(1000)
                    .duration(1200)
                    .interpolate(new AccelerateDecelerateInterpolator())
                    .withListener(aL2).playOn(galgePicture);
        }
        @Override
        public void onAnimationCancel(com.nineoldandroids.animation.Animator animation) {}
        @Override
        public void onAnimationRepeat(com.nineoldandroids.animation.Animator animation) {}
    };

    private final com.nineoldandroids.animation.Animator.AnimatorListener aL2 = new AnimatorListener() {
        @Override
        public void onAnimationStart(com.nineoldandroids.animation.Animator animation) {}
        @Override
        public void onAnimationEnd(com.nineoldandroids.animation.Animator animation) {
            YoYo.with(Techniques.FlipOutY)
                    .delay(1000)
                    .duration(1200)
                    .interpolate(new AccelerateDecelerateInterpolator())
                    .withListener(aL).playOn(galgePicture);
        }
        @Override
        public void onAnimationCancel(com.nineoldandroids.animation.Animator animation) {}
        @Override
        public void onAnimationRepeat(com.nineoldandroids.animation.Animator animation) {}
    };


    public void onClick(View v) {

        if (v == startSpil) {
            if(Settings_Frag.effectIsPlaying){MainActivity.soundButton.start();}
            getFragmentManager().beginTransaction()
                    .replace(R.id.include, new Spil_Frag(), "SPIL_FRAG")
                    .addToBackStack(null)
                    .commit();
        } else if (v == highscore) {
            if(Settings_Frag.effectIsPlaying){MainActivity.soundButton.start();}
            getFragmentManager().beginTransaction()
                    .replace(R.id.include, new Scoreboard_Frag())
                    .addToBackStack(null)
                    .commit();
        }
    }
}