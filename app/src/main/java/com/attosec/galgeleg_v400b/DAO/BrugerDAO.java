package com.attosec.galgeleg_v400b.DAO;

import com.attosec.galgeleg_v400b.DTO.BrugerDTO;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;

import java.util.ArrayList;

public class BrugerDAO implements IBrugerDAO {

    private Firebase firebaseRef = new Firebase("https://galgeleg.firebaseio.com/users");
    int highscore;

    @Override
    public BrugerDTO getHighscore(final String nickname) {
        Query queryRef = firebaseRef.orderByChild(nickname).equalTo(nickname);

        queryRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                highscore = Integer.valueOf(dataSnapshot.getValue().toString());
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
        firebaseRef.child(String.valueOf(nickname)).setValue(point);

    }

    @Override
    public ArrayList<BrugerDTO> getTop30scores() {
        final ArrayList<BrugerDTO> top30scores = new ArrayList<>();
        Query queryRef = firebaseRef.orderByValue().limitToLast(30);
        queryRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    String nickname = d.getKey().toString();
                    int highscore = Integer.valueOf(d.getChildren().toString());
                    top30scores.add(new BrugerDTO(highscore, nickname));
                }
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

        return top30scores;
    }
}
