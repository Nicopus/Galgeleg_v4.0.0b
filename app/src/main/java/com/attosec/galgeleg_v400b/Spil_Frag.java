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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

public class Spil_Frag extends Fragment implements View.OnClickListener{

    private NumberPicker charPicker;
    private String[] alphabet = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "æ", "ø", "å"};
    public static Button btnA, btnB, btnC, btnD, btnE, btnF, btnG, btnH, btnI, btnJ, btnK, btnL, btnM, btnN, btnO, btnP, btnQ, btnR, btnS, btnT, btnU, btnV, btnW, btnX, btnY, btnZ, btnÆ, btnØ, btnÅ;
    private LinearLayout letterBoxView;
    private LinearLayout charPickerView;
    private LinearLayout playAgainView;

    public static Button guessButton;
    private Button playagain;
    public static TextView wordText;
    public static TextView guessedWords;
    public static ImageView galgeImg;
    public static Integer[] wrongImg;
    public static TextView gættilbage;
    public static TextView normTekst;
    private Custom_Dialog_Frag dialogFragment;
    public static  boolean spilIgang = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //getActivity().setTitle("Galgeleg");
        View rod = inflater.inflate(R.layout.spil_frag, container, false);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //if(game == null){game = new HangmanLogic();}

        letterBoxView = (LinearLayout) rod.findViewById(R.id.letterBoxLayout);
        charPickerView = (LinearLayout) rod.findViewById(R.id.charPickerLayout);
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


        DrAsync firebaseOrdliste = new DrAsync();
        firebaseOrdliste.execute();
        MainActivity.game.nulstil();
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Spil Galgeleg");
        spilIgang = true;

        return rod;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        //finish();
        //android.os.Process.killProcess(android.os.Process.myPid());
        getActivity().finish();
        return true;
    }

    public void setVisibleView(){
        playAgainView.setVisibility(View.VISIBLE);
        //playagain.setVisibility(View.VISIBLE);

        charPickerView.setVisibility(View.GONE);
        //charPicker.setVisibility(View.INVISIBLE);
        //guessButton.setVisibility(View.INVISIBLE);
        //guessedWords.setVisibility(View.INVISIBLE);
        gættilbage.setVisibility(View.INVISIBLE);
        normTekst.setVisibility(View.INVISIBLE);

        letterBoxView.setVisibility(View.GONE);
    }

    public void restartSpil(){
        MainActivity.game.nulstil();
        guessedWords.setText("Brugte bogstaver: ");
        wordText.setText(MainActivity.game.getSynligtOrd());
        gættilbage.setText("7");
        galgeImg.setImageResource(R.drawable.galge);
        wrongImg = new Integer[]{
                R.drawable.forkert1, R.drawable.forkert2, R.drawable.forkert3, R.drawable.forkert4, R.drawable.forkert5, R.drawable.forkert6
        };
        guessButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == guessButton) {}
        else if(v == btnA){if (!MainActivity.game.erSpilletSlut()) {MainActivity.game.gætBogstav(alphabet[0]); btnLetterClick(); spilCheck();} btnA.setBackgroundColor(0);}
        else if(v == btnB){if (!MainActivity.game.erSpilletSlut()) {MainActivity.game.gætBogstav(alphabet[1]); btnLetterClick(); spilCheck();} btnB.setBackgroundColor(0);}
        else if(v == btnC){if (!MainActivity.game.erSpilletSlut()) {MainActivity.game.gætBogstav(alphabet[2]); btnLetterClick(); spilCheck();} btnC.setBackgroundColor(0);}
        else if(v == btnD){if (!MainActivity.game.erSpilletSlut()) {MainActivity.game.gætBogstav(alphabet[3]); btnLetterClick(); spilCheck();} btnD.setBackgroundColor(0);}
        else if(v == btnE){if (!MainActivity.game.erSpilletSlut()) {MainActivity.game.gætBogstav(alphabet[4]); btnLetterClick(); spilCheck();} btnE.setBackgroundColor(0);}
        else if(v == btnF){if (!MainActivity.game.erSpilletSlut()) {MainActivity.game.gætBogstav(alphabet[5]); btnLetterClick(); spilCheck();} btnF.setBackgroundColor(0);}
        else if(v == btnG){if (!MainActivity.game.erSpilletSlut()) {MainActivity.game.gætBogstav(alphabet[6]); btnLetterClick(); spilCheck();} btnG.setBackgroundColor(0);}
        else if(v == btnH){if (!MainActivity.game.erSpilletSlut()) {MainActivity.game.gætBogstav(alphabet[7]); btnLetterClick(); spilCheck();} btnH.setBackgroundColor(0);}
        else if(v == btnI){if (!MainActivity.game.erSpilletSlut()) {MainActivity.game.gætBogstav(alphabet[8]); btnLetterClick(); spilCheck();} btnI.setBackgroundColor(0);}
        else if(v == btnJ){if (!MainActivity.game.erSpilletSlut()) {MainActivity.game.gætBogstav(alphabet[9]); btnLetterClick(); spilCheck();} btnJ.setBackgroundColor(0);}
        else if(v == btnK){if (!MainActivity.game.erSpilletSlut()) {MainActivity.game.gætBogstav(alphabet[10]); btnLetterClick(); spilCheck();} btnK.setBackgroundColor(0);}
        else if(v == btnL){if (!MainActivity.game.erSpilletSlut()) {MainActivity.game.gætBogstav(alphabet[11]); btnLetterClick(); spilCheck();} btnL.setBackgroundColor(0);}
        else if(v == btnM){if (!MainActivity.game.erSpilletSlut()) {MainActivity.game.gætBogstav(alphabet[12]); btnLetterClick(); spilCheck();} btnM.setBackgroundColor(0);}
        else if(v == btnN){if (!MainActivity.game.erSpilletSlut()) {MainActivity.game.gætBogstav(alphabet[13]); btnLetterClick(); spilCheck();} btnN.setBackgroundColor(0);}
        else if(v == btnO){if (!MainActivity.game.erSpilletSlut()) {MainActivity.game.gætBogstav(alphabet[14]); btnLetterClick(); spilCheck();} btnO.setBackgroundColor(0);}
        else if(v == btnP){if (!MainActivity.game.erSpilletSlut()) {MainActivity.game.gætBogstav(alphabet[15]); btnLetterClick(); spilCheck();} btnP.setBackgroundColor(0);}
        else if(v == btnQ){if (!MainActivity.game.erSpilletSlut()) {MainActivity.game.gætBogstav(alphabet[16]); btnLetterClick(); spilCheck();} btnQ.setBackgroundColor(0);}
        else if(v == btnR){if (!MainActivity.game.erSpilletSlut()) {MainActivity.game.gætBogstav(alphabet[17]); btnLetterClick(); spilCheck();} btnR.setBackgroundColor(0);}
        else if(v == btnS){if (!MainActivity.game.erSpilletSlut()) {MainActivity.game.gætBogstav(alphabet[18]); btnLetterClick(); spilCheck();} btnS.setBackgroundColor(0);}
        else if(v == btnT){if (!MainActivity.game.erSpilletSlut()) {MainActivity.game.gætBogstav(alphabet[19]); btnLetterClick(); spilCheck();} btnT.setBackgroundColor(0);}
        else if(v == btnU){if (!MainActivity.game.erSpilletSlut()) {MainActivity.game.gætBogstav(alphabet[20]); btnLetterClick(); spilCheck();} btnU.setBackgroundColor(0);}
        else if(v == btnV){if (!MainActivity.game.erSpilletSlut()) {MainActivity.game.gætBogstav(alphabet[21]); btnLetterClick(); spilCheck();} btnV.setBackgroundColor(0);}
        else if(v == btnW){if (!MainActivity.game.erSpilletSlut()) {MainActivity.game.gætBogstav(alphabet[22]); btnLetterClick(); spilCheck();} btnW.setBackgroundColor(0);}
        else if(v == btnX){if (!MainActivity.game.erSpilletSlut()) {MainActivity.game.gætBogstav(alphabet[23]); btnLetterClick(); spilCheck();} btnX.setBackgroundColor(0);}
        else if(v == btnY){if (!MainActivity.game.erSpilletSlut()) {MainActivity.game.gætBogstav(alphabet[24]); btnLetterClick(); spilCheck();} btnY.setBackgroundColor(0);}
        else if(v == btnZ){if (!MainActivity.game.erSpilletSlut()) {MainActivity.game.gætBogstav(alphabet[25]); btnLetterClick(); spilCheck();} btnZ.setBackgroundColor(0);}
        else if(v == btnÆ){if (!MainActivity.game.erSpilletSlut()) {MainActivity.game.gætBogstav(alphabet[26]); btnLetterClick(); spilCheck();} btnÆ.setBackgroundColor(0);}
        else if(v == btnØ){if (!MainActivity.game.erSpilletSlut()) {MainActivity.game.gætBogstav(alphabet[27]); btnLetterClick(); spilCheck();} btnØ.setBackgroundColor(0);}
        else if(v == btnÅ){if (!MainActivity.game.erSpilletSlut()) {MainActivity.game.gætBogstav(alphabet[28]); btnLetterClick(); spilCheck();} btnÅ.setBackgroundColor(0);}

        else if(v == playagain){
            playAgainView.setVisibility(View.GONE);
            //playagain.setVisibility(View.GONE);
            gættilbage.setVisibility(View.VISIBLE);
            normTekst.setVisibility(View.VISIBLE);
            if(MainActivity.isLetterBox = false){
                charPickerView.setVisibility(View.VISIBLE);
                //charPicker.setVisibility(View.VISIBLE);
                //guessButton.setVisibility(View.VISIBLE);
                //guessedWords.setVisibility(View.VISIBLE);
            }else{
                letterBoxView.setVisibility(View.VISIBLE);
            }

            //restartSpil();

            spilRefresh();

            //getActivity().finish();
            //android.os.Process.killProcess(android.os.Process.myPid());

        }

    }

    public void btnLetterClick(){
        wordText.setText(MainActivity.game.getSynligtOrd());
        gættilbage.setText(String.valueOf(7 - MainActivity.game.getAntalForkerteBogstaver()));
    }


    public void spilCheck(){
        if(!MainActivity.game.erSidsteBogstavKorrekt()) {
            if (!MainActivity.game.erSpilletSlut()) {
                galgeImg.setImageResource(wrongImg[MainActivity.game.getAntalForkerteBogstaver() - 1]);
                spilIgang = true;
            } else {
                spilIgang = false;
                //Kan gøres til en metode for at spare kode men nu lavede jeg det i uden lige at tænke på det så fuck det (Y)
                if (MainActivity.game.erSpilletVundet()) {
                    galgeImg.setImageResource(R.drawable.vundet);
                    wordText.setText("Du har vundet! Ordet var: " + MainActivity.game.getOrdet());
                    setVisibleView();

                } else {
                    galgeImg.setImageResource(R.drawable.tabt);
                    wordText.setText("Du har tabt! Ordet var: " + MainActivity.game.getOrdet());
                    setVisibleView();
                }
            }
        }
        else if(MainActivity.game.erSpilletSlut())
            if (MainActivity.game.erSpilletVundet()) {
                spilIgang = false;
                galgeImg.setImageResource(R.drawable.vundet);
                wordText.setText("Du har vundet! Ordet var: " + MainActivity.game.getOrdet());
                setVisibleView();
                MainActivity.game.opdaterAlleBrugere();
                String nickname = MainActivity.game.readFromFile(getContext());

                if (nickname == "") {
                    FragmentManager fm = getFragmentManager();
                    dialogFragment = new Custom_Dialog_Frag();
                    dialogFragment.show(fm, "Highscore slået");
                }
            } else {
                spilIgang = false;
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
        spilIgang = true;
        MainActivity.game.nulstil();
        wordText.setText(MainActivity.game.getSynligtOrd());
        gættilbage.setText("7");
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
