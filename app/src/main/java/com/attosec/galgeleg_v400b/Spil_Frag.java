package com.attosec.galgeleg_v400b;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.nineoldandroids.animation.Animator;

public class Spil_Frag extends Fragment implements View.OnClickListener {

    private NumberPicker charPicker;
    private String[] alphabet = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "æ", "ø", "å"};
    public static Button btnA, btnB, btnC, btnD, btnE, btnF, btnG, btnH, btnI, btnJ, btnK, btnL, btnM, btnN, btnO, btnP, btnQ, btnR, btnS, btnT, btnU, btnV, btnW, btnX, btnY, btnZ, btnÆ, btnØ, btnÅ;
    private LinearLayout letterBoxView;
    private LinearLayout playAgainView;

    private Button playagain;
    public static Button hintBtn;
    public static TextView scoreText;
    public static TextView wordText;
    public static TextView guessedWords;
    public static ImageView galgeImg;
    public static Integer[] wrongImg;
    public static TextView gættilbage;
    public static TextView normTekst;
    private Nyt_Nickname_Dialog_Frag dialogFragment;
    private Ny_Highscore_Dialog_Frag nyhighscoreFragment;
    public static boolean spilIgang = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rod = inflater.inflate(R.layout.spil_frag, container, false);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //if(game == null){game = new HangmanLogic();}
        Log.v("teajja", "jfsjfjsf");
        letterBoxView = (LinearLayout) rod.findViewById(R.id.letterBoxLayout);
        playAgainView = (LinearLayout) rod.findViewById(R.id.playAgainLayout);

        btnA = (Button) rod.findViewById(R.id.btnLetterA);
        btnB = (Button) rod.findViewById(R.id.btnLetterB);
        btnC = (Button) rod.findViewById(R.id.btnLetterC);
        btnD = (Button) rod.findViewById(R.id.btnLetterD);
        btnE = (Button) rod.findViewById(R.id.btnLetterE);
        btnF = (Button) rod.findViewById(R.id.btnLetterF);
        btnG = (Button) rod.findViewById(R.id.btnLetterG);
        btnH = (Button) rod.findViewById(R.id.btnLetterH);
        btnI = (Button) rod.findViewById(R.id.btnLetterI);
        btnJ = (Button) rod.findViewById(R.id.btnLetterJ);
        btnK = (Button) rod.findViewById(R.id.btnLetterK);
        btnL = (Button) rod.findViewById(R.id.btnLetterL);
        btnM = (Button) rod.findViewById(R.id.btnLetterM);
        btnN = (Button) rod.findViewById(R.id.btnLetterN);
        btnO = (Button) rod.findViewById(R.id.btnLetterO);
        btnP = (Button) rod.findViewById(R.id.btnLetterP);
        btnQ = (Button) rod.findViewById(R.id.btnLetterQ);
        btnR = (Button) rod.findViewById(R.id.btnLetterR);
        btnS = (Button) rod.findViewById(R.id.btnLetterS);
        btnT = (Button) rod.findViewById(R.id.btnLetterT);
        btnU = (Button) rod.findViewById(R.id.btnLetterU);
        btnV = (Button) rod.findViewById(R.id.btnLetterV);
        btnW = (Button) rod.findViewById(R.id.btnLetterW);
        btnX = (Button) rod.findViewById(R.id.btnLetterX);
        btnY = (Button) rod.findViewById(R.id.btnLetterY);
        btnZ = (Button) rod.findViewById(R.id.btnLetterZ);
        btnÆ = (Button) rod.findViewById(R.id.btnLetterÆ);
        btnØ = (Button) rod.findViewById(R.id.btnLetterØ);
        btnÅ = (Button) rod.findViewById(R.id.btnLetterÅ);

        btnA.setOnClickListener(this);
        btnB.setOnClickListener(this);
        btnC.setOnClickListener(this);
        btnD.setOnClickListener(this);
        btnE.setOnClickListener(this);
        btnF.setOnClickListener(this);
        btnG.setOnClickListener(this);
        btnH.setOnClickListener(this);
        btnI.setOnClickListener(this);
        btnJ.setOnClickListener(this);
        btnK.setOnClickListener(this);
        btnL.setOnClickListener(this);
        btnM.setOnClickListener(this);
        btnN.setOnClickListener(this);
        btnO.setOnClickListener(this);
        btnP.setOnClickListener(this);
        btnQ.setOnClickListener(this);
        btnR.setOnClickListener(this);
        btnS.setOnClickListener(this);
        btnT.setOnClickListener(this);
        btnU.setOnClickListener(this);
        btnV.setOnClickListener(this);
        btnW.setOnClickListener(this);
        btnX.setOnClickListener(this);
        btnY.setOnClickListener(this);
        btnZ.setOnClickListener(this);
        btnÆ.setOnClickListener(this);
        btnØ.setOnClickListener(this);
        btnÅ.setOnClickListener(this);

        playagain = (Button) rod.findViewById(R.id.btnPlayAgain);
        hintBtn = (Button) rod.findViewById(R.id.hintBtn);
        scoreText = (TextView) rod.findViewById(R.id.scoreNumber);
        galgeImg = (ImageView) rod.findViewById(R.id.imageView);
        wordText = (TextView) rod.findViewById(R.id.wordText);
        gættilbage = (TextView) rod.findViewById(R.id.guessBackNumber);
        normTekst = (TextView) rod.findViewById(R.id.guessBackTitle);
        wordText.setText("Loading...");
        playagain.setOnClickListener(this);
        hintBtn.setOnClickListener(this);


        DrAsync firebaseOrdliste = new DrAsync();
        firebaseOrdliste.execute();
        MainActivity.game.nulstil();
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Spil Galgeleg");
        spilIgang = true;
        animFlipIn();

        return rod;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        getActivity().finish();
        return true;
    }

    public void setVisibleView(){
        playAgainView.setVisibility(View.VISIBLE);
        gættilbage.setVisibility(View.INVISIBLE);
        normTekst.setVisibility(View.INVISIBLE);
        hintBtn.setVisibility(View.INVISIBLE);
        letterBoxView.setVisibility(View.GONE);

    }

    public void restartSpil(){
        MainActivity.game.nulstil();
        //guessedWords.setText("Brugte bogstaver: ");
        wordText.setText(MainActivity.game.getSynligtOrd());
        gættilbage.setText("7");
        scoreText.setText("0");
        galgeImg.setImageResource(R.drawable.galge);
        wrongImg = new Integer[]{
                R.drawable.forkert1, R.drawable.forkert2, R.drawable.forkert3, R.drawable.forkert4, R.drawable.forkert5, R.drawable.forkert6
        };
    }

    @Override
    public void onClick(View v) {
        if(v == btnA){if (!MainActivity.game.erSpilletSlut()) {MainActivity.game.gætBogstav(alphabet[0]); btnLetterClick(); spilCheck();} YoYo.with(Techniques.FlipOutX).duration(400).playOn(btnA);}
        else if(v == btnB){if (!MainActivity.game.erSpilletSlut()) {MainActivity.game.gætBogstav(alphabet[1]); btnLetterClick(); spilCheck();} YoYo.with(Techniques.FlipOutX).duration(400).playOn(btnB);}
        else if(v == btnC){if (!MainActivity.game.erSpilletSlut()) {MainActivity.game.gætBogstav(alphabet[2]); btnLetterClick(); spilCheck();} YoYo.with(Techniques.FlipOutX).duration(400).playOn(btnC);}
        else if(v == btnD){if (!MainActivity.game.erSpilletSlut()) {MainActivity.game.gætBogstav(alphabet[3]); btnLetterClick(); spilCheck();} YoYo.with(Techniques.FlipOutX).duration(400).playOn(btnD);}
        else if(v == btnE){if (!MainActivity.game.erSpilletSlut()) {MainActivity.game.gætBogstav(alphabet[4]); btnLetterClick(); spilCheck();} YoYo.with(Techniques.FlipOutX).duration(400).playOn(btnE);}
        else if(v == btnF){if (!MainActivity.game.erSpilletSlut()) {MainActivity.game.gætBogstav(alphabet[5]); btnLetterClick(); spilCheck();} YoYo.with(Techniques.FlipOutX).duration(400).playOn(btnF);}
        else if(v == btnG){if (!MainActivity.game.erSpilletSlut()) {MainActivity.game.gætBogstav(alphabet[6]); btnLetterClick(); spilCheck();} YoYo.with(Techniques.FlipOutX).duration(400).playOn(btnG);}
        else if(v == btnH){if (!MainActivity.game.erSpilletSlut()) {MainActivity.game.gætBogstav(alphabet[7]); btnLetterClick(); spilCheck();} YoYo.with(Techniques.FlipOutX).duration(400).playOn(btnH);}
        else if(v == btnI){if (!MainActivity.game.erSpilletSlut()) {MainActivity.game.gætBogstav(alphabet[8]); btnLetterClick(); spilCheck();} YoYo.with(Techniques.FlipOutX).duration(400).playOn(btnI);}
        else if(v == btnJ){if (!MainActivity.game.erSpilletSlut()) {MainActivity.game.gætBogstav(alphabet[9]); btnLetterClick(); spilCheck();} YoYo.with(Techniques.FlipOutX).duration(400).playOn(btnJ);}
        else if(v == btnK){if (!MainActivity.game.erSpilletSlut()) {MainActivity.game.gætBogstav(alphabet[10]); btnLetterClick(); spilCheck();} YoYo.with(Techniques.FlipOutX).duration(400).playOn(btnK);}
        else if(v == btnL){if (!MainActivity.game.erSpilletSlut()) {MainActivity.game.gætBogstav(alphabet[11]); btnLetterClick(); spilCheck();} YoYo.with(Techniques.FlipOutX).duration(400).playOn(btnL);}
        else if(v == btnM){if (!MainActivity.game.erSpilletSlut()) {MainActivity.game.gætBogstav(alphabet[12]); btnLetterClick(); spilCheck();} YoYo.with(Techniques.FlipOutX).duration(400).playOn(btnM);}
        else if(v == btnN){if (!MainActivity.game.erSpilletSlut()) {MainActivity.game.gætBogstav(alphabet[13]); btnLetterClick(); spilCheck();} YoYo.with(Techniques.FlipOutX).duration(400).playOn(btnN);}
        else if(v == btnO){if (!MainActivity.game.erSpilletSlut()) {MainActivity.game.gætBogstav(alphabet[14]); btnLetterClick(); spilCheck();} YoYo.with(Techniques.FlipOutX).duration(400).playOn(btnO);}
        else if(v == btnP){if (!MainActivity.game.erSpilletSlut()) {MainActivity.game.gætBogstav(alphabet[15]); btnLetterClick(); spilCheck();} YoYo.with(Techniques.FlipOutX).duration(400).playOn(btnP);}
        else if(v == btnQ){if (!MainActivity.game.erSpilletSlut()) {MainActivity.game.gætBogstav(alphabet[16]); btnLetterClick(); spilCheck();} YoYo.with(Techniques.FlipOutX).duration(400).playOn(btnQ);}
        else if(v == btnR){if (!MainActivity.game.erSpilletSlut()) {MainActivity.game.gætBogstav(alphabet[17]); btnLetterClick(); spilCheck();} YoYo.with(Techniques.FlipOutX).duration(400).playOn(btnR);}
        else if(v == btnS){if (!MainActivity.game.erSpilletSlut()) {MainActivity.game.gætBogstav(alphabet[18]); btnLetterClick(); spilCheck();} YoYo.with(Techniques.FlipOutX).duration(400).playOn(btnS);}
        else if(v == btnT){if (!MainActivity.game.erSpilletSlut()) {MainActivity.game.gætBogstav(alphabet[19]); btnLetterClick(); spilCheck();} YoYo.with(Techniques.FlipOutX).duration(400).playOn(btnT);}
        else if(v == btnU){if (!MainActivity.game.erSpilletSlut()) {MainActivity.game.gætBogstav(alphabet[20]); btnLetterClick(); spilCheck();} YoYo.with(Techniques.FlipOutX).duration(400).playOn(btnU);}
        else if(v == btnV){if (!MainActivity.game.erSpilletSlut()) {MainActivity.game.gætBogstav(alphabet[21]); btnLetterClick(); spilCheck();} YoYo.with(Techniques.FlipOutX).duration(400).playOn(btnV);}
        else if(v == btnW){if (!MainActivity.game.erSpilletSlut()) {MainActivity.game.gætBogstav(alphabet[22]); btnLetterClick(); spilCheck();} YoYo.with(Techniques.FlipOutX).duration(400).playOn(btnW);}
        else if(v == btnX){if (!MainActivity.game.erSpilletSlut()) {MainActivity.game.gætBogstav(alphabet[23]); btnLetterClick(); spilCheck();} YoYo.with(Techniques.FlipOutX).duration(400).playOn(btnX);}
        else if(v == btnY){if (!MainActivity.game.erSpilletSlut()) {MainActivity.game.gætBogstav(alphabet[24]); btnLetterClick(); spilCheck();} YoYo.with(Techniques.FlipOutX).duration(400).playOn(btnY);}
        else if(v == btnZ){if (!MainActivity.game.erSpilletSlut()) {MainActivity.game.gætBogstav(alphabet[25]); btnLetterClick(); spilCheck();} YoYo.with(Techniques.FlipOutX).duration(400).playOn(btnZ);}
        else if(v == btnÆ){if (!MainActivity.game.erSpilletSlut()) {MainActivity.game.gætBogstav(alphabet[26]); btnLetterClick(); spilCheck();} YoYo.with(Techniques.FlipOutX).duration(400).playOn(btnÆ);}
        else if(v == btnØ){if (!MainActivity.game.erSpilletSlut()) {MainActivity.game.gætBogstav(alphabet[27]); btnLetterClick(); spilCheck();} YoYo.with(Techniques.FlipOutX).duration(400).playOn(btnØ);}
        else if(v == btnÅ){if (!MainActivity.game.erSpilletSlut()) {MainActivity.game.gætBogstav(alphabet[28]); btnLetterClick(); spilCheck();} YoYo.with(Techniques.FlipOutX).duration(400).playOn(btnÅ);}

        else if (v == hintBtn) {
            String bogstav = MainActivity.game.getHint();
            if (bogstav.equals("a")) {
                onClick(btnA);
            } else if (bogstav.equals("b")) {
                onClick(btnB);
            } else if (bogstav.equals("c")) {
                onClick(btnC);
            } else if (bogstav.equals("d")) {
                onClick(btnD);
            } else if (bogstav.equals("e")) {
                onClick(btnE);
            } else if (bogstav.equals("f")) {
                onClick(btnF);
            } else if (bogstav.equals("g")) {
                onClick(btnG);
            } else if (bogstav.equals("h")) {
                onClick(btnH);
            } else if (bogstav.equals("i")) {
                onClick(btnI);
            } else if (bogstav.equals("j")) {
                onClick(btnJ);
            } else if (bogstav.equals("k")) {
                onClick(btnK);
            } else if (bogstav.equals("l")) {
                onClick(btnL);
            } else if (bogstav.equals("m")) {
                onClick(btnM);
            } else if (bogstav.equals("n")) {
                onClick(btnN);
            } else if (bogstav.equals("o")) {
                onClick(btnO);
            } else if (bogstav.equals("p")) {
                onClick(btnP);
            } else if (bogstav.equals("q")) {
                onClick(btnQ);
            } else if (bogstav.equals("r")) {
                onClick(btnR);
            } else if (bogstav.equals("s")) {
                onClick(btnS);
            } else if (bogstav.equals("t")) {
                onClick(btnT);
            } else if (bogstav.equals("u")) {
                onClick(btnU);
            } else if (bogstav.equals("v")) {
                onClick(btnV);
            } else if (bogstav.equals("w")) {
                onClick(btnW);
            } else if (bogstav.equals("x")) {
                onClick(btnX);
            } else if (bogstav.equals("y")) {
                onClick(btnY);
            } else if (bogstav.equals("z")) {
                onClick(btnZ);
            } else if (bogstav.equals("æ")) {
                onClick(btnÆ);
            } else if (bogstav.equals("ø")) {
                onClick(btnØ);
            } else if (bogstav.equals("å")) {
                onClick(btnÅ);
            }

            hintBtn.setBackgroundColor(0);
        }

        else if(v == playagain){
            animFlipIn();
            if(Settings_Frag.effectIsPlaying){MainActivity.soundButton.start();}
            playAgainView.setVisibility(View.GONE);
            //playagain.setVisibility(View.GONE);
            gættilbage.setVisibility(View.VISIBLE);
            normTekst.setVisibility(View.VISIBLE);
            hintBtn.setVisibility(View.VISIBLE);
            letterBoxView.setVisibility(View.VISIBLE);


            //restartSpil();

            spilRefresh();

            //getActivity().finish();
            //android.os.Process.killProcess(android.os.Process.myPid());

        }


    }

    public void animFlipIn (){
        YoYo.with(Techniques.FlipInX).delay(0).duration(400).playOn(btnA);
        YoYo.with(Techniques.FlipInX).delay(10).duration(400).playOn(btnB);
        YoYo.with(Techniques.FlipInX).delay(20).duration(400).playOn(btnC);
        YoYo.with(Techniques.FlipInX).delay(30).duration(400).playOn(btnD);
        YoYo.with(Techniques.FlipInX).delay(40).duration(400).playOn(btnE);
        YoYo.with(Techniques.FlipInX).delay(50).duration(400).playOn(btnF);
        YoYo.with(Techniques.FlipInX).delay(60).duration(400).playOn(btnG);
        YoYo.with(Techniques.FlipInX).delay(70).duration(400).playOn(btnH);
        YoYo.with(Techniques.FlipInX).delay(80).duration(400).playOn(btnI);
        YoYo.with(Techniques.FlipInX).delay(90).duration(400).playOn(btnJ);
        YoYo.with(Techniques.FlipInX).delay(100).duration(400).playOn(btnK);
        YoYo.with(Techniques.FlipInX).delay(110).duration(400).playOn(btnL);
        YoYo.with(Techniques.FlipInX).delay(120).duration(400).playOn(btnM);
        YoYo.with(Techniques.FlipInX).delay(130).duration(400).playOn(btnN);
        YoYo.with(Techniques.FlipInX).delay(140).duration(400).playOn(btnO);
        YoYo.with(Techniques.FlipInX).delay(150).duration(400).playOn(btnP);
        YoYo.with(Techniques.FlipInX).delay(160).duration(400).playOn(btnQ);
        YoYo.with(Techniques.FlipInX).delay(170).duration(400).playOn(btnR);
        YoYo.with(Techniques.FlipInX).delay(180).duration(400).playOn(btnS);
        YoYo.with(Techniques.FlipInX).delay(190).duration(400).playOn(btnT);
        YoYo.with(Techniques.FlipInX).delay(200).duration(400).playOn(btnU);
        YoYo.with(Techniques.FlipInX).delay(210).duration(400).playOn(btnV);
        YoYo.with(Techniques.FlipInX).delay(220).duration(400).playOn(btnW);
        YoYo.with(Techniques.FlipInX).delay(230).duration(400).playOn(btnX);
        YoYo.with(Techniques.FlipInX).delay(240).duration(400).playOn(btnY);
        YoYo.with(Techniques.FlipInX).delay(250).duration(400).playOn(btnZ);
        YoYo.with(Techniques.FlipInX).delay(260).duration(400).playOn(btnÆ);
        YoYo.with(Techniques.FlipInX).delay(270).duration(400).playOn(btnØ);
        YoYo.with(Techniques.FlipInX).delay(280).duration(400).playOn(btnÅ);
    }


    public void btnLetterClick(){
        wordText.setText(MainActivity.game.getSynligtOrd());
        gættilbage.setText(String.valueOf(7 - MainActivity.game.getAntalForkerteBogstaver()));
        scoreText.setText(String.valueOf(MainActivity.game.getScore()));
    }


    public void spilCheck(){
        if(!MainActivity.game.erSidsteBogstavKorrekt()) {
            if (!MainActivity.game.erSpilletSlut()) {
                galgeImg.setImageResource(wrongImg[MainActivity.game.getAntalForkerteBogstaver() - 1]);
                spilIgang = true;
                if(Settings_Frag.effectIsPlaying){MainActivity.soundOuch.start();}
            } else {
                spilIgang = false;
                //Kan gøres til en metode for at spare kode men nu lavede jeg det i uden lige at tænke på det så fuck det (Y)
                if (MainActivity.game.erSpilletVundet()) {
                    galgeImg.setImageResource(R.drawable.vundet);
                    wordText.setText("Du har vundet! Ordet var: " + MainActivity.game.getOrdet());
                    if(Settings_Frag.effectIsPlaying){MainActivity.soundAlive.start();}
                    setVisibleView();

                } else {
                    galgeImg.setImageResource(R.drawable.tabt);
                    wordText.setText("Du har tabt! Ordet var: " + MainActivity.game.getOrdet());
                    if(Settings_Frag.effectIsPlaying){MainActivity.soundDeath.start();}
                    setVisibleView();
                }
            }
        }
        else {
            if(Settings_Frag.effectIsPlaying){MainActivity.soundYes.start();}
            if(MainActivity.game.erSpilletSlut())
                if (MainActivity.game.erSpilletVundet()) {
                    spilIgang = false;
                    galgeImg.setImageResource(R.drawable.vundet);
                    wordText.setText("Du har vundet! Ordet var: " + MainActivity.game.getOrdet());
                    if(Settings_Frag.effectIsPlaying){MainActivity.soundAlive.start();}
                    setVisibleView();
                    MainActivity.game.opdaterAlleBrugere();
                    String nickname = MainActivity.game.readFromFile(getContext());

                    if (nickname == "") {
                        FragmentManager fm = getFragmentManager();
                        dialogFragment = new Nyt_Nickname_Dialog_Frag();
                        dialogFragment.show(fm, "Highscore slået");
                    } else {
                        if (!(MainActivity.game.opdaterHighscore(nickname) == -1)) {
                            FragmentManager fm = getFragmentManager();
                            nyhighscoreFragment = new Ny_Highscore_Dialog_Frag();
                            nyhighscoreFragment.show(fm, "Highscore slået");
                        }
                    }
                } else {
                    spilIgang = false;
                    galgeImg.setImageResource(R.drawable.tabt);
                    wordText.setText("Du har tabt! Ordet var: " + MainActivity.game.getOrdet());
                    if(Settings_Frag.effectIsPlaying){MainActivity.soundDeath.start();}
                    setVisibleView();
                }
                /*
                if(!String.valueOf(guessedWords.getText()).contains(" " + alphabet[charPicker.getValue()] + " ")){
                    guessedWords.append(alphabet[charPicker.getValue()] + " ");
                }*/
        }


    }


    public static void spilRefresh(){
        spilIgang = true;
        MainActivity.game.nulstil();
        wordText.setText(MainActivity.game.getSynligtOrd());
        gættilbage.setText("7");
        scoreText.setText("0");
        galgeImg.setImageResource(R.drawable.galge);
        wrongImg = new Integer[]{
                R.drawable.forkert1, R.drawable.forkert2, R.drawable.forkert3, R.drawable.forkert4, R.drawable.forkert5, R.drawable.forkert6
        };

        btnA.setBackgroundColor(0xFF37474F);
        btnB.setBackgroundColor(0xFF37474F);
        btnC.setBackgroundColor(0xFF37474F);
        btnD.setBackgroundColor(0xFF37474F);
        btnE.setBackgroundColor(0xFF37474F);
        btnF.setBackgroundColor(0xFF37474F);
        btnG.setBackgroundColor(0xFF37474F);
        btnH.setBackgroundColor(0xFF37474F);
        btnI.setBackgroundColor(0xFF37474F);
        btnJ.setBackgroundColor(0xFF37474F);
        btnK.setBackgroundColor(0xFF37474F);
        btnL.setBackgroundColor(0xFF37474F);
        btnM.setBackgroundColor(0xFF37474F);
        btnN.setBackgroundColor(0xFF37474F);
        btnO.setBackgroundColor(0xFF37474F);
        btnP.setBackgroundColor(0xFF37474F);
        btnQ.setBackgroundColor(0xFF37474F);
        btnR.setBackgroundColor(0xFF37474F);
        btnS.setBackgroundColor(0xFF37474F);
        btnT.setBackgroundColor(0xFF37474F);
        btnU.setBackgroundColor(0xFF37474F);
        btnV.setBackgroundColor(0xFF37474F);
        btnW.setBackgroundColor(0xFF37474F);
        btnX.setBackgroundColor(0xFF37474F);
        btnY.setBackgroundColor(0xFF37474F);
        btnZ.setBackgroundColor(0xFF37474F);
        btnÆ.setBackgroundColor(0xFF37474F);
        btnØ.setBackgroundColor(0xFF37474F);
        btnÅ.setBackgroundColor(0xFF37474F);
        hintBtn.setBackgroundColor(0xFF37474F);

    }

    private class DrAsync extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            if (MainActivity.game.getMuligeOrd().size() == 8) {
                try {
                    MainActivity.game.opdaterOrdliste();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result){restartSpil();}

    }
}
