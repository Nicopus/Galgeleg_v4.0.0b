package com.attosec.galgeleg_v400b;

import android.content.Context;
import android.util.Log;

import com.attosec.galgeleg_v400b.DAO.BrugerDAO;
import com.attosec.galgeleg_v400b.DAO.OrdlisteDAO;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Random;

public class HangmanLogic {
    private ArrayList<String> muligeOrd = new ArrayList<>();
    private String ordet;
    private ArrayList<String> brugteBogstaver = new ArrayList<String>();
    private String synligtOrd;
    private int antalForkerteBogstaver;
    private boolean sidsteBogstavVarKorrekt;
    private boolean spilletErVundet;
    private boolean spilletErTabt;
    private int score = 0;
    private OrdlisteDAO ordlisteDAO = new OrdlisteDAO();
    private BrugerDAO brugerDAO = new BrugerDAO();
    private ArrayList<String> top30highscores = new ArrayList<>();
    private ArrayList<String> top30nicknames = new ArrayList<>();

    public ArrayList<String> getBrugteBogstaver() {
        return brugteBogstaver;
    }

    public String getSynligtOrd() {
        return synligtOrd;
    }

    public String getOrdet() {
        return ordet;
    }

    public int getAntalForkerteBogstaver() {
        return antalForkerteBogstaver;
    }

    public boolean erSidsteBogstavKorrekt() {
        return sidsteBogstavVarKorrekt;
    }

    public boolean erSpilletVundet() {
        return spilletErVundet;
    }

    public boolean erSpilletTabt() {
        return spilletErTabt;
    }

    public boolean erSpilletSlut() {
        return spilletErTabt || spilletErVundet;
    }

    public int getScore() {
        return score;
    }

    public HangmanLogic() {
        muligeOrd.add("bil");
        muligeOrd.add("computer");
        muligeOrd.add("programmering");
        muligeOrd.add("motorvej");
        muligeOrd.add("busrute");
        muligeOrd.add("gangsti");
        muligeOrd.add("skovsnegl");
        muligeOrd.add("solsort");
        nulstil();
    }

    public void nulstil() {
        brugteBogstaver.clear();
        antalForkerteBogstaver = 0;
        spilletErVundet = false;
        spilletErTabt = false;
        ordet = muligeOrd.get(new Random().nextInt(muligeOrd.size()));
        opdaterSynligtOrd();
        score = 0;
        Log.v("Ordet er", ordet);
    }


    private void opdaterSynligtOrd() {
        synligtOrd = "";
        spilletErVundet = true;
        for (int n = 0; n < ordet.length(); n++) {
            String bogstav = ordet.substring(n, n + 1);
            if (brugteBogstaver.contains(bogstav)) {
                synligtOrd = synligtOrd + bogstav;
            } else {
                synligtOrd = synligtOrd + "*";
                spilletErVundet = false;
            }
        }
    }

    public ArrayList getAllWords(){
        return muligeOrd;
    }

    public void addWord(String word){
        ordlisteDAO.tilføjOrd(word);
    }

    public void removeWord(String word){
        muligeOrd.remove(word);
    }

    public void gætBogstav(String bogstav) {
        if (bogstav.length() != 1) return;
        System.out.println("Der gættes på bogstavet: " + bogstav);
        if (brugteBogstaver.contains(bogstav)) return;
        if (spilletErVundet || spilletErTabt) return;

        brugteBogstaver.add(bogstav);

        if (ordet.contains(bogstav)) {
            sidsteBogstavVarKorrekt = true;
            System.out.println("Bogstavet var korrekt: " + bogstav);
            score += 20;
        } else {
            // Vi gættede på et bogstav der ikke var i ordet.
            sidsteBogstavVarKorrekt = false;
            System.out.println("Bogstavet var IKKE korrekt: " + bogstav);
            antalForkerteBogstaver = antalForkerteBogstaver + 1;
            score -=10;
            if (antalForkerteBogstaver > 6) {
                spilletErTabt = true;
            }
        }
        opdaterSynligtOrd();
    }

    public void logStatus() {
        System.out.println("---------- ");
        System.out.println("- ordet (skult) = " + ordet);
        System.out.println("- synligtOrd = " + synligtOrd);
        System.out.println("- forkerteBogstaver = " + antalForkerteBogstaver);
        System.out.println("- brugeBogstaver = " + brugteBogstaver);
        if (spilletErTabt) System.out.println("- SPILLET ER TABT");
        if (spilletErVundet) System.out.println("- SPILLET ER VUNDET");
        System.out.println("---------- ");
    }

    public void opdaterOrdliste() {
        int antalOrd = ordlisteDAO.getOrdliste().size();
        //muligeOrd.clear();
        for (int i = 0; i < antalOrd; i++) {
            muligeOrd.add(ordlisteDAO.getOrdliste().get(i).getOrd());
        }
        System.out.println("muligeOrd = " + muligeOrd);
        nulstil();
    }

    public int updateHigscore(final String nickname) {
        int tempHighscore = Integer.valueOf(brugerDAO.getHighscore(nickname).getHighScore());
        Log.v("highscore = ", String.valueOf(tempHighscore));
        if (tempHighscore < score) {

            brugerDAO.updateHighscore(nickname, score);
            return score;
        } else {
            return -1;
        }
    }

    public void indsætNyHighscore(String nickname) {
        brugerDAO.updateHighscore(nickname, score);
    }

    public void opdaterTop30() {
        int size = brugerDAO.getTop30scores().size()-1;
        for (int i=size; i>=0; i--) {
            top30nicknames.add(brugerDAO.getTop30scores().get(i).getNickname());
            top30highscores.add(brugerDAO.getTop30scores().get(i).getHighScore());
        }
    }

    public ArrayList<String> getTop30Nicknames() {
        return top30nicknames;

    }


    public ArrayList<String> getTop30Highscores() {
        return top30highscores;
    }

    public String readFromFile(Context context) {
        String nickname = "";
        try {
            InputStream inputStream = context.openFileInput("config.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                nickname = stringBuilder.toString();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return nickname;
    }
    public void writeToFile(Context context, String nickname) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("config.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(nickname);
            outputStreamWriter.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}