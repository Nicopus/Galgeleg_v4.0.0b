package com.attosec.galgeleg_v400b.DTO;

/**
 * Created by nicolaihansen on 08/01/16.
 */
public class OrdlisteDTO {
    private String ord;
    private String hint;

    public OrdlisteDTO (String ord, String hint) {
        this.ord = ord;
        this.hint = hint;
    }

    public String getOrd() {
        return ord;
    }

    public String getHint() {
        return hint;
    }
}
