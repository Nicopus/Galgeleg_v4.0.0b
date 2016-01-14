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
    private ArrayList<String> brugteBogstaver = new ArrayList<>();
    private String ordet;
    private String synligtOrd;
    private int antalForkerteBogstaver;
    private boolean sidsteBogstavVarKorrekt;
    private boolean spilletErVundet;
    private boolean spilletErTabt;
    private int score = 0;
    private ArrayList<String> top30highscores = new ArrayList<>();
    private ArrayList<String> top30nicknames = new ArrayList<>();
    private boolean ignorerRegistrering = false;
    private OrdlisteDAO ordlisteDAO = new OrdlisteDAO();
    private BrugerDAO brugerDAO = new BrugerDAO();

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

    public void opdaterOrdliste() {
        int antalOrd = ordlisteDAO.getOrdliste().size();
        for (int i = 0; i < antalOrd; i++) {
            muligeOrd.add(ordlisteDAO.getOrdliste().get(i).getOrd());
        }
        System.out.println("muligeOrd = " + muligeOrd);
        nulstil();
    }

    //Kaldes når spil er vundet: opdatere firebase hvis highscore slået og returnere ny highscore,
    //ellers returneres -1 hvilket betyder at highscoren ikke blev slået
    public int opdaterHigscore(final String nickname) {
        int tempHighscore = Integer.valueOf(brugerDAO.getHighscore(nickname).getHighScore());
        Log.v("highscore = ", String.valueOf(tempHighscore));
        if (tempHighscore < score) {
            brugerDAO.updateHighscore(nickname, score);
            return score;
        } else {
            return -1;
        }
    }

    //Kaldes når første gang brugeren indtaster nickname: indsætter ny bruger i firbase med den opnåede score
    public void indsætNyHighscore(String nickname) {
        brugerDAO.updateHighscore(nickname, score);
    }

    public void opdaterScoreboard() {
        int size = brugerDAO.getScoreboard().size()-1;
        for (int i=size; i>=0; i--) {
            top30nicknames.add(brugerDAO.getScoreboard().get(i).getNickname());
            top30highscores.add(brugerDAO.getScoreboard().get(i).getHighScore());
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