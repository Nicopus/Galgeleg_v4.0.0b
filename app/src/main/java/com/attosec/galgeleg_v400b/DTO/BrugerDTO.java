package com.attosec.galgeleg_v400b.DTO;

/**
 * Created by nicolaihansen on 08/01/16.
 */
public class BrugerDTO {

    private int highScore;
    private String nickname;

    public BrugerDTO(int highScore, String nickname) {
        this.highScore = highScore;
        this.nickname = nickname;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighscore(int point) {
        this.highScore = point;
    }

    public String getNickname() {
        return nickname;
    }

}

