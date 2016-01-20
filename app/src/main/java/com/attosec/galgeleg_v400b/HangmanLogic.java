package com.attosec.galgeleg_v400b;

import android.content.Context;
import android.util.Log;
import com.attosec.galgeleg_v400b.DAO.BrugerDAO;
import com.attosec.galgeleg_v400b.DAO.IBrugerDAO;
import com.attosec.galgeleg_v400b.DAO.IOrdlisteDAO;
import com.attosec.galgeleg_v400b.DAO.IScoreboardDAO;
import com.attosec.galgeleg_v400b.DAO.OrdlisteDAO;
import com.attosec.galgeleg_v400b.DAO.ScoreboardDAO;


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
    private ArrayList<String> brugteBogstaver = new ArrayList<>();
    private String ordet;
    private String synligtOrd;
    private String sidsteBogstav;
    private int antalForkerteBogstaver;
    private boolean sidsteBogstavVarKorrekt;
    private boolean spilletErVundet;
    private boolean spilletErTabt;
    public static int score = 0;
    private ArrayList<String> top30highscores = new ArrayList<>();
    private ArrayList<String> top30nicknames = new ArrayList<>();
    private ArrayList<String> alleNicknames = new ArrayList<>();
    private ArrayList<String> alleHighscores = new ArrayList<>();
    private boolean ignorerRegistrering = false;
    private IOrdlisteDAO ordlisteDAO = new OrdlisteDAO();
    private IScoreboardDAO scoreboardDAO = new ScoreboardDAO();
    private IBrugerDAO brugerDAO = new BrugerDAO();

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
                if (sidsteBogstav.equals(bogstav)) {
                    score += 20;
                }
            } else {
                synligtOrd = synligtOrd + "*";
                spilletErVundet = false;
            }
        }
    }

    public ArrayList getMuligeOrd(){
        return muligeOrd;
    }

    public void tilføjOrd(String word){
        ordlisteDAO.tilføjOrd(word);
    }

    public void fjernOrd(int position) {
        muligeOrd.remove(position);
    }

    public void gætBogstav(String bogstav) {
        sidsteBogstav = bogstav;
        if (bogstav.length() != 1) return;
        System.out.println("Der gættes på bogstavet: " + bogstav);
        if (brugteBogstaver.contains(bogstav)) return;
        if (spilletErVundet || spilletErTabt) return;

        brugteBogstaver.add(bogstav);

        if (ordet.contains(bogstav)) {
            sidsteBogstavVarKorrekt = true;
            System.out.println("Bogstavet var korrekt: " + bogstav);
        } else {
            // Vi gættede på et bogstav der ikke var i ordet.
            sidsteBogstavVarKorrekt = false;
            score -=10;
            System.out.println("Bogstavet var IKKE korrekt: " + bogstav);
            antalForkerteBogstaver = antalForkerteBogstaver + 1;
            if (antalForkerteBogstaver > 6) {
                spilletErTabt = true;
            }
        }
        opdaterSynligtOrd();
    }

    public void opdaterOrdliste() {
        muligeOrd.clear();
        muligeOrd = ordlisteDAO.getOrdliste();
        muligeOrd.add("bil");
        muligeOrd.add("computer");
        muligeOrd.add("programmering");
        muligeOrd.add("motorvej");
        muligeOrd.add("busrute");
        muligeOrd.add("gangsti");
        muligeOrd.add("skovsnegl");
        muligeOrd.add("solsort");
        int antalOrd = ordlisteDAO.getOrdliste().size();
        Log.v("muligeord size", String.valueOf(antalOrd));

        nulstil();
    }

    //Kaldes når spil er vundet: opdatere firebase hvis highscore slået og returnere ny highscore,
    //ellers returneres -1 hvilket betyder at highscoren ikke blev slået
    public int opdaterHighscore(String nickname) {
        int tempHighscore = getHighscore(nickname);
        if (tempHighscore < score) {
            brugerDAO.updateHighscore(nickname, score);
            return score;
        } else {
            return -1;
        }
    }

    public int getHighscore(String nickname) {
        int index = alleNicknames.indexOf(nickname);
        return Integer.valueOf(alleHighscores.get(index));
    }

    public boolean checkNicknames(String name) {
        if (alleNicknames.contains(name)) {
            return true;
        } else {
            return false;
        }
    }

    //Kaldes når første gang brugeren indtaster nickname: indsætter ny bruger i firbase med den opnåede score
    public void indsætNyHighscore(String nickname) {
        brugerDAO.updateHighscore(nickname, score);
    }

    public void opdaterAlleBrugere() {
        alleNicknames.clear();
        alleHighscores.clear();
        int size = brugerDAO.getAlleHighscore().size();
        for (int i = 0; i < size; i++) {
            alleNicknames.add(brugerDAO.getAlleNicknames().get(i));
            alleHighscores.add(brugerDAO.getAlleHighscore().get(i));
        }
    }

    public void opdaterScoreboard() {
        top30highscores.clear();
        top30nicknames.clear();
        int size = scoreboardDAO.getTop30Nicknames().size();

        for (int i = size-1 ; i >= 0; i--) {
            top30highscores.add(scoreboardDAO.getTop30Highscores().get(i));
            top30nicknames.add(scoreboardDAO.getTop30Nicknames().get(i));
        }

    }

    public ArrayList<String> getTop30Nicknames() {
        return top30nicknames;
    }

    public ArrayList<String> getTop30Highscores() {
        return top30highscores;
    }

    public void setIgnorerRegistrering(boolean ignorer) {
        ignorerRegistrering = ignorer;
    }

    public boolean getIgnorerRegistrering() {
        return ignorerRegistrering;
    }

    //Læser nickname i en fil i appen
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
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return nickname;
    }

    //Tilføjer nickname til en fil i appen
    public void writeToFile(Context context, String nickname) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("config.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(nickname);
            outputStreamWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}