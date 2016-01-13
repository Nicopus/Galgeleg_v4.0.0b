package com.attosec.galgeleg_v400b.DAO;

import android.util.Log;

import com.attosec.galgeleg_v400b.DTO.BrugerDTO;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

public class BrugerDAO implements IBrugerDAO {

    private Firebase firebaseRef = new Firebase("https://galgeleg.firebaseio.com/users");
    private ArrayList<BrugerDTO> top30scores = new ArrayList<>();
    private String highscore;
    private String nickname;

    @Override
    public BrugerDTO getHighscore(final String nickname) {
        Query queryRef = firebaseRef.orderByKey().equalTo(nickname);

        queryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                highscore = (String)dataSnapshot.child(nickname).getValue();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        return new BrugerDTO(highscore, nickname);
    }

    @Override
    public void updateHighscore(String nickname, int point) {
        firebaseRef.child(nickname).setValue(point);

    }

    @Override
    public ArrayList<BrugerDTO> getTop30scores() {
        //ArrayList<BrugerDTO> top30scores = new ArrayList<>();
        Query queryRef = firebaseRef.orderByValue().limitToFirst(30);
        queryRef.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                nickname = dataSnapshot.getKey();
                highscore = String.valueOf(dataSnapshot.getValue());
                top30scores.add(new BrugerDTO(highscore, nickname));

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    /*String nickname = dataSnapshot.getKey();
                    String highscore = String.valueOf(dataSnapshot.getValue());
                    Log.v("xdwfw", nickname);
                    top30scores.add(new BrugerDTO(highscore, nickname));*/

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
