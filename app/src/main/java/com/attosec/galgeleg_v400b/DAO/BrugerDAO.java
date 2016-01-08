package com.attosec.galgeleg_v400b.DAO;

/**
 * Created by nicolaihansen on 08/01/16.
 */

import com.attosec.galgeleg_v400b.DTO.BrugerDTO;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;



/**
 * Created by nicolaihansen on 06/01/16.
 */
public class BrugerDAO implements IBrugerDAO {

    private Firebase firebaseRef = new Firebase("https://galgeleg.firebaseio.com/users");
    int highscore;


    public BrugerDAO() {

    }

    @Override
    public BrugerDTO getHighscore(String nickname) {
        Query queryRef = firebaseRef.orderByKey().equalTo(nickname);
        queryRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                highscore = Integer.valueOf(dataSnapshot.child("highscore").getValue().toString());
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }

        });

        return new BrugerDTO(highscore, nickname);
    }

    @Override
    public void updateHighscore(String nickname, int point) {
        Firebase ref = firebaseRef.child(String.valueOf(nickname));
        ref.child("highscore").setValue(point);

    }
}
