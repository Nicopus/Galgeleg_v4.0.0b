package com.attosec.galgeleg_v400b.DAO;

import android.util.Log;

import com.attosec.galgeleg_v400b.DTO.OrdlisteDTO;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import java.util.ArrayList;

/**
 * Created by nicolaihansen on 08/01/16.
 */
public class OrdlisteDAO implements IOrdlisteDAO {
    private ArrayList<String> ordliste = new ArrayList<>();
    private Query queryRef;
    private Firebase firebaseRef;
    private ChildEventListener childEventListener;

    public OrdlisteDAO() {
        firebaseRef = new Firebase("https://galgeleg.firebaseio.com/wordlist");
        queryRef = firebaseRef.orderByKey();
        childEventListener = queryRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                String ord = dataSnapshot.getKey();
                if (previousChildName == null) {
                    ordliste.add(ord);
                } else {
                    int previousIndex = ordliste.indexOf(previousChildName);
                    int nextIndex = previousIndex + 1;
                    Log.v("index", String.valueOf(nextIndex));
                    if (nextIndex == ordliste.size()) {
                        ordliste.add(ord);
                    } else {
                        ordliste.add(nextIndex, ord);
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                String ord = dataSnapshot.getKey();
                int index = ordliste.indexOf(ord);
                ordliste.set(index, ord);

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String ord = dataSnapshot.getKey();
                int index = ordliste.indexOf(ord);
                ordliste.remove(index);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }

    @Override
    public void tilføjOrd(String ord) {
        firebaseRef.child(ord).setValue(ord);
    }

    @Override
    public ArrayList<String> getOrdliste() {
        return ordliste;
    }

    @Override
    public void fjernOrd(String ord) {
        firebaseRef.child(ord).removeValue();
    }

}


