package com.attosec.galgeleg_v400b;

/**
 * Created by nicolaihansen on 12/01/16.
 */
public class RowItem {

    private String nickname;
    private String highscore;
    private int rank;

    public RowItem(String nickname, String highscore, int rank) {
        this.nickname = nickname;
        this.highscore = highscore;
        this.rank = rank;

    }

    public String getNickname() {
        return nickname;
    }

    public String getHighscore() {
        return highscore;
    }

    public int getRank() {
        return rank;
    }

}
