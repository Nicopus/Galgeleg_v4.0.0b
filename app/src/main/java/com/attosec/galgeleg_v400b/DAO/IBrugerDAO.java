package com.attosec.galgeleg_v400b.DAO;

import com.attosec.galgeleg_v400b.DTO.BrugerDTO;
import java.util.ArrayList;

public interface IBrugerDAO {

    BrugerDTO getHighscore(String nickname);
    void updateHighscore(String nickname, int point);
    ArrayList<BrugerDTO> getTop30scores();
}
