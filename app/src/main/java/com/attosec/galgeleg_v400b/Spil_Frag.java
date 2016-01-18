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

    public static Button guessButton;
    private Button playagain;
    public static TextView wordText;
    public static TextView guessedWords;
    public static ImageView galgeImg;
    public static Integer[] wrongImg;
    public static TextView gættilbage;
    public static TextView normTekst;
    private Custom_Dialog_Frag dialogFragment;
    public static Button btnA, btnB, btnC, btnD, btnE, btnF, btnG, btnH, btnI, btnJ, btnK, btnL, btnM, btnN, btnO, btnP, btnQ, btnR, btnS, btnT, btnU, btnV, btnW, btnX, btnY, btnZ, btnÆ, btnØ, btnÅ;
    private LinearLayout letterBoxView;
    private LinearLayout charPickerView;
    private LinearLayout playAgainView;

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

        return rod;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        //finish();
        //android.os.Process.killProcess(android.os.Process.myPid());
        getActivity().finish();
        return true;
    }

    public void setVisibleView(){
        //playagain.setVisibility(View.VISIBLE);
        playAgainView.setVisibility(View.VISIBLE);

        //charPicker.setVisibility(View.INVISIBLE);
        //guessButton.setVisibility(View.INVISIBLE);
        //guessedWords.setVisibility(View.INVISIBLE);
        gættilbage.setVisibility(View.INVISIBLE);
        normTekst.setVisibility(View.INVISIBLE);

        charPickerView.setVisibility(View.GONE);
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
        if (v == guessButton) {
            if (!MainActivity.game.erSpilletSlut()) {
                MainActivity.game.gætBogstav(alphabet[charPicker.getValue()]);
                wordText.setText(MainActivity.game.getSynligtOrd());
                gættilbage.setText(String.valueOf(7 - MainActivity.game.getAntalForkerteBogstaver()));
                if(!String.valueOf(guessedWords.getText()).contains(" " + alphabet[charPicker.getValue()] + " ")){
                    guessedWords.append(alphabet[charPicker.getValue()] + " ");
                }
                if(!MainActivity.game.erSidsteBogstavKorrekt()) {
                    if (!MainActivity.game.erSpilletSlut()) {
                        galgeImg.setImageResource(wrongImg[MainActivity.game.getAntalForkerteBogstaver() - 1]);
                    } else {
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
                        galgeImg.setImageResource(R.drawable.vundet);
                        wordText.setText("Du har vundet! Ordet var: " + MainActivity.game.getOrdet());
                        setVisibleView();
                        //String nickname = MainActivity.game.readFromFile(getContext());
                        /*if (nickname == "") {
                            FragmentManager fm = getFragmentManager();
                            dialogFragment = new Custom_Dialog_Frag();
                            dialogFragment.show(fm, "Highscore slået");
                        } else {
                            MainActivity.game.opdaterHigscore(String.valueOf(nickname));
                        }*/


                    } else {
                        galgeImg.setImageResource(R.drawable.tabt);
                        wordText.setText("Du har tabt! Ordet var: " + MainActivity.game.getOrdet());
                        setVisibleView();
                    }
            }
        }
        else if(v == playagain){
            playAgainView.setVisibility(View.GONE);
            //playagain.setVisibility(View.GONE);

            charPickerView.setVisibility(View.VISIBLE);
            //charPicker.setVisibility(View.VISIBLE);
            //guessButton.setVisibility(View.VISIBLE);
            //guessedWords.setVisibility(View.VISIBLE);

            gættilbage.setVisibility(View.VISIBLE);
            normTekst.setVisibility(View.VISIBLE);

            restartSpil();

            //spilRefresh();

            //getActivity().finish();
            //android.os.Process.killProcess(android.os.Process.myPid());

        }

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
