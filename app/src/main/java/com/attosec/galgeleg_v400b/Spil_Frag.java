package com.attosec.galgeleg_v400b;


import android.graphics.Color;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

public class Spil_Frag extends Fragment implements View.OnClickListener{

    private NumberPicker charPicker;
    private String[] alphabet = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "æ", "ø", "å"};

    public static Button guessButton;
    private Button playagain;
    public static TextView wordText;
    public static TextView guessedWords;
    public static ImageView galgeImg;
    public static Integer[] wrongImg;
    public static TextView gættilbage;
    public static TextView normTekst;
    private Custom_Dialog_Frag dialogFragment;
    private Button btnA, btnB, btnC, btnD, btnE, btnF, btnG, btnH, btnI, btnJ, btnK, btnL;
    private LinearLayout letterBoxView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //getActivity().setTitle("Galgeleg");
        View rod = inflater.inflate(R.layout.spil_frag, container, false);

        letterBoxView = (LinearLayout) rod.findViewById(R.id.letterBox0);

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

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //if(game == null){game = new HangmanLogic();}

        //brugerDAO = new BrugerDAO();
        charPicker = (NumberPicker) rod.findViewById(R.id.charPicker);
        playagain = (Button) rod.findViewById(R.id.btnPlayAgain);
        galgeImg = (ImageView) rod.findViewById(R.id.imageView);
        wordText = (TextView) rod.findViewById(R.id.wordText);
        guessedWords = (TextView) rod.findViewById(R.id.guessedWords);
        gættilbage = (TextView) rod.findViewById(R.id.guessBackNumber);
        guessButton = (Button) rod.findViewById(R.id.btnGuess);
        normTekst = (TextView) rod.findViewById(R.id.guessBackTitle);
        charPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        charPicker.setMaxValue(28);
        charPicker.setDisplayedValues(alphabet);
        wordText.setText("Loading...");
        playagain.setOnClickListener(this);

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


        DrAsync firebaseOrdliste = new DrAsync();
        firebaseOrdliste.execute();
        MainActivity.game.nulstil();
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Spil Galgeleg");

        //btnA.setVisibility(View.VISIBLE);

        return rod;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        //finish();
        //android.os.Process.killProcess(android.os.Process.myPid());
        getActivity().finish();
        return true;
    }

    public void setVisibleView(){
        playagain.setVisibility(View.VISIBLE);
        //charPicker.setVisibility(View.INVISIBLE);
        //guessButton.setVisibility(View.INVISIBLE);
        //guessedWords.setVisibility(View.INVISIBLE);
        gættilbage.setVisibility(View.INVISIBLE);
        normTekst.setVisibility(View.INVISIBLE);
        letterBoxView.setVisibility(View.INVISIBLE);


        //btnAA.setVisibility(View.VISIBLE);
    }

    public void restartSpil(){
        MainActivity.game.nulstil();
        //guessedWords.setText("Brugte bogstaver: ");
        wordText.setText(MainActivity.game.getSynligtOrd());
        gættilbage.setText("7");
        galgeImg.setImageResource(R.drawable.galge);
        wrongImg = new Integer[]{
                R.drawable.forkert1, R.drawable.forkert2, R.drawable.forkert3, R.drawable.forkert4, R.drawable.forkert5, R.drawable.forkert6
        };
        //guessButton.setOnClickListener(this);

    }



    @Override
    public void onClick(View v) {
        if (v == guessButton) {}
        else if(v == playagain){
            playagain.setVisibility(View.GONE);
            //charPicker.setVisibility(View.VISIBLE);
            //guessButton.setVisibility(View.VISIBLE);
            //guessedWords.setVisibility(View.VISIBLE);
            gættilbage.setVisibility(View.VISIBLE);
            normTekst.setVisibility(View.VISIBLE);
            letterBoxView.setVisibility(View.VISIBLE);
            btnA.setBackgroundColor(getContext().getResources().getColor(R.color.colorPrimary));
            btnB.setBackgroundColor(getContext().getResources().getColor(R.color.colorPrimary));
            btnC.setBackgroundColor(getContext().getResources().getColor(R.color.colorPrimary));
            btnD.setBackgroundColor(getContext().getResources().getColor(R.color.colorPrimary));
            btnE.setBackgroundColor(getContext().getResources().getColor(R.color.colorPrimary));
            btnF.setBackgroundColor(getContext().getResources().getColor(R.color.colorPrimary));
            btnG.setBackgroundColor(getContext().getResources().getColor(R.color.colorPrimary));
            btnH.setBackgroundColor(getContext().getResources().getColor(R.color.colorPrimary));
            btnI.setBackgroundColor(getContext().getResources().getColor(R.color.colorPrimary));
            btnJ.setBackgroundColor(getContext().getResources().getColor(R.color.colorPrimary));
            btnK.setBackgroundColor(getContext().getResources().getColor(R.color.colorPrimary));
            btnL.setBackgroundColor(getContext().getResources().getColor(R.color.colorPrimary));

            restartSpil();

            //spilRefresh();

            //getActivity().finish();
            //android.os.Process.killProcess(android.os.Process.myPid());
        }
        else if(v == btnA){if (!MainActivity.game.erSpilletSlut()) {MainActivity.game.gætBogstav(alphabet[0]); btnLetterClick(); spilCheck();} btnA.setBackgroundColor(000000);}
        else if(v == btnB){if (!MainActivity.game.erSpilletSlut()) {MainActivity.game.gætBogstav(alphabet[1]); btnLetterClick(); spilCheck();} btnB.setBackgroundColor(000000);}
        else if(v == btnC){if (!MainActivity.game.erSpilletSlut()) {MainActivity.game.gætBogstav(alphabet[2]); btnLetterClick(); spilCheck();} btnC.setBackgroundColor(000000);}
        else if(v == btnD){if (!MainActivity.game.erSpilletSlut()) {MainActivity.game.gætBogstav(alphabet[3]); btnLetterClick(); spilCheck();} btnD.setBackgroundColor(000000);}
        else if(v == btnE){if (!MainActivity.game.erSpilletSlut()) {MainActivity.game.gætBogstav(alphabet[4]); btnLetterClick(); spilCheck();} btnE.setBackgroundColor(000000);}
        else if(v == btnF){if (!MainActivity.game.erSpilletSlut()) {MainActivity.game.gætBogstav(alphabet[5]); btnLetterClick(); spilCheck();} btnF.setBackgroundColor(000000);}
        else if(v == btnG){if (!MainActivity.game.erSpilletSlut()) {MainActivity.game.gætBogstav(alphabet[6]); btnLetterClick(); spilCheck();} btnG.setBackgroundColor(000000);}
        else if(v == btnH){if (!MainActivity.game.erSpilletSlut()) {MainActivity.game.gætBogstav(alphabet[7]); btnLetterClick(); spilCheck();} btnH.setBackgroundColor(000000);}
        else if(v == btnI){if (!MainActivity.game.erSpilletSlut()) {MainActivity.game.gætBogstav(alphabet[8]); btnLetterClick(); spilCheck();} btnI.setBackgroundColor(000000);}
        else if(v == btnJ){if (!MainActivity.game.erSpilletSlut()) {MainActivity.game.gætBogstav(alphabet[9]); btnLetterClick(); spilCheck();} btnJ.setBackgroundColor(000000);}
        else if(v == btnK){if (!MainActivity.game.erSpilletSlut()) {MainActivity.game.gætBogstav(alphabet[10]); btnLetterClick(); spilCheck();} btnK.setBackgroundColor(000000);}
        else if(v == btnL){if (!MainActivity.game.erSpilletSlut()) {MainActivity.game.gætBogstav(alphabet[11]); btnLetterClick(); spilCheck();} btnL.setBackgroundColor(000000);}
    }

    public void btnLetterClick(){
        wordText.setText(MainActivity.game.getSynligtOrd());
        gættilbage.setText(String.valueOf(7 - MainActivity.game.getAntalForkerteBogstaver()));
        }

    public void spilCheck(){


            if(!MainActivity.game.erSidsteBogstavKorrekt()) {
                if (!MainActivity.game.erSpilletSlut()) {
                    galgeImg.setImageResource(wrongImg[MainActivity.game.getAntalForkerteBogstaver() - 1]);
                } else {
                    //Kan gøres til en metode for at spare kode men nu lavede jeg det i uden lige at tænke på det så fuck det (Y)
                    if (MainActivity.game.erSpilletVundet()) {
                        galgeImg.setImageResource(R.drawable.vundet);
                        wordText.setText("Du har vundet! Ordet var: " + MainActivity.game.getOrdet());
                        setVisibleView();
                        String nickname = MainActivity.game.readFromFile(getContext());
                        if (nickname == "") {
                            FragmentManager fm = getFragmentManager();
                            dialogFragment = new Custom_Dialog_Frag();
                            dialogFragment.show(fm, "Highscore slået");
                        } else {
                            MainActivity.game.updateHigscore(String.valueOf(nickname));
                        }
                    } else {
                        galgeImg.setImageResource(R.drawable.tabt);
                        wordText.setText("Du har tabt! Ordet var: " + MainActivity.game.getOrdet());
                        setVisibleView();
                    }
                }
            }
            else if(MainActivity.game.erSpilletSlut())
                if (MainActivity.game.erSpilletVundet()) {
                    galgeImg.setImageResource(R.drawable.vundet);
                    wordText.setText("Du har vundet! Ordet var: " + MainActivity.game.getOrdet());
                    setVisibleView();
                    if (MainActivity.game.updateHigscore("nicolai") != -1) {
                        FragmentManager fm = getFragmentManager();
                        dialogFragment = new Custom_Dialog_Frag();
                        dialogFragment.show(fm, "Highscore slået");
                    }
                } else {
                    galgeImg.setImageResource(R.drawable.tabt);
                    wordText.setText("Du har tabt! Ordet var: " + MainActivity.game.getOrdet());
                    setVisibleView();
                }
                /*
                if(!String.valueOf(guessedWords.getText()).contains(" " + alphabet[charPicker.getValue()] + " ")){
                    guessedWords.append(alphabet[charPicker.getValue()] + " ");
                }*/

    }


    public static void spilRefresh(){
        MainActivity.game.nulstil();
        wordText.setText(MainActivity.game.getSynligtOrd());
        gættilbage.setText("7");
        guessedWords.setText("Brugte bogstaver: ");
        galgeImg.setImageResource(R.drawable.galge);
    }

    private class DrAsync extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            if (MainActivity.game.getAllWords().size() == 8) {
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
