package com.attosec.galgeleg_v400b;

/**
 * Created by nicolaihansen on 08/01/16.
 */
public interface IBrugerDAO {

    BrugerDTO getHighscore(String nickname);
    void updateHighscore(String nickname, int point);
}
