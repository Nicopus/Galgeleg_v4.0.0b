package com.attosec.galgeleg_v400b.DAO;

import com.attosec.galgeleg_v400b.DTO.BrugerDTO;
import java.util.ArrayList;

public interface IBrugerDAO {

    String getHighscore();
    String getNickname();
    void updateHighscore(String nickname, int point);

}
