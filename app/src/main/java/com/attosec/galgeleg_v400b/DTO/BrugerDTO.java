package com.attosec.galgeleg_v400b.DTO;

/**
 * Created by nicolaihansen on 08/01/16.
 */
public class BrugerDTO {

    private String highScore;
    private String nickname;

    public BrugerDTO(String highScore, String nickname) {
        this.highScore = highScore;
        this.nickname = nickname;
    }

    public String getHighScore() {
        return highScore;
    }

    public String getNickname() {
        return nickname;
    }

}

