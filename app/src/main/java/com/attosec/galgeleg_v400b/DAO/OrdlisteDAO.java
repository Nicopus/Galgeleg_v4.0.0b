package com.attosec.galgeleg_v400b.DAO;

import android.util.Log;

import com.attosec.galgeleg_v400b.DTO.OrdlisteDTO;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nicolaihansen on 08/01/16.
 */
public class OrdlisteDAO implements IOrdlisteDAO {

    private Firebase firebaseRef = new Firebase("https://galgeleg.firebaseio.com/wordlist");
    private ArrayList<OrdlisteDTO> ordlisteDTO = new ArrayList<>();

    @Override
    public void tilføjOrd(String ord) {
        Firebase ref = firebaseRef.child(String.valueOf(ord));
        ref.child("word").setValue(ord);

    }

    @Override
    public ArrayList<OrdlisteDTO> getOrdliste() {

        firebaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot d : snapshot.getChildren()) {
                    String ord = (String)d.child("word").getValue();
                    ordlisteDTO.add(new OrdlisteDTO(ord));
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        return ordlisteDTO;
    }
}

