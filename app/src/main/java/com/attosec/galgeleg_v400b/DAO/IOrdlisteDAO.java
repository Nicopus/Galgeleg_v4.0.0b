package com.attosec.galgeleg_v400b.DAO;

import com.attosec.galgeleg_v400b.DTO.OrdlisteDTO;

import java.util.List;

/**
 * Created by nicolaihansen on 08/01/16.
 */
public interface IOrdlisteDAO {

    void tilføjOrd (String ord, String hint);
    List<OrdlisteDTO> getOrdliste();
}
