package com.attosec.galgeleg_v400b.DAO;

import com.attosec.galgeleg_v400b.DTO.OrdlisteDTO;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nicolaihansen on 08/01/16.
 */
public class OrdlisteDAO implements IOrdlisteDAO {

    private Firebase firebaseRef = new Firebase("https://galgeleg.firebaseio.com/wordlist");
    private List<OrdlisteDTO> ordlisteDTO = new ArrayList<>();

    @Override
    public void tilføjOrd(String ord, String hint) {
        Firebase ref = firebaseRef.child(String.valueOf(ord));
        ref.child("word").setValue(ord);
        ref.child("hint").setValue(hint);
    }

    @Override
    public List<OrdlisteDTO> getOrdliste() {
        firebaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot d : snapshot.getChildren()) {
                    String ord = (String) d.child("word").getValue();
                    String hint = (String) d.child("hint").getValue();
                    ordlisteDTO.add(new OrdlisteDTO(ord, hint));
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        return ordlisteDTO;
    }
}

