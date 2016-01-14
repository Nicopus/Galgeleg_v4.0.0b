package com.attosec.galgeleg_v400b.DTO;

/**
 * Created by nicolaihansen on 08/01/16.
 */
public class BrugerDTO {
    private String highscore;
    private String nickname;

    public BrugerDTO(String highscore, String nickname) {
        this.highscore = highscore;
        this.nickname = nickname;
    }

    public String getHighScore() {
        return highscore;
    }

    public String getNickname() {
        return nickname;
    }
}

