package com.attosec.galgeleg_v400b.DAO;
import java.util.ArrayList;

public interface IBrugerDAO {

    ArrayList<String> getAlleHighscore();
    ArrayList<String> getAlleNicknames();
    void updateHighscore(String nickname, int score);

}
