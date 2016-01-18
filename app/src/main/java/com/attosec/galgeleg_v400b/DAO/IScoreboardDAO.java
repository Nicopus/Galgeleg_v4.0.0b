package com.attosec.galgeleg_v400b.DAO;

import java.util.ArrayList;

/**
 * Created by nicolaihansen on 18/01/16.
 */
public interface IScoreboardDAO {
    ArrayList<String> getTop30Nicknames();
    ArrayList<String> getTop30Highscores();
}
